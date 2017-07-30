package org.qhd.zy.ssh.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.URLDataSource;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.qhd.zy.ssh.dao.IAttachmentDao;
import org.qhd.zy.ssh.dao.IMessageDao;
import org.qhd.zy.ssh.dao.IUserDao;
import org.qhd.zy.ssh.dto.AttachDto;
import org.qhd.zy.ssh.exception.UserException;
import org.qhd.zy.ssh.model.Attachment;
import org.qhd.zy.ssh.model.Message;
import org.qhd.zy.ssh.model.Pager;
import org.qhd.zy.ssh.model.SystemContext;
import org.qhd.zy.ssh.model.User;
import org.qhd.zy.ssh.model.UserEmail;
import org.qhd.zy.ssh.model.UserMessage;
import org.qhd.zy.ssh.util.Document_Message_addAttachUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service("messageService")
public class MessageService implements IMessageService {
	private IAttachmentDao attachmentDao;
	private IMessageDao messageDao;
	private IUserDao userDao;
	private JavaMailSender mailSender;
	private TaskExecutor taskExecutor;
	
	
	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}
	//实现bean中的异步线程id
	@Resource
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	public JavaMailSender getMailSender() {
		return mailSender;
	}
	@Resource
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	public IAttachmentDao getAttachmentDao() {
		return attachmentDao;
	}
	@Resource
	public void setAttachmentDao(IAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}
	public IMessageDao getMessageDao() {
		return messageDao;
	}
	@Resource
	public void setMessageDao(IMessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}
	@Resource
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void add(Message message, Integer[] sUserId,AttachDto atts,int isEmail) throws IOException {
		//1.添加发送端对象和消息
		message.setUser(SystemContext.getLoginUser());
		message.setCreateDate(new Date());
		messageDao.add(message);
		for(User u:userDao.ByUserId(sUserId)){
			UserMessage um=new UserMessage();
			um.setIsRead(0);
			um.setMessage(message);
			um.setUser(u);
			messageDao.addObj(um);
		}
		//因为AttachDto是临时的，当执行异步时就没了附件地址,所以需要得到上传文件的名字,就能找到地址,发邮箱
		String []newNames=Document_Message_addAttachUtil.add(message, null, atts, attachmentDao);
		//需要传另一个线程中
		UserEmail ue=userDao.LoadUserEmail(SystemContext.getLoginUser().getId());
		if(isEmail>0){
		if(ue==null) throw new UserException("您没有绑定邮箱！请先绑定邮箱");	
		//发送邮件肯定是最后发送(同步的一般不用，都用异步,里面的类必须是继承接口ruunable的线程,他会异步当中的run方法)
			taskExecutor.execute(new SendEmailThread(ue, message, sUserId, atts,newNames,SystemContext.getRealPath()));
		}
	}
	private class SendEmailThread implements Runnable{
		private Message message;
		private Integer[]sUserId;
		private AttachDto atts;
		private String[]newNames;
		private String realPath;
		private UserEmail userEmail;
		public SendEmailThread(UserEmail userEmail,Message message, Integer[] sUserId,
				AttachDto atts,String[]newNames,String realPath) {
			super();
			this.message = message;
			this.sUserId = sUserId;
			this.atts = atts;
			this.newNames=newNames;
			this.realPath=realPath;
			this.userEmail=userEmail;
		}

		@Override
		public void run() {
			SendMail(userEmail,message, sUserId, atts,newNames,realPath);
		}
	}
	//得到内容里面得照片地址
	private List<String> getContentUrl(String Content){
		Pattern p=Pattern.compile("<img.*?\\s+src=['\"](.*?)['\"].*?/>");
		Matcher m=p.matcher(Content);
		List<String>cs=new ArrayList<String>();
		while(m.find()){
			cs.add(m.group(1));
		}
		return cs;
	}
	private void SendMail(UserEmail ue,Message message, Integer[] sUserId, AttachDto atts,String[]newNames,String realPath) {
		System.out.println("--------开始发送-----------");
		//获得注入了bean的类
		JavaMailSenderImpl jsm=(JavaMailSenderImpl)mailSender;
		jsm.setHost(ue.getHost());
		jsm.setProtocol(ue.getProtocol());
		jsm.setUsername(ue.getUsername());
		jsm.setPassword(ue.getPassword());
		MimeMessage mm=jsm.createMimeMessage();
		try {
			MimeMessageHelper helper=new MimeMessageHelper(mm, true,"utf-8");
			helper.setSubject(message.getTitle());
			helper.setFrom(ue.getUser().getEmail());
			List<String>es=userDao.getSendEmail(sUserId);
			for(String s:es){
				helper.addTo(s);
			}
			//题目完就附件
			if(atts.isFiles()){
				String upload=atts.getUpload();
				File[] f=atts.getAtts();
				String[] oldNames=atts.getFileName();
			for(int i=0;i<f.length;i++){
				String name=oldNames[i];
				helper.addAttachment(MimeUtility.encodeText(name),new File(upload+"/"+newNames[i]));
			}
			}
			//得到所有图片地址
			List<String>urls=this.getContentUrl(message.getContent());
			String content=message.getContent();
			System.out.println(content+"----------");
			Map<String,String>mappers=new HashMap<String,String>();
			int j=0;
			//把全部图片的地址保存在Map
			for(String s:urls){
				mappers.put("x"+j,s );
				content=content.replace(s,"cid:x"+j);
				j++;
				
			}
			//把替代好得内容存好
			helper.setText(content, true);
			Set<String>keys=mappers.keySet();
			for(String ss:keys){
				String imgUrl=mappers.get(ss);
				//分辨是本地还是网页的图片
				if(imgUrl.startsWith("http")){
					//网络用这种添加
					helper.addInline(ss, new URLDataSource(new URL(imgUrl)));
				}else{
					//得到存在本地的图片地址
					String path=realPath+"/"+imgUrl;
					//本地用这种添加
					helper.addInline(ss, new FileSystemResource(path));
				}
			}
			
			jsm.send(mm);
			System.out.println("--------邮件发送成功--------");
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MailException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void deleteReceive(int msgId) {
		User u=SystemContext.getLoginUser();
		//删除关联对象(找msg.id和user.id)
		String hql="delete  UserMessage um " +
				"where um.user.id=? and um.message.id=?";
		messageDao.executeByHql(hql, new Object[]{u.getId(),msgId});
		//因为只要删除了message里面的附件就相当于删除了(看不见了)
	}

	@Override
	public void deleteSend(int msgId) {
		//先删除所有有关关联对象
		String hql="delete  UserMessage um " +
				"where  um.message.id=?";
		//必须要在删除message消息之前把附件取出来,不然得不到附件
		List<Attachment>atts=this.attachByMsgId(msgId);
		messageDao.executeByHql(hql,msgId);
		//先删除附件,防止消息删除了附件没删除
		String ahql="delete Attachment at where at.message.id=?";
		attachmentDao.executeByHql(ahql, msgId);
		//再删除上传的图片
		String path=SystemContext.getRealPath()+"/upload";
		for(Attachment a:atts){
			String RealPath=path+"/"+a.getNewName();
			File f=new File(RealPath);
			f.delete();
		}
		//再删除Message中的对象
		messageDao.delete(msgId);
	}

	@Override
	public Message load(int msgId) {
		User u=SystemContext.getLoginUser();
		//查看个人信息（登录的用户）
		String hql="select msg from Message msg" +
				" where msg.user.id=? and msg.id=?";
		return (Message)messageDao.queryByHql(hql, new Object[]{u.getId(),msgId});
	}

	@Override
	public Pager<Message> findSendMessage(String con) {
		Pager<Message>ps=new Pager<Message>();
		User u=SystemContext.getLoginUser();
		//全部抓取Message User Department 降序是最新的在上面
		String hql="select msg from Message msg left join fetch " +
				" msg.user u left join fetch u.department where msg.user.id=?";
		if(con==null||"".equals(con.trim())){
			ps=messageDao.find(hql+"order by msg.createDate desc", u.getId());
		}else{
			hql+=" and (msg.title like ? or msg.content like  ?) order by msg.createDate desc";
			ps=messageDao.find(hql, new Object[]{u.getId(),"%"+con+"%","%"+con+"%"});
		}
		return ps;
	}

	@Override
	public Pager<Message> findREceiveMessage(String con, int isRead) {
		
		User u=SystemContext.getLoginUser();
		//全部抓取
		String hql="select um.message from UserMessage um left join fetch " +
				"um.message.user u left join " +
				" fetch u.department where um.user.id=? and um.isRead=?";
		if(con==null||"".equals(con.trim())){
			return messageDao.find(hql+"order by um.message.createDate desc",
					new Object[]{u.getId(),isRead});
		}else{
			//or加括号,不然会把or前面看成整体,or后面一个整体
			hql+=" and (um.message.title like ? or um.message.content like  ?)" +
					" order by um.message.createDate desc";
			return messageDao.find(hql, new Object[]{u.getId(),isRead,"%"+con+"%","%"+con+"%"});
		}
	}
	@Override
	public Message updateIsRead( int msgId, int isRead) {
		User u=SystemContext.getLoginUser();
		String hql="select um.message  from UserMessage um  left join fetch " +
				" um.message.user u left join fetch u.department" +
				" where um.message.id=? and um.isRead=?";
		Message msg=null;
		//判断是否已读
		if(isRead==0){
			String umSql="update UserMessage set isRead=? where message.id=? and user.id=?";
			messageDao.executeByHql(umSql,new Object[]{1,msgId,u.getId()});
			//因为已经更新,所以要用1才能查到这条信息
			msg=(Message)messageDao.queryByHql(hql, new Object[]{msgId,1});
		}else{
		msg=(Message)messageDao.queryByHql(hql, new Object[]{msgId,isRead});
		}
		return msg;
	}
	@Override
	public List<Attachment> attachByMsgId(int MsgId) {
		String hql="select at from Attachment at" +
				"	 where at.message.id=?";
		return attachmentDao.lists(hql, MsgId);
	}
	
	
}

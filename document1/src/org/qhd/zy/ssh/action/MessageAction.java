package org.qhd.zy.ssh.action;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;


import org.qhd.zy.ssh.dto.AttachDto;
import org.qhd.zy.ssh.exception.UserException;
import org.qhd.zy.ssh.model.Message;
import org.qhd.zy.ssh.model.SystemContext;
import org.qhd.zy.ssh.model.User;
import org.qhd.zy.ssh.service.IMessageService;
import org.qhd.zy.ssh.service.IUserSerivce;
import org.qhd.zy.ssh.util.ActionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller("messageAction")
@Scope("prototype")
public class MessageAction extends ActionSupport implements ModelDriven<Message>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IMessageService messageService;
	private IUserSerivce userService;
	private Message msg;
	private String con;
	private int isRead;
	private Integer[]sus;
	private File[]atts;
	private String[] attsFileName;
	private String[] attsContentType;
	private int isEmail;
	
	
	
	public int getIsEmail() {
		return isEmail;
	}
	public void setIsEmail(int isEmail) {
		this.isEmail = isEmail;
	}
	public File[] getAtts() {
		return atts;
	}
	public void setAtts(File[] atts) {
		this.atts = atts;
	}
	public String[] getAttsFileName() {
		return attsFileName;
	}
	public void setAttsFileName(String[] attsFileName) {
		this.attsFileName = attsFileName;
	}
	public String[] getAttsContentType() {
		return attsContentType;
	}
	public void setAttsContentType(String[] attsContentType) {
		this.attsContentType = attsContentType;
	}
	public Integer[] getSus() {
		return sus;
	}
	public void setSus(Integer[] sus) {
		this.sus = sus;
	}
	public IUserSerivce getUserService() {
		return userService;
	}
	@Resource
	public void setUserService(IUserSerivce userService) {
		this.userService = userService;
	}
	public int getIsRead() {
		return isRead;
	}
	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
	public String getCon() {
		return con;
	}
	public void setCon(String con) {
		this.con = con;
	}
	public IMessageService getMessageService() {
		return messageService;
	}
	@Resource
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	@Override
	public Message getModel() {
		if(msg==null)msg=new Message();
		return msg;
	}
	public String listReceive(){
		ActionContext.getContext().put("mr",messageService.findREceiveMessage(con, isRead));
		return SUCCESS;
	}
	public String listSend(){
		ActionContext.getContext().put("ms",messageService.findSendMessage(con));
		return SUCCESS;
	}
	public String showSend(){
		Message m=messageService.load(msg.getId());
		BeanUtils.copyProperties(m, msg);
		ActionContext.getContext().put("us",userService.ByUMMid(msg.getId()));
		ActionContext.getContext().put("atts",messageService.attachByMsgId(msg.getId()));
		return SUCCESS;
	}
	public String showReceive(){
		ActionContext.getContext().put("msg",messageService.updateIsRead(msg.getId(), isRead));
		ActionContext.getContext().put("atts",messageService.attachByMsgId(msg.getId()));
		return SUCCESS;
	}
	public String deleteReceive(){
		messageService.deleteReceive(msg.getId());
		ActionContext.getContext().put("url","/message_listReceive.action");
		return ActionUtil.REDIRECT;
	}
	public String deleteSend(){
		if(SystemContext.getLoginUser().getId()!=userService.ByMsgId(msg.getId()).getId())
			throw new UserException("你没有权限删除该消息");
		messageService.deleteSend(msg.getId());
		ActionContext.getContext().put("url","/message_listSend.action");
		return ActionUtil.REDIRECT;
	}
	public String addInput(){
		User u=SystemContext.getLoginUser();
		ActionContext.getContext().put("us",userService.ByUserFindAllUser(u.getId()));
		return SUCCESS;
	}
	public String add() throws IOException{
		if(atts==null||atts.length<=0){
		messageService.add(msg,sus,new AttachDto(false),isEmail);
		}else{
			//正常应该用下面的方法得到相对路径，但是STS它用的是自己建立的虚拟路径,所以只能用绝对路径
			/*String upload=ServletActionContext.getServletContext().getRealPath("upload");*/
			String upload="D:\\JAVA\\document\\document1\\WebContent\\upload";
			messageService.add(msg, sus, new AttachDto(atts, attsContentType, attsFileName, upload),isEmail);
		}
		ActionContext.getContext().put("url","/message_listSend");
		return ActionUtil.REDIRECT;
	}
	public void validateAdd(){
		if(sus==null||sus.length<=0){
			this.addFieldError("sus","请选择相应的用户!");
		}
		if(ActionUtil.isEmpty(msg.getTitle())){
			this.addFieldError("title","标题不能为空!");
		}
		if(this.hasErrors()){
			this.addInput();
		}
	}
}

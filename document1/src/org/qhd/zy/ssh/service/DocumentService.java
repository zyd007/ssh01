package org.qhd.zy.ssh.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.qhd.zy.ssh.dao.IAttachmentDao;
import org.qhd.zy.ssh.dao.IDepartmentDao;
import org.qhd.zy.ssh.dao.IDocumentDao;
import org.qhd.zy.ssh.dao.IUserDao;
import org.qhd.zy.ssh.dto.AttachDto;
import org.qhd.zy.ssh.model.Attachment;
import org.qhd.zy.ssh.model.DepDocument;
import org.qhd.zy.ssh.model.Department;
import org.qhd.zy.ssh.model.Document;
import org.qhd.zy.ssh.model.Pager;
import org.qhd.zy.ssh.model.SystemContext;
import org.qhd.zy.ssh.model.User;
import org.qhd.zy.ssh.model.UserReadDocument;
import org.qhd.zy.ssh.util.Document_Message_addAttachUtil;
import org.springframework.stereotype.Service;
@Service("documentService")
public class DocumentService implements IDocumentService{
	private IUserDao userDao;
	private IDocumentDao documentDao;
	private IAttachmentDao attachmentDao;
	private IDepartmentDao departmentDao;
	
	
	public IDepartmentDao getDepartmentDao() {
		return departmentDao;
	}
	@Resource
	public void setDepartmentDao(IDepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
	public IUserDao getUserDao() {
		return userDao;
	}
	@Resource
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public IDocumentDao getDocumentDao() {
		return documentDao;
	}
	@Resource
	public void setDocumentDao(IDocumentDao documentDao) {
		this.documentDao = documentDao;
	}

	public IAttachmentDao getAttachmentDao() {
		return attachmentDao;
	}
	@Resource
	public void setAttachmentDao(IAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	@Override
	public void add(Document document, Integer[] sDepId, AttachDto at) throws IOException {
		document.setUser(SystemContext.getLoginUser());
		document.setCreateDate(new Date());
		documentDao.add(document);
		//添加要发给那些部门
		for(Department p:departmentDao.ByDepID(sDepId)){
			DepDocument dd=new DepDocument();
			dd.setDepartment(p);
			dd.setDocument(document);
			documentDao.addObj(dd);
		}
		//写一个公共的Util满足在公文或信息添加附件
		Document_Message_addAttachUtil.add(null, document, at, attachmentDao);
	}

	@Override
	public void delete(int id) {
		//删除的时候delete(中间没值) from
		//1.删除用户与公文的关系
		String hql="delete from UserReadDocument urd where urd.document.id=?";
		documentDao.executeByHql(hql, id);
		//2.删除部门与公文的关系
		hql=" delete  from DepDocument dd where dd.document.id=?";
		documentDao.executeByHql(hql, id);
		//3.删除附件
		List<Attachment>atts=new ArrayList<Attachment>();
		atts=this.listAttachmentByDocument(id);
		hql="delete  from Attachment am where am.document.id=?";
		documentDao.executeByHql(hql, id);
		//4.删除公文
		documentDao.delete(id);
		//5.删除上传公文附件
		for(Attachment at:atts){
			File f=new File(SystemContext.getRealPath()+"/upload/"+at.getNewName());
			f.delete();
		}
	}
	
	private boolean checkDocIsRead(int userId,int docId) {
		String hql = "select count(*) from UserReadDocument urd where urd.user.id=?" +
				" and urd.document.id=?";
		Long count = (Long)documentDao.queryByHql(hql, new Object[]{userId,docId});
		if(count==null||count==0) return false;
		return true;
	}
	@Override
	public Document updateRead(int id, Integer isRead) {
		User u=SystemContext.getLoginUser();
		Document dm=this.load(id);
		if(isRead==null||isRead==0){
			//在UserReadDocument中检查是否已读
			if(!checkDocIsRead(u.getId(), id)) {
			UserReadDocument urd=new UserReadDocument();
			urd.setUser(u);
			urd.setDocument(dm);
			documentDao.addObj(urd);
			}
		}
		return dm;
	}

	@Override
	public Pager<Document> findSendDocument(String con) {
		User u=SystemContext.getLoginUser();
		//在document中找user.id(一个id号代表发送了这条信息)
		String hql="select new Document(dm.id,dm.title,dm.content,dm.createDate)" +
				" from Document dm where dm.user.id=?";
		if(con!=null&&!"".equals(con)){
			hql+=" and (dm.title like ? or dm.content like ?) order by dm.createDate desc";
			return documentDao.find(hql,
					new Object[]{u.getId(),"%"+con+"%","%"+con+"%"});
		}
		hql+=" order by dm.createDate desc";
		return documentDao.find(hql, u.getId());
	}

	
	@Override
	public Pager<Document> findReadDocument(String con, Integer depId) {
		User u=SystemContext.getLoginUser();
		//在UserReadDocument中找有user的id确定读了那些
		String hql="select urd.document from UserReadDocument urd " +
				" left join fetch urd.document.user u left join fetch u.department" +
				" where urd.user.id=? ";
		if(con!=null&&!"".equals(con)){
			hql+=" and (urd.document.title like '%"+
		con+"%' or urd.document.content like '%"+con+"%')";
		}
		if(depId!=null&&depId>0) {
			hql+=" and urd.user.department.id="+depId;
		}
		hql+=" order by urd.document.createDate desc";
		return documentDao.find(hql, u.getId());
	}

	@Override
	public Pager<Document> findNotReadDocument(String con, Integer depId) {
		User u = SystemContext.getLoginUser();
		//取用户没读的所有公文(不包括没有发给用户部门的)
		String hql = "select doc from  Document doc left join " +
				"fetch doc.user u left join fetch u.department dep where " +
				"doc.id not in (select urd.document.id from UserReadDocument urd " +
				" where urd.user.id=? )" ;
		//如果给出了部门就只在发给用户部门中没读过的公文
		if(depId!=null&&depId>0){
			hql+="and doc.id  in (select dd.document.id from DepDocument dd" +
					" where dd.department.id="+depId+") " ;
		}
		//如果有查询条件就加上
		if(con!=null&&!"".equals(con)) {
			hql+=" and (doc.title like '%"+con+"%' or doc.content like '%"+con+"%')";
		}
		return documentDao.find(hql,new Object[]{u.getId()});
	}

	@Override
	public List<Attachment> listAttachmentByDocument(int docId) {
		String hql="select am from Attachment am where am.document.id=?";
		return attachmentDao.lists(hql, docId);
	}
	@Override
	public Document load(int id) {
		return documentDao.load(id);
	}

}

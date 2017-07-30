package org.qhd.zy.ssh.action;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.qhd.zy.ssh.dto.AttachDto;
import org.qhd.zy.ssh.exception.UserException;
import org.qhd.zy.ssh.model.Document;

import org.qhd.zy.ssh.model.SystemContext;
import org.qhd.zy.ssh.model.User;
import org.qhd.zy.ssh.service.IDepartmentService;
import org.qhd.zy.ssh.service.IDocumentService;
import org.qhd.zy.ssh.service.IUserSerivce;
import org.qhd.zy.ssh.util.ActionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller("documentAction")
@Scope("prototype")
public class DocumentAction extends ActionSupport implements ModelDriven<Document>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Document doc;
	private IDocumentService documentService;
	private Integer isRead;
	private String con;
	private IUserSerivce userSrvice;
	private IDepartmentService departmentService;
	//一组部门id
	private Integer[]ds;
	private File[]atts;
	private String []attsFileName;
	private String [] attsContentType;
	
	
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
	public Integer[] getDs() {
		return ds;
	}
	public void setDs(Integer[] ds) {
		this.ds = ds;
	}
	public IDepartmentService getDepartmentService() {
		return departmentService;
	}
	@Resource
	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	public IUserSerivce getUserSrvice() {
		return userSrvice;
	}
	@Resource
	public void setUserSrvice(IUserSerivce userSrvice) {
		this.userSrvice = userSrvice;
	}
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public String getCon() {
		return con;
	}
	public void setCon(String con) {
		this.con = con;
	}
	public IDocumentService getDocumentService() {
		return documentService;
	}
	@Resource
	public void setDocumentService(IDocumentService documentService) {
		this.documentService = documentService;
	}


	public Document getModel() {
		if(doc==null)doc=new Document();
		return doc;
	}
	public String listReceive(){
		User u=(User)ActionContext.getContext().getSession().get("loginUser");
		if(isRead==null||isRead==0){
			ActionContext.getContext().put("ds",documentService.findNotReadDocument(con, u.getDepartment().getId()));
		}else{
			ActionContext.getContext().put("ds",documentService.findReadDocument(con, u.getDepartment().getId()));
		}
		return SUCCESS;
	}
	public String listSend(){
		ActionContext.getContext().put("ss",documentService.findSendDocument(con));
		return SUCCESS;
	}
	public String addInput(){
		User u=SystemContext.getLoginUser();
		ActionContext.getContext().put("deps",departmentService.ByUserId(u.getId()));
		return SUCCESS;
	}
	public String add() throws IOException{
		if(atts==null||atts.length<=0){
			documentService.add(doc,ds,new AttachDto(false));
			}else{
				//正常应该用下面的方法得到相对路径，但是STS它用的是自己建立的虚拟路径,所以只能用绝对路径
				/*String upload=ServletActionContext.getServletContext().getRealPath("upload");*/
				String upload="D:\\JAVA\\document\\document1\\WebContent\\upload";
				documentService.add(doc, ds, new AttachDto(atts, attsContentType, attsFileName, upload));
			}
		ActionContext.getContext().put("url","/document_listSend.do");
		return ActionUtil.REDIRECT;
	}
	public void validateAdd(){
		if(ds==null||ds.length<=0){
			this.addFieldError("ds","请选择相应的部门!");
		}
		if(ActionUtil.isEmpty(doc.getTitle())){
			this.addFieldError("title","标题不能为空!");
		}
		if(this.hasErrors()){
			this.addInput();
		}
	}
	public String showSend(){
		Document d=documentService.load(doc.getId());
		BeanUtils.copyProperties(d, doc);
		//document发送的部门
		ActionContext.getContext().put("deps",departmentService.ByDocId(d.getId()));
		ActionContext.getContext().put("atts",documentService.listAttachmentByDocument(d.getId()));
		return SUCCESS;
	}
	public String showReceive(){
		ActionContext.getContext().put("doc",documentService.updateRead(doc.getId(), isRead));
		ActionContext.getContext().put("atts",documentService.listAttachmentByDocument(doc.getId()));
		return SUCCESS;
	}
	public String deleteSend(){
		if(!SystemContext.getLoginUser().equals(userSrvice.ByDocId(doc.getId())))
			throw new UserException("你没有权限删除该公文");
		documentService.delete(doc.getId());
		ActionContext.getContext().put("url","/document_listSend.action");
		return ActionUtil.REDIRECT;
	}
}

package org.qhd.zy.ssh.action;

import javax.annotation.Resource;

import org.qhd.zy.ssh.exception.UserException;
import org.qhd.zy.ssh.model.SystemContext;
import org.qhd.zy.ssh.model.User;
import org.qhd.zy.ssh.model.UserEmail;
import org.qhd.zy.ssh.service.IDepartmentService;
import org.qhd.zy.ssh.service.IUserSerivce;
import org.qhd.zy.ssh.util.ActionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
@Controller("userAction")
@Scope("prototype")
public class UserAction extends ActionSupport implements ModelDriven<User> {
	private User user;
	private int depId;
	private UserEmail ue;

	private IUserSerivce UserService;
	private IDepartmentService departmentService;
	
	public UserEmail getUe() {
		return ue;
	}
	public void setUe(UserEmail ue) {
		this.ue = ue;
	}
	public int getDepId() {
		return depId;
	}
	public void setDepId(int depId) {
		this.depId = depId;
	}
	@Resource
	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	@Resource
	public void setUserService(IUserSerivce userService) {
		UserService = userService;
	}
	public String list(){
		ActionContext.getContext().put("ds", departmentService.lists());
		ActionContext.getContext().put("us",UserService.list(depId));
		return SUCCESS;
	}
	public String addInput(){
		ActionContext.getContext().put("ds", departmentService.lists());
		return SUCCESS;
	}
	public String add(){
		UserService.add(user, depId);
		ActionContext.getContext().put("url","/user_list.action");
		return ActionUtil.REDIRECT;
	}
	public void validateAdd(){
		if(ActionUtil.isEmpty(user.getUsername())){
			this.addFieldError("username", "用户名不能为空!");
		}
		if(ActionUtil.isEmpty(user.getPassword())){
			this.addFieldError("password","密码不能为空!");
		}
		if(!user.getEmail().matches("^\\w+(\\.\\w)*@\\w+((.\\w{2,3}){1,3})$")){
			this.addFieldError("email","邮箱格式不对!");
		}
		if(this.hasErrors()){
			this.addInput();
		}
	}
	public String addEmailInput(){
		User u=(User)ActionContext.getContext().getSession().get("loginUser");
		UserEmail ues=UserService.LoadUserEmail(u.getId());
		if(ues!=null)
			throw new UserException("你已经绑定过邮箱!");
		return SUCCESS;
	}
	public String addEmail(){
		User u=(User)ActionContext.getContext().getSession().get("loginUser");
		UserService.addEmail(ue,u.getId());
		ActionContext.getContext().put("url", "/user_showEmail.action");
		return ActionUtil.REDIRECT;
	}
	public String showEmail(){
		ue=UserService.LoadUserEmail(user.getId());
		return SUCCESS;
	}
	public String updateEmailInput(){
		//当没用modeldriven的对象就必须使用ue.XXX取
		ue=UserService.LoadUserEmail(user.getId());
		return SUCCESS;
	}
	public String updateEmail(){
		UserEmail ueu=UserService.LoadUserEmail(user.getId());
		ueu.setHost(ue.getHost());
		ueu.setProtocol(ue.getProtocol());
		ueu.setUsername(ue.getUsername());
		ueu.setPassword(ue.getPassword());
		UserService.updateEmail(ueu);
		ActionContext.getContext().put("url", "/user_showEmail.action");
		return ActionUtil.REDIRECT;
	}
	
	public String updateInput(){
		User u=UserService.load(user.getId());
		BeanUtils.copyProperties(u, user);
		ActionContext.getContext().put("ds", departmentService.lists());
		return SUCCESS;
	}
	public String update(){
		User u=UserService.load(user.getId());
		BeanUtils.copyProperties(user, u);
		UserService.update(u, depId);
		ActionContext.getContext().put("url","/user_list.action");
		return ActionUtil.REDIRECT;
	}
	public void validateUpdate(){
		
		if(ActionUtil.isEmpty(user.getPassword())){
			this.addFieldError("password","密码不能为空!");
		}
		if(!user.getEmail().matches("^\\w+(\\.\\w)*@\\w+((.\\w{2,3}){1,3})$")){
			this.addFieldError("email","邮箱格式不对!");
		}
		if(this.hasErrors()){
			this.updateInput();
		}
	}
	public String show(){
		User u=UserService.load(user.getId());
		BeanUtils.copyProperties(u, user);
		return SUCCESS;
	}
	public String delete(){
		UserService.delete(user.getId());
		ActionContext.getContext().put("url","/user_list.do");
		return ActionUtil.REDIRECT;
	}
	
	public String updateSelfInput(){
		User u=UserService.load(user.getId());
		BeanUtils.copyProperties(u, user);
		return SUCCESS;
	}
	public String updateSelf(){
		User u=UserService.load(user.getId());
		u.setPassword(user.getPassword());
		u.setNickname(user.getNickname());
		u.setEmail(user.getEmail());
		UserService.updateSelf(u);
		ActionContext.getContext().put("url","/user_showSelf.action");
		return ActionUtil.REDIRECT;
	}
	public void validateUpdateSelf(){
		if(ActionUtil.isEmpty(user.getPassword())){
			this.addFieldError("password","密码不能为空!");
		}
		if(!user.getEmail().matches("^\\w+(\\.\\w)*@\\w+((.\\w{2,3}){1,3})$")){
			this.addFieldError("email","邮箱格式不对!");
		}
		if(this.hasErrors()){
			this.updateSelfInput();
		}
	}
	public String showSelf(){
		User u=UserService.load(SystemContext.getLoginUser().getId());
		BeanUtils.copyProperties(u, user);
		return SUCCESS;
	}
	public String status(){
		User u=UserService.load(user.getId());
		if(u.getType()==1)throw new UserException("不能改变管理员的状态");
		if(u.getStatus()==0){
			u.setStatus(1);
			UserService.update(u, u.getDepartment().getId());
		}else{
			u.setStatus(0);
			UserService.update(u, u.getDepartment().getId());
		}
		ActionContext.getContext().put("url","/user_list.do");
		return ActionUtil.REDIRECT;
	}
	@Override
	public User getModel() {
		if(user==null)user=new User();
		return user;
	}
	
}

package org.qhd.zy.ssh.action;

import javax.annotation.Resource;

import org.qhd.zy.ssh.model.User;
import org.qhd.zy.ssh.service.IUserSerivce;
import org.qhd.zy.ssh.util.ActionUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;

@Component("loginAction")
@Scope("prototype")
public class LoginAction {
	private String username;
	private String password;
	private IUserSerivce userService;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public IUserSerivce getUserService() {
		return userService;
	}
	@Resource
	public void setUserService(IUserSerivce userService) {
		this.userService = userService;
	}
	public String loginInput(){
		return "loginInput";
	}
	public String login(){
		User u=userService.loginUser(username, password);
		ActionContext.getContext().getSession().put("loginUser",u);
		ActionContext.getContext().put("url","/user_showSelf.do?id="+u.getId());
		return ActionUtil.REDIRECT;
	}
	public String logout(){
		ActionContext.getContext().getSession().clear();
		return "loginInput";
	}
}

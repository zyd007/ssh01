package org.qhd.zy.ssh.filter;

import org.qhd.zy.ssh.exception.DepartmentException;
import org.qhd.zy.ssh.exception.UserException;
import org.qhd.zy.ssh.model.User;
import org.qhd.zy.ssh.util.ActionUtil;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@SuppressWarnings("serial")
@Component("authInterceptor")
public class AuthInterceptor extends AbstractInterceptor {
	
	
	public String intercept(ActionInvocation invocation) throws Exception {
		//获得方法名字
		String name=invocation.getProxy().getActionName();
			User u=(User)ActionContext.getContext().getSession().get("loginUser");
			//返回值和ActionXXX中返回值效果一样
			if(u==null) return "loginInput";
			if(u.getDepartment().getStatus()==1){
				ActionContext.getContext().getSession().clear();
				ActionContext.getContext().put("message","你所在的部门已停用!");
				return "exception";
			}
			if(u.getStatus()==1){
				ActionContext.getContext().getSession().clear();
				ActionContext.getContext().put("message","用户已被停用!");
				return "exception";
			}
			if(u.getType()!=1){
				if(!ActionUtil.checkAuth(name)){
					ActionContext.getContext().getSession().put("message","权限不够!");
				return "exception";
				}
			}
		return invocation.invoke();
	}

}

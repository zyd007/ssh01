package org.qhd.zy.ssh.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.qhd.zy.ssh.exception.DepartmentException;
import org.qhd.zy.ssh.model.Department;
import org.qhd.zy.ssh.model.User;
import org.qhd.zy.ssh.service.IDepartmentService;
import org.qhd.zy.ssh.util.ActionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
@Controller("departmentAction")
@Scope("prototype")
public class DepartmentAction extends ActionSupport implements ModelDriven<Department>{
	private Department dep;
	private IDepartmentService departmentService;
	//用来保存上传的setDepScopeInput.jsp
	private int[]sDeps;
	
	public int[] getsDeps() {
		return sDeps;
	}
	public void setsDeps(int[] sDeps) {
		this.sDeps = sDeps;
	}
	public IDepartmentService getDepartmentService() {
		return departmentService;
	}
	@Resource
	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	public String addInput(){
		return SUCCESS;
	}
	public String add(){
		Department p=new Department();
		p.setName(dep.getName());
		departmentService.add(p);
		ActionContext.getContext().put("url","/department_list");
		return ActionUtil.REDIRECT;
	}
	public void validateAdd(){
		if(ActionUtil.isEmpty(dep.getName())){
			this.addFieldError("name","部门必须要名字");
		}
	}
	public String updateInput(){
		Department p=departmentService.load(dep.getId());
		dep.setName(p.getName());
		return SUCCESS;
	}
	public String update(){
		Department p=departmentService.load(dep.getId());
		p.setName(dep.getName());
		departmentService.update(p);
		ActionContext.getContext().put("url","/department_list");
		return ActionUtil.REDIRECT;
	}
	public void validateUpdate(){
		if(ActionUtil.isEmpty(dep.getName())){
			this.addFieldError("name","部门必须要名字");
		}
		if(this.hasErrors()){
			this.updateInput();
		}
	}
	public String delete(){
		departmentService.delete(dep.getId());
		ActionContext.getContext().put("url","/department_list");
		return ActionUtil.REDIRECT;
	}
	//排除自己的部门以外的所有部门
	private List<Department> getOutSelf(){
		List<Department>ls=departmentService.lists();
		List<Department>ss=new ArrayList<Department>();
		for(Department dp:ls){
			if(!(dp.getId()==dep.getId())){
				ss.add(dp);
			}
		}
		return ss;
	}
	public String setDepScopeInput(){
		Department p=departmentService.load(dep.getId());
		BeanUtils.copyProperties(p, dep);
		/*//Department类中定义了id的hashCode和equal方法
		//,如果id相等就删除提高效率
		List<Department>ls=departmentService.lists();
		ls.remove(dep);*/
		ActionContext.getContext().put("sDeps",departmentService.listDepScopeDepId(dep.getId()));
		ActionContext.getContext().put("ds",this.getOutSelf());
		return SUCCESS;
	}
	public String setDepScope(){
		departmentService.addDepScopes(dep.getId(), sDeps);
		ActionContext.getContext().put("url","department_show.action?id="+dep.getId());
		return ActionUtil.REDIRECT;
	}
	public String show(){
		Department p=departmentService.load(dep.getId());
		BeanUtils.copyProperties(p, dep);
		ActionContext.getContext().put("ds",departmentService.listDepScope(dep.getId()));
		return SUCCESS;
	}
	public String list(){
		ActionContext.getContext().put("ds", departmentService.lists());
		return SUCCESS;
	}
	public String status(){
		Department d=departmentService.load(dep.getId());
		if(((User)ActionContext.getContext().getSession().get("loginUser"))
				.getDepartment().getId()==d.getId())throw new DepartmentException("不能停用自己的部门");
		if(d.getStatus()==0){
			d.setStatus(1);
			departmentService.update(d);
		}else{
			d.setStatus(0);
			departmentService.update(d);
		}
		ActionContext.getContext().put("url","/department_list.do");
		return ActionUtil.REDIRECT;
	}
	@Override
	public Department getModel() {
		if(dep==null)dep=new Department();
		return dep;
	}

}

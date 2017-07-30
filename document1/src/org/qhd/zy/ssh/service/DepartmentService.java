package org.qhd.zy.ssh.service;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Resource;

import org.qhd.zy.ssh.dao.IDepartmentDao;

import org.qhd.zy.ssh.model.DepScope;
import org.qhd.zy.ssh.model.Department;
import org.springframework.stereotype.Service;
@Service("departmentService")
public class DepartmentService implements IDepartmentService {
	private IDepartmentDao departmentDao;
	
	public IDepartmentDao getDepartmentDao() {
		return departmentDao;
	}
	@Resource
	public void setDepartmentDao(IDepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	@Override
	public void add(Department department) {
		departmentDao.add(department);
	}

	@Override
	public void delete(int id) {
		//TODO 1.部门中是否有用户（以后再写）(删除部门太麻烦，还是停用好)
		/**
		 * 2.是否有关联部门；（无论在发送部门还是接受）
		 * 在DepScope中一个对象是一一对应的关系
		 */
		String hql="delete from DepScope des where des.dep=? or des.department.id=?";
		departmentDao.executeByHql(hql, new Object[]{id,id});
		departmentDao.delete(id);
	}

	@Override
	public void update(Department department) {
		departmentDao.update(department);
	}

	@Override
	public Department load(int id) {
		return departmentDao.load(id);
	}

	@Override
	public List<Department> lists() {
		String hql="from Department";
		return departmentDao.lists(hql);
	}

	@Override
	public void addDepScope(int depId, int sDepId) {
		String hql="select ds from DepScope ds " +
				" where ds.dep=? and ds.department.id=?";
		DepScope ds=(DepScope)departmentDao.queryByHql(hql, new Object[]{depId,sDepId});
		//如果这个一对一映射有了就不用再添加
		if(ds!=null) return;
		ds=new DepScope();
		ds.setDep(depId);
		ds.setDepartment(departmentDao.load(sDepId));
		//只要在数据库的类都能通用添加
		departmentDao.addObj(ds);
	}
	/**
	 * 把发送的id变成数组
	 * @param sDepId
	 * @return
	 */
	private List<Integer> getArrays(int []sDepId){
		List<Integer>ls=new ArrayList<Integer>();
		for(int id:sDepId){
			ls.add(id);
		}
		return ls;
	}
	@Override
	public void addDepScopes(int depId, int[] sDepId){
		//需要设置发送的id
		List<Integer>sDepIds=this.getArrays(sDepId);
		List<Integer>ds=new ArrayList<Integer>();
		//得到所有存在id;
		List<Integer>fs=departmentDao.getExsitAllId(depId);
		for(int id:fs){
			//把要删除的id添加到一个数组中
			if(!sDepIds.contains(id)){
				ds.add(id);
			}
		}
		//遍历删除id
		for(int id:ds){
			this.deleteDepScope(depId, id);
		}
		//添加新的id,排除重复id的方法在this.addDepScope()中判断了
		for(int id:sDepId){
			this.addDepScope(depId, id);
		}
	}

	@Override
	public void deleteDepScope(int depId, int sDepId) {
		String hql="delete DepScope des where des.dep=? and des.department.id=?";
		departmentDao.executeByHql(hql, new Object[]{depId,sDepId});
	}

	@Override
	public void deleteDepScopes(int depId) {
		String hql="delete DepScope des where des.dep=?";
		departmentDao.executeByHql(hql,depId);
	}

	@Override
	public List<Department> listDepScope(int depId) {
		String hql="select dem from DepScope des left join" +
				" des.department dem where des.dep=?";
		List<Department>ls=departmentDao.lists(hql,depId);
		return ls;
	}
	@Override
	public List<Integer> listDepScopeDepId(int depId) {
		return departmentDao.getExsitAllId(depId);
	}
	@Override
	public List<Department> ByUserId(int userId) {
		return departmentDao.ByUserId(userId);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Department> ByDocId(int docId) {
		String hql="select dd.department from DepDocument dd where dd.document.id=?";
		return (List)departmentDao.listByObj(hql, docId);
	}
	

}

package org.qhd.zy.ssh.service;

import java.util.List;



import org.qhd.zy.ssh.model.Department;




public interface IDepartmentService {
	public void add(Department department);
	public void delete(int id);
	public void update(Department department);
	public Department load(int id);
	public List<Department>lists();
	//TODO
	/**
	 * 为一个部门增加一个可发送的部门
	 * @param dep
	 * @param sDep
	 */
	public void addDepScope(int depId,int sDepId);
	//TODO
	/**
	 * 为一个部门增加一组可发送的部门
	 * @param dep
	 * @param sDep
	 */
	public void addDepScopes(int depId,int[]sDepId);
	//TODO
	/**
	 * 为一个部门删除一个可发送的部门
	 * @param dep
	 * @param sDep
	 */
	public void deleteDepScope(int depId,int sDepId);
	//TODO
	/**
	 * 为一个部门删除所有可发送的部门
	 * @param dep
	 * @param sDep
	 */
	public void deleteDepScopes(int depId);
	/**
	 * 某个部门能发送的所有其他部门
	 * @param dep
	 * @return
	 */
	public List<Department> listDepScope(int depId);
	/**
	 * 某个部门能发送的所有其他部门id
	 * @param depId
	 * @return
	 */
	public List<Integer> listDepScopeDepId(int depId);
	/**
	 * 通过用户id寻找能发送的部门(这种用sql更方便些)
	 * @param userId
	 * @return
	 */
	public List<Department> ByUserId(int userId);
	/**
	 * 通过docId找到发送部门
	 * @param docId
	 * @return
	 */
	public List<Department> ByDocId(int docId);
	
}

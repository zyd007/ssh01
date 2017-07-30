package org.qhd.zy.ssh.dao;

import java.util.List;

import org.qhd.zy.ssh.model.Department;


public interface IDepartmentDao extends IBaseDao<Department> {
	/**
	 * 得到某个部门可发送部门的id
	 * @param depId
	 * @return
	 */
	public List<Integer> getExsitAllId(int depId);
	/**
	 * 通过id找Department对象
	 * @param sDepId
	 * @return
	 */
	public List<Department> ByDepID(Integer[]sDepId);
	/**
	 * 通过用户id寻找能发送的部门(这种用sql更方便些)
	 * @param userId
	 * @return
	 */
	public List<Department> ByUserId(int userId);
	
}

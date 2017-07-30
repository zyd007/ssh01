package org.qhd.zy.ssh.dao;



import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.qhd.zy.ssh.model.Department;

import org.springframework.stereotype.Repository;
@Repository("departmentDao")
public class DepartmentDao extends BaseDao<Department> implements
		IDepartmentDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getExsitAllId(int depId) {
		/**
		 * 得到某个部门能可发送部门的id
		 * @param depId
		 * @return
		 */
		String hql="select des.department.id" +
				" from DepScope des where des.dep=?";
		return this.getSession().createQuery(hql)
				.setParameter(0,depId)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> ByDepID(Integer[] sDepId) {
	
		String hql="select new Department(d.id,d.name) from Department d where d.id in(:did)";
		Query q=this.getSession().createQuery(hql)
				.setParameterList("did",sDepId );
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> ByUserId(int userId) {
		String sql="select t3.* from s_user t1 left join " +
				" s_depscope t2 on(t1.dep_id=t2.dep) " +
				"left join s_dep t3 on(t2.dep_id=t3.id)" +
				" where t1.id=?";
		return this.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(Department.class))
				.setParameter(0,userId).list();
	}

}

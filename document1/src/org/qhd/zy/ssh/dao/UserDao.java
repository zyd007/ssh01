package org.qhd.zy.ssh.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.qhd.zy.ssh.model.User;
import org.qhd.zy.ssh.model.UserEmail;
import org.springframework.stereotype.Repository;
@SuppressWarnings("unchecked")
@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao {

	
	@Override
	public List<User> ByUserId(Integer[] sUserId) {
		/*//抓取后少发sql,但是这里只需要id,关联对象不好
		String hql="select u from User u left join fetch u.department where u.id in(:uid)";
	*/	String hql="select new User(u.id) from User u  where u.id in(:uid)";
		Query q=this.getSession().createQuery(hql)
				.setParameterList("uid",sUserId );
		return q.list();
	}

	@Override
	public List<User> ByUMMid(int Ummid) {
		String hql="select	um.user from UserMessage um " +
				" left join fetch um.user.department where um.message.id=?";
		return this.getSession().createQuery(hql).setParameter(0,Ummid).list();
	}

	@Override
	public List<User> ByUserFindAllUser(int uid) {
		String sql="select t3.id,t3.nickname from s_user t1 " +
				" left  join s_depscope t2  on(t1.dep_id=t2.dep) " +
				"right join s_user t3 on(t2.dep_id=t3.dep_id)  where t1.id=?";
		//这样就不被托管,不需要加入的dep_id
		Query q=this.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(User.class)).setParameter(0, uid);
		return q.list();
	}
	public UserEmail LoadUserEmail(int id) {
		String hql="select ue from UserEmail ue where ue.user.id=?";
		return (UserEmail)this.queryByHql(hql, id);
	}

	@Override
	public List<String> getSendEmail(Integer[] sUserId) {
		String hql="select u.email from User u where u.id in (:cid) and u.email is not null";
		List<String>ss=(List<String>)this.getSession().createQuery(hql).setParameterList("cid",sUserId).list();
		return ss;
	}
	
}

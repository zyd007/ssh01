package org.qhd.zy.ssh.service;

import java.util.List;

import javax.annotation.Resource;

import org.qhd.zy.ssh.dao.IDepartmentDao;
import org.qhd.zy.ssh.dao.IUserDao;
import org.qhd.zy.ssh.exception.UserException;
import org.qhd.zy.ssh.model.Department;
import org.qhd.zy.ssh.model.Pager;
import org.qhd.zy.ssh.model.SystemContext;
import org.qhd.zy.ssh.model.User;
import org.qhd.zy.ssh.model.UserEmail;
import org.springframework.stereotype.Service;
@Service("userService")
public class UserService implements IUserSerivce {
	private IUserDao userDao;
	private IDepartmentDao departmentDao;
	
	public IDepartmentDao getDepartmentDao() {
		return departmentDao;
	}
	@Resource
	public void setDepartmentDao(IDepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
	public IUserDao getUserDao() {
		return userDao;
	}
	@Resource
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	//得到所有username
	private List<Object> getAllUser(){
		String hql="select username from User";
		return userDao.listByObj(hql);
	}
	@Override
	public void add(User user, int DepId) {
		if(this.getAllUser().contains(user.getUsername()))
			throw new UserException("该用户存在,不能添加!");
		Department p=departmentDao.load(DepId);
		user.setDepartment(p);
		userDao.add(user);
	}
	

	@Override
	public void delete(int id) {
		//TODO 删除所有相关的(停用比较好)
		userDao.delete(id);
	}

	@Override
	public void update(User user, int DepId) {
		Department p=departmentDao.load(DepId);
		user.setDepartment(p);
		userDao.update(user);
	}

	@Override
	public User load(int id) {
		return userDao.load(id);
	}

	@Override
	public Pager<User> list(Integer DepId) {
		Pager<User>ps=null;
		//Integer默认值为null
		if(DepId==null||DepId<=0){
			String hql="from User u left join fetch u.department";
			ps=userDao.find(hql);
		}else{
			String hql="from User u left join fetch u.department dep where dep.id=?";
			ps=userDao.find(hql, DepId);
		}
		return ps;
	}
	public User ByUsername(String username){
		String hql="select u from User u where u.username=?";
		return (User)userDao.queryByHql(hql, username);
	}
	public User loginUser(String username,String password){
		User u=this.ByUsername(username);
		if(u==null)throw new UserException("用户名不存在");
		if(!u.getPassword().equals(password))throw new UserException("密码不正确");
		return u;
	}
	@Override
	public void updateSelf(User user) {
		userDao.update(user);
	}
	//通过UserMessage中messageId找出发送了那些人(在那个部门)
	@Override
	public List<User> ByUMMid(int Ummid) {
		return userDao.ByUMMid(Ummid);
		}
	/**
	 * 通过用户id找出能发送的所有user
	 * @param uid
	 * @return
	 */
	public List<User> ByUserFindAllUser(int uid) {
		return userDao.ByUserFindAllUser(uid);
	}
	@Override
	public User ByDocId(int docId) {
		User u=SystemContext.getLoginUser();
		String hql="select d.user from Document d where d.user.id=? and d.id=?";
		return (User)userDao.queryByHql(hql, new Object[]{u.getId(),docId});
	}
	@Override
	public User ByMsgId(int MsgId) {
		User u=SystemContext.getLoginUser();
		String hql="select msg.user from Message msg where msg.user.id=? and msg.id=?";
		return (User)userDao.queryByHql(hql, new Object[]{u.getId(),MsgId});
	}
	@Override
	public void addEmail(UserEmail ue, int userId) {
		ue.setUser(this.load(userId));
		userDao.addObj(ue);
	}
	@Override
	public UserEmail LoadUserEmail(int id) {
		return userDao.LoadUserEmail(id);
	}
	@Override
	public void updateEmail(UserEmail ue) {
		userDao.updateObj(ue);
	}
	@Override
	public List<String> getSendEmail(Integer[] sUserId) {
		return userDao.getSendEmail(sUserId);
	}

}

package org.qhd.zy.ssh.dao;

import java.util.List;

import org.qhd.zy.ssh.model.User;
import org.qhd.zy.ssh.model.UserEmail;

public interface IUserDao extends IBaseDao<User> {
	/**
	 * 通过id找user对象
	 * @param sUserId
	 * @return
	 */
	public List<User> ByUserId(Integer[]sUserId);
	/**
	 * 通过UserMessage中messageId找出发送了那些人(在那个部门)
	 * @param Ummid mId
	 * @return
	 */
	public List<User> ByUMMid(int Ummid);
	/**
	 * 通过用户id找出能发送的所有user
	 * @param uid
	 * @return
	 */
	public List<User> ByUserFindAllUser(int uid);
	//通过userid得到绑定的邮件信息
	public UserEmail LoadUserEmail(int id);
	/**
	 * 通过一组id得到其email
	 * @param sUserId
	 * @return
	 */
	public List<String> getSendEmail(Integer[]sUserId);
}

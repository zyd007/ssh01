package org.qhd.zy.ssh.service;

import java.util.List;

import org.qhd.zy.ssh.model.Pager;
import org.qhd.zy.ssh.model.User;
import org.qhd.zy.ssh.model.UserEmail;


public interface IUserSerivce {
	public void add(User user,int DepId);
	/**
	 * 绑定邮箱
	 * @param ue
	 * @param userId
	 */
	public void addEmail(UserEmail ue,int userId);
	public void delete(int id);
	public void update(User user,int DepId);
	/**
	 * 更新肯定不能改变绑定用户
	 * @param ue
	 */
	public void updateEmail(UserEmail ue);
	public void updateSelf(User user);
	public User load(int id);
	/**
	 * 通过userid得到绑定的邮件信息
	 * @param id
	 * @return
	 */
	public UserEmail LoadUserEmail(int id);
	/**
	 * 如果没有DepId查看全部用户
	 * @param DepId
	 * @return
	 */
	public Pager<User> list(Integer DepId);
	public User ByUsername(String username);
	public User loginUser(String username,String password);
	//通过UserMessage中messageId找出发送了那些人(在那个部门)
	public List<User> ByUMMid(int Ummid);
	/**
	 * 通过用户id找出能发送的所有user
	 * @param uid
	 * @return
	 */
	public List<User> ByUserFindAllUser(int uid);
	/**
	 * 通过公文的id得到发文者
	 * @param docId
	 * @return
	 */
	public User ByDocId(int docId);
	/**
	 * 通过消息的id得到发消息的人
	 * @param MsgId
	 * @return
	 */
	public User ByMsgId(int MsgId);
	/**
	 * 通过一组id得到其email
	 * @param sUserId
	 * @return
	 */
	public List<String> getSendEmail(Integer[]sUserId);
}

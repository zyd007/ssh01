package org.qhd.zy.ssh.service;

import java.io.IOException;
import java.util.List;

import org.qhd.zy.ssh.dto.AttachDto;
import org.qhd.zy.ssh.model.Attachment;
import org.qhd.zy.ssh.model.Message;
import org.qhd.zy.ssh.model.Pager;

public interface IMessageService {
	/**
	 * 发送给那些用户
	 * @param message 发送的信息(其对象中有那个用户发送的表式)
	 * @param sUserId(接收端的对象id)
	 * @param atts(附件列表)
	 * @throws IOException 
	 */
	public void add(Message message,Integer[]sUserId,AttachDto atts,int isEmail) throws IOException;
	/**
	 * 删除UserMassage中的关联对象
	 * @param 对象id
	 */
	public void deleteReceive(int msgId);
	/**
	 * 先删除关联对象,再删除Message中的对象
	 * @param msgId
	 */
	public void deleteSend(int msgId);
	/**
	 * 加载自己的信息
	 * @param msgId
	 * @return
	 */
	public Message load(int msgId);
	/**
	 * 查询发送的信息
	 * @param con 条件
	 * @return
	 */
	public Pager<Message>findSendMessage(String con);
	/**
	 * 查询接收的信息
	 * @param con 条件
	 * @param isRead 是否已读0未读,1表示已读
	 * @return
	 */
	public Pager<Message>findREceiveMessage(String con,int isRead);
	/**
	 * 只要查询接收的文件,就把isRead=1，表示已读
	 * @param msgId 消息id
	 * @param isRead 是否已读
	 * @return
	 */
	public Message updateIsRead(int msgId,int isRead);
	/**
	 * 通过msgId得到附件
	 * @param MsgId
	 * @return
	 */
	public List<Attachment> attachByMsgId(int MsgId);

}

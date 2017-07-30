package org.qhd.zy.ssh.dao;



import org.qhd.zy.ssh.model.Message;
import org.springframework.stereotype.Repository;

@Repository("messageDao")
public class MessageDao extends BaseDao<Message> implements IMessageDao {

}

package org.qhd.zy.ssh.service;

import java.io.IOException;
import java.util.List;

import org.qhd.zy.ssh.dto.AttachDto;
import org.qhd.zy.ssh.model.Attachment;
import org.qhd.zy.ssh.model.Document;
import org.qhd.zy.ssh.model.Pager;

public interface IDocumentService {
	public void add(Document document,Integer[]sDepId,AttachDto at) throws IOException ;
	//只有发送者才能删除公文管理
	public void delete(int id);
	public Document load(int id);
	public Document updateRead(int id,Integer isRead);
	/**
	 * 获取某个用户发的所有公文(可以根据条件查)
	 * @param con
	 * @return
	 */
	public Pager<Document> findSendDocument(String con);
	/**
	 * 获取某个部门(部门中用户已读)已读的公文
	 * @param con
	 * @param depId
	 * @return
	 */
	public Pager<Document> findReadDocument(String con,Integer depId);
	/**
	 * 获取某个部门(未读)未读的公文
	 * @param con
	 * @param depId
	 * @return
	 */
	public Pager<Document> findNotReadDocument(String con,Integer depId);
	/**
	 * 获取某个公文的附件信息
	 * @param docId
	 * @return
	 */
	public List<Attachment> listAttachmentByDocument(int docId);
	
}

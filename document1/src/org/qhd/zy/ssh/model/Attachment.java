package org.qhd.zy.ssh.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="s_attachment")
public class Attachment {
	private int id;
	private String newName;
	private String oldName;
	private String contentType;
	private Date createDate;
	private String type;
	private long size;
	private Document document;
	private Message message;
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 附件的新名称，这个名称是不会重复的，可以通过uuid或者时间的毫秒数获取
	 * @return
	 */
	@Column(name="new_name")
	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
	}
	/**
	 * 附件的原有名称
	 * @return
	 */
	@Column(name="old_name")
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	/**
	 * 附件的文件类型
	 * @return
	 */
	@Column(name="content_type")
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	/**
	 * 附件的创建时间
	 * @return
	 */
	@Column(name="attach_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 附件的后缀名
	 * @return
	 */
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 附件的大小，以字节为单位
	 * @return
	 */
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	@ManyToOne
	@JoinColumn(name="doc_id")
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	@ManyToOne
	@JoinColumn(name="msg_id")
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public Attachment(String newName, String oldName, Message message) {
		super();
		this.newName = newName;
		this.oldName = oldName;
		this.message = message;
	}
	public Attachment() {
		super();
	}
	
}

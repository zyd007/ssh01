package org.qhd.zy.ssh.dto;

import java.io.File;

public class AttachDto {
	private File[]atts;
	private String[]contentType;
	private String[]fileName;
	private boolean isFiles;
	private String upload;
	public boolean isFiles() {
		return isFiles;
	}
	public void setFiles(boolean isFiles) {
		this.isFiles = isFiles;
	}
	public File[] getAtts() {
		return atts;
	}
	public void setAtts(File[] atts) {
		this.atts = atts;
	}
	public String[] getContentType() {
		return contentType;
	}
	public void setContentType(String[] contentType) {
		this.contentType = contentType;
	}
	public String[] getFileName() {
		return fileName;
	}
	public void setFileName(String[] fileName) {
		this.fileName = fileName;
	}
	
	public String getUpload() {
		return upload;
	}
	public void setUpload(String upload) {
		this.upload = upload;
	}
	public AttachDto(File[] atts, String[] contentType, String[] fileName,String upload) {
		this.atts = atts;
		this.contentType = contentType;
		this.fileName = fileName;
		this.upload=upload;
		this.isFiles=true;
	}
	public AttachDto() {
	}
	public AttachDto(boolean isFiles) {
		
		this.isFiles = isFiles;
	}
	
}

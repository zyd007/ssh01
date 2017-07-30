package org.qhd.zy.ssh.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.qhd.zy.ssh.dao.IAttachmentDao;
import org.qhd.zy.ssh.dto.AttachDto;
import org.qhd.zy.ssh.model.Attachment;
import org.qhd.zy.ssh.model.Document;
import org.qhd.zy.ssh.model.Message;

public class Document_Message_addAttachUtil{
	
	public static String[] add(Message msg,Document doc,AttachDto atts,IAttachmentDao attachmentDao) throws IOException{
		//添加附件进数据库
				if(atts.isFiles()){
					File[]ats=atts.getAtts();
					String[]contentTypes=atts.getContentType();
					String[]FileNames=atts.getFileName();
					//得到新名字的长度
					String[]newNames=new String[ats.length];
					for(int i=0;i<ats.length;i++){
						Attachment at=new Attachment();
						at.setContentType(contentTypes[i]);
						at.setOldName(FileNames[i]);
						if(doc!=null)
						at.setDocument(doc);
						if(msg!=null)
						at.setMessage(msg);
						at.setCreateDate(new Date());
						at.setSize(ats[i].length());
						//得到扩展名
						at.setType(FilenameUtils.getExtension(FileNames[i]));
						//通过秒数唯一的到新名字存在upload中
						String Newname=getNewName(FileNames[i]);
						at.setNewName(Newname);
						//存新名字
						newNames[i]=Newname;
						attachmentDao.add(at);
					}
					//上传附件
					//得到上传路径
					String upload=atts.getUpload();
					for(int i=0;i<ats.length;i++){
						File f=ats[i];
						String newName=newNames[i];
						//这里得重新用个String
						String ss=upload+"/"+newName;
						FileUtils.copyFile(f, new File(ss));
					}
					return newNames;
				}
				return null;
	}
	//得到一个通过秒数得到的唯一新名字
		private static String getNewName(String oldName){
			String name=new Long(new Date().getTime()).toString();
			String newName=name+"."+FilenameUtils.getExtension(oldName);
			return newName;
		}
}

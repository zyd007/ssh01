package org.qhd.zy.ssh.test;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.qhd.zy.ssh.dto.AttachDto;
import org.qhd.zy.ssh.model.Document;
import org.qhd.zy.ssh.model.SystemContext;
import org.qhd.zy.ssh.model.User;
import org.qhd.zy.ssh.service.IDocumentService;
import org.qhd.zy.ssh.service.IUserSerivce;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class testDocument {
	@Resource
	private IDocumentService documentService;
	@Resource
	private IUserSerivce userService;
	@Before
	public void init(){
		User u=userService.load(2);
		SystemContext.setLoginUser(u);
	}
	@Test
	public void add(){
		try{
		Integer[] ds=new Integer[]{2,3,4,5,6};
		Document doc=new Document();
		doc.setTitle("听妈妈的话");
		doc.setContent("七里香");
		documentService.add(doc, ds, new AttachDto(false));
		}catch(IOException i){
			i.printStackTrace();
		}
	}
	@Test
	public void Read(){
		User u=userService.load(3);
		SystemContext.setLoginUser(u);
		Document d=documentService.updateRead(2, 0);
		System.out.println(d.getContent());
	}
	@Test
	public void SendDocument(){
		SystemContext.setOffset(0);
		SystemContext.setPageSize(10);
		
		List<Document>us=documentService.findSendDocument("帅").getDatas();
		for(Document du:us){
			System.out.println(du.getContent());
		}
	}
	@Test
	public void ReadDocument(){
		SystemContext.setOffset(0);
		SystemContext.setPageSize(10);
		User u=userService.load(3);
		SystemContext.setLoginUser(u);
		List<Document>us=documentService.findReadDocument("",5).getDatas();
		for(Document d:us){
			System.out.println(d.getContent());
		}
	}
	@Test
	public void NotReadDocument(){
		SystemContext.setOffset(0);
		SystemContext.setPageSize(10);
		User u=userService.load(2);
		SystemContext.setLoginUser(u);
		List<Document>us=documentService.findNotReadDocument("我",0).getDatas();
		for(Document d:us){
			System.out.println(d.getTitle()+","+d.getCreateDate());
		}
	}
	@Test
	public void delete(){
		documentService.delete(4);
	}
}

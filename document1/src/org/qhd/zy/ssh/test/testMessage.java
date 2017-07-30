package org.qhd.zy.ssh.test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.qhd.zy.ssh.dto.AttachDto;
import org.qhd.zy.ssh.model.Attachment;
import org.qhd.zy.ssh.model.Message;
import org.qhd.zy.ssh.model.Pager;
import org.qhd.zy.ssh.model.SystemContext;
import org.qhd.zy.ssh.service.IMessageService;
import org.qhd.zy.ssh.service.IUserSerivce;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class testMessage {
	@Resource
	private IMessageService messageService;
	@Resource
	private IUserSerivce userService;
	
	@Test
	public void testAddUM(){
		Message m=new Message();
		m.setContent("嘻嘻,我帅");
		m.setTitle("哈哈，我来了");
		SystemContext.setLoginUser(userService.load(2));
		try {
			messageService.add(m, new Integer[]{5445,7,8,6,},new AttachDto(false),0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testDeleteReceive(){
		SystemContext.setLoginUser(userService.load(3));
		messageService.deleteReceive(1);
	}
	@Test
	public void testDeleteSend(){
		messageService.deleteSend(1);
	}
	@Test
	public void testMessageSelf(){
		SystemContext.setLoginUser(userService.load(2));
		System.out.println(messageService.load(4));
	}
	
	@Test
	public void testSendMessage(){
		SystemContext.setOffset(0);
		SystemContext.setPageSize(15);
		SystemContext.setLoginUser(userService.load(2));
		Pager<Message>pager=messageService.findSendMessage(null);
		for(Message m:pager.getDatas()){
			System.out.println(m);
		}
	}
	
	@Test
	public void testRecevieMessage(){
		SystemContext.setOffset(0);
		SystemContext.setPageSize(15);
		SystemContext.setLoginUser(userService.load(2));
		Pager<Message>pager=messageService.findREceiveMessage("张益",0);
		for(Message m:pager.getDatas()){
			System.out.println(m);
		}
	}
	@Test
	public void getNewName(){
		String name=new Long(new Date().getTime()).toString();
		String newName=name+"."+FilenameUtils.getExtension("sdgd.text");
		System.out.println(newName);
	}
	@Test
	public void getAttachBymsgId(){
		List<Attachment>ls=messageService.attachByMsgId(33);
		for(Attachment s:ls){
			System.out.println(s.getNewName()+","+s.getOldName()+","+s.getMessage().getId());
		}
	}
}

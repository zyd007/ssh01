package org.qhd.zy.ssh.test;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class testMail {
	//注入bean
	@Resource(name="mailSender")
	private JavaMailSender mailSender; 
	@Test
	public void test01(){
		try {
			//创建信息
			MimeMessage mm=mailSender.createMimeMessage();
			//封装好了的整个multipart
			MimeMessageHelper helper=new MimeMessageHelper(mm,true,"utf-8");
			//发邮件的用户名
			helper.setFrom("zy_120775@sina.com");
			//发个那些用户
			helper.setTo(new InternetAddress[]{new InternetAddress("zy_120775@sohu.com")
			,new InternetAddress("1207757535@qq.com")});
			//标题
			helper.setSubject("通过spring发送的邮件");
			//增加邮件发送(如果用中文名就得改编码,但是后缀必须和源文件一样)
			helper.addAttachment(MimeUtility.encodeText("周杰伦.jpg")
					,new FileSystemResource(new File("C:/Users/Administrator/Desktop/img/jay.jpg")));
			//引入图片别名cid
			helper.setText("<h1 style='color:red;font-size:15px'>spring邮箱来啦</h1><img src='cid:zzz'/>", true);
			FileSystemResource fsr=new FileSystemResource("C:/Users/Administrator/Desktop/img/火箭.jpg");
			//在邮件内容中添加图片
			helper.addInline("zzz",fsr);
			//发送信息
			mailSender.send(mm);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MailException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
	@Test
	public void testAscII(){
		char a='A';
		int b=65;
		if(a==b){
			System.out.println(a==b);
		}
	}
	@Test
	public void testRegex(){
		String url="dsfsdfsdf <img sdf src='gfgdgdg.sadsaf' sdtgsd/>" +
				"sdggdst <img dsf src=\"gfhfgh.hjjasda\" fsdf=\"\" dsgsg/>";
		//所有*后面加个?是非贪婪模式
		Pattern p=Pattern.compile("<img.*?\\s+src=['\"](.*?)['\"].*?/>");
		Matcher m=p.matcher(url);
		while(m.find()){
			System.out.println(m.group(1));
		}
		
	}
}

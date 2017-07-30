package org.qhd.zy.ssh.test;


import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.qhd.zy.ssh.dao.IUserDao;
import org.qhd.zy.ssh.model.User;
import org.qhd.zy.ssh.service.IUserSerivce;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class testUser {
	private Random ran = new Random();
	
	@Resource
	private IUserSerivce userService;
	@Resource
	private IUserDao userDao;
	@Test
	public void initUser() {
		int[] depIds = {1,2,3,4,5,6,7,8};
		for(int i=0;i<100;i++) {
			User u = new User();
			u.setEmail("user"+i+"@mail.com");
			u.setNickname(getName());
			u.setUsername("user"+i);
			u.setPassword("123");
			u.setType(0);
			userService.add(u, depIds[ran.nextInt(depIds.length)]);
		}
	}
	private String getName() {
		String[] name1 = new String[]{"孔","张","叶","李","叶入","孔令",
				"张立","陈","刘","牛","夏侯","林","令狐","赵","薛","穆","倪",
				"詹","称","程","何","王","刘","金","冬","吴","周"};
		
		String[] name2 = new String[]{"凡","何","颖","之","益","力",
				"浩","皓","西","东","北","南","冲","杰","逸","量","妮",
				"敏","捷","杰","坚","名","生","华","鸣","德","春","虎","刚","诚"};
		
		String[] name3 = new String[]{"吞","明","敦","刀","备","伟",
				"唯","楚","勇","诠","佺","河","正","震","点","贝","侠",
				"伟","大","凡","琴","讯","玲","宏","谦","伦"};
		
		boolean two = ran.nextInt(50)>=45?false:true;
		if(two) {
			String n1 = name1[ran.nextInt(name1.length)];
			String n2;
			int n = ran.nextInt(10);
			if(n>5) {
				n2 = name2[ran.nextInt(name2.length)];
			} else {
				n2 = name3[ran.nextInt(name3.length)];
			}
			return n1+n2;
		} else {
			String n1 = name1[ran.nextInt(name1.length)];
			String n2 = name2[ran.nextInt(name2.length)];
			String n3 = name3[ran.nextInt(name3.length)];
			return n1+n2+n3;
		}
	}
	@Test
	public void testUserById(){
		List<User>ls=userDao.ByUserId(new Integer[]{2,3,4});
		for(User u:ls){
			System.out.println(u.getId());
		}
	}
	
	@Test
	public void testByUMMid(){
		List<User>list=userService.ByUMMid(2);
		for(User u:list){
			System.out.println(u.getDepartment().getName());
		}
	}
	
	@Test
	public void testUserByfindAllUser(){
		List<User>ls=userDao.ByUserFindAllUser(7);
		for(User u:ls){
			System.out.println(u.getId()+","+u.getNickname());
		}
	}
	@Test
	public void testEmail(){
		List<String>es=userDao.getSendEmail(new Integer[]{1,2,3,4,5});
		for(String s:es){
			System.out.println(s);
		}
	}
}

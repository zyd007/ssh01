package org.qhd.zy.ssh.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.qhd.zy.ssh.model.User;

public class tset01 {
	@Test
	public void test01(){
		//+是一个以上
		System.out.println("".matches("s+"));
		//^为开头 @前面有0个以上.和w @后面的.有2个到3个字母 有1组到三组
		System.out.println("sdf.ds.dfsd@mail.com.cn.cn"
				.matches("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$"));
	}
	@Test
	public void test02(){
		String con="";
		if(con!=null&&!"".equals(con)){
			System.out.println("dsfs");
		}else{
			System.out.println("1111");
		}
	}
	@Test
	public void testNotList(){
		List<User>us=new ArrayList<>();
		for(User u:us){
			System.out.println(u);
		}
	}
}

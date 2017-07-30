package org.qhd.zy.ssh.util;

import java.util.Properties;

public class ActionUtil {
	public static String REDIRECT="redirect";
	public static boolean isEmpty(String name){
		if(name==null||"".equals(name.trim()))
			return true;
		return false;
	}
	private static String[] getAllUser(){
		Properties p=AuthUtil.getAllAuth();
		return p.getProperty("user").split(",");
	}
	private static String[] getAllAdmin(){
		Properties p=AuthUtil.getAllAuth();
		return p.getProperty("admin").split(",");
	}
	public static boolean checkAuth(String name){
		for(String st:getAllUser()){
			if(name.startsWith(st))return true;
		}
		for(String st:getAllAdmin()){
			if(name.startsWith(st))return false;
		}
		return true;
	}
}

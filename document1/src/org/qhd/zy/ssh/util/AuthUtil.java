package org.qhd.zy.ssh.util;

import java.io.IOException;
import java.util.Properties;


public class AuthUtil {
	private static Properties properties;
	//单列
	private AuthUtil(){}
	public static Properties getAllAuth(){
		if(properties==null){
			properties=new Properties();
			try {
				//加载auth.properties文件
				properties.load(AuthUtil.class.getClassLoader().getResourceAsStream("auth.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return properties;
	}
}

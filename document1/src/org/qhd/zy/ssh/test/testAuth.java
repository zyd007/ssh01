package org.qhd.zy.ssh.test;

import java.util.Properties;

import org.junit.Test;
import org.qhd.zy.ssh.util.AuthUtil;

public class testAuth {

	@Test
	public void getAuth(){
		Properties p=AuthUtil.getAllAuth();
		System.out.println(p.getProperty("user").split(",")[0]);
	}
}

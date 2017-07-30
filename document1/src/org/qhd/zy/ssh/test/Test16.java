package org.qhd.zy.ssh.test;


import org.junit.Test;

public class Test16 {
	
	@Test
	public void test16_2(){
		        String hexString = "a30f";  
		        System.out.println(hexString2binaryString(hexString));  
	}
	
	 private static String hexString2binaryString(String hexString)  
	    {  
	        if (hexString == null)  
	            return "0000";  
	        String bString = ""; 
	        String tmp="";
	        for (int i = 0; i < hexString.length(); i++)  
	        {  
	            tmp = "0000"  
	                    + Integer.toBinaryString(Integer.parseInt(hexString  
	                            .substring(i, i + 1), 16));  
	            bString += tmp.substring(tmp.length() - 4);  
	        }  
	        return bString;  
	    }  
	  
	
}

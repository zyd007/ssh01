package org.qhd.zy.ssh.model;

public class SystemContext {
	private static ThreadLocal<Integer>offest=new ThreadLocal<Integer>();
	private static ThreadLocal<Integer>pagerSize=new ThreadLocal<Integer>();
	private static ThreadLocal<User>loginUser=new ThreadLocal<User>();
	private static ThreadLocal<String>realPath=new ThreadLocal<String>();
	public static void setRealPath(String _realPath){
		realPath.set(_realPath);
	}
	public static String getRealPath(){
		return realPath.get();
	}
	public static void removeRealPath(){
		realPath.remove();
	}
	public static void setLoginUser(User user){
		loginUser.set(user);
	}
	public static User getLoginUser(){
		return loginUser.get();
	}
	public static void removeLoginUser(){
		loginUser.remove();
	}
	public static void setOffset(int _offest){
		offest.set(_offest);
	}
	public static int getOffest(){
		return offest.get();
	}
	public static void removeOffest(){
		offest.remove();
	}
	public static void setPageSize(int _pageSize){
		pagerSize.set(_pageSize);
	}
	public static int getPagerSize(){
		return pagerSize.get();
	}
	public static void removePagerSize(){
		pagerSize.remove();
	}
	
}

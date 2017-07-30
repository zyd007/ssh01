<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加用户</title>
<link rel="stylesheet" href="<%=request.
getContextPath()%>/css/main.css" type="text/css"/>
</head>
<body>
<jsp:include page="nvc.jsp"/>
<s:form action="user_add" method="post">
	<s:textfield label="用户名" name="username"/>
	<s:password label="密码" name="password"/>
	<s:textfield label="用户昵称" name="nickname"/>
	<s:textfield label="邮箱" name="email"/>
	<s:radio list="#{'0':'普通用户','1':'管理员' }" listKey="key" 
	listValue="value" name="type" label="选择用户类型"/>
	<s:select list="#ds" listKey="id" listValue="name" name="depId" label="选择部门"/>
	<s:submit value="添加"/>
</s:form>
</body>
</html>
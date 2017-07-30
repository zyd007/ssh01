<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>绑定邮箱用户:[${loginUser.nickname }]</title>
<link rel="stylesheet" href="<%=request.
getContextPath()%>/css/main.css" type="text/css"/>
</head>
<body>
<jsp:include page="nvc.jsp"/>
<s:form action="user_addEmail" method="post">
	<s:textfield label="主机" name="ue.host"/>
	<s:textfield label="传输协议" name="ue.protocol"/>
	<s:textfield label="用户名" name="ue.username"/>
	<s:password label="用户密码" name="ue.password"/>
	<s:submit value="添加邮箱"/>
</s:form>
</body>
</html>
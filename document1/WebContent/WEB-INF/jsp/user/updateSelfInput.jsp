<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>更新用户</title>
<link rel="stylesheet" href="<%=request.
getContextPath()%>/css/main.css" type="text/css"/>
</head>
<body>
<jsp:include page="nvc.jsp"/>
<s:form action="user_updateSelf" method="post">
<s:hidden name="id" value="%{id}"/>
	<s:password label="密码" name="password" showPassword="true" disabled="false"/>
	<s:textfield label="用户昵称" name="nickname"/>
	<s:textfield label="邮箱" name="email"/>
	<s:submit value="更新"/>
</s:form>
</body>
</html>
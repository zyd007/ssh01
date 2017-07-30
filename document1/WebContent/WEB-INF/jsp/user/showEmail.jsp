<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户邮箱信息</title>
<link rel="stylesheet" href="<%=request.
getContextPath()%>/css/main.css" type="text/css"/>
</head>
<body>
<jsp:include page="nvc.jsp"/>
<table align="center" cellpadding="0"
 cellspacing="0" border="0" width="600" class="ct">
 <tr>
 <td>主机</td><td>${ue.host }</td>
 </tr>
 <tr>
 <td>传输协议</td><td>${ue.protocol }</td>
 </tr>
 <tr>
 <td>用户名</td><td>${ue.username }</td>
 </tr>
 <tr>
 <td>用户密码</td><td>${ue.password }</td>
 </tr>
 
 </table>
</body>
</html>
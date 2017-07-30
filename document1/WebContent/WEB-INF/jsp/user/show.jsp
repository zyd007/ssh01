<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息</title>
<link rel="stylesheet" href="<%=request.
getContextPath()%>/css/main.css" type="text/css"/>
</head>
<body>
<jsp:include page="nvc.jsp"/>
<table align="center" cellpadding="0"
 cellspacing="0" border="0" width="600" class="ct">
 <tr>
 <td>用户id</td><td>${id }</td>
 </tr>
 <tr>
 <td>用户名称</td><td>${username }</td>
 </tr>
 <tr>
 <td>用户密码</td><td>${password }</td>
 </tr>
 <tr>
 <td>用户昵称</td><td>${nickname }</td>
 </tr>
 <tr>
 <td>用户类型</td><c:if test="${type eq 1}"><td>管理员</td></c:if>
 				<c:if test="${type eq 0}"><td>普通用户</td></c:if>
 </tr>
 <tr>
 <td>用户邮箱</td><td>${email }</td>
 </tr>
 <tr>
 <td>用户所在部门</td><td>${department.name }</td>
 </tr>
 </table>
</body>
</html>
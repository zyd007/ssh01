<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门列表</title>
<link rel="stylesheet" href="<%=request.
getContextPath()%>/css/main.css" type="text/css"/>
</head>
<body>
<jsp:include page="nvc.jsp"/>
<table width="700" class="ct" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td>部门id</td><td>部门名称</td><td>部门状态</td><td>操作</td>
	</tr>
<s:iterator value="#ds">
	<tr>
		<td>${id }</td><td><a href="department_show?id=${id }">${name }</a></td>
		<td>${status }</td>
		<td><a href="department_updateInput?id=${id }">更新</a>
			<a href="department_delete?id=${id }">删除</a>
			<a href="department_status?id=${id }">改变状态</a>
			<a href="department_setDepScopeInput?id=${id }">设置可发送的部门</a>
		</td>
	</tr>
</s:iterator>
</table>
</body>
</html>
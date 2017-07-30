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
<table  width="800" class="ct" align="center" cellpadding="0" cellspacing="0">
<tr ><td colspan="8">
	<form action="user_list.action" method="post">
	<s:select theme="simple" list="#ds" listKey="id" listValue="name" value="%{depId}" 
	name="depId" headerKey="0" headerValue="所有用户"/>
	<s:submit theme="simple" value="查询"/>
	</form>
</td>
</tr>
	<tr>
		<td>用户id</td><td>用户名</td><td>用户名称</td>
		<td>用户类型</td><td>用户状态</td><td>所在部门</td><td>email</td><td>操作</td>
	</tr>

<s:iterator value="#us.datas">
	<tr>
		<td>${id }</td>
		<td><a href="user_show?id=${id }">${username }</a></td>
		<td>${nickname}</td><s:if test="type==0"><td>普通用户</td>
		</s:if><s:else><td>管理员</td></s:else>
		<s:if test="status==0"><td>使用中</td></s:if>
		<s:else><td>停用中</td></s:else>
		<td>${department.name }</td><td>${email }</td>
		<td><a href="user_updateInput?id=${id }">更新</a>
			<a href="user_delete?id=${id }">删除</a>
			<a href="user_status?id=${id }">改变状态</a>
		</td>
	</tr>
</s:iterator>
<tr><td colspan="8">
<jsp:include page="/inc/pager.jsp">
	<jsp:param value="user_list.action" name="url"/>
	<jsp:param value="${us.totalRecord }" name="items"/>
	<jsp:param value="depId" name="params"/>
</jsp:include>
</td></tr>
</table>
</body>
</html>
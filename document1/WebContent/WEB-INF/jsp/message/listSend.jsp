<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发送信息列表</title>
<link rel="stylesheet" href="<%=request.
getContextPath()%>/css/main.css" type="text/css"/>
</head>
<body>
<jsp:include page="nvc.jsp"/>
<table  width="800" class="ct" align="center" cellpadding="0" cellspacing="0">
<tr ><td colspan="4">
	<form action="message_listSend.action" method="post">
	查询条件	<input type="text" name="con" value="${con }"/>
			<input type="submit" value="查询">
	</form>
</td>
</tr>
	<tr>
		<td>标题</td><td>内容</td><td>发送时间</td><td>操作</td>
		
	</tr>
	
	<s:if test="#ms.totalRecord==0"><tr><td colspan="4">当前发送收到信件</td></tr></s:if>
	<s:else>
<s:iterator value="#ms.datas">
	<tr>
		<td>${title }</td>
		<td>${content}</td>
		<td><s:date name="createDate" format="yyyy-MM-DD HH:mm:ss"/></td>
		<td><a href="message_deleteSend?id=${id }">删除</a>
			<a href="message_showSend?id=${id }">查看</a>
		</td>
	</tr>
</s:iterator>
</s:else>
<tr><td colspan="4">
<jsp:include page="/inc/pager.jsp">
	<jsp:param value="message_listSend.action" name="url"/>
	<jsp:param value="${ms.totalRecord }" name="items"/>
	<jsp:param value="con" name="params"/>
</jsp:include>
</td></tr>
</table>
</body>
</html>
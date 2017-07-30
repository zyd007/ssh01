<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>接收信息列表</title>
<link rel="stylesheet" href="<%=request.
getContextPath()%>/css/main.css" type="text/css"/>
</head>
<body>
<jsp:include page="nvc.jsp"/>
<table  width="800" class="ct" align="center" cellpadding="0" cellspacing="0">
<tr ><td colspan="5">
	<form action="message_listReceive.action" method="get">
	<!-- 需要传一个idRead过去才知道在那个里面查询 -->
	<input type="hidden" name="isRead" value="${isRead }">
	查询条件	<input type="text" name="con" value="${con }"/>
			<input type="submit" value="查询">
	</form>
		<a href="message_listReceive?isRead=0">未读信件</a>
&nbsp;	<a href="message_listReceive?isRead=1">已读信件</a>
</td>

</tr>
	<tr>
		<td>标题</td><td>发送时间</td><td>发送人信息</td><td>操作</td>
	</tr>
	<s:if test="#mr.totalRecord==0"><tr><td colspan="5">当前没有收到信件</td></tr></s:if>
	<s:else>
<s:iterator value="#mr.datas">
	<tr>
		<td>${title }</td>
		<td><s:date name="createDate" format="yyyy-MM-DD HH:mm:ss"/></td>
		<td>${user.nickname }[${user.department.name }]</td>
		<td><a href="message_deleteReceive?id=${id }">删除</a>
			<a href="message_showReceive?isRead=${isRead }&id=${id }">查看</a>
		</td>
	</tr>
</s:iterator>
</s:else>
<tr><td colspan="5">
<jsp:include page="/inc/pager.jsp">
	<jsp:param value="message_listReceive.action" name="url"/>
	<jsp:param value="${ms.totalRecord }" name="items"/>
	<jsp:param value="con,isRead" name="params"/>
</jsp:include>
</td></tr>
</table>
</body>
</html>
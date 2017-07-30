<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门信息</title>
<link rel="stylesheet" href="<%=request.
getContextPath()%>/css/main.css" type="text/css"/>
</head>
<body>

<jsp:include page="nvc.jsp"/>
<form action="department_setDepScope" method="post">
<table width="700" class="ct" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td>部门id</td><td>部门名称</td>
	</tr>
	<tr>
		<td>${id }</td><td>${name }</td>
	</tr>
	<tr><td colspan="2">可发送部门</td></tr>
	<!-- theme设置为simple不用他写的   value代表选中的id-->
<tr><td colspan="2"><s:checkboxlist list="#ds" listKey="id" listValue="name"
 label="选择可发送部门" name="sDeps" theme="simple" value="%{sDeps}"/></td></tr>
 <!--struts标签中用%{} -->
 <s:hidden name="id" value="%{id}"></s:hidden>
 <tr><td colspan="2"><input type="submit" value="设置"/></td></tr>
</table>
</form>
</body>
</html>
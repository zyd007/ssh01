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
<table width="700" class="ct" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td>部门id</td><td>部门名称</td>
	</tr>
	<tr>
		<td>${id }</td><td>${name }</td>
	</tr>
	<tr><td colspan="2">可发送部门</td></tr>
<tr><td colspan="2" align="center">
<!-- #ds就相当于arrayList -->
<s:if test="#ds.isEmpty==true" >
		没有能发送的部门,请添加!
	</s:if>
<s:iterator value="#ds">
	${name }&nbsp;
</s:iterator>
</td></tr>
</table>
</body>
</html>
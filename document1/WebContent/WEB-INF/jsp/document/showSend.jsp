<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公文信息</title>
<link rel="stylesheet" href="<%=request.
getContextPath()%>/css/main.css" type="text/css"/>
</head>
<body>
<jsp:include page="nvc.jsp"/>
<table align="center" cellpadding="0"
 cellspacing="0" border="0" width="600" class="ct">
 
 <tr>
 <td>标题</td><td>${title}</td>
 </tr>
 <tr>
 <td>用户内容</td><td>${content }</td>
 </tr>
 <tr>
 <td colspan="2">发送附件</td>
 </tr>
  <tr>
 <td colspan="2">
 	<s:if test="#atts.size()<=0">
 	没有发送附件!!!
 </s:if>
 <s:else>
 	<s:iterator value="#atts">
 		<a href="<%=request.getContextPath()%>/upload/${newName}">${oldName }</a>&nbsp;&nbsp;
 	</s:iterator>
 </s:else>
 </td>
 </tr>
 
 <tr>
 	<td colspan="2">发送部门</td>
 </tr>
 <tr>
 <td colspan="2">
 <s:iterator value="#deps">
 	${name }&nbsp;
 </s:iterator>
 </td>
 </tr>
 

 </table>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发送公文[当前部门:${loginUser.department.name }]</title>
<link rel="stylesheet" href="<%=request.
getContextPath()%>/css/main.css" type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/xheditor/jquery/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/xheditor/xheditor-1.1.14-zh-cn.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#content").xheditor({tools:"full"});
	var ab = document.getElementById("addAttach");
	var ac = document.getElementById("attachContainer");
	ab.onclick = function(){
		var node = 
"<span><br/><input type='file' name='atts'/><input type='button' onclick='remove(this)' value='移除'/></span>";
		ac.innerHTML = ac.innerHTML+node;
	};
});

function remove(obj) {
	var ac = document.getElementById("attachContainer");
	ac.removeChild(obj.parentNode);
}
</script>
</head>
<body>
<jsp:include page="nvc.jsp"/>
<form action="document_add" method="post" enctype="multipart/form-data">
<s:fielderror/>
<table  width="800" class="ct" align="center" cellpadding="0" cellspacing="0">
<tr>
	<td>标题</td><td><input size="40" type="text" name="title" value="${title }"/></td>
</tr>
<tr>
<!-- ognl能通过方法 -->
	<s:if test="#deps.size()<=0"><td colspan="2">没有能发送的的部门</td></s:if>
	<s:else>
	<td>发送部门</td><td><s:checkboxlist theme="simple" list="#deps" listKey="id" listValue="name" name="ds" value="%{ds}"/></td>
	</s:else>
</tr>
<tr>
	<td colspan="2">添加附件:</td>
</tr>
	<tr>
		<td colspan="2">
		<input type="button" value="添加附件" id="addAttach"/>
			<div id="attachContainer">
			<input type="file" name="atts" />
			</div>
		</td>
	</tr>
<tr>
	<td colspan="2" align="center" style="font-size: 40px">内容</td>
</tr>
<tr>
	<td colspan="2"><textarea  rows="30" cols="100" id="content" name="content" >
	<s:if test="content==null"></s:if>
	
	<s:else><%=request.getParameter("content") %></s:else></textarea></td>
</tr>
<s:if test="#deps.size()>0">
<tr>
	<td colspan="2"><input type="submit" value="发送"/></td>
</tr>
</s:if>
</table>
</form>
</body>
</html>
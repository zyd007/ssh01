<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><decorator:title/></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/main.css"/>
<decorator:head/>
</head>
<body>
<s:if test="#session.loginUser!=null">欢迎[${loginUser.nickname }]进入系统&nbsp;
<s:if test="#session.loginUser.type==1">
<a href="<%=request.getContextPath()%>/user_list.do">用户管理</a>
<a href="<%=request.getContextPath()%>/department_list.action">部门管理</a>
</s:if>
<a href="message_listReceive">私人信件管理</a>
<a href="document_listReceive">私人公文管理</a>
<a href="user_showSelf.do?id=${loginUser.id }">查看个人信息</a>
<a href="user_updateSelfInput.do?id=${loginUser.id }">修改个人信息 </a>
<a href="user_addEmailInput.do">绑定个人邮箱</a>
<a href="user_updateEmailInput.do?id=${loginUser.id }">修改个人邮箱</a>
<a href="user_showEmail.do?id=${loginUser.id }">个人邮箱信息</a>
<a href="login!logout">退出系统</a>
</s:if>


<!-- 如果没有title的就算默认值 -->
<hr/>
<h2 align="center"><decorator:title default="公文管理系统"/></h2>
<decorator:body/>
<div align="center"  style="width:100%;border-top:1px solid; float:left;margin-top:10px;font-size:25px;padding-bottom: 40px ">CopyRight@zy</div>
</body>
</html>
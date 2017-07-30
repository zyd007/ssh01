<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<s:if test="#session.loginUser.type==1">
<a href="user_addInput">添加用户</a>
<a href="user_list">用户列表</a><br>
</s:if>
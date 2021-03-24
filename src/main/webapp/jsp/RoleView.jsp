<%@page import="in.co.rays.project_4.controller.RoleCtl"%>
<%@page import="in.co.rays.project_4.util.DataUtility"%>
<%@page import="in.co.rays.project_4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Role View</title>
</head>
<body>
<%@include file="Header.jsp" %>
<jsp:useBean id="bean" class="in.co.rays.project_4.bean.RoleBean" scope="request" ></jsp:useBean>
<div align="center" >
<h1  align="center">
<%if(bean.getId()>0){ %>
Edit Role
<%}else{ %>
Add Role
<%} %>
</h1>
<h3>
<font color="red" ><%=ServletUtility.getErrorMessage(request) %></font>
<font  color="green" ><%=ServletUtility.getSuccessMessage(request)%></font>
</h3>
<form action="<%=ORSView.ROLE_CTL %>" method="post" >
<input type="hidden" name="id" value="<%=bean.getId()%>"> 
			<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
			<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
<table align="center" > 
	<tr>
	<th align="left" >Name<font color="red" >*</font></th>
	<td><input type="text" name="roleName" value="<%=DataUtility.getStringData(bean.getName()) %>" placeholder="Enter Role Name"  >
	&nbsp;&nbsp;<font color="red" style="position:fixed"><%=ServletUtility.getErrorMessage("roleName", request) %></font></td>
	</tr>
	
<tr></tr>
	<tr>
	<th align="left" >Description<font color="red" >*</font></th>
	<td><input type="text" name="desc" value="<%=DataUtility.getStringData(bean.getDescription()) %>" placeholder="Enter Role Description"   >
	&nbsp;&nbsp;<font color="red" style="position:fixed" ><%=ServletUtility.getErrorMessage("desc", request) %></font></td>
	</tr>
<tr></tr>
<tr>
<th></th>
<%if(bean.getId()>0){ %>
<td><input type="submit" name="operation" value="<%=RoleCtl.OP_UPDATE %>" style="width:77px;padding:7px" >&nbsp;&nbsp;
<input type="submit" name="operation" value="<%=RoleCtl.OP_CANCEL %>" style="width:77px;padding:7px" ></td>
<%}else{ %>
<td><input type="submit" name="operation" value="<%=RoleCtl.OP_SAVE %>" style="width:77px;padding:7px" >&nbsp;&nbsp;
<input type="submit" name="operation" value="<%=RoleCtl.OP_RESET %>" style="width:77px;padding:7px" ></td>
<%} %>
</tr>
</table>
</form>
</div>
<%@include file="Footer.jsp" %>
</body>
</html>
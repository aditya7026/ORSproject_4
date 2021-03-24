<%@page import="in.co.rays.project_4.controller.ChangePasswordCtl"%>
<%@page import="in.co.rays.project_4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Change Password View</title>
</head>
<body>
<%@include file="Header.jsp" %>
<jsp:useBean id="bean" class="in.co.rays.project_4.bean.UserBean" scope="request" ></jsp:useBean>
<div align="center" >
<h1>
Change Password
</h1>
<h2>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h2>
<form action="<%=ORSView.CHANGE_PASSWORD_CTL %>" method="post" >
<input type="hidden" name="id" value="<%=bean.getId() %>"  >
<table>
<tr>
<th align="left" >Old Password<font color="red">*</font></th>
<td><input type="password" name="oldPass" placeholder="Enter Old Password" >
&nbsp;&nbsp;<font color="red" style="position:fixed" ><%=ServletUtility.getErrorMessage("oldPass", request) %></font></td>
</tr>

<tr></tr>
<tr>
<th align="left" >New Password<font color="red">*</font></th>
<td><input type="password" name="newPass" placeholder="Enter New Password" >
&nbsp;&nbsp;<font color="red" style="position:fixed" ><%=ServletUtility.getErrorMessage("newPass", request) %></font></td>
</tr>
<tr></tr>
<tr>
<th align="left" >Confirm Password<font color="red">*</font></th>
<td><input type="password" name="conPass" placeholder="Confirm new Password" >
&nbsp;&nbsp;<font color="red" style="position:fixed" ><%=ServletUtility.getErrorMessage("conPass", request) %></font></td>
</tr>
<tr></tr>
<tr>
<th></th>
<td>
<input type="submit" name="operation" value="<%=ChangePasswordCtl.OP_SAVE %>" style="width:77px;" >&nbsp;&nbsp;
<input type="submit" name="operation" value="<%=ChangePasswordCtl.OP_PROFILE %>" style="width:77px;" ></td>
</tr>
</table>
</form>
</div>
<%@include file="Footer.jsp" %>
</body>
</html>
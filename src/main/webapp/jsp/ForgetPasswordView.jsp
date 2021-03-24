<%@page import="in.co.rays.project_4.util.DataUtility"%>
<%@page import="in.co.rays.project_4.controller.ForgetPasswordCtl" %>
<%@page import="in.co.rays.project_4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forget Password</title>
</head>
<body>
<%@include file="Header.jsp" %>
<jsp:useBean id="bean" class="in.co.rays.project_4.bean.UserBean" scope="request" ></jsp:useBean>
<center>
<h1>Forgot your Password?</h1>
<h2>Submit your email address and we'll send you your password.</h2>
<h2>
<font color=green ><%=ServletUtility.getSuccessMessage(request) %></font>
<font color=red ><%=ServletUtility.getErrorMessage(request) %></font>
</h2>
<form action="<%=ORSView.FORGET_PASSWORD_CTL%>" method="post">
<table> 
<tr>
<th>Email ID<font color="red">*</font></th>
<td> &nbsp;&nbsp; <input type="text" name="login" value="<%=DataUtility.getStringData(bean.getLogin())%>">
&nbsp;&nbsp;<font color=red style="position: fixed"><%=ServletUtility.getErrorMessage("login", request) %></font>
</td>
</tr>
<tr></tr>
<tr>
<th></th>
<td>&nbsp;&nbsp;&nbsp;<input type="submit" name="operation" value="<%=ForgetPasswordCtl.OP_GO %>" >&nbsp;&nbsp;
<input type="submit" name="operation" value="<%=ForgetPasswordCtl.OP_RESET %>" >
</td>
<tr>
</table>
</form>
</center> 
<%@ include file="Footer.jsp" %>
</body>
</html>
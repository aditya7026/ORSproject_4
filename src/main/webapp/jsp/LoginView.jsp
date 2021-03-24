<%@page import="in.co.rays.project_4.controller.LoginCtl"%>
<%@page import="in.co.rays.project_4.util.DataUtility"%>
<%@page import="in.co.rays.project_4.util.ServletUtility"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Login View</title>
</head>
<body>
	<form action="<%=ORSView.LOGIN_CTL%>" method="post">
		<%@include file="Header.jsp"%>
		<jsp:useBean id="bean" class="in.co.rays.project_4.bean.UserBean"
			scope="request"></jsp:useBean>
		<center>
		<br>
		<br>
		<br>
		
			<h1>Login</h1>
			<%
				String path = (String)request.getAttribute("repath");
			%>
			<input type="hidden"
				value=<%=((path != null) ? path : "")%> name="repath">
			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
			<h2>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h2>
			<table>
				<tr>
					<th align="left">Login ID<font color="red">*</font></th>
					<td><input type="text" name="login"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"
						placeholder="Enter E-mail Id">&nbsp;&nbsp; <font
						color=red style="position: fixed"><%=ServletUtility.getErrorMessage("login", request)%></font></td>
				</tr>
				<tr></tr>
				<tr></tr>
				<tr>
					<th align="left">Password<font color="red">*</font></th>
					<td><input type="password" name="password"
						value="<%=DataUtility.getStringData(bean.getPassword())%>"
						placeholder="Enter Password">&nbsp;&nbsp; <font color=red
						style="position: fixed"><%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>
				<tr></tr>
				<tr></tr>
				<tr>
					<th></th>
					<td><input type="submit" name="operation"
						value="<%=LoginCtl.OP_SIGN_IN%>" style="padding: 7px 17px">&nbsp;
						<input type="submit" name="operation"
						value="<%=LoginCtl.OP_SIGN_UP%>" style="padding: 7px 17px"></td>
				</tr>
				<tr>
					<th></th>
					<td><h3>
							<a href="<%=ORSView.FORGET_PASSWORD_CTL%>">Forgot Password?</a>
						</h3></td>
				</tr>
			</table>
		</center>
	</form>
	<%@ include file="Footer.jsp"%>
</body>
</html>
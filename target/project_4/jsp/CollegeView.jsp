<%@page import="in.co.rays.project_4.controller.CollegeCtl"%>
<%@page import="in.co.rays.project_4.util.DataUtility"%>
<%@page import="in.co.rays.project_4.util.ServletUtility"%>
<%@page import="in.co.rays.project_4.controller.CollegeListCtl" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>College View</title>
</head>
<body>
<%@ include file="Header.jsp" %>
<center>
<jsp:useBean id="bean" class="in.co.rays.project_4.bean.CollegeBean" scope="request"></jsp:useBean>
<h1>
<%if(bean.getId()>0){ %>
Edit College
<% } else if(bean.getId()==0){ %>
Add College
<%} %>
</h1>
			<h3>
			<font color="red" ><%=ServletUtility.getErrorMessage(request) %></font>
			<font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
			</h3>
<form action="<%=ORSView.COLLEGE_CTL%>" method="post">
<input type="hidden" name="id" value="<%=DataUtility.getStringData((bean.getId())) %>" >
	<input type="hidden" name="createdBy" value="<%=DataUtility.getStringData((bean.getCreatedBy())) %>"  >
	<input type="hidden" name="modifiedBy" value="<%=DataUtility.getStringData((bean.getModifiedBy())) %>" >
	<input type="hidden" name="createdDateTime" value="<%=DataUtility.getStringData((bean.getCreatedDatetime())) %>" >
	<input type="hidden" name="modifiedDateTime" value="<%=DataUtility.getStringData((bean.getModifiedDatetime())) %>"  >
<table>
	<tr>
	<th align="left" >Name<font color="red">*</font></th>
	<td><input type="text" name="name" value="<%=DataUtility.getStringData(bean.getName()) %>" placeholder="Enter College Name" >
	&nbsp;&nbsp;<font color="red" style="position:fixed" ><%=ServletUtility.getErrorMessage("name", request) %></font></td>
	</tr>
<tr></tr>
	<tr>
	<th align="left" >Address<font color="red">*</font></th>
	<td><input type="text" name="address" value="<%=DataUtility.getStringData(bean.getAddress()) %>" placeholder="Enter College Address" >
	&nbsp;&nbsp;<font color="red" style="position:fixed" ><%=ServletUtility.getErrorMessage("address", request) %></font></td>
	</tr>
	
<tr></tr>
	<tr>
	<th align="left" >State<font color="red">*</font></th>
	<td><input type="text" name="state" value="<%=DataUtility.getStringData(bean.getState()) %>" placeholder="Enter State" >
	&nbsp;&nbsp;<font color="red" style="position:fixed" ><%=ServletUtility.getErrorMessage("state", request) %></font></td>
	</tr>
<tr></tr>
	<tr>
	<th align="left" >City<font color="red">*</font></th>
	<td><input type="text" name="city" value="<%=DataUtility.getStringData(bean.getCity()) %>" placeholder="Enter City"  >
	&nbsp;&nbsp;<font color="red" style="position:fixed" ><%=ServletUtility.getErrorMessage("city", request) %></font></td>
	</tr>
<tr></tr>
	<tr>
	<th align="left" >Phone no<font color="red">*</font></th>
	<td><input type="text" name="phNo" value="<%=DataUtility.getStringData(bean.getPhNo()) %>" placeholder="Enter PhoneNo" >
	&nbsp;&nbsp;<font color="red" style="position:fixed" ><%=ServletUtility.getErrorMessage("phNo", request) %></font></td>
	</tr>
<tr></tr>
	<tr>
	<th></th>
	<%if(bean.getId()>0){ %>
	<td><input type="submit" name="operation" value="<%=CollegeCtl.OP_UPDATE%>" style="width:77px;padding:7px" >&nbsp;&nbsp;
	<input type="submit" name="operation" value="<%=CollegeCtl.OP_CANCEL%>" style="width:77px;padding:7px" ></td>
	<%} else if(bean.getId()==0) { %>
	<td><input type="submit" name="operation" value="<%=CollegeCtl.OP_SAVE%>" style="width:77px;padding:7px" >&nbsp;&nbsp;
	<input type="submit" name="operation" value="<%=CollegeCtl.OP_RESET%>" style="width:77px;padding:7px" ></td>
	<%} %>
	<tr>
	</table>
</form>
</center>
<%@ include file="Footer.jsp" %>
</body>
</html>
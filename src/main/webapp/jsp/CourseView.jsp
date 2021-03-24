<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_4.util.HTMLUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="in.co.rays.project_4.util.ServletUtility" %>
    <%@page import="in.co.rays.project_4.util.DataUtility"%>
    <%@page import="in.co.rays.project_4.controller.CourseCtl" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Course View</title>
</head>
<body>
<%@include file="Header.jsp" %>
<div align="center" >
<jsp:useBean id="bean" class="in.co.rays.project_4.bean.CourseBean" scope="request" ></jsp:useBean>
<h1>
<%if(bean.getId()>0){ %>
Edit Course
<%}else{ %>
Add Course
<%} %>
</h1>
<h3>
			<font color="red" ><%=ServletUtility.getErrorMessage(request) %></font>
			<font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
			</h3>
			<form action="<%=ORSView.COURSE_CTL %>" method="post" >
			<input type="hidden" name="id" value="<%=bean.getId()%>"> 
			<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
			<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
<table>
<tr>
	<th align="left" >Name<font color="red" >*</font></th>
	<td><input type="text" name="courseName" value="<%=DataUtility.getStringData(bean.getName()) %>" placeholder="Enter Course Name"  >
	&nbsp;&nbsp;<font color="red" style="position:fixed"><%=ServletUtility.getErrorMessage("courseName", request) %></font></td>
	</tr>
<tr></tr>
	<tr>
	<th align="left" >Description<font color="red" >*</font></th>
	<td><input type="text" name="description" value="<%=DataUtility.getStringData(bean.getDescription()) %>" placeholder="Enter Course Description" >
	&nbsp;&nbsp;<font color="red" style="position:fixed" ><%=ServletUtility.getErrorMessage("description", request) %></font></td>
	</tr>
<tr></tr>
	<% 
	HashMap map = new HashMap();
	map.put("2 years", "2 years");
	map.put("3 years", "3 years");
	map.put("4 years", "4 years");
	%>
	<tr>
	<th align="left" >Duration<font color="red" >*</font></th>
	<td><%=HTMLUtility.getList("duration", DataUtility.getStringData(bean.getDuration()), map) %>
	&nbsp;&nbsp;<font color="red" style="position:fixed" ><%=ServletUtility.getErrorMessage("duration", request) %></font></td>
	</tr>
<tr></tr>
	<tr>
<th></th>
<%if(bean.getId()>0){ %>
<td><input type="submit" name="operation" value="<%=CourseCtl.OP_UPDATE %>" style="width:77px;padding:7px" >&nbsp;&nbsp;
<input type="submit" name="operation" value="<%=CourseCtl.OP_CANCEL %>" style="width:77px;padding:7px" ></td>
<%}else{ %>
<td><input type="submit" name="operation" value="<%=CourseCtl.OP_SAVE %>" style="width:77px;padding:7px" >&nbsp;&nbsp;
<input type="submit" name="operation" value="<%=CourseCtl.OP_RESET %>" style="width:77px;padding:7px" ></td>
<%} %>
</tr>
</table>

</form>
</div>
<%@include file ="Footer.jsp" %>
</body>
</html>
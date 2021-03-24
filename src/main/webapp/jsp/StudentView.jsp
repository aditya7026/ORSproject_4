<%@page import="in.co.rays.project_4.controller.StudentCtl"%>
<%@page import="in.co.rays.project_4.util.HTMLUtility"%>
<%@page import="in.co.rays.project_4.util.DataUtility"%>
<%@page import="in.co.rays.project_4.bean.CollegeBean" %>
<%@page import="in.co.rays.project_4.util.ServletUtility" %>
<%@page import="java.util.List" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6/jquery.min.js" type="text/javascript"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" 
  		type="text/javascript"></script>
  <link href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" 
 		 rel="Stylesheet" type="text/css" />
<script>
	$( function() {
		$( "#datepicker" ).datepicker({
			defaultDate : "01/01/1984",
			changeMonth: true,
			changeYear: true,
			yearRange : '-35:-17',
			dateFormat: "dd/mm/yy",
		});
	} );
	</script>
	<script type="text/javascript" src="./js/calendar.js"></script>
<title>Student View</title>
</head>
<body>
<%@include file="Header.jsp" %>
<div align="center" >
<jsp:useBean id="bean" class="in.co.rays.project_4.bean.StudentBean" scope="request" ></jsp:useBean>
<h1>
<%if(bean.getId()>0){ %>
Edit Student
<%}else{ %>
Add Student
<%} %>
</h1>
<h3>
<font color="green" ><%=ServletUtility.getSuccessMessage(request) %></font>
<font color="red" ><%=ServletUtility.getErrorMessage(request)%></font>
</h3>
<form action="<%=ORSView.STUDENT_CTL%>" method="post">
			<input type="hidden" name="id" value="<%=bean.getId()%>"> 
			<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
			<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
<table >
<tr>
<th align="left" >College<font color="red">*</font></th>
<% List<CollegeBean>list = (List<CollegeBean>)request.getAttribute("list"); %>
<td><%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeId()), list) %>
&nbsp;&nbsp; <font color="red" style="position:fixed"><%=ServletUtility.getErrorMessage("collegeId", request) %></font></td>
</tr>
<tr></tr>
<tr>
<th align="left" >First Name<font color="red">*</font></th>
<td><input type="text" name="firstName" value="<%=DataUtility.getStringData(bean.getFirstName()) %>" placeholder="Enter First Name" >
&nbsp;&nbsp; <font color="red" style="position:fixed"><%=ServletUtility.getErrorMessage("firstName", request) %></font></td>
</tr>
<tr></tr>
<tr>
<th align="left" >Last Name<font color="red">*</font></th>
<td><input type="text" name="lastName" value="<%=DataUtility.getStringData(bean.getLastName()) %>" placeholder="Enter Last Name" >
&nbsp;&nbsp; <font color="red" style="position:fixed"><%=ServletUtility.getErrorMessage("lastName", request) %></font></td>
</tr>
<tr></tr>
<tr>
<th align="left" >Email ID<font color="red">*</font></th>
<td>
<input type="text" name="email" value="<%=DataUtility.getStringData(bean.getEmail()) %>" placeholder="Enter Email Id" <%=(bean.getId() > 0) ? "readonly" :""%> >
&nbsp;&nbsp; <font color="red" style="position:fixed"><%=ServletUtility.getErrorMessage("email", request) %></font>
</td>
</tr>
<tr></tr>
<tr>
<th align="left" >Date of Birth<font color="red">*</font></th>
<td><input type="text" value="<%=DataUtility.getDateString(bean.getDob()) %>" placeholder="Enter Date of Birth" name="dob" id="datepicker" placeholder="Enter Date of Birth">
&nbsp;&nbsp;<font color=red style="position: fixed" ><%=ServletUtility.getErrorMessage("dob", request) %></font></td>
</tr>
<tr></tr>
<tr>
<th align="left">Mobile No<font color="red">*</font></th>
<td><input type="text" name="mobileNo" value="<%=DataUtility.getStringData(bean.getMobileNo())%>" placeholder="Enter MobileNo">
&nbsp;&nbsp; <font color="red" style="position:fixed"><%=ServletUtility.getErrorMessage("mobileNo", request) %></font></td>
</tr>
<tr></tr>

<tr>
<th></th>
<%if(bean.getId()>0){ %>
<td><input type="submit" name="operation" value="<%=StudentCtl.OP_UPDATE %>" style="width:77px;padding:7px" >&nbsp;&nbsp;
<input type="submit" name="operation" value="<%=StudentCtl.OP_CANCEL %>" style="width:77px;padding:7px" ></td>
<%}else{ %>
<td><input type="submit" name="operation" value="<%=StudentCtl.OP_SAVE %>" style="width:77px;padding:7px" >&nbsp;&nbsp;
<input type="submit" name="operation" value="<%=StudentCtl.OP_RESET %>" style="width:77px;padding:7px" ></td>
<% } %>
</tr>
</table>
</form>
</div>
<%@include file="Footer.jsp" %>
</body>
</html>
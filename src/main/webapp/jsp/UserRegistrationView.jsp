<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="in.co.rays.project_4.controller.UserRegistrationCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_4.util.HTMLUtility"%>
<%@page import="in.co.rays.project_4.util.DataUtility"%>
<%@page import="in.co.rays.project_4.util.ServletUtility"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>User Registration View</title>
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
</head>
<body>
<%@include file="Header.jsp" %>
<jsp:useBean id="bean" class="in.co.rays.project_4.bean.UserBean" scope="request" ></jsp:useBean>
<center >
<h1 >User Registration</h1>
<h3>
	<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
	<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
</h3>
<form action="<%=ORSView.USER_REGISTRATION_CTL%>" method="post" >
<input type="hidden" name="id" value="<%=bean.getId()%>">
<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy() %>">
<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy() %>">
<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
<input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime()) %>">
<table >
<tr >
<th align="left"  >First Name<font color="red">*</font></th>
<td><input type="text"  name="firstName" value="<%=DataUtility.getStringData(bean.getFirstName())%>" placeholder="Enter First Name" >
&nbsp;&nbsp; <font color="red" style="position:fixed"><%=ServletUtility.getErrorMessage("firstName", request) %></font></td>
</tr>
<tr></tr>
<tr>
<th align="left" >Last Name<font color="red">*</font></th>
<td><input type="text" name="lastName" value="<%=DataUtility.getStringData(bean.getLastName())%>" placeholder="Enter Last Name">
&nbsp;&nbsp;<font color=red style="position: fixed"><%=ServletUtility.getErrorMessage("lastName", request) %></font></td>
</tr>
<tr></tr>
<tr>
<th align="left" >Login ID<font color="red">*</font></th>
<td><input type="text" name="login" placeholder="Must be Email ID" value="<%=DataUtility.getStringData(bean.getLogin())%>"  >
&nbsp;&nbsp;<font color=red style="position: fixed"><%=ServletUtility.getErrorMessage("login", request) %></font></td>
</tr>
<tr></tr>
<tr>
<th align="left" >Password<font color="red">*</font></th>
<td><input type="password" name="password" value="<%=DataUtility.getStringData(bean.getPassword())%>" placeholder="Enter Password">
&nbsp;&nbsp;<font color=red style="position: fixed"><%=ServletUtility.getErrorMessage("password", request) %></font></td>
</tr>
<tr></tr>
<tr>
<th align="left"  >Confirm Password<font color="red">*</font></th>
<td><input type="password" name="confirmPassword" value="<%=DataUtility.getStringData(bean.getConfirmPassword())%>" placeholder="Enter Confirm Password" >
&nbsp;&nbsp;<font color=red style="position: fixed"><%=ServletUtility.getErrorMessage("confirmPassword", request) %></font></td>
</tr>
<tr></tr>
<tr>
<th  align="left"  >Mobile No<font color="red">*</font></th>
<td><input type="text" name="mobileNo" value="<%=DataUtility.getStringData(bean.getMobileNo())%>" placeholder="Enter Mobile No." >
&nbsp;&nbsp;<font color=red style="position: fixed"><%=ServletUtility.getErrorMessage("mobileNo", request) %></font></td>
</tr>
<tr></tr>
<tr>
<th align="left" >Gender<font color="red">*</font></th>
<td>
<%  
HashMap<String,String> map = new HashMap<String,String>();
map.put("Male", "Male");
map.put("Female","Female");
String dd = HTMLUtility.getList("gender", DataUtility.getStringData(bean.getGender()) , map); %>
<%=dd %>&nbsp;&nbsp;&nbsp;<font color=red style="position: fixed"><%=ServletUtility.getErrorMessage("gender", request) %></font>
</td>
</tr>
<tr></tr>
<tr>
<th align="left" >Date Of Birth<font color="red">*</font></th>
<td><input type="text" placeholder="Enter Date of Birth" name="dob" id="datepicker" value="<%=DataUtility.getDateString(bean.getDob()) %>" >
&nbsp;&nbsp;<font color=red style="position: fixed" ><%=ServletUtility.getErrorMessage("dob", request) %></font></td>
</tr>
<tr></tr>
<tr>
<th></th>
<td><input type="submit" name="operation" value="<%=UserRegistrationCtl.OP_SIGN_UP %>" style="padding: 7px;width:79px" >&nbsp;
<input type="submit" name="operation" value="<%=UserRegistrationCtl.OP_RESET %>" style="padding: 7px;width:79px" ></td>
</tr>
</table>

</form>
</center>
<%@include file="Footer.jsp" %>
</body>
</html>
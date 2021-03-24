	<%@page import="in.co.rays.project_4.model.RoleModel"%>
<%@page import="in.co.rays.project_4.util.HTMLUtility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_4.util.ServletUtility"%>
<%@page import="in.co.rays.project_4.util.DataUtility"%>
<%@page import="in.co.rays.project_4.controller.ORSView" %>
<%@page import="in.co.rays.project_4.controller.UserCtl" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User View</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6/jquery.min.js" type="text/javascript"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" 
  		type="text/javascript"></script>
  <link href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" 
 		 rel="Stylesheet" type="text/css" />
<script>
	$( function() {
		$( "#datepicker" ).datepicker({
			changeMonth: true,
			changeYear: true,
			yearRange : '-35:-17',
			dateFormat: "dd/mm/yy",
		});
	} );
	</script>
	<script type="text/javascript" src="./js/calendar.js"></script>
</head>
<body>
<%@include file="Header.jsp" %>

<div align="center"  >
			

			<jsp:useBean id="bean" class="in.co.rays.project_4.bean.UserBean" scope="request"></jsp:useBean>
			<form action="<%=ORSView.USER_CTL%>" method="post" >

			<input type="hidden" name="id" value="<%=bean.getId()%>"> 
			<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
			<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<%if(bean.getId()>0){ %>
			<h1>Edit User</h1>
			<%}else {%>
			<h1>Add User</h1>
			<%} %>
			<h3>
			<font color="red" ><%=ServletUtility.getErrorMessage(request) %></font>
			<font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
			</h3>
<table >
<tr>
<th align="left">First Name<font color="red">*</font></th>
<td><input type="text" name="firstName" value="<%=DataUtility.getStringData(bean.getFirstName())%>" placeholder="Enter First Name" >
&nbsp;&nbsp; <font color="red" style="position:fixed"><%=ServletUtility.getErrorMessage("firstName", request) %></font></td>
</tr>
<tr></tr>
<tr>
<th align="left" >Last Name<font color="red">*</font></th>
<td><input type="text" name="lastName" value="<%=DataUtility.getStringData(bean.getLastName())%>" placeholder="Enter Last Name">
&nbsp;&nbsp; <font color="red" style="position:fixed"><%=ServletUtility.getErrorMessage("lastName", request) %></font></td>
</tr>
<tr></tr>
<tr>
<th align="left" >Login ID<font color="red">*</font></th>
<td><input type="text" name="login" value="<%=DataUtility.getStringData(bean.getLogin()) %>" placeholder="Must be Email Id" <%=(bean.getId() > 0) ? "readonly" :""%>>
&nbsp;&nbsp; <font color="red" style="position:fixed"><%=ServletUtility.getErrorMessage("login", request) %></font></td>

</tr>

<tr></tr>
<tr>
<th align="left">Password<font color="red">*</font></th>
<td><input type="password" name="password" value="<%=DataUtility.getStringData(bean.getPassword())%>" placeholder="Enter Password" >
&nbsp;&nbsp; <font color="red" style="position:fixed"><%=ServletUtility.getErrorMessage("password", request) %></font></td>
</tr>
<tr></tr>
<tr>
<%if(bean.getId()==0){ %>
<th align="left">Confirm Password<font color="red">*</font></th>
<td><input type="password" name="confirmPassword" value="<%=DataUtility.getStringData(bean.getConfirmPassword())%>" placeholder="Enter Confirm Password">
&nbsp;&nbsp; <font color="red" style="position:fixed"><%=ServletUtility.getErrorMessage("confirmPassword", request) %></font></td>
</tr>
<%} %>

<tr></tr>
<tr>
<th align="left">Gender<font color="red">*</font></th>
<td>
<%
HashMap<String,String> map = new HashMap<String,String>();
map.put("Male", "Male");
map.put("Female","Female");
String dd = HTMLUtility.getList("gender", DataUtility.getStringData(bean.getGender()) ,map); 
%>
<%=dd%>&nbsp;&nbsp; <font color="red" style="position:fixed">&nbsp;<%=ServletUtility.getErrorMessage("gender", request) %></font>
</td>
</tr>

<tr></tr>
<tr>
<th align="left">Mobile No<font color="red">*</font></th>
<td><input type="text" name="mobileNo" value="<%=DataUtility.getStringData(bean.getMobileNo())%>" placeholder="Enter MobileNo">
&nbsp;&nbsp; <font color="red" style="position:fixed"><%=ServletUtility.getErrorMessage("mobileNo", request) %></font></td>
</tr>

<tr></tr>
<tr>
<th align="left">Role<font color="red">*</font></th>
<% List<RoleBean> l = (List<RoleBean>)request.getAttribute("list");
String roledd = HTMLUtility.getList("roleId", String.valueOf(bean.getRoleId()), l);%>

<td><%=roledd%>&nbsp;&nbsp; <font color="red" style="position:fixed">&nbsp;<%=ServletUtility.getErrorMessage("roleId", request) %></font></td>
</tr>

<tr></tr>
<tr>
<th align="left" >Date Of Birth<font color="red">*</font></th>
<td><input type="text" value="<%=DataUtility.getDateString((bean.getDob())) %>" placeholder="Enter Date of Birth" name="dob" id="datepicker" placeholder="Enter Date of Birth">
&nbsp;&nbsp;<font color=red style="position: fixed" ><%=ServletUtility.getErrorMessage("dob", request) %></font></td>
</tr>

<tr></tr>
<tr>
<th></th>

<% if(bean.getId()>0){ %>
<td><input type="submit" name="operation" value="<%=UserCtl.OP_UPDATE %>" style="width:77px;padding:7px" >&nbsp;&nbsp;
<input type="submit" name="operation" value="<%=UserCtl.OP_CANCEL%>"style="width:77px;padding:7px" ></td>
<%}else{ %>                              
<td><input type="submit" name="operation" value="<%=UserCtl.OP_SAVE%>" style="width:77px;padding:7px">&nbsp;&nbsp;
<input type="submit" name="operation" value="<%=UserCtl.OP_RESET %>" style="width:77px;padding:7px" ></td>
<%} %>
</tr>
</table>
</form>
</div>
<%@include file="Footer.jsp" %>
</body>
</html>
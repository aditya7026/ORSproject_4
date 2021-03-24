<%@page import="in.co.rays.project_4.util.ServletUtility"%>
<%@page import="in.co.rays.project_4.controller.MyProfileCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_4.util.ServletUtility"%>
<%@page import="in.co.rays.project_4.util.DataUtility"%>
<%@page import="in.co.rays.project_4.util.HTMLUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Profile View</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.6/jquery.min.js"
	type="text/javascript"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"
	type="text/javascript"></script>
<link
	href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css"
	rel="Stylesheet" type="text/css" />
<script>
	$(function() {
		$("#datepicker").datepicker({
			defaultDate : "01/01/1984",
			changeMonth : true,
			changeYear : true,
			yearRange : '-35:-17',
			dateFormat : "dd/mm/yy",
		});
	});
</script>
<script type="text/javascript" src="./js/calendar.js"></script>
</head>
<body>
	<%@include file="Header.jsp"%>
	<div align="center">
		<h1>My Profile</h1>
		<form action="<%=ORSView.MY_PROFILE_CTL%>" method="post">
			<jsp:useBean id="bean" class="in.co.rays.project_4.bean.UserBean"
				scope="request"></jsp:useBean>
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<!-- Hidden form fields -->
			<input type="hidden" name="role" value="<%=bean.getRoleId()%>">
			<input type="hidden" name="password" value="<%=bean.getPassword()%>">
			<table>
				<tr>
					<th align="left">First Name<font color="red">*</font></th>
					<td><input type="text" name="firstName"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"
						placeholder="Enter First Name" size="23"> &nbsp;&nbsp; <font
						color="red" style="position: fixed"><%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
				</tr>
				<tr></tr>
				<tr>
					<th align="left">Last Name<font color="red">*</font></th>
					<td><input type="text" name="lastName"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"
						placeholder="Enter Last Name" size="23"> &nbsp;&nbsp; <font
						color="red" style="position: fixed"><%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>
				<tr></tr>
				<tr>
					<th align="left">Login ID<font color="red">*</font></th>
					<td><input type="text" name="login"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"
						placeholder="Must be Email Id"
						<%=(bean.getId() > 0) ? "readonly" : ""%> size="23">
						&nbsp;&nbsp; <font color="red" style="position: fixed"><%=ServletUtility.getErrorMessage("login", request)%></font></td>
				<tr></tr>
				<tr>
					<th align="left">Gender<font color="red">*</font></th>
					<td>
						<%
							HashMap<String, String> map = new HashMap<String, String>();
							map.put("Male", "Male");
							map.put("Female", "Female");
							String dd = HTMLUtility.getCList("gender", DataUtility.getStringData(bean.getGender()), map,
									"style='width:184px; height:22px'");
						%> <%=dd%>&nbsp;&nbsp; <font color="red" style="position: fixed">&nbsp;<%=ServletUtility.getErrorMessage("gender", request)%></font>
					</td>

				<tr></tr></tr>

				<tr>
					<th align="left">Mobile No<font color="red">*</font></th>
					<td><input type="text" name="mobileNo"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"
						placeholder="Enter MobileNo" size="23"> &nbsp;&nbsp; <font
						color="red" style="position: fixed"><%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
				</tr>
				<tr></tr>
				<tr>
					<th align="left">Date Of Birth<font color="red">*</font></th>
					<td><input type="text"
						value="<%=DataUtility.getDateString((bean.getDob()))%>"
						placeholder="Enter Date of Birth" name="dob" id="datepicker"
						placeholder="Enter Date of Birth" size="23"> &nbsp;&nbsp;<font
						color=red style="position: fixed"><%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
				<tr></tr>
				<tr>
					<th></th>
					<td><input type="submit" name="operation"
						value="<%=MyProfileCtl.OP_SAVE%>">&nbsp;&nbsp; <input
						type="submit" name=operation
						value="<%=MyProfileCtl.OP_CHANGE_MY_PASSWORD%>"></td>
				</tr>
			</table>
		</form>
	</div>
	<%@include file="Footer.jsp"%>
</body>
</html>
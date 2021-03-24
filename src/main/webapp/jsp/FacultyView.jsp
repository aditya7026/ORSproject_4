<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="in.co.rays.project_4.util.ServletUtility" %>
    <%@page import="in.co.rays.project_4.util.HTMLUtility"%>
<%@page import="in.co.rays.project_4.util.DataUtility"%>
<%@page import="in.co.rays.project_4.bean.CollegeBean" %>
<%@ page import="in.co.rays.project_4.bean.CourseBean" %>
    <%@ page import="in.co.rays.project_4.bean.SubjectBean" %>
    <%@page import="java.util.HashMap"%>
    <%@page import="in.co.rays.project_4.controller.FacultyCtl" %>
<%@page import="java.util.List" %>
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
			//defaultDate : "01/01/1959",
			changeMonth: true,
			changeYear: true,
			yearRange : '-62:-21',
			dateFormat: "dd/mm/yy",
		});
	} );
	</script>
	<script type="text/javascript" src="./js/calendar.js"></script>
<title>Faculty View</title>
</head>
<body>
<%@include file="Header.jsp" %>
<div align="center" >
 <jsp:useBean id="bean" class="in.co.rays.project_4.bean.FacultyBean" scope="request" ></jsp:useBean>
 <h1>
 <%if(bean.getId()>0){ %>
 Edit Faculty
 <%}else{ %>
 Add Faculty
 <%} %>
 </h1>
 <h3>
			<font color="red" ><%=ServletUtility.getErrorMessage(request) %></font>
			<font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
			</h3>
 <form action="<%=ORSView.FACULTY_CTL%>" method="post">
 <input type="hidden" name="id" value="<%=bean.getId()%>"> 
			<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
			<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
 <table>
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
<th align="left" >College Name<font color="red">*</font></th>
<td><%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeId()), (List<CollegeBean>)request.getAttribute("collegeList")) %>
&nbsp;&nbsp; <font color="red" style="position:fixed"><%=ServletUtility.getErrorMessage("collegeId", request) %></font></td>
</tr>
<tr></tr>
<tr>
	<th align="left" >Course Name<font color="red" >*</font></th>
	<td><%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), (List<CourseBean>)request.getAttribute("courseList"))%>
	&nbsp;&nbsp;<font color="red" style="position:fixed" ><%=ServletUtility.getErrorMessage("courseId", request) %></font></td>
	</tr>
<tr></tr>
<tr>
	<th align="left" >Subject Name<font color="red" >*</font></th>
	<td><%=HTMLUtility.getList("subjectId", String.valueOf(bean.getSubjectId()), (List<SubjectBean>)request.getAttribute("subjectList"))%>
	&nbsp;&nbsp;<font color="red" style="position:fixed" ><%=ServletUtility.getErrorMessage("subjectId", request) %></font></td>
	</tr>
<tr></tr>
	<tr>
<th align="left">Gender<font color="red">*</font></th>
<td>
<%
HashMap<String,String> map = new HashMap<String,String>();
map.put("Male", "Male");
map.put("Female","Female");
%>
<%=HTMLUtility.getList("gender", DataUtility.getStringData(bean.getGender()) ,map)%>&nbsp;&nbsp; <font color="red" style="position:fixed">&nbsp;<%=ServletUtility.getErrorMessage("gender", request) %></font>
</td>
</tr>
<tr></tr>
<tr>
<th align="left" >Date Of Birth<font color="red">*</font></th>
<td><input type="text" value="<%=DataUtility.getDateString((bean.getDob())) %>" placeholder="Enter Date of Birth" name="dob" id="datepicker" placeholder="Enter Date of Birth">
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
<th align="left" >Email ID<font color="red">*</font></th>
<td>
<input type="text" name="email" value="<%=DataUtility.getStringData(bean.getEmailId()) %>" placeholder="Enter Email Id" <%=(bean.getId() > 0) ? "readonly" :""%> >
&nbsp;&nbsp; <font color="red" style="position:fixed"><%=ServletUtility.getErrorMessage("email", request) %></font>
</td>
</tr>
<tr></tr>
<tr>
	<th align="left" >Qualification<font color="red" >*</font></th>
	<td><input type="text" name="qualification" value="<%=DataUtility.getStringData(bean.getQualification()) %>" placeholder="Enter Qualification" >
	&nbsp;&nbsp;<font color="red" style="position:fixed" ><%=ServletUtility.getErrorMessage("qualification", request) %></font></td>
</tr>

<tr></tr>
<tr>
<th></th>
<%if(bean.getId()>0){ %>
<td><input type="submit" name="operation" value="<%=FacultyCtl.OP_UPDATE %>" style="width:77px;padding:7px" >&nbsp;&nbsp;
<input type="submit" name="operation" value="<%=FacultyCtl.OP_CANCEL %>" style="width:77px;padding:7px" ></td>
<%}else{ %>
<td><input type="submit" name="operation" value="<%=FacultyCtl.OP_SAVE %>" style="width:77px;padding:7px" >&nbsp;&nbsp;
<input type="submit" name="operation" value="<%=FacultyCtl.OP_RESET %>" style="width:77px;padding:7px" ></td>
<%} %>
</tr>
 </table>
 </form>
</div>
<%@include file="Footer.jsp" %>
</body>
</html>
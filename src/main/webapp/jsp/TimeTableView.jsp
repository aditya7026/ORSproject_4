<%@page import="java.util.LinkedHashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="in.co.rays.project_4.util.HTMLUtility"%>
<%@ page import="in.co.rays.project_4.util.ServletUtility"%>
<%@	page import="in.co.rays.project_4.util.DataUtility"%>
<%@ page import="java.util.List"%>
<%@ page import="in.co.rays.project_4.controller.TimeTableCtl"%>
<%@ page import="in.co.rays.project_4.bean.CourseBean"%>
<%@ page import="in.co.rays.project_4.bean.SubjectBean"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@	page import="java.util.Date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Time Table View</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.6/jquery.min.js"
	type="text/javascript"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"
	type="text/javascript"></script>
<link
	href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css"
	rel="Stylesheet" type="text/css" />
	 
	<!--   <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
   -->
<script>
	$(function() {
		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			minDate: +1,
			maxDate: "+1Y",
			dateFormat: "dd/mm/yy",
		});
	});
</script>
<script type="text/javascript" src="./js/calendar.js"></script>
</head>
<body>
	<%@include file="Header.jsp"%>
	<jsp:useBean id="bean" class="in.co.rays.project_4.bean.TimeTableBean"
		scope="request"></jsp:useBean>
	<div align="center">
		<h1>
			<%
				if (bean.getId() > 0) {
			%>
			Edit TimeTable
			<%
				} else {
			%>
			Add TimeTable
			<%
				}
			%>
		</h1>
		<form action="<%=ORSView.TIMETABLE_CTL%>" method="post">
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
			<table>
				<tr>
					<th align="left">Course Name<font color="red">*</font></th>
					<td><%=HTMLUtility.getList("courseId", DataUtility.getStringData(bean.getCourseId()),
					(List<CourseBean>) request.getAttribute("courseList"))%>
						&nbsp;&nbsp;<font color="red" style="position: fixed"><%=ServletUtility.getErrorMessage("courseId", request)%></font></td>
				</tr>
				<tr></tr>
				<tr>
					<th align="left">Subject Name<font color="red">*</font></th>
					<td><%=HTMLUtility.getList("subjectId", DataUtility.getStringData(bean.getSubjectId()),
					(List<SubjectBean>) request.getAttribute("subjectList"))%>
						&nbsp;&nbsp;<font color="red" style="position: fixed"><%=ServletUtility.getErrorMessage("subjectId", request)%></font></td>
				</tr>
<tr></tr>
				<tr>
					<th align="left">Semester<font color="red">*</font></th>
					<%
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

						map.put("I", "I");
						map.put("II", "II");
						map.put("III", "III");
						map.put("IV", "IV");
						map.put("V", "V");
						map.put("VI", "VI");
						map.put("VII", "VII");
						map.put("VIII", "VIII");
					%>
					<td><%=HTMLUtility.getList("semester", DataUtility.getStringData(bean.getSemester()), map)%>
						&nbsp;&nbsp;<font color="red" style="position: fixed"><%=ServletUtility.getErrorMessage("semester", request)%></font></td>
				</tr>
<tr></tr>
				<tr>
					<th align="left">Exam Date<font color="red">*</font></th>
					<td><input type="text" placeholder="Enter Date of Exam"
						name="examDate" id="datepicker"
						value="<%=DataUtility.getDateString(bean.getExamDate())%>" readonly="readonly" >
						&nbsp;&nbsp;<font color="red" style="position: fixed"><%=ServletUtility.getErrorMessage("examDate", request)%></font></td>
				</tr>
		
<tr></tr>		
				<tr>
					<th align="left">Exam Time<font color="red">*</font></th>
					
					<td>
					<%
				LinkedHashMap<String, String> tMap = new LinkedHashMap<String, String>();
				tMap.put("10:00 am to 01:00 pm", "10:00 am to 01:00 pm");
				tMap.put("01:00 pm to 04:00 pm", "01:00 pm to 04:00 pm");
				tMap.put("04:00 pm to 07:00 pm", "04:00 pm to 07:00 pm");
				
				%>
					<%=HTMLUtility.getList("examTime", DataUtility.getStringData( bean.getTime()), tMap) %> &nbsp;&nbsp;<font
						color="red" style="position: fixed"><%=ServletUtility.getErrorMessage("examTime", request)%></font></td>
				</tr>
<tr></tr>
				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td><input type="submit" name="operation"
						value="<%=TimeTableCtl.OP_UPDATE%>"
						style="width: 77px; padding: 7px">&nbsp;&nbsp; <input
						type="submit" name="operation"
						value="<%=TimeTableCtl.OP_CANCEL%>"
						style="width: 77px; padding: 7px"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=TimeTableCtl.OP_SAVE%>"
						style="width: 77px; padding: 7px">&nbsp;&nbsp; <input
						type="submit" name="operation" value="<%=TimeTableCtl.OP_RESET%>"
						style="width: 77px; padding: 7px"></td>
					<%
						}
					%>
				</tr>
			</table>
		</form>
	</div>
	<%@include file="Footer.jsp"%>>
</body>
</html>
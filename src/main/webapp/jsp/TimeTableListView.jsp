<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.LinkedHashMap"%>
    <%@ page import="in.co.rays.project_4.util.ServletUtility" %>
        <%@ page import="java.util.List" %>
    <%@page import="in.co.rays.project_4.util.DataUtility"%>
    <%@page import="java.util.Iterator"%>
    <%@page import="in.co.rays.project_4.util.HTMLUtility"%>
    <%@page import="in.co.rays.project_4.bean.TimeTableBean"%>
    <%@page import="in.co.rays.project_4.controller.TimeTableListCtl" %>
    <%@page import="in.co.rays.project_4.bean.CourseBean"%>
    <%@page import="java.util.HashMap" %>
    <%@page import="java.util.Date" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Time Table List View</title>
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
	$( function() {
		$( "#datepicker" ).datepicker({
			changeMonth: true,
			changeYear: true,
			maxDate: "+1Y",
			dateFormat: "yy-mm-dd",
		});
	} );
	</script>
	<script type="text/javascript" src="./js/calendar.js"></script>
<script type="text/javascript">
	function selectAll(source)
	{
		var checkboxes = document.getElementsByName('ids');
		  for(var i=0,n=checkboxes.length;i<n;i++) {
		    checkboxes[i].checked = source.checked;
		  }
	}
	</script>
</head>
<body>
<%@include file="Header.jsp" %>
<div align="center" >
<h1>Time Table List</h1>
<h3>
<font color="green" ><%=ServletUtility.getSuccessMessage(request) %></font>
<font color="red" ><%=ServletUtility.getErrorMessage(request)%></font>
</h3>
<form action="<%=ORSView.TIMETABLE_LIST_CTL %>" method="post" >
<label><b>Course Name : </b></label><%=HTMLUtility.getSList("courseId", DataUtility.getStringData(request.getAttribute("courseId")), (List<CourseBean>)request.getAttribute("courseList")) %>
<label>&nbsp;&nbsp;<b>Subject Name : </b></label><input type="text" name="subjectName" value="<%=DataUtility.getStringData(request.getAttribute("subjectName"))%>" placeholder="Enter Subject Name" style="font-size: 14px;padding: 6px" >
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
<label>&nbsp;&nbsp;<b>Semester : </b></label><%=HTMLUtility.getSList("semester", DataUtility.getStringData(request.getAttribute("semester")), map) %>
<label>&nbsp;&nbsp;<b>Exam Date : </b></label><input type="text" placeholder="Enter Date of Exam" name="date" id="datepicker" value="<%=DataUtility.getSDateString((Date)request.getAttribute("date")) %>" style="font-size: 14px;padding: 6px" >
&nbsp;&nbsp;<input type="submit" name="operation" value="<%=TimeTableListCtl.OP_SEARCH %>" style="width:100px;padding:7px" >
&nbsp;<input type="submit" value="<%=TimeTableListCtl.OP_RESET %>" name="operation" style="width:100px;padding:7px">
<br>
<br>
<table width="100%" border="1px" >
<tr>
<th><input type="checkbox" onClick="selectAll(this)" >Select All</th>
<th>S.No</th>
<th>Course Name</th>
<th>Subject Name</th>
<th>Semester</th>
<th>Date</th>
<th>Time</th>
<th>Edit</th>
</tr>
<%
int next = DataUtility.getInt(DataUtility.getStringData(request.getAttribute("nextlistsize")));
List<TimeTableBean> list = (List<TimeTableBean>)ServletUtility.getList(request);
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);

Iterator<TimeTableBean> it = list.iterator();
int index = ((pageNo - 1) * pageSize) + 1;

while(it.hasNext()){
	
	TimeTableBean bean = it.next();
%>
<tr>
<td align="center" ><input type="checkbox" name="ids" value="<%=bean.getId() %>" ></td>
<td align="center" ><%=index++ %></td>
<td align="center" ><%=bean.getCourseName() %></td>
<td align="center" ><%=bean.getSubjectName() %></td>
<td align="center" ><%=bean.getSemester() %></td>
<td align="center" ><%=bean.getExamDate() %></td>
<td align="center" ><%=bean.getTime() %></td>
<td align="center" ><a href="<%=ORSView.TIMETABLE_CTL%>?id=<%=bean.getId() %>" >Edit</a></td>
</tr>
<%} %>
</table>
<br>
<% 
if(list.size() ==0){
	%>
	<input type="submit" name="operation"  value="<%=TimeTableListCtl.OP_BACK %>" style="width:100px;padding:7px" >
	<%
}%>
<br>
<br>
<input type="hidden" name="pageNo" value="<%=pageNo %>">
<input type="hidden" name="pageSize" value="<%=pageSize %>">
<table width="100%" style="position:fixed;bottom: 60px" >
<tr>
<td align="left" ><input type="submit" value="<%=TimeTableListCtl.OP_PREVIOUS%>" name="operation" <%=pageNo==1?"disabled":"" %> style="width:100px;padding:7px" align="left" ></td>
<td align="center" ><input type="submit" value="<%=TimeTableListCtl.OP_NEW%>" name="operation" style="width:100px;padding:7px"  align="middle" ></td>
<td align="center" ><input type="submit" value="<%=TimeTableListCtl.OP_DELETE%>" name="operation" style="width:100px;padding:7px" align="middle" ></td>
<td align="right" ><input type="submit" value="<%=TimeTableListCtl.OP_NEXT %>" name="operation" <%=next==0?"disabled":"" %> style="width:100px;padding:7px" align="right"  ></td>
</tr>
</table>
</form>
</div>
<%@include file="Footer.jsp" %>>
</body>
</html>
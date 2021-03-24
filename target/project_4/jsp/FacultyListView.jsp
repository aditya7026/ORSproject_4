<%@page import="in.co.rays.project_4.bean.CourseBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="in.co.rays.project_4.util.ServletUtility" %>
    <%@ page import="java.util.List" %>
    <%@page import="in.co.rays.project_4.util.DataUtility"%>
    <%@page import="java.util.Iterator"%>
    <%@page import="in.co.rays.project_4.util.HTMLUtility"%>
    <%@page import="in.co.rays.project_4.bean.FacultyBean"%>
    <%@page import="in.co.rays.project_4.controller.FacultyListCtl" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Faculty List View</title>
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
<%@ include file="Header.jsp" %>
<div align="center" >
<h1>Faculty List</h1>
<h3>
<font color="green" ><%=ServletUtility.getSuccessMessage(request) %></font>
<font color="red" ><%=ServletUtility.getErrorMessage(request)%></font>
</h3>
<form action="<%=ORSView.FACULTY_LIST_CTL%>" method="post"  >
<label><b>Full Name : </b></label><%=HTMLUtility.getSList("facultyId", DataUtility.getStringData(request.getAttribute("facultyId")),(List<FacultyBean>) request.getAttribute("facultyList")) %>
&nbsp;&nbsp;<label><b>First Name : </b></label><input type="text" placeholder="Enter First Name" name="firstName" value="<%=DataUtility.getStringData(request.getAttribute("firstName")) %>" style="font-size: 14px;padding: 6px" >
&nbsp;&nbsp;<label><b>Last Name : </b></label><input type="text" placeholder="Enter Last Name" name="lastName" value="<%=DataUtility.getStringData(request.getAttribute("lastName")) %>" style="font-size: 14px;padding: 6px" >
&nbsp;&nbsp;<label><b>Course : </b></label><%=HTMLUtility.getSList("courseId", DataUtility.getStringData(request.getAttribute("courseId")),(List<CourseBean>) request.getAttribute("courseList")) %> 
&nbsp;&nbsp;<input type="submit" name="operation" value="<%=FacultyListCtl.OP_SEARCH %>" style="width:100px;padding:7px" >
&nbsp;<input type="submit" value="<%=FacultyListCtl.OP_RESET %>" name="operation" style="width:100px;padding:7px">
<br>
<br>
<table width="100%" border="1px" >
<tr>
<th><input type="checkbox" onClick="selectAll(this)" >Select All</th>
<th>S.No</th>
<th>First Name</th>
<th>Last Name</th>
<th>Gender</th>
<th>Date Of Birth</th>
<th>Qualification</th>
<th>Email</th>
<th>Mobile No.</th>
<th>College Name</th>
<th>Course Name</th>
<th>Subject Name</th>
<th>Edit</th>
</tr>
<%
int next = DataUtility.getInt(DataUtility.getStringData(request.getAttribute("nextlistsize")));
List<FacultyBean> list = (List<FacultyBean>)ServletUtility.getList(request);
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);

Iterator<FacultyBean> it = list.iterator();
int index = ((pageNo - 1) * pageSize) + 1;

while(it.hasNext()){
	
	FacultyBean bean = it.next();
%>
<tr>
<td align="center" ><input type="checkbox" name="ids" value="<%=bean.getId() %>" ></td>
<td align="center" ><%=index++ %></td>
<td align="center" ><%=bean.getFirstName() %></td>
<td align="center" ><%=bean.getLastName() %></td>
<td align="center" ><%=bean.getGender() %></td>
<td align="center" ><%=bean.getDob() %></td>
<td align="center" ><%=bean.getQualification() %></td>
<td align="center" ><%=bean.getEmailId() %></td>
<td align="center" ><%=bean.getMobileNo() %></td>
<td align="center" ><%=bean.getCollegeName() %></td>
<td align="center" ><%=bean.getCourseName() %></td>
<td align="center" ><%=bean.getSubjectName() %></td>
<td align="center" ><a href="<%=ORSView.FACULTY_CTL %>?id=<%=bean.getId() %>" >Edit</a></td>
</tr>
<%} %>
</table>
<br>
<% 
if(list.size() ==0){
	%>
	<input type="submit" name="operation"  value="<%=FacultyListCtl.OP_BACK %>" style="width:100px;padding:7px" >
	<%
}%>
<br>
<br>
<input type="hidden" name="pageNo" value="<%=pageNo %>">
<input type="hidden" name="pageSize" value="<%=pageSize %>">
<table width="100%" style="position:fixed;bottom: 60px" >
<tr>
<td align="left" ><input type="submit" value="<%=FacultyListCtl.OP_PREVIOUS%>" name="operation" <%=pageNo==1?"disabled":"" %> style="width:100px;padding:7px" align="left" ></td>
<td align="center" ><input type="submit" value="<%=FacultyListCtl.OP_NEW%>" name="operation" style="width:100px;padding:7px"  align="middle" ></td>
<td align="center" ><input type="submit" value="<%=FacultyListCtl.OP_DELETE%>" name="operation" style="width:100px;padding:7px" align="middle" ></td>
<td align="right" ><input type="submit" value="<%=FacultyListCtl.OP_NEXT %>" name="operation" <%=next==0?"disabled":"" %> style="width:100px;padding:7px" align="right"  ></td>
</tr>
</table>
</form>
</div>
<%@ include file="Footer.jsp" %>
</body>
</html>
<%@page import="in.co.rays.project_4.util.HTMLUtility"%>
<%@page import="in.co.rays.project_4.bean.CourseBean"%>
<%@page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="in.co.rays.project_4.util.ServletUtility" %>
    <%@ page import="java.util.List" %>
    <%@page import="in.co.rays.project_4.util.DataUtility"%>
    <%@page import="in.co.rays.project_4.controller.CourseListCtl" %>
    <%@page import="java.util.HashMap" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Course List View</title>
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
<form action="<%=ORSView.COURSE_LIST_CTL%>" method="post">
<div align="center" >
<h1>Course List</h1>
<h3>
<font color="green" ><%=ServletUtility.getSuccessMessage(request) %></font>
<font color="red" ><%=ServletUtility.getErrorMessage(request)%></font>
</h3>
<label><b>Course Name : </b></label><input type="text" name="courseName" placeholder="Enter Course Name" value="<%=DataUtility.getStringData(request.getAttribute("courseName")) %>" style="font-size: 14px;padding: 6px" >
<% 
	HashMap map = new HashMap();
	map.put("2 years", "2 years");
	map.put("3 years", "3 years");
	map.put("4 years", "4 years");
%>
&nbsp;&nbsp;<label><b>Duration : </b></label><%=HTMLUtility.getSList("duration", DataUtility.getStringData(request.getAttribute("duration")), map) %>
&nbsp;&nbsp;<label><b>Description : </b></label><input type="text" name="description" placeholder="Enter course Description" value="<%=DataUtility.getStringData(request.getAttribute("description")) %>" style="font-size: 14px;padding: 6px" >
&nbsp;&nbsp;<input type="submit" name="operation" value="<%=CourseListCtl.OP_SEARCH %>" style="width:100px;padding:7px" >
&nbsp;<input type="submit" value="<%=CourseListCtl.OP_RESET %>" name="operation" style="width:100px;padding:7px">
<br>
<br>
<table width="100%" border="1px" >
<tr>
<th><input type="checkbox" onClick="selectAll(this)" >Select All</th>
<th>S.No.</th>
<th>Course Name</th>
<th>Description</th>
<th>Duration</th>
<th>Edit</th>
<tr>
<%
int next = DataUtility.getInt(DataUtility.getStringData(request.getAttribute("nextlistsize")));
List<CourseBean> list = (List<CourseBean>)ServletUtility.getList(request);
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);

Iterator<CourseBean> it = list.iterator();
int index = ((pageNo - 1) * pageSize) + 1;

while(it.hasNext()){
	
CourseBean bean = it.next();
%>
<tr>
<td align="center" ><input type="checkbox" name="ids" value="<%=bean.getId() %>" ></td>
<td align="center"><%=index++ %>
<td align="center" ><%=bean.getName() %></td>
<td align="center" ><%=bean.getDescription() %></td>
<td align="center" ><%=bean.getDuration() %></td>
<td align="center" ><a href="<%=ORSView.COURSE_CTL %>?id=<%=bean.getId() %>" >Edit</a></td>
</tr>
<%} %>
</table>

<br>
<% 
if(list.size() ==0){
	%>
	<input type="submit" name="operation"  value="<%=CourseListCtl.OP_BACK %>" style="width:100px;padding:7px" >
	<%
}%>
<br>
<br>
<input type="hidden" name="pageNo" value="<%=pageNo %>">
<input type="hidden" name="pageSize" value="<%=pageSize %>">
<table width="100%" style="position:fixed;bottom: 60px" >
<tr>
<td align="left" ><input type="submit" value="<%=CourseListCtl.OP_PREVIOUS%>" name="operation" <%=pageNo==1?"disabled":"" %> style="width:100px;padding:7px" align="left" ></td>
<td align="center" ><input type="submit" value="<%=CourseListCtl.OP_NEW%>" name="operation" style="width:100px;padding:7px"  align="middle" ></td>
<td align="center" ><input type="submit" value="<%=CourseListCtl.OP_DELETE%>" name="operation" style="width:100px;padding:7px" align="middle" ></td>
<td align="right" ><input type="submit" value="<%=CourseListCtl.OP_NEXT %>" name="operation" <%=next==0?"disabled":"" %> style="width:100px;padding:7px" align="right"  ></td>
</tr>
</table>
</div>
</form>
<%@include file ="Footer.jsp" %>
</body>
</html>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project_4.util.DataUtility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.co.rays.project_4.util.HTMLUtility"%>
<%@page import="in.co.rays.project_4.controller.MarksheetListCtl"%>
<%@page import="in.co.rays.project_4.bean.MarksheetBean" %>
<%@page import="java.util.List" %>
<%@page import="in.co.rays.project_4.bean.StudentBean" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="in.co.rays.project_4.util.ServletUtility" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Marksheet List View</title>
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
<h1>Marksheet List</h1>
<h3>
<font color="green" ><%=ServletUtility.getSuccessMessage(request) %></font>
<font color="red" ><%=ServletUtility.getErrorMessage(request)%></font>
</h3>
<form action="<%=ORSView.MARKSHEET_LIST_CTL %>" method="post" >
<label><b>Student Name : </b></label><%=HTMLUtility.getSList("studentId",DataUtility.getStringData(request.getAttribute("studentId")), (List<StudentBean>) request.getAttribute("studentList")) %>
&nbsp;&nbsp;<label><b>Roll No : </b></label><input type="text" name="rollNo" value="<%=DataUtility.getStringData(request.getAttribute("rollNo")) %>" placeholder="Enter Roll No" style="font-size: 14px;padding: 6px" >
&nbsp;&nbsp;<label><b>Student Name : </b></label><input type="text" name="studentName" value="<%=DataUtility.getStringData(request.getAttribute("studentName")) %>" placeholder="Enter Name" style="font-size: 14px;padding: 6px" >

&nbsp;&nbsp;<input type="submit" value="<%=MarksheetListCtl.OP_SEARCH%>" name="operation" style="width:100px;padding:7px" >
&nbsp;<input type="submit" value="<%=MarksheetListCtl.OP_RESET %>" name="operation" style="width:100px;padding:7px" >
<br><br>
<%
List<MarksheetBean>list = new ArrayList<MarksheetBean>();
list = ServletUtility.getList(request);
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);
int next = DataUtility.getInt(DataUtility.getStringData(request.getAttribute("nextlistsize")));
int index = ((pageNo - 1) * pageSize) + 1;
Iterator<MarksheetBean>it = list.iterator();

%>
<input type="hidden" name="pageNo" value="<%=pageNo %>">
<input type="hidden" name="pageSize" value="<%=pageSize %>">

<table width="100%" border="1px" >
<tr>
<th><input type="checkbox" onClick="selectAll(this)" >Select All</th>
<th>S.No.</th>
<th>Roll No</th>
<th>Name</th>
<th>Physics</th>
<th>Chemistry</th>
<th>Mathematics</th>
<th>Total</th>
<th>Percentage</th>
<th>Result</th>
<th>Edit</th>
</tr>
<% while(it.hasNext()){
	MarksheetBean bean = it.next();
	int total  = bean.getPhysics()+bean.getChemistry()+bean.getMaths();
	float percentage = total/3;
	%>
	<tr>
	<td align="center"><input type="checkbox" name="ids" value="<%=bean.getId() %>"  ></td>
	<td align="center" ><%=index++ %></td>
	<td align="center" ><%=bean.getRollNo() %></td>
	<td align="center" ><%=bean.getName() %></td>
	<td align="center" ><%=bean.getPhysics() %></td>
	<td align="center" ><%=bean.getChemistry() %></td>
	<td align="center" ><%=bean.getMaths() %></td>
	<td align="center" ><%=total %>/300 </td>
	<td align="center" ><%=percentage %>%</td>
	<td align="center" ><%=DataUtility.result(percentage, bean.getPhysics(), bean.getChemistry(), bean.getMaths())%></td>
	<td align="center" ><a href="<%=ORSView.MARKSHEET_CTL%>?id=<%=bean.getId() %>" >Edit</a></td>
	</tr>
<%} %>
</table>

<br>
<br>
<% 
if(list.size() ==0){
	%>
	<input type="submit" name="operation"  value="<%=MarksheetListCtl.OP_BACK %>" style="width:100px;padding:7px" >
	<%
}%>
<table width="100%" style="position:fixed;bottom: 60px" >
<tr>
<td align="left" ><input type="submit" value="<%=MarksheetListCtl.OP_PREVIOUS%>" name="operation" <%=pageNo==1?"disabled":"" %> style="width:100px;padding:7px" align="left" ></td>
<td align="center" ><input type="submit" value="<%=MarksheetListCtl.OP_NEW%>" name="operation" style="width:100px;padding:7px"  align="middle" ></td>
<td align="center" ><input type="submit" value="<%=MarksheetListCtl.OP_DELETE%>" name="operation" style="width:100px;padding:7px" align="middle" ></td>
<td align="right" ><input type="submit" value="<%=MarksheetListCtl.OP_NEXT %>" name="operation" <%=next==0?"disabled":"" %> style="width:100px;padding:7px" align="right"  ></td>
</tr>
</table>
</form>
</div>
<%@include file="Footer.jsp" %>
</body>
</html>
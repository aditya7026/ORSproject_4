<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.List" %>
    <%@page import="in.co.rays.project_4.util.ServletUtility" %> 
    <%@page import="java.util.Iterator"%>
    <%@page import="in.co.rays.project_4.bean.MarksheetBean" %>
    <%@page import="java.util.ArrayList"%>
    <%@page import="in.co.rays.project_4.util.DataUtility"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Marksheet Merit List View</title>
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
<h1>
Marksheet Merit List
</h1>
<table width="100%" border="1px" >
<tr>
<th>S.No.</th>
<th>Roll No</th>
<th>Name</th>
<th>Physics</th>
<th>Chemistry</th>
<th>Math</th>
<th>Total</th>
<th>Percentage</th>
</tr>
<%
List<MarksheetBean>list = new ArrayList<MarksheetBean>();
list = ServletUtility.getList(request);
int pageNo = ServletUtility.getPageNo(request);  
int pageSize = ServletUtility.getPageSize(request);
int index = ((pageNo - 1) * pageSize) + 1;
Iterator<MarksheetBean>it = list.iterator();
while(it.hasNext()){
	MarksheetBean bean = it.next();
	int total  = bean.getPhysics()+bean.getChemistry()+bean.getMaths();
	float percentage = total/3;


%>
<tr>
	<td align="center" ><%=index++ %></td>
	<td align="center" ><%=bean.getRollNo() %></td>
	<td align="center" ><%=bean.getName() %></td>
	<td align="center" ><%=bean.getPhysics() %></td>
	<td align="center" ><%=bean.getChemistry() %></td>
	<td align="center" ><%=bean.getMaths() %></td>
	<td align="center" ><%=total %> </td>
	<td align="center" ><%=percentage %>%</td>
	</tr>
	<%} %>
</table>
</div>
<%@include file="Footer.jsp" %>
</body>
</html>
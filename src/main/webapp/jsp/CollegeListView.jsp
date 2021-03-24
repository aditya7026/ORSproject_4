<%@page import="in.co.rays.project_4.util.HTMLUtility"%>
<%@page import="in.co.rays.project_4.util.DataUtility"%>
<%@page import="in.co.rays.project_4.controller.CollegeListCtl"%>
<%@page import="in.co.rays.project_4.bean.CollegeBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>College List View</title>
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
<div align="center">
<h1>
College List
</h1>
<h3>
<font color="green" ><%=ServletUtility.getSuccessMessage(request) %></font>
<font color="red" ><%=ServletUtility.getErrorMessage(request)%></font>
</h3>
<form action="<%=ORSView.COLLEGE_LIST_CTL%>" method="post" >
<table>
<tr>
<td>
<label><b>Name : </b></label> <%=HTMLUtility.getSList("collegeId", DataUtility.getStringData(request.getAttribute("collegeId")), (List<CollegeBean>)request.getAttribute("collegeList")) %>
&nbsp;&nbsp;<label><b>State : </b></label> <input type="text" name="state" value="<%=DataUtility.getStringData(request.getAttribute("state")) %>" placeholder="Enter State" style="font-size: 14px;padding: 6px" >
&nbsp;&nbsp;<label><b>City : </b></label> <input type="text" name="city" value="<%=DataUtility.getStringData(request.getAttribute("city")) %>" placeholder="Enter City" style="font-size: 14px;padding: 6px" >
&nbsp;<input type="submit" name="operation" value="Search" style="width:100px;padding:7px" >
&nbsp;<input type="submit" value="<%=CollegeListCtl.OP_RESET %>" name="operation" style="width:100px;padding:7px" >
</td>
</tr>
</table>
<br>
<table width="100%" border="1"  >
<tr>
<th><input type="checkbox" onClick="selectAll(this)" >Select All</th>
<th>S.No.</th>
<th>Name</th>
<th>Address</th>
<th>State</th>
<th>City</th>
<th>Phone No.</th>
<th>Edit</th>
</tr>
<%
List<CollegeBean>list = new ArrayList<CollegeBean>();
list = ServletUtility.getList(request);
int next = DataUtility.getInt(DataUtility.getStringData(request.getAttribute("nextlistsize")));
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);
int index = ((pageNo - 1) * pageSize) + 1;
CollegeBean bean = new CollegeBean();
Iterator<CollegeBean>it = list.iterator();

while(it.hasNext()){
	bean = it.next();

%>
<tr>
<td align="center" ><input type="checkbox"	name="ids" value="<%=bean.getId() %>" ></td>
<td align="center" ><%=index++ %></td>
<td align="center" ><%=bean.getName() %></td>
<td align="center" ><%=bean.getAddress() %></td>
<td align="center" ><%=bean.getState() %></td>
<td align="center" ><%=bean.getCity() %></td>
<td align="center" ><%=bean.getPhNo()%></td>
<td align="center" ><a href="<%=ORSView.COLLEGE_CTL%>?id=<%=bean.getId() %>" >Edit</a></td>
</tr>
<%} %>
</table>
<br>
<% 
if(list.size() ==0){
		%>
		<input type="submit" name="operation"  value="<%=CollegeListCtl.OP_BACK %>" style="width:100px;padding:7px"  >
		<%
	}
%>
<br>
<table width="100%" style="position:fixed;bottom: 60px" >
<tr>
<td align="left" ><input type="submit" value="<%=CollegeListCtl.OP_PREVIOUS%>" name="operation" <%=pageNo==1?"disabled":"" %> style="width:100px;padding:7px" align="left" ></td>
<td align="center" ><input type="submit" value="<%=CollegeListCtl.OP_NEW%>" name="operation" style="width:100px;padding:7px"  align="middle" ></td>
<td align="center" ><input type="submit" value="<%=CollegeListCtl.OP_DELETE%>" name="operation" style="width:100px;padding:7px" align="middle" ></td>
<td align="right" ><input type="submit" value="<%=CollegeListCtl.OP_NEXT %>" name="operation" <%=next==0?"disabled":"" %> style="width:100px;padding:7px" align="right"  ></td>
</tr>
</table>
<input type="hidden" name="pageNo"  value="<%=pageNo %>"  >
<input type="hidden" name="pageSize" value="<%=pageSize %>" >

</form>
</div>
<%@include file="Footer.jsp" %>
</body>
</html>
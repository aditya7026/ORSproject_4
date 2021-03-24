<%@page import="in.co.rays.project_4.util.HTMLUtility"%>
<%@page import="in.co.rays.project_4.util.DataUtility"%>
<%@page import="in.co.rays.project_4.controller.StudentListCtl"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.project_4.bean.StudentBean"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="in.co.rays.project_4.controller.ORSView"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student List View</title>
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
<h1>Student List</h1>
<h3>
<font color="green" ><%=ServletUtility.getSuccessMessage(request) %></font>
<font color="red" ><%=ServletUtility.getErrorMessage(request)%></font>
</h3>
<form action="<%=ORSView.STUDENT_LIST_CTL %>" method="post" >
<%
List<StudentBean>clist = (List<StudentBean>) request.getAttribute("collegeList");
 String dd = HTMLUtility.getSList("collegeId", DataUtility.getStringData(request.getAttribute("collegeId")), clist);
%>
<label><b>College Name : </b></label>
<%=dd %>
&nbsp;&nbsp;<label><b>First Name : </b></label> <input type="text" name="firstName" placeholder="Enter First Name" value="<%=DataUtility.getStringData(request.getAttribute("firstName")) %>" style="font-size: 14px;padding: 6px" >
&nbsp;&nbsp;<label><b>Last Name : </b></label> <input type="text" name="lastName" placeholder="Enter Last Name" value="<%=DataUtility.getStringData(request.getAttribute("lastName")) %>" style="font-size: 14px;padding: 6px" >

&nbsp;&nbsp;<input type="submit" name="operation" value="<%=StudentListCtl.OP_SEARCH %>" style="width:100px;padding:7px" > 
&nbsp;<input type="submit" value="<%=StudentListCtl.OP_RESET %>" name="operation" style="width:100px;padding:7px" >
<br>
<br>
<table width="100%" border="1" >
<tr>
<th><input type="checkbox" onClick="selectAll(this)" >Select All</th>
<th>S.No.</th>
<th>College</th>
<th>First Name</th>
<th>Last Name</th>
<th>DOB</th>
<th>Mobile No.</th>
<th>Email</th>
<th>Edit</th>
</tr>
<% 
	List<StudentBean>list= ServletUtility.getList(request);
	  int pageNo = ServletUtility.getPageNo(request);
	int pageSize = ServletUtility.getPageSize(request);
	int index = ((pageNo - 1) * pageSize) + 1;
	int next = DataUtility.getInt(DataUtility.getStringData(request.getAttribute("nextlistsize")));
	Iterator<StudentBean>it = list.iterator();
	while(it.hasNext()){
	StudentBean bean = it.next();	
%>
<tr>
<td align="center"><input type="checkbox" name="ids" value="<%=bean.getId() %>"  ></td>
<td align="center" ><%=index++ %></td>
<td align="center" ><%=bean.getCollegeName() %></td>
<td align="center" ><%=bean.getFirstName() %></td>
<td align="center" ><%=bean.getLastName() %></td>
<td align="center" ><%=DataUtility.getDateString(bean.getDob()) %></td>
<td align="center" ><%=bean.getMobileNo() %></td>
<td align="center" ><%=bean.getEmail() %></td>
<td align="center" ><a href="<%=ORSView.STUDENT_CTL%>?id=<%=bean.getId() %>" >Edit</a></td>
</tr>
<% }%>
</table>
<br>
<br>
<% 
if(list.size() ==0){
	%>
	<input type="submit" name="operation"  value="<%=StudentListCtl.OP_BACK %>" style="width:100px;padding:7px" >
	<%
}%>
<input type="hidden" name="pageNo" value="<%=pageNo %>" >
<input type= "hidden" name="pageSize" value="<%=pageSize%>">
<br>
<br>
<table width="100%" style="position:fixed;bottom: 60px" >
<tr>
<td align="left" ><input type="submit" value="<%=StudentListCtl.OP_PREVIOUS%>" name="operation" <%=pageNo==1?"disabled":"" %> style="width:100px;padding:7px" align="left" ></td>
<td align="center" ><input type="submit" value="<%=StudentListCtl.OP_NEW%>" name="operation" style="width:100px;padding:7px"  align="middle" ></td>
<td align="center" ><input type="submit" value="<%=StudentListCtl.OP_DELETE%>" name="operation" style="width:100px;padding:7px" align="middle" ></td>
<td align="right" ><input type="submit" value="<%=StudentListCtl.OP_NEXT %>" name="operation" <%=next==0?"disabled":"" %> style="width:100px;padding:7px" align="right"  ></td>
</tr>
</table>
</form>
</div>
<%@include file="Footer.jsp" %>
</body>
</html>
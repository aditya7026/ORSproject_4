
<%@page import="in.co.rays.project_4.util.HTMLUtility"%>
<%@page import="in.co.rays.project_4.util.DataUtility"%>
<%@page import="in.co.rays.project_4.model.UserModel"%>
<%@page import="in.co.rays.project_4.model.RoleModel"%>
<%@page import="in.co.rays.project_4.controller.UserListCtl"%>
<%@page import="in.co.rays.project_4.controller.ORSView"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="in.co.rays.project_4.bean.UserBean" %>    
<%@ page import="in.co.rays.project_4.util.ServletUtility" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User List View</title>
<script type="text/javascript">
	function selectAll(source)
	{
		var checkboxes = document.getElementsByName('ids');
		  for(var i=0,n=checkboxes.length;i<n;i++) {
			 /* if(checkboxes.item(i)==1){
				  alert(checkboxes.item(i));
				  continue;
			  }*/
		    checkboxes[i].checked = source.checked;
		  }
	}
	</script>
</head>
<body>
<%@include file="Header.jsp" %>

<div align="center" >
<h1>User List</h1>
<h3>
<font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
</h3>
<%int next = DataUtility.getInt(DataUtility.getStringData(request.getAttribute("nextlistsize")));%>


<form action="<%=ORSView.USER_LIST_CTL%>" method="post" >
<table>
<tr>
<td align="center" >
<%
List<RoleBean> l = (List<RoleBean>)request.getAttribute("roleList");
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);
%>
<label><b>Role : </b></label>
<%=HTMLUtility.getSList("role", DataUtility.getStringData(request.getAttribute("role")) , l)  %>&nbsp;&nbsp;
<label><b>First Name : </b></label> <input type="text" name="firstName" placeholder="Enter First Name" 
					value="<%=DataUtility.getStringData(request.getAttribute("firstName"))%>" style="font-size: 14px;padding: 6px" >
&nbsp;&nbsp;<label><b>Login : </b></label> <input type="text" name="login" placeholder="Enter Last Name" 
					value="<%=DataUtility.getStringData(request.getAttribute("login"))%>" style="font-size: 14px;padding: 6px" >
&nbsp;&nbsp;

<input type="hidden" name="pageNo" value="<%=pageNo %>">
<input type="hidden" name="pageSize" value="<%=pageSize %>">
<input type="submit" value="<%=UserListCtl.OP_SEARCH%>" name="operation" style="width:100px;padding:7px" >
&nbsp;&nbsp;
<input type="submit" value="<%=UserListCtl.OP_RESET %>" name="operation" style="width:100px;padding:7px"  >
</td>
</tr>
</table>
<br>
<table border="1" width="100%">
<tr>
<th><input type="checkbox" onClick="selectAll(this)" >Select All</th>
<th>S.no</th>
<th>First Name</th>
<th>Last Name</th>
<th>Login ID</th>
<th>Gender</th>
<th>Mobile No.</th>
<th>DOB</th>
<th>Edit</th>
</tr>
<%	
	
	int index = ((pageNo - 1) * pageSize) + 1;
	List<UserBean>list = new ArrayList<UserBean>();
	list = ServletUtility.getList(request);
	
	Iterator<UserBean>it = list.iterator();
	UserBean bean = new UserBean();
	
	
	while(it.hasNext()){
	bean = it.next();

%>
	<tr>
	<td align="center" ><input type="checkbox" name="ids" value="<%=bean.getId()%>" 
	<%if(bean.getRoleId()==RoleBean.ADMIN){%><%="disabled"%><% } %> ></td>
	<td align="center" ><%=index++ %></td>
	<td align="center" ><%=bean.getFirstName()%></td>
	<td align="center" ><%=bean.getLastName()%></td>
	<td align="center" ><%=bean.getLogin()%></td>
	<td align="center" ><%=bean.getGender()%></td>
	<td align="center" ><%=bean.getMobileNo()%></td>
	<td align="center" ><%=DataUtility.getDateString(bean.getDob())%></td>
	<td align="center" ><%if(bean.getRoleId()!=RoleBean.ADMIN){ %><a href="<%=ORSView.USER_CTL%>?id=<%=bean.getId()%>" >Edit</a><%}else{ %>---<%} %></td>
	</tr>
<%} %>
</table>
<br>
<% 
if(list.size() ==0){
		%>
		<input type="submit" name="operation"  value="<%=UserListCtl.OP_BACK %>" style="width:100px;padding:7px"  >
		<%
	}
%>
<br>
<table width="100%" style="position:fixed;bottom: 60px" >
<tr>
<td align="left" ><input type="submit" value="<%=UserListCtl.OP_PREVIOUS%>" name="operation" <%=pageNo==1?"disabled":"" %> style="width:100px;padding:7px" align="left" ></td>
<td align="center" ><input type="submit" value="<%=UserListCtl.OP_NEW%>" name="operation" style="width:100px;padding:7px"  align="middle" ></td>
<td align="center" ><input type="submit" value="<%=UserListCtl.OP_DELETE%>" name="operation" style="width:100px;padding:7px" align="middle" ></td>
<td align="right" ><input type="submit" value="<%=UserListCtl.OP_NEXT %>" name="operation" <%=next==0?"disabled":"" %> style="width:100px;padding:7px" align="right"  ></td>
</tr>
</table>
</form>
</div>
<%@ include file="Footer.jsp" %>
</body>
</html>
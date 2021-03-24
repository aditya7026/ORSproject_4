<%@page import="in.co.rays.project_4.util.HTMLUtility"%>
<%@page import="javax.swing.text.html.HTML"%>
<%@page import="in.co.rays.project_4.util.DataUtility"%>
<%@page import="in.co.rays.project_4.util.ServletUtility"%>
<%@page import="in.co.rays.project_4.controller.ORSView"%>
<%@page import="in.co.rays.project_4.bean.RoleBean"%>
<%@page import="in.co.rays.project_4.controller.RoleListCtl" %>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Role List View</title>
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
<h1>Role List</h1>
<h3>
<font color="red" ><%=ServletUtility.getErrorMessage(request) %></font>
<font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
</h3>
<%%>
<form action="<%=ORSView.ROLE_LIST_CTL%>" method="post" >
<%List roleList = (List)request.getAttribute("roleList"); %>
<table>
<tr>
<td><label><b>Role : </b></label> <%=HTMLUtility.getSList("roleId", DataUtility.getStringData(request.getAttribute("roleId")), roleList) %></td>
<td>&nbsp;&nbsp;<label><b>Description : </b></label>
 <input type="text" name="desc" placeholder="Enter Description" 
			value="<%=DataUtility.getStringData(request.getAttribute("desc")) %>" style="font-size: 14px;padding: 6px" ></td>
<td>&nbsp;&nbsp;<input type="submit" value="<%=RoleListCtl.OP_SEARCH %>" name="operation" style="width:100px;padding:7px" >
&nbsp;&nbsp;<input type="submit" value="<%=RoleListCtl.OP_RESET %>" name="operation" style="width:100px;padding:7px" align="middle" ></td>
</tr>
</table>
<br>
<table border="1px" width="100%" >
<tr>
<th><input type="checkbox" onClick="selectAll(this)" >Select All</th>
<th>S.No.</th>
<th>Role</th>
<th>Description</th>
<th>Edit</th>
</tr>
<%
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);
int index = ((pageNo - 1) * pageSize) + 1;
int next = DataUtility.getInt(DataUtility.getStringData(request.getAttribute("nextlistsize")));
List<RoleBean>list = new ArrayList<RoleBean>();
list = ServletUtility.getList(request);
RoleBean bean = new RoleBean(); 
Iterator<RoleBean>it = list.iterator();
while(it.hasNext()){
	bean = it.next();

%>
<tr>
<td align="center"><input type="checkbox" name="ids" value="<%=bean.getId()%>" <%if(bean.getId()==RoleBean.ADMIN){%><%="disabled"%><% } %>>
</td>
<td align="center" ><%=index++ %></td>
<td align="center" ><%=bean.getName() %></td>
<td align="center" ><%=bean.getDescription() %></td>
<td align="center" ><%if(bean.getId()!=RoleBean.ADMIN){ %><a href="<%=ORSView.ROLE_CTL%>?id=<%=bean.getId()%>" >Edit</a> <%}else{ %>---<%} %></td>
</tr>
<%
	}
%>
</table>
<br>
<% 
if(list.size() ==0){
		%>
		<input type="submit" name="operation"  value="<%=RoleListCtl.OP_BACK %>" style="width:100px;padding:7px"  >
		<%
	}
%>
<br>
<table width="100%" style="position:fixed;bottom: 60px" >
<tr>
<td align="left" ><input type="submit" value="<%=RoleListCtl.OP_PREVIOUS%>" name="operation" <%=pageNo==1?"disabled":"" %> style="width:100px;padding:7px" align="left" ></td>
<td align="center" ><input type="submit" value="<%=RoleListCtl.OP_NEW%>" name="operation" style="width:100px;padding:7px"  align="middle" ></td>
<td align="center" ><input type="submit" value="<%=RoleListCtl.OP_DELETE%>" name="operation" style="width:100px;padding:7px" align="middle" ></td>
<td align="right" ><input type="submit" value="<%=RoleListCtl.OP_NEXT %>" name="operation" <%=next==0?"disabled":"" %> style="width:100px;padding:7px" align="right"  ></td>
</tr>
</table>
<input type="hidden" name="pageNo" value="<%=pageNo %>" >
<input type="hidden" name="pageSize" value="<%=pageSize %>" >
</form>
</div>
<%@include file="Footer.jsp" %>
</body>
</html>
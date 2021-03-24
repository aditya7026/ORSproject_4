<%@page import="in.co.rays.project_4.util.HTMLUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
   <%@page import="in.co.rays.project_4.util.DataUtility" %>
   <%@page import="in.co.rays.project_4.util.ServletUtility" %>
   <%@page import="in.co.rays.project_4.controller.MarksheetCtl" %>
   <%@page import="in.co.rays.project_4.bean.StudentBean" %>
   <%@page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Marksheet View</title>
</head>
<body>
<%@include file="Header.jsp" %>
<jsp:useBean id="bean" class="in.co.rays.project_4.bean.MarksheetBean" scope="request"></jsp:useBean>
<div align="center" >
<h1>
<%if(bean.getId()>0){ %>
Edit Marksheet
<%}else{ %>
Add Marksheet
<%} %>
</h1>
<h3>
<font color="green" ><%=ServletUtility.getSuccessMessage(request) %></font>
<font color="red" ><%=ServletUtility.getErrorMessage(request)%></font>
</h3>
<form action="<%=ORSView.MARKSHEET_CTL%>" method="post">
			<input type="hidden" name="id" value="<%=bean.getId()%>"> 
			<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
			<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

<table>
<tr>
<th align="left">Roll No<font color="red">*</font></th>
<td><input type="text" name="rollNo" value="<%=DataUtility.getStringData(bean.getRollNo())%>" placeholder="Enter Roll No" <%=(bean.getId() > 0) ? "readonly" :""%> >
&nbsp;&nbsp; <font color="red" style="position:fixed"><%=ServletUtility.getErrorMessage("rollNo", request) %></font></td>
</tr>
<tr></tr>
<tr>
<th align="left">Name<font color="red">*</font></th>
<% String dd = HTMLUtility.getList("studentId", DataUtility.getStringData(bean.getStudentId()), (List<StudentBean>)request.getAttribute("list")); %>
<td><%=dd %>
&nbsp;&nbsp; <font color="red" style="position:fixed"><%=ServletUtility.getErrorMessage("studentId", request) %></font></td>
</tr>
<tr></tr>
<tr>
<th align="left">Physics<font color="red">*</font></th>
<td><input type="text" name="physics" value="<%=DataUtility.getStringData(bean.getPhysics())%>" placeholder="Enter Physics Marks" >
&nbsp;&nbsp; <font color="red" style="position:fixed"><%=ServletUtility.getErrorMessage("physics", request) %></font></td>
</tr>
<tr></tr>
<tr>
<th align="left">Chemistry<font color="red">*</font></th>
<td><input type="text" name="chemistry" value="<%=DataUtility.getStringData(bean.getChemistry())%>" placeholder="Enter Chemistry Marks" >
&nbsp;&nbsp; <font color="red" style="position:fixed"><%=ServletUtility.getErrorMessage("chemistry", request) %></font></td>
</tr>
<tr></tr>
<tr>
<th align="left">Mathematics<font color="red">*</font></th>
<td><input type="text" name="maths" value="<%=DataUtility.getStringData(bean.getMaths())%>" placeholder="Enter Maths Marks" >
&nbsp;&nbsp; <font color="red" style="position:fixed"><%=ServletUtility.getErrorMessage("maths", request) %></font></td>
</tr>
<tr></tr>
<tr>
<th></th>
<%if(bean.getId()>0){ %>
<td><input type="submit" name="operation" value="<%=MarksheetCtl.OP_UPDATE %>" style="width:77px;padding:7px" >&nbsp;&nbsp;
<input type="submit" name="operation" value="<%=MarksheetCtl.OP_CANCEL %>" style="width:77px;padding:7px" ></td>
<%}else{ %>
<td><input type="submit" name="operation" value="<%=MarksheetCtl.OP_SAVE %>" style="width:77px;padding:7px" >&nbsp;&nbsp;
<input type="submit" name="operation" value="<%=MarksheetCtl.OP_RESET %>" style="width:77px;padding:7px" ></td>
<%} %>
</tr>
</table>
</form>
</div>
<%@include file="Footer.jsp" %>
</body>
</html>
<%@page import="in.co.rays.project_4.bean.StudentBean"%>
<%@page import="in.co.rays.project_4.util.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="in.co.rays.project_4.controller.GetMarksheetCtl" %>
    <%@page import="in.co.rays.project_4.util.ServletUtility" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Get Marksheet</title>
</head>
<body>
<%@include file="Header.jsp" %>
<jsp:useBean id="bean" class="in.co.rays.project_4.bean.MarksheetBean" scope="request"></jsp:useBean>
<div align="center" >
<h1>
Get Marksheet
</h1>
<h3>
<font color="green" ><%=ServletUtility.getSuccessMessage(request) %></font>
<font color="red" ><%=ServletUtility.getErrorMessage(request)%></font>
</h3>
<form action="<%=ORSView.GET_MARKSHEET_CTL%>" method="post" >
<label><b>Roll No. </b></label> <input type="text" name="rollNo" placeholder="Enter Roll No" value="<%=DataUtility.getStringData(bean.getRollNo())%>" >

<input type="submit" name="operation" value="<%=GetMarksheetCtl.OP_GO %>" >
<input type="submit" name="operation" value="<%=GetMarksheetCtl.OP_RESET %>" >
&nbsp;&nbsp;<font color="red" style="position:fixed"><%=ServletUtility.getErrorMessage("rollNo", request) %></font></td>
<%if((bean.getId())>0){ %>
<br>
<br>
<% int physics = Integer.parseInt(DataUtility.getStringData(bean.getPhysics()));
                int chemistry = Integer.parseInt(DataUtility.getStringData(bean.getChemistry()));
                int maths = Integer.parseInt(DataUtility.getStringData(bean.getMaths()));
                int total = physics+chemistry+maths; 
        		String phyremark = DataUtility.subResult(physics);
        		String cheremark = DataUtility.subResult(chemistry);
        		String mathremark = DataUtility.subResult(maths);
        		float per = total/3;
        		String res = DataUtility.result(per, physics, chemistry, maths);
        		StudentBean sBean = (StudentBean)request.getAttribute("sBean");
        		%>
<table align="center" border="1" width="70%" height="60%">
					<tr>
					<td colspan="5" align="center" >
                		<font align="center"><h1><%=sBean.getCollegeName() %></h1></font>
                	
                		<font align="center"  ><h2>Marksheet</h2></font>
                	
                		<font><b>Roll No : </b><%=DataUtility.getStringData(bean.getRollNo()) %>&nbsp;&nbsp;
                		<b>Student Name : </b><%=DataUtility.getStringData(bean.getName()) %></font><br><br>
                	</td>
                	</tr>
                		
                	<tr align="center">
                		<td  rowspan="2" style="width: 20%; font-weight: bold;">Subject</td>
                		<td  rowspan="2" style="width: 20%; font-weight: bold;">Maximum Marks</td>
                		<td  rowspan="2" style="width: 20%; font-weight: bold;">Passing Marks</td>
                		<td  rowspan="2" style="width: 20%; font-weight: bold;">Marks Obtained</td>
                		<td  rowspan="2" style="width: 20%; font-weight: bold;" >Remark</td>
                	</tr>
                	
                	<tr align="center">
                		
                	</tr>
                	
                	<tr align="center">
                		<td>Physics</td>
                		<td>100</td>
                		<td>35</td>
                		<td><%=bean.getPhysics()%></td>
                		<td><%=phyremark %></td>
                	</tr>
                	
                	<tr align="center">
                		<td>Chemistry</td>
                		<td>100</td>
                		<td>35</td>
                		<td><%=bean.getChemistry()%></td>
                		<td><%=cheremark %></td>
                	</tr>
                	
                	<tr align="center">
                		<td>Maths</td>
                		<td>100</td>
                		<td>35</td>
                		<td><%=bean.getMaths()%></td>
                		<td><%=mathremark %></td>
                	</tr>
                	
                	<tr align="center">
                		<td style="font-weight: bold;">Total</td>
                		<td>300</td>
                		<td>105</td>
                		<td colspan="2" style="font-weight: bold;"><%=total%></td>
                	</tr>
                	
                	<tr>
                		<td colspan="5" align="center" >
                		<b>Grand Total : </b><%=total %> Out of 300&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                		<b>Percentage : </b><%=per %>%&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                		<b>Result : </b><%=res %></td>
                	</tr>
                
                </table>
<%} %>
</form>
</div>
<%@include file="Footer.jsp" %>
</body>
</html>
<%@page import="in.co.rays.project_4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page isErrorPage="true" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error</title>
</head>
<body>
<div align="center" >
<h1><font color="red" >An Unexpected error has Occured please try again later</font></h1>
<b><a href="<%=ORSView.LOGIN_CTL %>" >Take me back to login page</a></b>
</div>
</body>
</html>
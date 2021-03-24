<%@page import="in.co.rays.project_4.bean.RoleBean" %>
<%@page import="in.co.rays.project_4.controller.LoginCtl" %>
<%@page import="in.co.rays.project_4.bean.UserBean" %>
<%@page import="in.co.rays.project_4.controller.ORSView" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	UserBean userBean = (UserBean)session.getAttribute("user");
	
	boolean userLoggedIn = userBean != null;
	
	String welcomeMsg = "Hi, ";

    if (userLoggedIn) {
        String role = (String) session.getAttribute("role");
        welcomeMsg += userBean.getFirstName() + " (" + role + ")";
    } else {
        welcomeMsg += "Guest";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
      <meta charset = "utf-8">
<body>
	<table border="0" align="left" width="100%" >
	<tr>
	<td><a href="<%=ORSView.WELCOME_CTL%>" ><b>Welcome</b></a>&nbsp;&#124;
	<%
	if(userLoggedIn){
	%>
	&nbsp;<a href="<%=ORSView.LOGIN_CTL %>?operation=<%=LoginCtl.OP_LOG_OUT%>" ><b>Logout</b></a>
	<%} else { %>
	&nbsp;<a href="<%=ORSView.LOGIN_CTL %>" ><b>Login</b></a></td>
	<%} %>
	<td rowspan="2" align="right">
		<h1 align="right" >
		<img align="right" src="<%=ORSView.APP_CONTEXT%>/img/customLogo.png">
		</h1>
		
	</td>
	</tr>
	
	<tr>
        <td >
            <h3>
                <%=welcomeMsg%>
            </h3>
        </td>
    </tr>
    <% if(userLoggedIn){ %>              
    <tr>
    <% if(userBean.getRoleId() ==  RoleBean.STUDENT){ %>
    <td>
    <b>
    	&nbsp;<a href="<%=ORSView.MY_PROFILE_CTL%>">MyProfile</a>&nbsp;&#124;
    	&nbsp;<a href="<%=ORSView.GET_MARKSHEET_CTL%>">Get Marksheet</a>&nbsp;&#124;
    	&nbsp;<a href="<%=ORSView.CHANGE_PASSWORD_CTL%>">Change Password</a>&nbsp;&#124;
    	&nbsp;<a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL %>" >Marksheet Merit List</a>&nbsp;&#124;
    	</b>
    </td>
    <% }else if(userBean.getRoleId() == RoleBean.ADMIN){ %>
    <td>
    	<b>
    	&nbsp;<a href="<%=ORSView.MY_PROFILE_CTL%>">MyProfile</a>&nbsp;&#124;
    	&nbsp;<a href="<%=ORSView.CHANGE_PASSWORD_CTL%>">Change Password</a>&nbsp;&#124;
    	&nbsp;<a href="<%=ORSView.USER_CTL%>">Add User</a>&nbsp;&#124;
    	&nbsp;<a href="<%=ORSView.USER_LIST_CTL%>">User List</a>&nbsp;&#124;
    	&nbsp;<a href="<%=ORSView.ROLE_CTL%>">Add Role</a>&nbsp;&#124;
    	&nbsp;<a href="<%=ORSView.ROLE_LIST_CTL%>">Role List</a>&nbsp;&#124;
    	&nbsp;<a href="<%=ORSView.COLLEGE_CTL%>">Add College</a>&nbsp;&#124;
    	&nbsp;<a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</a>&nbsp;&#124;
    	&nbsp;<a href="<%=ORSView.STUDENT_CTL%>">Add Student</a>&nbsp;&#124;
    	&nbsp;<a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</a>&nbsp;&#124;
    	&nbsp;<a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL %>" >Marksheet Merit List</a>&nbsp;&#124;<br>
    	&nbsp;<a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</a>&nbsp;&#124;
    	&nbsp;<a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</a>&nbsp;&#124;
    	&nbsp;<a href="<%=ORSView.GET_MARKSHEET_CTL%>">Get Marksheet</a>&nbsp;&#124;
    	
    	&nbsp;<a href="<%=ORSView.COURSE_CTL %>" >Add Course</a>&nbsp;&#124;
    	&nbsp;<a href="<%=ORSView.COURSE_LIST_CTL %>" >Course List</a>&nbsp;&#124;
    	&nbsp;<a href="<%=ORSView.SUBJECT_CTL %>" >Add Subject</a>&nbsp;&#124;
    	&nbsp;<a href="<%=ORSView.SUBJECT_LIST_CTL %>" >Subject List</a>&nbsp;&#124;
    	&nbsp;<a href="<%=ORSView.FACULTY_CTL %>" >Add Faculty</a>&nbsp;&#124;
    	&nbsp;<a href="<%=ORSView.FACULTY_LIST_CTL %>" >Faculty List</a>&nbsp;&#124;
    	&nbsp;<a href="<%=ORSView.TIMETABLE_CTL %>" >Add TimeTable</a>&nbsp;&#124;
    	&nbsp;<a href="<%=ORSView.TIMETABLE_LIST_CTL %>" >TimeTable List</a>&nbsp;&#124;
    	&nbsp;<a  href="<%=ORSView.JAVA_DOC_VIEW%>" target="blank"  >Java doc</a>&nbsp;&#124;
    	</b>
    </td>
    <%} %>
    </tr>
    <% } %>
    <tr><th></th></tr>
    <tr><th></th></tr>
    <tr><th></th></tr>
    <tr><th></th></tr>
    <tr><th></th></tr>
    <tr><th></th></tr>
    <tr><th></th></tr>
    <tr><th></th></tr>
	</table>
	<br>
	<hr width="100%">

</body>
</html>
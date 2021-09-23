<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="in.co.sunrays.proj4.bean.RoleBean"%>
<%@page import="in.co.sunrays.proj4.controller.LoginCtl"%>
<%@page import="in.co.sunrays.proj4.controller.ORSView"%>
<%@page import="in.co.sunrays.proj4.bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>

</head>
</head>

<body>

	<%
		UserBean userBean = (UserBean) session.getAttribute("user");
		boolean userLoggedIn = userBean != null;

		String welcomeMsg = "Hi, ";

		if (userLoggedIn) {

			String prefix = null;

			if (userBean.getGender().equalsIgnoreCase("male")) {

				prefix = "Mr";

			}
			if (userBean.getGender().equalsIgnoreCase("female")) {

				prefix = "Miss";
			}

			String role = (String) session.getAttribute("role");
			//	System.out.println("this is from header Role " + role);

			welcomeMsg += prefix + " " + userBean.getFirstName() + " " + "(" + role + ")";
		} else {

			welcomeMsg += "Guest";
		}
	%>
	<table border="0" width="100%">
		<tr>
			<td><a href="<%=ORSView.WELCOME_CTL%>">Welcome</b></a> | <%
				if (userLoggedIn) {
			%> <a
				href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>">Logout</b></a>
				<%
					} else {
				%><a href="<%=ORSView.LOGIN_CTL%>">Login</b></a> <%
 	}
 %></td>
			<td position="fixed" rowspan="2"><img align="right"
				src="<%=ORSView.APP_CONTEXT%>/image/Logo.jpg" width="130"
				height="50"></td>
		</tr>
		<tr>
			<td><b><%=welcomeMsg%> </b> <%
 	if (userLoggedIn) {
 %>
				</div></td>

		</tr>
		<tr>
			<td colspan="2"><a href="<%=ORSView.MY_PROFILE_CTL%>">My
					Profile</a>| <a href="<%=ORSView.CHANGE_PASSWORD_CTL%>">Change
					Password</a>| <a href="<%=ORSView.JAVA_DOC_VIEW%>" target="blank">Java Doc</a> | <a
				href="<%=ORSView.GET_MARKSHEET_CTL%>">Get Marksheet</b>
			</a> | <a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">Marksheet
					Merit List</b>
			</a> | <%
				if (userBean.getRoleId() == RoleBean.ADMIN) {
			%> <a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a> | <a
				href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> | <a
				href="<%=ORSView.USER_CTL%>">Add User</b></a> | <a
				href="<%=ORSView.USER_LIST_CTL%>">User List</b></a> | <a
				href="<%=ORSView.COLLEGE_CTL%>">Add College</b></a> | <a
				href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> | <a
				href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> | <a
				href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> 
				| <a href="<%=ORSView.COURSE_CTL %>">Add Course </b></a>
				
				 
				| <a href="<%=ORSView.SUBJECT_Ctl %>">Add Subject </b></a>
	
				| <a href="<%=ORSView.COURSE_LIST_CTL %>">Course List </b></a>

				| <a href="<%=ORSView.SUBJECT_LIST_CTL %>">Subject List </b></a>
				
				
				
				| <a
				href="<%=ORSView.FACULTY_CTL%>">Add Faculty</b></a> | <a
				href="<%=ORSView.FACULTY_LIST_CTL%>"> Faculty List</a>| <a
				href="<%=ORSView.ROLE_CTL%>">Add Role</b></a> | <a
				href="<%=ORSView.ROLE_LIST_CTL%>">Role List</b></a> | <a
				href="<%=ORSView.TIMETABLE_CTL%>"> Add TimeTable</a> | <a
				href="<%=ORSView.TIMETABLELIST_CTL%>"> Time Table List</a> <%
 	}

 		if (userBean.getRoleId() == RoleBean.Faculty) {
 %> <a href="<%=ORSView.STUDENT_CTL%>"> Add Student</a> | <a
				href="<%=ORSView.STUDENT_LIST_CTL%>"> StudentList </a> <%
 	}

 	}
 %></td>
		</tr>
	</table>

	<hr>

</body>
</html>
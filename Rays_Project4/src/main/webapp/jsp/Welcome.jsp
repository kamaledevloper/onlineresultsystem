<%@page import="in.co.sunrays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Rays ORS</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>

	<form action="<%=ORSView.WELCOME_CTL%>">

		<%@include file="Header.jsp"%>

		<h1 align="Center">

			<font size="10px" color="red"> Welcome to ORS</font>

		</h1>
		<%
			UserBean beanUserBean = (UserBean) session.getAttribute("user");

			if (beanUserBean != null) {

				if (beanUserBean.getRoleId() == RoleBean.STUDENT) {
		%>

		<h2 align="Center">

			<a href="<%=ORSView.GET_MARKSHEET_CTL%>"> Click here to see your
				mark sheet </a>

		</h2>
		<%
			}
			}
		%>


	</form>

	<%@include file="Footer.jsp"%>

</body>
</html>
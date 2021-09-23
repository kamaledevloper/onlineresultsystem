<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forget password</title>
</head>
<body>

	<%@include file="Header.jsp"%>
	<center>

		<form action="<%=ORSView.FORGET_PASSWORD_CTL%>" method="post">

			<h1>Forget Your password?</h1>

			<label><font style=""> Enter your Registred email ID
					We will send Your password on email &nbsp;&nbsp; </font> </label>


			<table>
				<br>
				<br>
				<tr>
					<input type="text" name="email" placeholder="enter valid email id"
						size="25">
				</tr>
				&nbsp;
				<tr>
					<input type="submit" value="Go" name="operation">
				</tr>
				<h1>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
				</h1>
				<h1>
					<font color="Green"> <%=ServletUtility.getSuccessMessage(request)%></font>
				</h1>

			</table>



			<%@include file="Footer.jsp"%>
		</form>

	</center>
</body>
</html>
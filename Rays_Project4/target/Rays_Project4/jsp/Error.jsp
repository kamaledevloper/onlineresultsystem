<%@page import="in.co.sunrays.proj4.controller.WelcomeCtl"%>
<%@page import="in.co.sunrays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<title>Sorry !! Some thing wrong...</title>
</head>
<body>

	<div class=" container p-3 text-center">


		<img src="/Rays_Project4/image/error.jpg" class="img-fluid"
			align="middle" width="680" height="600">



		<p><%=exception%></p>

		<a href="<%=ORSView.WELCOME_CTL%>">GO Back</a>


	</div>


</body>
</html>
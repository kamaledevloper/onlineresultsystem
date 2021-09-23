<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.controller.ORSView"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Rays ORS</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>

	<form action="<%=ORSView.LOGIN_CTL%>" method="post">

		<%@include file="Header.jsp"%>


		<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.UserBean"
			scope="request"></jsp:useBean>

		<input type="hidden" name="URI"
			value="<%=request.getAttribute("URI")%>">


		<center>

			<h1>Login</h1>
			<H3>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
			</H3>
			<H3>
				<font color="Green"> <%=ServletUtility.getSuccessMessage(request)%></font>
			</H3>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">

			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%> ">

			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>
				<h4>


					<%
						if (request.getAttribute("message") != null) {
					%>
					<h4 style="color: red">
						<%=request.getAttribute("message")%></h4>

					<%
						}
					%>



					<h4>
						<tr>

							<th align="right">LoginId<span style="color: red">*</span></th>
							<td><input type="text" name="login"
								placeholder="Enter Vallid Login-ID" size=25
								value="<%=DataUtility.getStringData(bean.getLogin())%>"></td>
							<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("login", request)%>
							</font></td>



						</tr>



						<tr>
							<th style="padding: 3px"></th>
						</tr>
						<tr>
							<th></th>
						</tr>

						<tr>
							<th align="right">Password<span style="color: red">*</span></th>
							<td><input type="password" name="password"
								placeholder="Enter Vallid Password" size=25
								value="<%=DataUtility.getStringData(bean.getPassword())%>">
							</td>
							<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
						</tr>


						<th></th>


						<td colspan="2"><input type="submit" name="operation"
							value="<%=LoginCtl.OP_SIGN_IN%>"> &nbsp; <input
							type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_UP%>">
							&nbsp</td>
						</tr>
						<tr>
							<th></th>

							<td><a href="<%=ORSView.FORGET_PASSWORD_CTL%>"> <b>
										Forget my password </b>


							</a>&nbsp;</td>
						</tr>
			</table>
		</center>

		<%@include file="Footer.jsp"%>
	</form>

</body>
</html>
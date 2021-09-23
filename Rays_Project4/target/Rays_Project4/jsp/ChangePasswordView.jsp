<%@page import="in.co.sunrays.proj4.controller.ChangePasswordCtl"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Change password</title>
</head>
<body>
	<form action="<%=ORSView.CHANGE_PASSWORD_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.UserBean"
			scope="request"></jsp:useBean>

		<center>
			<h1>Change Password</h1>
			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
			</H2>
			</div>
			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>
				<tr>
					<th align="right">Old Password <span style="color: red;">*</span></th>
					<td><input type="password" name="oldPassword"
						placeholder="Enter Old Password" size="25"
						value=<%=DataUtility.getStringData(request.getParameter("oldPassword"))%>>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("oldPassword", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="right">New Password <span style="color: red;">*</span></th>
					<td><input type="password" name="newPassword"
						placeholder="Enter New Password" size="25"
						value=<%=DataUtility.getStringData(request.getParameter("newPassword"))%>>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("newPassword", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="right">Confirm Password <span style="color: red;">*</span></th>
					<td><input type="password" name="confirmPassword"
						placeholder="Re-Enter New Password" size="25"
						value=<%=DataUtility.getStringData(request.getParameter("confirmPassword"))%>>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
				<tr>
					<th></th>
					<td colspan="2">&nbsp; <input type="submit" name="operation"
						value="<%=ChangePasswordCtl.OP_SAVE%>"> <input
						type="submit" name="operation"
						value="<%=ChangePasswordCtl.OP_CHANGE_MY_PROFILE%>">
						&nbsp;
					</td>
				</tr>

			</table>
			
	</form>
	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj4.controller.UserCtl"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="Header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User</title>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1980:2020',
			dateFormat : 'mm/dd/yy'
		});
	});
</script>


</head>
<body>

	<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.UserBean"
		scope="request"></jsp:useBean>

	<form action="<%=ORSView.USER_CTL%>" method="post">
		<center>

			<%
				List l = (List) request.getAttribute("roleList");

				//System.out.println("This is from User view "+l);
			%>



			<%
				if (bean != null && bean.getId() > 0) {
			%>


			<h1>Edit User</h1>
			<%
				} else {
			%>

			<h1>Add User</h1>
			<%
				}
			%>

			<div align="center">
				<H2>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
				</H2>

				<H2>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
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

					<th align="right">First Name <span style="color: red">*</span></th>
					<td><input type="text" name="firstName" placeholder="First Name" size="25"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>">
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>

				</tr>

				<tr>
					<th align="right">Last Name<span style="color: red">*</span></th>
					<td><input type="text" name="lastName" size="25" placeholder="Last Name"
						value="<%=DataUtility.getStringData(bean.getLastName())%>">
					</td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>

				</tr>
				<tr>
					<th align="right">LoginId<span style="color: red">*</span></th>
					<td><input type="text" name="login" size="25" placeholder="Email_ID"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"
						<%=(bean.getId() > 0) ? "readonly" : ""%>></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
				</tr>
				<tr>
					<th align="right">Password<span style="color: red">*</span></th>
					<td><input type="password" name="password" size="25" placeholder="Abc@123"
						value="<%=DataUtility.getStringData(bean.getPassword())%>">

					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>
				<tr>
					<th align="right">Confirm Password<span style="color: red">*</span></th>
					<td><input type="password" name="Abc@123" size="25" placeholder="Confirm_password"
						value="<%=DataUtility.getStringData(bean.getPassword())%>">

					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%></font></td>
				</tr>
				<tr>
					<th align="right">Gender <span style="color: red">* </span></th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Male", "Male");
							map.put("Female", "Female");

							String htmlList = HTMLUtility.getList("gender", bean.getGender(), map);
						%> <%=htmlList%>
					</td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%></font></td>
				</tr>
				<tr>


					<th align="right">Role <span style="color: red">*</span>
					</th>

					<td><%=HTMLUtility.getList("rolename", String.valueOf(bean.getRoleId()), l)%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("rolename", request)%></font></td>
				</tr>
				<tr>
					<th align="right">Date Of Birth <span style="color: red">*</span></th>

					<td><input type="text" id="datepicker" name="dob" size="25"
						maxlength="10" placeholder="DD/MM/YY" size="19.5"
						value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
			</table>


			<div align="center">
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				<label> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
					&nbsp; &nbsp; &nbsp; <input type="submit" name="operation"
					value="<%=UserCtl.OP_SAVE%>">
				</label> <label> <input type="submit" name="operation"
					value="<%=UserCtl.OP_BACK%>"></label>


				<%
					} else {
				%>


				<label> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
					&nbsp; &nbsp; &nbsp; <input type="submit" name="operation"
					value="<%=UserCtl.OP_SAVE%>">
				</label> <label> <input type="submit" name="operation"
					value="<%=UserCtl.OP_CANCEL%>">
				</label>
				<%
					}
				%>

			</div>
			<%@ include file="Footer.jsp"%>
	</form>

</body>

</html>
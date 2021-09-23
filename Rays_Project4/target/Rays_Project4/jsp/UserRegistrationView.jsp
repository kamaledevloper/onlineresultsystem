<%@page import="in.co.sunrays.proj4.controller.UserRegistrationCtl"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.controller.ORSView"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>User Registration</title>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">



<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	var d = new Date();
	function disableSunday(d) {
		var day = d.getDay();
		if (day == 0) {
			return [ false ];
		} else {
			return [ true ];
		}
	}

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


	<form action=" <%=ORSView.USER_REGISTRATION_CTL%>" method="post">

		<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.UserBean"
			scope="request"></jsp:useBean>
		<%@include file="Header.jsp"%>

		<center>
			<h1>User Registration</h1>

			<h2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
			</h2>

			<h2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
			</h2>


			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime" value="<%=bean.getCreatedDatetime()%>">

			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>
				<tr>
					<th align="right">First Name <span style="color: red">*</span></th>

					<td><input type="text" name="firstName" size="25"
						placeholder="Your Name"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>">
					</td>
					<td style="position: fixed;"><font color="red "> <%=ServletUtility.getErrorMessage("firstName", request)%></font>
					</td>
				</tr>
				<tr>
					<th align="right">Last Name <span style="color: red">*</span></th>
					<td><input type="text" name="lastName" placeholder="Last Name"
						size="25"
						value="<%=DataUtility.getStringData(bean.getLastName())%>">


					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>
				<tr>
					<th align="right">LoginId <span style="color: red">*</span></th>
					<td><input type="text" name="login" size="25"
						placeholder="Must be Email ID"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
				</tr>
				<tr>
					<th align="right">Password <span style="color: red">*</span>
					</th>
					<td><input type="password" name="password" size="25"
						placeholder="Abc@123"
						value="<%=DataUtility.getStringData(bean.getPassword())%>">
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>
				<tr>
					<th align="right">Confirm Password <span style="color: red">*</span></th>
					<td><input type="password" name="confirmPassword" size="25"
						placeholder="Same as pass"
						value="<%=DataUtility.getStringData(bean.getConfirmPassword())%>">


					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%></font>
					</td>
				</tr>
				<tr>
					<th align="right">Gender <span style="color: red">*</span></th>
					<td>
						<%
							HashMap map = new HashMap();

							map.put("Male", "Male");
							map.put("Female", "Female");
							System.out.print("Gender" + bean.getGender());
							String htmlList = HTMLUtility.getList("gender", bean.getGender(), map);
						%> <%
 	System.out.println("this is from User registration view: " + htmlList);
 %> <%=htmlList%></td>
					<td style="position: fixed;"><font color="red" aline="left"><%=ServletUtility.getErrorMessage("gender", request)%></font>
					</td>

				</tr>
				<tr>
					<th align="right">Date Of Birth<font color="red">*</font></th>
					<td><input name="dob" id="datepicker" placeholder="DD/MM/YYYY"
						size=25 value="<%=DataUtility.getDateString(bean.getDob())%>">
					</td>
					<td style="position: fixed;"><font style="position: fixed;"
						color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font>
					</td>
				</tr>

				<tr>
					<th align="right">Mobile No. <span style="color: red">*</span></th>
					<td><input type="text" name="mobileNo" size="25"
						placeholder="Enter mobile number"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("MobileNo", request)%></font>
					</td>
				</tr>
				<tr>

					<th></th>
					<td colspan="2">&nbsp; &nbsp; &nbsp; <input type="submit"
						name="operation" value="<%=UserRegistrationCtl.OP_SIGN_UP%>">
						<input type="submit" name="operation"
						value="<%=UserRegistrationCtl.OP_RESET%>">
					</td>
				</tr>
			</table>
		</center>
	</form>

	<%@include file="Footer.jsp"%>
</body>
</html>
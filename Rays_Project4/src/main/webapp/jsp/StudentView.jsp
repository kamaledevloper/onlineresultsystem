<%@page import="in.co.sunrays.proj4.controller.StudentCtl"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.bean.CollegeBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student</title>

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
			yearRange : '1980:2021',
			dateFormat : 'mm/dd/yy'
		});
	});
</script>



</head>
<body>

	<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.StudentBean"
		scope="request"></jsp:useBean>

	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.STUDENT_CTL%>" method="post">

		<%
			List<CollegeBean> clist = (List<CollegeBean>) request.getAttribute("collegeList");

			System.out.println("this is from list get attribute " + clist);
		%>
		<center>

			<h1>

				<tr>

					<%
						if (bean.getId() != 0 && bean != null) {
					%>

					<th><font>Edit Student</font></th>


					<%
						} else {
					%>
					<th><font>Add Student</font></th>

					<%
						}
					%>


				</tr>


			</h1>
			<div>
				<h1>
					<font style="color: green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h1>
				<h1>
					<font style="color: red"><%=ServletUtility.getErrorMessage(request)%></font>
				</h1>
			</div>
			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdby" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedby"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createddatetime" value="<%=bean.getCreatedDatetime()%>">
			<input type="hidden" name="modifieddatetime"
				value="<%=bean.getModifiedDatetime()%>">


			<table>

				<tr>
					<th align="right">CollegeName <span style="color: red">*</span></th>

					<td ><%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeId()), clist)%>
					</td>

					<td style="position: fixed"> <font color="red"><%=ServletUtility.getErrorMessage("collegeId", request)%></font>
					</td>

				</tr>
				<%
					System.out.println("this is from College Option :" + clist);
				%>

				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="right">FirstName <span style="color: red">*</span></th>
					<td><input type="text" name="firstName"
						placeholder="Enter First Name" size="25"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("firstName", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="right">LastName <span style="color: red">*</span></th>
					<td><input type="text" name="lastName"
						placeholder="Enter Last Name" size="25"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("lastName", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="right">MobileNo <span style="color: red">*</span></th>
					<td><input type="text" name="mobileNo" maxlength="10"
						placeholder="Enter Mobile No" size="25"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("mobileNo", request)%></font>
					</td>
				</tr>

				<tr>
					<th align="right">Date of Birth <span style="color: red">*</span>
					</th>

					<td><input type="text" id="datepicker" name="dob"
						maxlength="10" placeholder="DD/MM/YY" size="25"
						value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("dob", request)%></font>
					</td>
				</tr>


				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="right">Email-Id <span style="color: red">*</span></th>
					<td><input type="text" name="email"
						placeholder="Enter Email_Id" size="25"
						value="<%=DataUtility.getStringData(bean.getEmail())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("email", request)%></font>
					</td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
			</table>

			<table>


				<tr>

					<%
						if (bean.getId() > 0 && bean != null) {
					%>
					<td><input type="submit" name="operation"
						value="<%=StudentCtl.OP_UPDATE%>"></td>


					<td><input type="submit" name="operation"
						value="<%=StudentCtl.OP_BACK%>"></td>



					<%
						} else {
					%>

					<td><input type="submit" name="operation"
						value="<%=StudentCtl.OP_SAVE%>"></td>


					<td><input type="submit" name="operation"
						value="<%=StudentCtl.OP_CANCEL%>"></td>
					<%
						}
					%>

				</tr>


			</table>

			<%@include file="Footer.jsp"%>
	</form>
</body>
</html>
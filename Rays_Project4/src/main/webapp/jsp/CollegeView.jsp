<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="in.co.sunrays.proj4.controller.CollegeCtl"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>College</title>
</head>
<body>
	<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.CollegeBean"
		scope="request"></jsp:useBean>
	<form action="<%=ORSView.COLLEGE_CTL%>" method="post">

		<%@ include file="Header.jsp"%>



		<%
			if (bean != null && bean.getId() > 0) {
		%>


		<h1 align="center">Edit College</h1>


		<%
			} else {
		%>


		<h1 align="center">Add College</h1>

		<%
			}
		%>
		</h1>


		<H2 align="center">
			<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
		</H2>
		<H2 align="center">
			<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
		</H2>


		<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
			type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
		<input type="hidden" name="modifiedBy"
			value="<%=bean.getModifiedBy()%>"> <input type="hidden"
			name="createdDatetime"
			value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
		<input type="hidden" name="modifiedDatetime"
			value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

		<table align="center">
			<tr>
				<th align="right">Name <span style="color: red">*</span></th>
				<td><input type="text" name="name"
					placeholder="Enter College Name" size="25px"
					value="<%=DataUtility.getStringData(bean.getName())%>"></td>
				<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
			</tr>



			<tr>
				<th align="right">Address <span style="color: red">*</span></th>
				<td><input type="text" name="address"
					placeholder="Enter College Address" size="25px"
					value="<%=DataUtility.getStringData(bean.getAddress())%>">
				</td>
				<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("address", request)%></font></td>
			</tr>

			<tr>
				<th align="right">State <span style="color: red">*</span></th>
				<td><input type="text" name="state" placeholder="Enter State"
					size="25px" value="<%=DataUtility.getStringData(bean.getState())%>"></td>
				<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("state", request)%></font></td>
			</tr>

			<tr>
				<th align="right">City <span style="color: red">*</span></th>
				<td><input type="text" name="city" placeholder="Enter City"
					size="25px" value="<%=DataUtility.getStringData(bean.getCity())%>">
				</td>
				<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("city", request)%></font></td>
			</tr>

			<tr>
				<th align="right">MobileNo <span style="color: red">*</span></th>
				<td><input type="text" name="phoneNo"
					placeholder="Enter Mobile No" maxlength="10" size="25px"
					value="<%=DataUtility.getStringData(bean.getPhoneNo())%>">
				</td>
				<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("phoneNo", request)%></td>
			</tr>


		</table>


		<table align="center">


			<tr>

				<th></th>


				<%
					if (bean != null && bean.getId() > 0) {
				%>
				<td colspan="2"><input type="submit" name="operation"
					value="<%=CollegeCtl.OP_SAVE%>"></td>
				<td colspan="2"><input type="submit" name="operation"
					value="<%=CollegeCtl.OP_BACK%>"></td>



				<%
					} else {
				%>

				<td colspan="2"><input type="submit" name="operation"
					value="<%=CollegeCtl.OP_SAVE%>"></td>
				<td colspan="2"><input type="submit" name="operation"
					value="<%=CollegeCtl.OP_CANCEL%>"></td>
				<%
					}
				%>
			</tr>


		</table>
		<%@ include file="Footer.jsp"%>
	</form>


</body>
</html>
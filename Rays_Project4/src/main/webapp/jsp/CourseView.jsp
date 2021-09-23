<%@page import="in.co.sunrays.proj4.controller.CourseCtl"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<body>
	<center>
		<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.CourseBean"
			scope="request"></jsp:useBean>
		<form action="<%=ORSView.COURSE_CTL%>" method="post">
			<%@include file="Header.jsp"%>

			<center>
				<h1>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th>Update Course</th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th>Add Course</th>
					</tr>
					<%
						}
					%>
				</h1>
				<div align="center">
					<h2>
						<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
					</h2>
					<h2>
						<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					</h2>
				</div>

				<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
					type="hidden" name="createdby" value="<%=bean.getCreatedBy()%>">
				<input type="hidden" name="modifiedby"
					value="<%=bean.getModifiedBy()%>"> <input type="hidden"
					name="createdDatetime"
					value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
				<input type="hidden" name="modifiedDatetime"
					value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

				<table>
					<tr>
						<th align="right">Course Name <span style="color: red">*</span></th>
						<td><input type="text" name="name"
							placeholder="Enter Course Name" size="25"
							value="<%=DataUtility.getStringData(bean.getName())%>"></td>
						<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font>
						</td>
					</tr>
					<tr>
						<th style="padding: 3px"></th>
					</tr>

					<th align="right">Description <span style="color: red">*</span></th>
					<td><input type="text" name="description"
						placeholder="Enter Description" size="25"
						value="<%=DataUtility.getStringData(bean.getDescription())%>">
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("description", request)%></font>
					</td>
					</tr>
					<tr>
						<th style="padding: 3px"></th>
					</tr>
					<tr>
						<th></th>
						<%
							if (bean.getId() > 0) {
						%>
						<td><input type="submit" name="operation"
							value="<%=CourseCtl.OP_UPDATE%>"> <input type="submit"
							name="operation" value="<%=CourseCtl.OP_CANCEL%>"></td>
						<%
							} else {
						%>
						<td><input type="submit" name="operation"
							value="<%=CourseCtl.OP_SAVE%>"> <input type="submit"
							name="operation" value="<%=CourseCtl.OP_RESET%>"></td>
						<%
							}
						%>
					</tr>
				</table>
		</form>
	</center>

	<%@include file="Footer.jsp"%>
</body>
</body>

</html>
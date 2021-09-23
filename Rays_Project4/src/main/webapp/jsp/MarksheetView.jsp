<%@page import="in.co.sunrays.proj4.util.DataValidator"%>
<%@page import="in.co.sunrays.proj4.controller.MarksheetCtl"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MarkSheet</title>
</head>
<body>


	<form action="<%=ORSView.MARKSHEET_CTL%>" method="post">
		<%@include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.MarksheetBean"
			scope="request"></jsp:useBean>

		<center>
			<%
				List l = (List) request.getAttribute("studentList");
			%>


			<%
				if (bean != null && bean.getId() > 0) {
			%>
			<h1>Edit Marksheet</h1>

			<%
				} else {
			%>
			<h1>Add Marksheet</h1>
			<%
				}
			%>

			<h3>

				<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>

			</h3>

			<h3>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>


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
					<th align="right">Rollno<span style="color: red">* </span></th>
					<td><input type="text" name="rollNo" placeholder="00EX0000"
						size="25" value="<%=DataUtility.getStringData(bean.getRollNo())%>"
						<%if (bean.getId() > 0) {%> readonly="readonly" <%} else {
			}%>></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("rollNo", request)%></font>
					</td>
				</tr>

				<tr>
					<th align="right">Name <span style="color: red">* </span></th>
					<td><%=HTMLUtility.getList("studentId", String.valueOf(bean.getStudentId()), l)%></td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("studentId", request)%></font>
					</td>


				</tr>


				<tr>
					<th align="right">Physics <span style="color: red">* </span></th>
					<td><input type="text" name="physics" size="25" placeholder ="Enter_phy_marks"
						value="<%=String.valueOf( DataUtility.getStringData(bean.getPhysics()))%>">

					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("physics", request)%></font></td>
				</tr>
				<tr>
					<th align="right">Chemistry<span style="color: red">* </span></th>
					<td><input type="text" name="chemistry" size="25" placeholder ="Enter_Chemi_marks"
						value="<%=DataUtility.getStringData(bean.getChemistry())%>">

					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("chemistry", request)%></font></td>

				</tr>
				<tr>
					<th align="right">Maths<span style="color: red">* </span></th>
					<td><input type="text" name="maths" size="25" placeholder ="Enter_Maths_marks"
						value="<%=DataUtility.getStringData(bean.getMaths())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("maths", request)%></font></td>

				</tr>


			</table>

			<%
				if (bean != null && bean.getId() > 0) {
			%>
<table>
			<td><input type="submit" name="operation"
				value="<%=MarksheetCtl.OP_UPDATE%>"> <input type="submit"
				name="operation" value="<%=MarksheetCtl.OP_CANCEL%>"> <%
 	} else {
 %> <input type="submit" name="operation"
				value="<%=MarksheetCtl.OP_SAVE%>"> <input type="submit"
				name="operation" value="<%=MarksheetCtl.OP_RESET%>"></td>
			<%
				}
			%>
</table>
		</center>
	</form>
	<%@ include file="Footer.jsp"%>
</body>
</html>
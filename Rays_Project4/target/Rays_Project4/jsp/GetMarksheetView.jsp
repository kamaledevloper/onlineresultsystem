<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.bean.MarksheetBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.controller.GetMarksheetCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Marksheet</title>

</head>

<body>

	<%@include file="Header.jsp"%>
	<h2>View Marksheet</h2>

	<form action="<%=ORSView.GET_MARKSHEET_CTL%>" method="post">

		<center>


			<label>Enter Roll No <span style="color: red">*</span>

			</label>
			<lable> <input type="text" name="rollno"></lable>
			<lable> <input type="submit" name="operation"
				value="<%=GetMarksheetCtl.OP_GO%>"> </lable>
			<lable> <input type="submit" name="operation"
				value="<%=GetMarksheetCtl.OP_RESET%>"> </lable>

			<h3>
				<font color="red"><%=DataUtility.getString(ServletUtility.getErrorMessage(request))%>

				</font>
			</h3>

			<h3>
				<font color="red"><%=DataUtility.getString(ServletUtility.getSuccessMessage(request))%>

				</font>
			</h3>
			<br>
			<%
				List list = (List<MarksheetBean>) ServletUtility.getList(request);

				if (list != null && list.size() > 0) {
			%>


			<%
				MarksheetBean bean = new MarksheetBean();
					Iterator it = list.iterator();
			%>

			<%
				while (it.hasNext()) {
						bean = (MarksheetBean) it.next();
			%>
			<table width="100%" border=".75">
				<tr style="border: 0">
					<th style="border: 0"><font size="6"> Marksheet</th>
				</tr>
			</table>
			<table width="30%" border="0">
				<tr style="border: 0">
					<th>Rollnumber:</th>
					<th><%=bean.getRollNo()%></th>
					<th>Student Name:</th>
					<th><%=bean.getName()%></th>
				</tr>

			</table>
			<table width="100%" border="1">
				<tr>
					<th>Subject</th>
					<th>Maximum Marks</th>
					<th>Passing Marks</th>
					<th>Marks Obtained</th>
					<th>Remark</th>
				<tr align="center">
					<td>Maths</td>
					<td>100</td>
					<td>33</td>
					<td><%=bean.getMaths()%></td>

					<%
						if (bean.getMaths() >= 33) {
					%>
					<td>Pass</td>
					<%
						} else {
					%>

					<td>Fail</td>
					<%
						}
					%>
				</tr>

				<tr align="center">
					<td>Physis</td>
					<td>100</td>
					<td>33</td>
					<td><%=bean.getPhysics()%></td>
					<%
						if (bean.getPhysics() >= 33) {
					%>
					<td>Pass</td>
					<%
						} else {
					%>

					<td>Fail</td>
					<%
						}
					%>



				</tr>


				<tr align="center">
					<td>Chemistry</td>
					<td>100</td>
					<td>33</td>
					<td><%=bean.getChemistry()%></td>
					<%
						if (bean.getChemistry() >= 33) {
					%>
					<td>Pass</td>
					<%
						} else {
					%>

					<td>Fail</td>
					<%
						}
					%>


				</tr>


				<tr>

					<th>Total</th>
					<th><%=300%></th>
					<th>99</th>

					<%
						Integer obtainmarks = bean.getPhysics() + bean.getChemistry() + bean.getMaths();
					%>


					<th><%=obtainmarks%></th>


					<%
						if (obtainmarks > 99) {
					%>

					<th style="color: blue">Pass</th>
					<%
						} else

								{
					%>
					<th style="color: red;">Fail</th>
					<%
						}
					%>

				</tr>
			</table>

			<table width="100%" border=".75">


				<th>
					<%
						if (obtainmarks >= 99) {
					%> Congratulations PASS  <%
						} else {
					%>FAIL  <%
						}
					%> Percentage:<%=obtainmarks * 100 / 300%>%
				</th>
			</table>


			<%
				}
			%>


			<%
				}
			%>






		</center>

	</form>

	<%@include file="Footer.jsp"%>
</body>
</html>
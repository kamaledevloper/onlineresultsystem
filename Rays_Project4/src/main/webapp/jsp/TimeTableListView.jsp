<%@page import="in.co.sunrays.proj4.bean.SubjectBean"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="javax.swing.text.html.HTML"%>
<%@page import="in.co.sunrays.proj4.controller.TimeTableListCtl"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.bean.TimeTableBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
			yearRange : '1980:2025',
			dateFormat : 'mm/dd/yy'
		});
	});

	$(document).ready(function() {
		$("#selectall").click(function() {
			if (this.checked) {
				$('.checkboxall').each(function() {
					$(".checkboxall").prop('checked', true);
				})
			} else {
				$('.checkboxall').each(function() {
					$(".checkboxall").prop('checked', false);
				})
			}
		});
	});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Time Table List</title>
</head>
<body>
	<center>

		<form method="post" action="<%=ORSView.TIMETABLELIST_CTL%>">
			<%@ include file="Header.jsp"%>

			<h2>TimeTable List</h2>

			<h3>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>
			<h3>
				<font color="red"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>


			<%
				int pageNo = ServletUtility.getPageNo(request);

				int pageSize = ServletUtility.getPageSize(request);

				int intex = ((pageNo - 1) * pageSize) + 1;
			%>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">



			<%
				List tlist = (List) ServletUtility.getList(request);
				int next = DataUtility.getInt(DataUtility.getStringData(request.getAttribute("nextListSize")));

				if (tlist.size() > 0 && tlist != null) {
			%>

			<lable>Extam Date </lable>
			<input type="text" name="examdate" placeholder="MM/DD/YY"
				id="datepicker">
			<lable>Subject </lable>

			<%
				List list = (List) request.getAttribute("slist");

					SubjectBean bean1 = new SubjectBean();
			%>

			<%=HTMLUtility.getList("subject", String.valueOf(bean1.getSubjectName()), list)%>

			<lable> <input type="submit" name="operation"
				value="<%=TimeTableListCtl.OP_SEARCH%>"> <input
				type="submit" name="operation"
				value="<%=TimeTableListCtl.OP_RESET%>"> <br>
			<br>
			<table border="1" class="center" width="100%">
				<tr align="center">

					<th><input type="checkbox" id="selectall" class="css-checkbox"
						name="selectall"> Select all</th>
					<th>SNo.</th>
					<th>Semester</th>
					<th>Course Name</th>
					<th>Subject Name</th>
					<th>Exam Date</th>
					<th>Exam Time</th>
					<th>Edit</th>
				</tr>
				<%
					Iterator it = tlist.iterator();
						TimeTableBean bean = new TimeTableBean();

						while (it.hasNext()) {

							bean = (TimeTableBean) it.next();
				%>

				<tr align="center">
					<td align="center"><input type="checkbox" class="checkboxall"
						name="ids" value="<%=bean.getId()%>"></td>
					<td><%=intex++%></td>
					<td><%=bean.getSemester()%></td>
					<td><%=bean.getCourseName()%></td>
					<td><%=bean.getSubjectName()%></td>
					<td><%=DataUtility.getDateString(bean.getExamDate())%></td>
					<td><%=bean.getExamTime()%></td>
					<td><a href="TimeTableCtl?id=<%=bean.getId()%>">Edit </a></td>
				</tr>

				<%
					}
				%>
			</table>
			<br>

			<table width="100%" >
				<tr>


					<th>
						<%
							if (pageNo <= 1) {
						%> <input type="submit" disabled="disabled"
						value="<%=TimeTableListCtl.OP_PREVIOUS%>" name="operation">

					</th>
					<%
						} else {
					%>
					<th> <input type="submit"
						value="<%=TimeTableListCtl.OP_PREVIOUS%>" name="operation">
					</th>
					<%
						}
					%>
					<th> <input type="submit" name="operation"
						value="<%=TimeTableListCtl.OP_DELETE%>">
						
						<input type="submit"
						name="operation" value="<%=TimeTableListCtl.OP_NEW%>">
						
						 </th>
					<%
						if (tlist.size() == 0 || next == 0) {
					%>
					<th> <input type="submit" disabled="disabled"
						value="<%=TimeTableListCtl.OP_NEXT%>" name="operation">

					</th>
					<%
						} else {
					%>
					<th> <input type="submit"
						value="<%=TimeTableListCtl.OP_NEXT%>" name="operation">
					</th>
					<%
						}
					%>
					<%
						}
						if (tlist.size() == 0) {
					%>
					<th> <input type="submit"
						value="<%=TimeTableListCtl.OP_BACK%>" name="operation">
					</th>
					<%
						}
					%>

				</tr>
			</table>
		</form>
		<%@ include file="Footer.jsp"%>
	</center>
</body>
</html>


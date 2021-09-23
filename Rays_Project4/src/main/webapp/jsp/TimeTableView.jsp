<%@page import="in.co.sunrays.proj4.controller.TimeTableCtl"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.bean.TimeTableBean"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Time Table</title>



<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

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

		$('#datepicker').datepicker({
			minDate : 0,
			beforeShowDay : disableSunday,
			//beforeDateShowDay : $.datepicker.noWeekends,
			changeMonth : true,
			changeYear : true,
			//	yearRange : '1980:2020',
			dateFormat : 'mm/dd/yy'

		});
	});
</script>


</head>
<body>
	<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.TimeTableBean"
		scope="request"></jsp:useBean>

	<form action="<%=ORSView.TIMETABLE_CTL%>" method="post">
		<%@include file="Header.jsp"%>
		<center>
			<%
				List<TimeTableBean> courseList = (List<TimeTableBean>) request.getAttribute("CourseList");
				List<TimeTableBean> subjectList = (List<TimeTableBean>) request.getAttribute("SubjectList");
			%>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdby" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedby"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createddatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedby"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<div align="center">
				<h1>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th>Update TimeTable</th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th>Add TimeTable</th>
					</tr>
					<%
						}
					%>
				</h1>
				<h2 align="center">
					<font style="color: red"><%=ServletUtility.getErrorMessage(request)%></font>
				</h2>
				<h2 align="center">
					<font style="color: green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h2>
			</div>

			<table>
				<tr>
					<th align="right">Course <span style="color: red">*</span></th>
					<td><%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), courseList)%></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("courseId", request)%></font>
					</td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="right">Subject <span style="color: red">*</span></th>
					<td><%=HTMLUtility.getList("subjectId", String.valueOf(bean.getSubjectId()), subjectList)%></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("subjectId", request)%></font>
					</td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="right">Semester<span style="color: red">*</span></th>

					<%
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

						map.put("1st", "1st");
						map.put("2st", "2st");
						map.put("3st", "3st");
						map.put("4st", "4st");
						map.put("5st", "5st");
						map.put("6st", "6st");
						map.put("7st", "7st");
						map.put("8st", "8st");

						String htmlList = HTMLUtility.getList("semester", String.valueOf(bean.getSemester()), map);
					%>
					<td><%=htmlList%></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("semester", request)%></font>
					</td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="right">Exam Date <span style="color: red">*</span></th>
					<td><input type="text" id="datepicker" size="25"
						placeholder="Select Date" name="ExDate"
						value="<%=DataUtility.getDateString(bean.getExamDate())%>">
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("ExDate", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="right">Exam Time <span style="color: red">*</span></th>
					<td>
						<%
							LinkedHashMap<String, String> map1 = new LinkedHashMap<String, String>();
							map1.put("08:00 AM to 11:00 AM", "08:00 AM to 11:00 AM");
							map1.put("12:00 PM to 03:00 PM", "12:00 PM to 03:00 PM");
							map1.put("04:00 PM to 07:00 PM", "04:00 PM to 07:00 PM");

							String htmlList1 = HTMLUtility.getList("ExTime", bean.getExamTime(), map1);
						%> <%=htmlList1%>
					</td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("ExTime", request)%></font>
					</td>
				</tr>


				<tr>
					<th style="padding: 3px"></th>
					<td></td>
				</tr>

				<tr>
					<th></th>
					<td></td>
				</tr>

				<tr align="center">
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=TimeTableCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=TimeTableCtl.OP_CANCEL%>"></td>
					<%
						} else {
					%>

					<td colspan="2"><input type="submit" name="operation"
						value="<%=TimeTableCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=TimeTableCtl.OP_RESET%>"></td>
					<%
						}
					%>
				</tr>
			</table>

		</center>
		<%@include file="Footer.jsp"%>
	</form>
</body>
</html>
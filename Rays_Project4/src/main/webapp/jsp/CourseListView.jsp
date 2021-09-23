<%@page import="in.co.sunrays.proj4.bean.CourseBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj4.model.CourseModel"%>
<%@page import="in.co.sunrays.proj4.controller.CourseListCtl"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<title>Course List</title>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
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
</head>
<body>

	<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.CourseBean"
		scope="request"></jsp:useBean>
	<form action="<%=ORSView.COURSE_LIST_CTL%>" method="post">
		<%@include file="Header.jsp"%>
		<%
			List<CourseBean> clist = (List<CourseBean>) request.getAttribute("CourseList");
		%>
		<center>
			<div align="center">
				<h1>Course List</h1>
				<h2>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h2>
			</div>
			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = (pageNo - 1) * pageSize + 1;
				List list = ServletUtility.getList(request);
				Iterator<CourseBean> it = list.iterator();
				if (list.size() != 0) {
			%>
			<table width="100%" align="center">

				<tr>

					<td align="center"><label>Course Name: </label> <%=HTMLUtility.getList("id", String.valueOf(bean.getId()), clist)%>
						<input type="submit" name="operation"
						value="<%=CourseListCtl.OP_SEARCH%>"> &emsp; <input
						type="submit" name="operation" value="<%=CourseListCtl.OP_RESET%>">
					</td>
				</tr>
			</table>
			<br>
			<table border="1" width="100%" align="center">

				<tr>
					<th><input type="checkbox" name="selectall" id="selectall"
						class="css-checkbox">Select All</th>
					<th>S.No</th>
					<th>Course Name</th>
					<th>Description</th>
					<th>Edit</th>
				</tr>
				<%
					while (it.hasNext()) {
							bean = it.next();
				%>

				<tr align="center">

					<td><input type="checkbox" name="ids" class="checkboxall"
						value="<%=bean.getId()%>"></td>

					<td><%=index++%></td>
					<td><%=bean.getName()%></td>
					<td><%=bean.getDescription()%></td>
					<td><a href="CourseCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>
			<table width="100%">
				<tr>
					<%
						if (pageNo == 1) {
					%>
					<td><input type="submit" name="operation" disabled="disabled"
						value="<%=CourseListCtl.OP_PREVIOUS%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=CourseListCtl.OP_PREVIOUS%>"></td>
					<%
						}
					%>
					<%
						CourseModel model = new CourseModel();
					%>
					<%
						if (list.size() < pageSize || model.nextPk() - 1 == bean.getId()) {
								System.out.println("-------------------" + bean.getId());
					%>
					<td align="right"><input type="submit" name="operation"
						disabled="disabled" value="<%=CourseListCtl.OP_NEXT%>"></td>
					<%
						} else {
					%>
					<td align="right"><input type="submit" name="operation"
						value="<%=CourseListCtl.OP_NEXT%>"></td>
					<%
						}
					%>
				</tr>
			</table>
			<%
				}
				if (list.size() == 0) {
			%>
			<td align="center"><input type="submit" name="operation"
				value="<%=CourseListCtl.OP_BACK%>"></td>
			<%
				}
			%>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
			</table>
		</center>
	</form>
</body>
</html>
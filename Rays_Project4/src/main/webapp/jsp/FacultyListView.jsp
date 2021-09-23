<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.bean.FacultyBean"%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.controller.FacultyListCtl"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page errorPage="Error.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

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

	<jsp:useBean id="beanf" class="in.co.sunrays.proj4.bean.FacultyBean"
		scope="request"></jsp:useBean>

	<%@include file="Header.jsp"%>

	<center>
		<h2>Faculty List</h2>

		<form action="<%=ORSView.FACULTY_LIST_CTL%>" method="post">


			<h3>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>
			<h3>
				<font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>
			<%
				List list = ServletUtility.getList(request);
				int next = DataUtility.getInt(DataUtility.getStringData(request.getAttribute("nextlistsize")));

				System.out.println("dfghjkldfghjkl" + list);

				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
			%>


			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">


			<%
				if (list != null && list.size() > 0) {
			%>
			<label>FirstName :</label> <input type="text" name="firstName"
				value="<%=ServletUtility.getParameter("firstName", request)%>">
			<label>LoginId:</label>


			<%
				List flist = (List) request.getAttribute("flist");
			%>

			<%=HTMLUtility.getList("id", String.valueOf(beanf.getLoginId()), flist)%>





			<input type="submit" name="operation"
				value="<%=FacultyListCtl.OP_SEARCH%>"> <input type="submit"
				name="operation" value="<%=FacultyListCtl.OP_RESET%>"> <br>
			<br>


			<table border="1" width="100%">

				<tr>
					<th><input type="checkbox" id="selectall" class="css-checkbox"
						name="selectall">Select all</th>

					<th>Sno.</th>
					<th>FirstName</th>
					<th>LastName</th>
					<th>LoginId</th>
					<th>MobileNo.</th>
					<th>Date Of joining</th>
					<th>Edit</th>
				</tr>



				<%
					Iterator<FacultyBean> it = list.iterator();
						while (it.hasNext()) {
							FacultyBean bean = it.next();
				%>

				<tr>
					<td align="center"><input type="checkbox" name="ids"
						class="checkboxall" value="<%=bean.getId()%>"></td>

					<%
						System.out.println(bean.getId());
					%>
					<td align="center"><%=index++%></td>
					<td align="center"><%=bean.getFirstName()%></td>
					<td align="center"><%=bean.getLastName()%></td>
					<td align="center"><%=bean.getLoginId()%></td>
					<td align="center"><%=bean.getMobileno()%></td>
					<td align="center"><%=DataUtility.getDateString(bean.getDateofJoining())%></td>

					<td><a href="FacultyCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>




			<br>

			<table width="100%">
				<tr>

					<%
						if (pageNo <= 1) {
					%>



					<th><input type="submit" name="operation"
						value="<%=FacultyListCtl.OP_PREVIOUS%>" disabled="disabled"></th>



					<%
						} else {
					%>

					<th><input type="submit" name="operation"
						value="<%=FacultyListCtl.OP_PREVIOUS%>"></th>



					<%
						}
					%>
					<th><input type="submit" name="operation"
						value="<%=FacultyListCtl.OP_DELETE%>"> <input
						type="submit" name="operation" value="<%=FacultyListCtl.OP_NEW%>"></th>

					<%
						if (list.size() == 0 || next == 0) {
					%>
					<th><input type="submit" name="operation" disabled="disabled"
						value="<%=FacultyListCtl.OP_NEXT%>"></th>
					<%
						} else {
					%>
					<th><input type="submit" name="operation"
						value="<%=FacultyListCtl.OP_NEXT%>"></th>
					<%
						}
					%>

					<%
						}
					%>
				</tr>
			</table>
			<%
				if (list.size() == 0) {
			%>
			<label> <input type="submit" name="operation"
				value="<%=FacultyListCtl.OP_BACK%>"></label> <label> <input
				type="submit" name="operation" value="<%=FacultyListCtl.OP_NEW%>">
			</label>

			<%
				}
			%>



		</form>
	</center>
	<%@include file="Footer.jsp"%>

</body>
</html>
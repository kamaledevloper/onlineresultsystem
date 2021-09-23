<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.controller.RoleListCtl"%>
<%@page import="in.co.sunrays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<title>Role List</title>

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

	<form action="<%=ORSView.ROLE_LIST_CTL%>" method="post">
		<%@ include file="Header.jsp"%>



		<jsp:useBean scope="request" class="in.co.sunrays.proj4.bean.RoleBean"
			id="bean"></jsp:useBean>
		<center>
			<h2>Role List</h2>
			<lable>Name:</lable>
			<label><input type="text" name="name" value=""></label> <label><input
				type="submit" name="operation" value="<%=RoleListCtl.OP_SEARCH%>">
			</label> <label><input type="submit" name="operation"
				value="<%=RoleListCtl.OP_RESET%>"></i> </label>

			<h3>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
			</h3>

			<h3>
				<font color="Green"> <%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>

			<%
				List list = ServletUtility.getList(request);

				int next = DataUtility.getInt(DataUtility.getStringData(request.getAttribute("NextPageSize")));

				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);

				System.out.println("pageSize From JSP" + pageSize);

				int index = ((pageNo - 1) * pageSize) + 1;

				if (list != null && list.size() > 0) {
			%>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">


			<table border="1" width="95%">

				<tr>
					<th><input type="checkbox" name="selectall" id="selectall"
						class="css-checkbox">Select all</th>

					<th>SNo.</th>
					<th>Name</th>
					<th>Description</th>
					<th>Edit</th>
				</tr>

				<%
					Iterator it = list.iterator();

						while (it.hasNext()) {

							bean = (RoleBean) it.next();
				%>

				<tr>
					<td align="center"><input type="checkbox" name="ids"
						value="<%=bean.getId()%>" class="checkboxall"></td>
					<td align="center"><%=index++%></td>


					<td align="center"><%=bean.getName()%></td>

					<td><%=bean.getDescription()%></td>
					<td align="center"><a href="RoleCtl?id=<%=bean.getId()%>">Edit
					</a></td>
				</tr>

				<%
					}
				%>
			</table>
			<br>
			<%
				if (pageNo <= 1) {
			%>

			<table width="94%">
				<tr>
					<th><input type="submit" disabled="disabled"
						value="<%=RoleListCtl.OP_PREVIOUS%>" name="operation"></th>
					<%
						} else {
					%>

					<th><input type="submit" value="<%=RoleListCtl.OP_PREVIOUS%>"
						name="operation"></th>
					<%
						}
					%>
					
						<%
							if (list.size() == 0 || next == 0) {
						%> <input type="submit" disabled="disabled"
						value="<%=RoleListCtl.OP_NEXT%>" name="operation">
					
					<%
						} else {
					%>
					<input type="submit" value="<%=RoleListCtl.OP_NEXT%>"
						name="operation"></th>
					<%
						}
					%>
				</tr>
			</table>
			<%
				}
			%>

			<%
				if (list.size() == 0 || list == null) {
			%>
			<label>

				<th><input type="submit" value="<%=RoleListCtl.OP_BACK%>"
					name="operation"></th>
			</label>

			<%
				}
			%>
		</center>
		<%@ include file="Footer.jsp"%>

	</form>
</body>
</html>
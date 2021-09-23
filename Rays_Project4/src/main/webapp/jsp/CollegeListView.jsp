<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.controller.CollegeListCtl"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.controller.CollegeCtl"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.sunrays.proj4.bean.CollegeBean"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Colleges List</title>

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
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.COLLEGE_LIST_CTL%>" method="post">
		<center>
			<h2>College List</h2>


			<h3>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>

				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>


			<%
				List list = ServletUtility.getList(request);
				int next = DataUtility.getInt(DataUtility.getStringData(request.getAttribute("nextlistsize")));

				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);

				if (list != null && list.size() > 0) {
			%>
			<label> College Name: <%
				List clist = (List) request.getAttribute("clist");
					CollegeBean bean1 = new CollegeBean();
			%> <%=HTMLUtility.getList("id", String.valueOf(bean1.getName()), clist)%>
				</lable>
			</label> <label> City: <input type="text" placeholder="city name"
				name="city">

			</label> <label> <input type="submit" name="operation"
				value="<%=CollegeCtl.OP_SEARCH%>">
			</label> <label> <input type="submit" name="operation"
				value="<%=CollegeCtl.OP_RESET%>">
			</label> <br> <br>

			<table border="1" width="100%">



				<tr>
					<th><input type="checkbox" name="selectall" id="selectall"
						class="css-checkbox">Select All.</th>
					<th>SNo.</th>
					<th>Name</th>
					<th>PhoneNo.</th>
					<th>City</th>
					<th>State</th>
					<th>Address</th>
					<th>Edit</th>
				</tr>
				<%
					Iterator it = list.iterator();

						CollegeBean bean = new CollegeBean();
						while (it.hasNext()) {
							bean = (CollegeBean) it.next();
				%>
				<tr>
					<td align="center"><input type="checkbox" class="checkboxall"
						name="ids" value="<%=bean.getId()%>"></td>

					<td align="center"><%=bean.getId()%></td>
					<td><%=bean.getName()%></td>
					<td align="center"><%=bean.getPhoneNo()%></td>
					<td align="center"><%=bean.getCity()%></td>
					<td align="center"><%=bean.getState()%></td>
					<td align="center"><%=bean.getAddress()%></td>

					<td align="center"><a href="CollegeCtl?id=<%=bean.getId()%>">Edit</a>
					</td>
					<%
						}
					%>
				</tr>
			</table>
			<table width="100%">

				<tr>
					<%
						if (pageNo <= 1) {
					%>
					<th><input type="submit" name="operation" disabled="disabled"
						value="<%=CollegeListCtl.OP_PREVIOUS%>"></th>
					<%
						} else {
					%>

					<th><input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_PREVIOUS%>"></th>

					<%
						}
					%>

					<th><input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_DELETE%>"> <input
						type="submit" name="operation" value="<%=CollegeListCtl.OP_NEW%>">
					</th>

					<%
						if (list.size() == 0 || next == 0) {
					%>
					<th><input type="submit" name="operation" disabled="disabled"
						value="<%=CollegeListCtl.OP_NEXT%>"></th>
					<%
						} else {
					%>

					<th><input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_NEXT%>"></th>
					<%
						}
					%>
					<%
						}
					%>
				</tr>

			</table>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<%
				if (list.size() == 0) {
			%>

			<label> <input type="submit" name="operation"
				value="<%=CollegeListCtl.OP_BACK%>">
			</label> <label><input type="submit" name="operation"
				value="<%=CollegeListCtl.OP_NEW%>"> </label>
			<%
				}
			%>
			<%@include file="Footer.jsp"%>
	</form>

</body>

</html>
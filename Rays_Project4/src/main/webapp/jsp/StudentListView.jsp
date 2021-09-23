<%@page import="in.co.sunrays.proj4.bean.CollegeBean"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.controller.StudentCtl"%>
<%@page import="in.co.sunrays.proj4.model.StudentModel"%>
<%@page import="in.co.sunrays.proj4.controller.StudentListCtl"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj4.bean.StudentBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Students</title>

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
	<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.StudentBean"
		scope="request"></jsp:useBean>
	<form action="<%=ORSView.STUDENT_LIST_CTL%>" method="post">
		<%@include file="Header.jsp"%>
		<center>

			<div align="center">

				<h2>Student List</h2>
				<h3>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</h3>
				<h3>
					<font color="red"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</h3>


				<%
					List stlist = (List) ServletUtility.getList(request);

					int next = DataUtility.getInt(DataUtility.getStringData((request.getAttribute("NextListSize"))));
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;
				%>
				<input type="hidden" value="<%=pageNo%>" name="pageNo"> <input
					type="hidden" value="<%=pageSize%>" name="pageSize">

				<%
					if (stlist.size() > 0 && stlist != null) {
				%>
				<label> FirstName: <input type="text" name="firstName">


				</label> <label> Last Name: <input type="text" name="lastName">
				</label> <label> E_mail: <input type="text" name="email">
				</label>
				<%
				
				List clist = (List) request.getAttribute("clist");
				

				%>
				<label> College Name: <%= HTMLUtility.getList("id", String.valueOf(bean.getCollegeName()), clist)%>

				</label>
				
				<lable> <input type="submit" name="operation"
					value="<%=StudentListCtl.OP_SEARCH%>"> </lable>
				<lable> <input type="submit" name="operation"
					value="<%=StudentListCtl.OP_RESET%>"> </lable>

				<br> <br>





				<table border="1" width="100%">

					<tr>
						<th align="center"><input type="checkbox" id="selectall"
							class="css-checkbox" name="selectall"></> Select All</th>
						<th>SnNo.</th>
						<th>College Name</th>
						<th>F Name</th>
						<th>L Name</th>
						<th>Date of Birth</th>
						<th>Mobile no.</th>
						<th>Email Id</th>
						<th>Edit</th>
					</tr>



					<%
						Iterator it = stlist.iterator();

							while (it.hasNext()) {

								bean = (StudentBean) it.next();
					%>
					<tr>
						<td align="center"><input type="checkbox" name="ids"
							class="checkboxall" name="ids" value="<%=bean.getId()%>"></td>
						<td align="center"><%=index++%></td>
						<td align="center"><%=bean.getCollegeName()%></td>
						<td align="center"><%=bean.getFirstName()%></td>
						<td align="center"><%=bean.getLastName()%></td>
						<td align="center"><%=DataUtility.getDateString(bean.getDob())%></td>
						<td align="center"><%=bean.getMobileNo()%></td>
						<td align="center"><%=bean.getEmail()%></td>
						<td align="center"><a href="StudentCtl?id=<%=bean.getId()%>">Edit</a></td>
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
							value="<%=StudentListCtl.OP_PREVIOUS%>" disabled="disabled">
						</th>
						<%
							} else {
						%>
						<th><input type="submit" name="operation"
							value="<%=StudentListCtl.OP_PREVIOUS%>"></th>
						<%
							}
						%>

						<th><input type="submit" name="operation"
							value="<%=StudentListCtl.OP_DELETE%>"><input
							type="submit" name="operation" value="<%=StudentListCtl.OP_NEW%>"></th>


						<%
							if (stlist.size() == 0 || next == 0) {
						%>
						<th><input type="submit" name="operation"
							value="<%=StudentListCtl.OP_NEXT%>" disabled="disabled">
						</th>
						<%
							} else {
						%>
						<th><input type="submit" name="operation"
							value="<%=StudentListCtl.OP_NEXT%>"></th>

						<%
							}
						%>



						<%
							}
							if (stlist.size() == 0) {
						%>
						<th><input type="submit" name="operation"
							value="<%=StudentListCtl.OP_BACK%>"></th>


						<th><input type="submit" name="operation"
							value="<%=StudentListCtl.OP_NEW%>"></th>
						<%
							}
						%>

					</tr>
				</table>
	</form>
	</center>

	<%@ include file="Footer.jsp"%>
</body>
</html>




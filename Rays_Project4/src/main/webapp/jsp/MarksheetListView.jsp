<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.sunrays.proj4.bean.MarksheetBean"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.controller.MarksheetListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Marksheet List</title>
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

	<jsp:useBean id="beanmar"
		class="in.co.sunrays.proj4.bean.MarksheetBean" scope="request"></jsp:useBean>

	<form action="<%=ORSView.MARKSHEET_LIST_CTL%>" method="post">
		<%@include file="Header.jsp"%>

		<center>
			<h3>MarkSheet List</h3>

			<h3>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>


			<h3>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>



			<%
				int pageNo = ServletUtility.getPageNo(request);

				System.out.println("page Number in JSP :" + pageNo);

				int pageSize = ServletUtility.getPageSize(request);

				int index = ((pageNo - 1) * pageSize) + 1;
			%>

			<input type="hidden" value="<%=pageNo%>" name="pageNo"> <input
				type="hidden" value="<%=pageSize%>" name="pageSize">

			<%
				MarksheetBean bean = new MarksheetBean();
				int next = DataUtility.getInt(DataUtility.getStringData(request.getAttribute("nextlistsize")));

				System.out.println("next list size is " + next);
				List list = (List<MarksheetBean>) ServletUtility.getList(request);

				//	List Nextlist = (List<MarksheetBean>) request.getAttribute("nextList");

				if (list.size() != 0) {
			%>
			<%
				List listrole1 = (List) request.getAttribute("listrole");
					System.out.println("list is::" + listrole1);
			%>
			<label>Name</label> <input type="text" name="name" placeholder= "Enter Name"> <label>RollNo</label>
			<%=HTMLUtility.getList("Rollid", String.valueOf(beanmar.getRollNo()), listrole1)%>
			<input type="submit" name="operation"
				value="<%=MarksheetListCtl.OP_SEARCH%>"> <input
				type="submit" name="operation"
				value="<%=MarksheetListCtl.OP_RESET%>"> <br> <br>


			<table border="1" width="100%">

				<tr>
					<th><input type="checkbox" name="selectall" id="selectall"
						class="css-checkbox"> Select All.</th>
					<th>SNo.</th>
					<th>RollNo</th>
					<th>Name</th>
					<th>Physics</th>
					<th>Chemistry</th>
					<th>Maths</th>
					<th>Total</th>
					<th>Result</th>
					<th>Edit</th>

				</tr>

				<%
					Iterator it = list.iterator();
						while (it.hasNext()) {

							bean = (MarksheetBean) it.next();
				%>
				<tr>
					<td align="center"><input type="checkbox" class="checkboxall"
						name="ids" value="<%=bean.getId()%>"></td>
					<td><%=index++%></td>
					<td><%=bean.getRollNo()%></td>
					<td><%=bean.getName()%></td>
					<td><%=bean.getPhysics()%></td>
					<td><%=bean.getChemistry()%></td>
					<td><%=bean.getMaths()%></td>
					<td><%=bean.getPhysics() + bean.getChemistry() + bean.getMaths()%></td>
					<%
						if ((bean.getPhysics() + bean.getChemistry() + bean.getMaths()) >= 99) {
					%>
					<td>pass</td>

					<%
						}

								else {
					%>
					<td>Fail</td>
					<%
						}
					%>

					<td><a href="MarksheetCtl?id=<%=bean.getId()%>"> Edit</a></td>
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

								System.out.println("i am in  if condition");
					%>
					<th><input type="submit"
						value="<%=MarksheetListCtl.OP_PREVIOUS%>" name="operation"
						disabled="disabled"></th>

					<%
						} else {
								System.out.println("i am in  else condition");
					%>


					<th><input type="submit"
						value="<%=MarksheetListCtl.OP_PREVIOUS%>" name="operation"></th>


					<%
						}
					%>





					<th><input type="submit"
						value="<%=MarksheetListCtl.OP_DELETE%>" name="operation">

						<input type="submit" name="operation"
						value="<%=MarksheetListCtl.OP_NEW%>"></th>
					<%
						if (list.size() == 0 || next == 0) {
					%>
					<th><input type="submit" value="<%=MarksheetListCtl.OP_NEXT%>"
						disabled="disabled" name="operation"></th>
					<%
						} else {
					%>
					<th><input type="submit" value="<%=MarksheetListCtl.OP_NEXT%>"
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
				if (list.size() == 0) {
			%>
			<label><input type="submit"
				value="<%=MarksheetListCtl.OP_BACK%>" name="operation"></label> <label>
				<input type="submit" name="operation"
				value="<%=MarksheetListCtl.OP_NEW%>">
			</label>
			<%
				}
			%>
			<%@include file="Footer.jsp"%>
		</center>
	</form>
</body>
</html>
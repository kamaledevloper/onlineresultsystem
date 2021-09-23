<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.bean.MarksheetBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.controller.MarksheetMeritListCtl"%>
<%@page import="in.co.sunrays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MaritList</title>
</head>
<body>

	<center>
		<form action=<%=ORSView.MARKSHEET_MERIT_LIST_CTL%> method="post">
			<%@includefile= "Header.jsp"%>

			<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.MarksheetBean"
				scope="request"></jsp:useBean>


			<h2>MarkSheet Merit List</h2>


			<%
				List list = ServletUtility.getList(request);

				if (list.size() != 0) {
			%>


			<%
				int pageno = ServletUtility.getPageNo(request);

					int pageSize = ServletUtility.getPageSize(request);
			%>


			<table border="1" width="100%">
				<br>
				<tr>
					<th>SNo.</th>
					<th>RoleNumber</th>
					<th>Name</th>
					<th>physics</th>
					<th>Chemistry</th>
					<th>Maths</th>
					<th>Total</th>
					<th>Percentage</th>

				</tr>
				<%
					Iterator it = list.iterator();

						while (it.hasNext()) {

							bean = (MarksheetBean) it.next();
				%>

				<tr align="center">

					<td><%=pageno++%></td>
					<td><%=bean.getRollNo()%></td>
					<td><%=bean.getName()%></td>
					<td><%=bean.getPhysics()%></td>
					<td><%=bean.getChemistry()%></td>
					<td><%=bean.getMaths()%></td>


					<%
						int total = bean.getMaths() + bean.getChemistry() + bean.getPhysics();

								float per = total * 100 / 300;
					%>

					<td><%=total%></td>
					<td><b><%=per + "%"%></td>
				</tr>





				<input type="hidden" name="pageno" value="<%=pageno%>">
				</input>
				<input type="hidden" name="pagesize" value="<%=pageSize%>"></input>
				<%
					}
				%>

				<%
					}
				%>

			</table>

<br>
			<label><input type="submit" name="operation"
				value="<%=MarksheetMeritListCtl.OP_BACK%>"> </label>

			<%@includefile= "Footer.jsp"%>
		</form>

	</center>
</body>
</html>
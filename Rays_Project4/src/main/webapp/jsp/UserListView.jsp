<%@page import="org.sonatype.guice.bean.inject.BeanBinder"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.controller.UserListCtl"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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

	<jsp:useBean id="beanrol" class="in.co.sunrays.proj4.bean.RoleBean"
		scope="request"></jsp:useBean>

	<%@include file="Header.jsp"%>

	<center>
		<h2>User List</h2>

		<form action="<%=ORSView.USER_LIST_CTL%>" method="post">

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

				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
			%>
			<%
				System.out.println("JSP Next List Size" + next);
				String role = null;
			%>
			<%
				if (list.size() != 0 && list != null) {
			%>

			<label>FirstName : <input type="text" name="firstName" placeholder ="F Name">
			</label> <label>LoginId: <input type="text" name="login" placeholder = "E_mail ID"> <%
 	List<RoleBean> listr = (List<RoleBean>) request.getAttribute("listrole");
 %>


			</label> <label>Role: <%=HTMLUtility.getList("roleid", String.valueOf(beanrol.getId()), listr)%>

			</label>


			<lable> <input type="submit" name="operation"
				value="<%=UserListCtl.OP_SEARCH%>"> </label> <lable> <input
				type="submit" name="operation" value="<%=UserListCtl.OP_RESET%>">
			</lable> <br>
			<br>
			<table border="1" width="100%">

				<tr>
					<th><input type="checkbox" name="selectall" id="selectall"
						class="css-checkbox"> Select All</th>
					<th>SNo.</th>
					<th>FirstName</th>
					<th>LastName</th>
					<th>LoginId</th>
					<th>Gender</th>
					<th>DOB</th>
					<th>Role</th>
					<th>Edit</th>
				</tr>
				<%
					Iterator<UserBean> it = list.iterator();
						while (it.hasNext()) {
							UserBean bean = it.next();

							if (bean.getRoleId() == 1) {
								role = "Admin";
							} else if (bean.getRoleId() == 2) {
								role = "Student";
							} else if (bean.getRoleId() == 3) {
								role = "Faculty";
							} else {
								role = "NA";
							}
				%>
				<tr>
					<td align="center"><input type="checkbox" class="checkboxall"
						value="<%=bean.getId()%>" <%if (bean.getRoleId() == 1) {%>
						disabled="disabled"> <%
 	} else {
 %> <%
 	}
 %></td>


					<td><%=index++%></td>
					<td><%=DataUtility.getString(bean.getFirstName())%></td>
					<td><%=bean.getLastName()%></td>
					<td><%=bean.getLogin()%></td>
					<td><%=bean.getGender()%></td>
					<td><%=bean.getDob()%></td>



					<td><%=role%></td>




					<%
						if (bean.getRoleId() != 1) {
					%>




					<td><a href="UserCtl?id=<%=bean.getId()%>">Edit </a></td>

					<%
						} else {
					%>

					<td>Edit</td>

					<%
						}
					%>
				</tr>


				<%
					}
				%>


			</table>

			<br>

			<table width="100%">



				<tr>
					<%
						if (pageNo > 1) {
					%>
					<th><input type="submit" name="operation"
						value="<%=UserListCtl.OP_PREVIOUS%>"></th>

					<%
						} else {
					%>

					<th><input type="submit" name="operation"
						value="<%=UserListCtl.OP_PREVIOUS%>" disabled="disabled"></th>



					<%
						}
					%>


					<th><input type="submit" name="operation"
						value="<%=UserListCtl.OP_DELETE%>"><input type="submit"
						name="operation" value="<%=UserListCtl.OP_NEW%>"> <%
 	if (next == 0) {
 %>
					<th><input type="submit" name="operation"
						value="<%=UserListCtl.OP_NEXT%>" disabled="disabled"></th>
					<%
						} else {
					%>
					<th><input type="submit" name="operation"
						value="<%=UserListCtl.OP_NEXT%>"></th>
					<%
						}
					%>


				</tr>



				<%
					}
				%>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> </input> <input
				type="hidden" name="pageSize" value="<%=pageSize%>"> </input> <%
 	if (list.size() == 0) {
 %> <label> <input type="submit"
				value="<%=UserListCtl.OP_BACK%>" name="operation"></label> <label>
				<input type="submit" name="operation"
				value="<%=UserListCtl.OP_NEW%>">
			</label> <%
 	}
 %>
			
		</form>
	</center>
	<%@include file="Footer.jsp"%>
</body>
</html>
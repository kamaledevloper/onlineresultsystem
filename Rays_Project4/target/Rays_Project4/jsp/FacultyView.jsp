<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj4.bean.CourseBean"%>
<%@page import="in.co.sunrays.proj4.bean.SubjectBean"%>
<%@page import="in.co.sunrays.proj4.bean.CollegeBean"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.controller.FacultyCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Faculty</title>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">



<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#datepicker").datepicker({
			minDate : 0,
			changeMonth : true,
			changeYear : true,
			//	yearRange : '1980:2020',
			dateFormat : 'mm/dd/yy'

		});
	});
</script>



</head>
<body>
	<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.FacultyBean"
		scope="request"></jsp:useBean>

	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.FACULTY_CTL%>" method="post">

		<%
			List<CollegeBean> clist = (List<CollegeBean>) request.getAttribute("collegeList");
			List<SubjectBean> slist = (List<SubjectBean>) request.getAttribute("subjectList");
			List<CourseBean> colist = (List<CourseBean>) request.getAttribute("courseList");

			System.out.println("this is from list get attribute " + clist);
		%>
		<center>

			<h2>

				<tr>

					<%
						if (bean.getId() > 0 && bean != null) {
					%>



					<th><font>Edit Faculty</font></th>
					<%
						} else {
					%>
					<th><font>Add Faculty</font></th>
					<%
						}
					%>

				</tr>


			</h2>
			<div>
				<h3>
					<font style="color: green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>
				<h3>
					<font style="color: red"><%=ServletUtility.getErrorMessage(request)%></font>
				</h3>
			</div>
			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdby" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedby"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createddatetime" value="<%=bean.getCreatedDatetime()%>">
			<input type="hidden" name="modifieddatetime"
				value="<%=bean.getModifiedDatetime()%>">


			<table>

				<%
					System.out.println("this is from College Option :" + clist);
				%>


				<tr>
					<th align="right">FirstName <span style="color: red">*</span></th>
					<td><input type="text" name="firstName"
						placeholder="Enter First Name" size="25"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("firstName", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="right">LastName <span style="color: red">*</span></th>
					<td><input type="text" name="lastName"
						placeholder="Enter Last Name" size="25"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("lastname", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>

				<tr>
					<th align="right">Login-Id <span style="color: red">*</span></th>
					<td><input type="text" name="email"
						placeholder="Enter Email_Id" size="25"
						value="<%=DataUtility.getStringData(bean.getLoginId())%>"
						<%if (bean.getLoginId() != null && bean.getId() > 1) {%>
						readonly="readonly" <%} else {
			}%>></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("email", request)%></font>
					</td>
				</tr>

				<tr>
					<th style="padding: 1px"></th>
				</tr>


				<tr>
					<th align="right">Qualification <span style="color: red">*</span></th>
					<td><input type="text" name="qualifi"
						placeholder="Enter Degree" size="25"
						value="<%=DataUtility.getStringData(bean.getQualification())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("qualifi", request)%></font>
					</td>
				</tr>

				<tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="right">Gender <span style="color: red">* </span></th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Male", "Male");
							map.put("Female", "Female");

							String htmlList = HTMLUtility.getList("gender", bean.getGender(), map);
						%> <%=htmlList%>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("gender", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>

				<tr>
					<th align="right">CollegeName <span style="color: red">*</span></th>

					<td><%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeid()), clist)%>
					</td>

					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("collegeId", request)%></font>
					</td>

				</tr>

				<tr>
					<th style="padding: 1px"></th>
				</tr>

				<tr>
					<th align="right">CourseName <span style="color: red">*</span></th>

					<td><%=HTMLUtility.getList("CourseId", String.valueOf(bean.getCourseId()), colist)%>
					</td>

					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("CourseId", request)%></font>
					</td>
					<%
						System.out.println("sdfghnm,.dfghjk,.;xcvbhnjmk" + ServletUtility.getErrorMessage("CourseId", request));
					%>
				</tr>

				<tr>
					<th style="padding: 1px"></th>
				</tr>

				<tr>

					<th align="right">SubjectName <span style="color: red">*</span></th>

					<td><%=HTMLUtility.getList("subjectId", String.valueOf(bean.getSubjectId()), slist)%>
					</td>

					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("subjectId", request)%></font>
					</td>



				</tr>




				<tr>
					<th style="padding: 1px"></th>
				</tr>

				<tr>
					<th align="right">MobileNo <span style="color: red">*</span></th>
					<td><input type="text" name="mobileNo" maxlength="10"
						placeholder="Enter Mobile No" size="25"
						value="<%=DataUtility.getStringData(bean.getMobileno())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("mobile", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>

				<tr>
					<th align="right">Date of Joining <span style="color: red">*</span>
					</th>

					<td><input type="text" id="datepicker" name="dob"
						maxlength="10" placeholder="DD/MM/YY" size="25"
						value="<%=DataUtility.getDateString(bean.getDateofJoining())%>"></td>





					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("DOB", request)%></font>
					</td>
				</tr>


				<tr>
					<th style="padding: 1px"></th>
				</tr>

			</table>

			<table>
				<tr>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<td><input type="submit" name="operation"
						value="<%=FacultyCtl.OP_UPDATE%>"></td>
					<td><input type="submit" name="operation"
						value="<%=FacultyCtl.OP_BACK%>"></td>

					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=FacultyCtl.OP_SAVE%>"></td>

					<td><input type="submit" name="operation"
						value="<%=FacultyCtl.OP_RESET%>"></td>
				</tr>

				<%
					}
				%>

			</table>

			<%@include file="Footer.jsp"%>
	</form>
</center>

</body>
</html>
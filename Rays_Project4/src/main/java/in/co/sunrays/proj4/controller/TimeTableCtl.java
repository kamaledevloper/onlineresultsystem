
package in.co.sunrays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.search.SubjectTerm;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.CourseBean;
import in.co.sunrays.proj4.bean.SubjectBean;
import in.co.sunrays.proj4.bean.TimeTableBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.CourseModel;
import in.co.sunrays.proj4.model.SubjectModel;
import in.co.sunrays.proj4.model.TimeTableModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.ServletUtility;

/**
 *  Time Table functionality Controller. 
 *
 * @author Kamal
 *
 */
@WebServlet(name = "TimeTableCtl", urlPatterns = { "/ctl/TimeTableCtl" })
public class TimeTableCtl extends BaseCtl {
	/** Validate user input data */
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean flag = true;

		if (DataValidator.isNull(request.getParameter("courseId"))) {

			request.setAttribute("courseId", "Course Name required");
			flag = false;

		}

		if (DataValidator.isNull(request.getParameter("subjectId"))) {

			// System.out.println("in subject block" + SubjectName);

			request.setAttribute("subjectId", "Subject Name required");
			flag = false;

		}
		if (DataValidator.isNull(request.getParameter("semester")))

		{

			request.setAttribute("semester", "Subject Name required");
			// request.setAttribute("semester", "Semester Name required");

			flag = false;

		}

		if (DataValidator.isNull(request.getParameter("ExTime")))

		{

			request.setAttribute("ExTime", "Time required");
			// request.setAttribute("semester", "Semester Name required");

			flag = false;

		}

		if (DataValidator.isNull(request.getParameter("ExDate"))) {

			request.setAttribute("ExDate", "Date required");
			// request.setAttribute("date", "Date required");
			flag = false;

		} else if (!DataValidator.isDate(request.getParameter("ExDate"))) {

			request.setAttribute("ExDate", "Enter Valid date");
			flag = false;

		}
		/*
		 * System.out .println("data from JSP: " + course + "   " + subject +
		 * "   " + semester + "   " + date + "   " + time);
		 * 
		 * System.out.println("velidate going to return ::::" + flag); // return
		 * super.validate(request);
		 */
		return flag;

	}

	/** loads the data in form of dropdown when page load */
	@Override
	protected void preload(HttpServletRequest request) {
		List clist = null;
		List slist = null;

		CourseModel cmodel = new CourseModel();
		SubjectModel smodel = new SubjectModel();

		try {
			slist = smodel.list();
			clist = cmodel.list();

			System.out.println("clist print :::" + clist);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("SubjectList", slist);
		request.setAttribute("CourseList", clist);
	}

	/** get the data from View and set into bean */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		TimeTableBean bean = new TimeTableBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));

		long cid = DataUtility.getLong(request.getParameter("courseId"));

		System.out.println("this is from Populate course id is ::" + cid);

		bean.setCourseId(cid);

		bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
		System.out.println("this is from Populate Subject id  is ::" + request.getParameter("slist"));

		bean.setSemester(DataUtility.getString(request.getParameter("semester")));

		bean.setExamDate(DataUtility.getDate(request.getParameter("ExDate")));

		bean.setExamTime(DataUtility.getString(request.getParameter("ExTime")));

		populateDTO(bean, request);
		return bean;
	}

	/**
	 * Contains display logic
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = DataUtility.getInt(request.getParameter("id"));

		TimeTableBean bean = null;

		if (id > 0) {

			bean = new TimeTableBean();
			TimeTableModel model = new TimeTableModel();

			try {
				bean = model.findByPk(id);

				if (bean != null) {

					ServletUtility.setBean(bean, request);

					// System.out.println("sdfghjkasdfghj" +
					// bean.getSemester());

				} /*
					 * else {
					 * 
					 * ServletUtility.redirect(ORSView.TIMETABLE_CTL, request,
					 * response);
					 * 
					 * }
					 */
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} /*
			 * else {
			 * 
			 * 
			 * ServletUtility.redirect(ORSView.TIMETABLE_CTL, request,
			 * response); }
			 */

		ServletUtility.forward(getView(), request, response);
		System.out.println("Id From Do get::++++++++++" + id);

		System.out.println("i am from get goinf to forward");

	}

	/**
	 * Contains submit logic
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = request.getParameter("operation");
		System.out.println("operation save::::: " + op);
		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("id is fgvhnmk,l" + id);

		if (TimeTableCtl.OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			System.out.println("in the if condition under save ");

			TimeTableBean bean = (TimeTableBean) populateBean(request);

			System.out.println(
					bean.getCourseId() + "<   >" + bean.getSubjectId() + "<   >" + bean.getSemester() + "<   >");

			TimeTableModel model = new TimeTableModel();

			if (OP_UPDATE.equalsIgnoreCase(op)) {

				try {
					model.update(bean);

					ServletUtility.setSuccessMessage("Update Sucess", request);
				} catch (ApplicationException e) {
					ServletUtility.handleException(e, request, response);
					e.printStackTrace();
				} catch (DuplicateRecordException e) {
					ServletUtility.setErrorMessage("Time Table Already Available", request);
					e.printStackTrace();
				}

			}

			if (OP_SAVE.equalsIgnoreCase(op)) {

				try {
					model.add(bean);
					ServletUtility.setSuccessMessage("Time table added", request);
				} catch (ApplicationException e) {
					ServletUtility.handleException(e, request, response);
					e.printStackTrace();
				} catch (DuplicateRecordException e) {
					ServletUtility.setErrorMessage("Timetable Already available", request);
					e.printStackTrace();
				}

			}

		}

		if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TIMETABLELIST_CTL, request, response);

			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	/** get the view */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.TIMETABLE_VIEW;
	}

}

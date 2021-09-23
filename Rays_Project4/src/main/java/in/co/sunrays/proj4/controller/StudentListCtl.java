package in.co.sunrays.proj4.controller;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.StudentBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.model.CollegeModel;
import in.co.sunrays.proj4.model.StudentModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 
 *
 * @author kamal
 */
/**
 * Student List functionality Controller. Performs operation for list, search
 * and delete operations of Student
 */
@WebServlet(name = "StudentListCtl", urlPatterns = { "/ctl/StudentListCtl" })



public class StudentListCtl extends BaseCtl {

	/** loads the data in form of dropdown when page load */

	@Override
	protected void preload(HttpServletRequest request) {
		System.out.println("Preload call ::::::::::::::::::");
		// List list = null;

		List clist = null;

		CollegeModel cmodel = new CollegeModel();

		try {
			clist = cmodel.list();

			request.setAttribute("clist", clist);

			System.out.println("College list sated is scope :::::" + clist);
		} catch (ApplicationException e1) {
			// TODO Auto-generated catch block

			System.out.println("i am in catch block ");
			e1.printStackTrace();
		}
		/*
		 * StudentModel model = new StudentModel();
		 * 
		 * try { list = model.list(); } catch (Exception e) {
		 * e.printStackTrace(); }
		 * 
		 * System.out.println(list);
		 * 
		 * request.setAttribute("list", list);
		 */
	}

	private static Logger log = Logger.getLogger(StudentListCtl.class);

	@Override

	/** get the data from View and set into bean*/

	protected BaseBean populateBean(HttpServletRequest request) {

		StudentBean bean = new StudentBean();

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));

		bean.setCollegeId(DataUtility.getInt(request.getParameter("id")));

		System.out.println("Populate bean started :::::" + bean.getCollegeId());

		return bean;
	}

	/**
	 * Contains Display logics
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// log.debug("StudentListCtl doGet Start");
		List list = null;
		List next = null;

		int pageNo = 1;
		// get value od page size from System.property file
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		System.out.println("this is from doGet pageSize " + pageSize);
		// get data from jsp and set in bean object
		StudentBean bean = (StudentBean) populateBean(request);

		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("Opration is:" + op);

		StudentModel model = new StudentModel();

		list = model.list(pageNo, pageSize);

		next = model.list(pageNo + 1, pageSize);

		System.out.println("this is from doget List" + list);
		// set list in request atribute

		if (list == null || list.size() == 0) {
			ServletUtility.setErrorMessage("No record found ", request);
		}

		if (next == null || next.size() == 0) {

			request.setAttribute("NextListSize", 0);
		} else {

			request.setAttribute("NextListSize", next.size());

		}

		ServletUtility.setList(list, request);

		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
		// log.debug("StudentListCtl doGet End");
	}

	/**
	 * Contains Submit logics
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// log.debug("StudentListCtl doPost Start");
		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		System.out.println(" i am from Do get page number is:" + pageNo);
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		String[] ids = request.getParameterValues("ids");
		// System.out.println("ID array:" + ids);

		System.out.println("i am from do post page Size is " + pageSize);
		// pageNo = (pageNo == 0) ? 1 : pageNo;
		// pageSize = (pageSize == 0) ?
		// DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		StudentBean bean = (StudentBean) populateBean(request);

		System.out.println(
				"geted value from JSP :" + bean.getFirstName() + "  " + bean.getLastName() + "  " + bean.getEmail());
		String op = DataUtility.getString(request.getParameter("operation"));

		System.out.println("I am from Do post opration is" + op);
		StudentModel model = new StudentModel();

		if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)
				|| OP_DELETE.equalsIgnoreCase(op) || OP_NEW.equalsIgnoreCase(op)) {

			if (OP_SEARCH.equalsIgnoreCase(op)) {
				pageNo = 1;

			}
			if (OP_NEXT.equalsIgnoreCase(op)) {
				pageNo++;
			}
			if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
				pageNo--;
			}

			if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;

				StudentBean bean1 = new StudentBean();
				if (ids != null && ids.length > 0) {

					for (String id : ids) {

						long idd = DataUtility.getLong(id);
						bean1.setId(idd);

						try {
							model.delete(bean1);

							System.out.println("in try block ");
						} catch (ApplicationException e) {
							e.printStackTrace();
						}
					}

					ServletUtility.setSuccessMessage("Record Delete Sucessfully", request);

				} else {
					ServletUtility.setErrorMessage("Select atlist one Record", request);
				}
			}
			if (OP_NEW.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);

				return;
			}

		}

		list = model.search(bean, pageNo, pageSize);

		next = model.search(bean, pageNo + 1, pageSize);
		// System.out.println("List is "+list);

		// ServletUtility.setList(list, request);
		if (list == null || list.size() == 0)

		{
			ServletUtility.setErrorMessage("No record found ", request);
		}

		if (next == null || next.size() == 0) {

			request.setAttribute("NextListSize", 0);
		} else {

			request.setAttribute("NextListSize", next.size());

		}

		ServletUtility.setList(list, request);

		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(

				getView(), request, response);
		// log.debug("StudentListCtl doGet End");
	}

	/** get the view */

	@Override
	protected String getView() {
		return ORSView.STUDENT_LIST_VIEW;
	}

}

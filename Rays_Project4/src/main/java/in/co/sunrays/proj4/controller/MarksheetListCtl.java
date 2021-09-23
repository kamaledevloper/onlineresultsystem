
package in.co.sunrays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.CollegeBean;
import in.co.sunrays.proj4.bean.MarksheetBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.model.MarksheetModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

/**
 * @author Kamal
 *
 */

/**
 * 
 * Responsible for Mark Sheet table
 * 
 */
@WebServlet(name = "MarksheetListCtl", urlPatterns = { "/ctl/MarksheetListCtl" })



public class MarksheetListCtl extends BaseCtl {

	/** get the data from View and set into bean */

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		MarksheetBean bean = new MarksheetBean();

		bean.setId(DataUtility.getLong(request.getParameter("Rollid")));
		bean.setName(DataUtility.getString(request.getParameter("name")));

		System.out.println(
				"This is from Populate bean roll number and name:" + bean.getRollNo() + "    " + bean.getName());
		return bean;

	}
	/*
	 * @Override protected void preload(HttpServletRequest request) { List list
	 * = null;
	 * 
	 * MarksheetModel model = new MarksheetModel();
	 * 
	 * // int pageNo = 0; // int pageSize = //
	 * DataUtility.getInt(PropertyReader.getValue("page.size"));
	 * 
	 * try { list = model.list(); } catch (ApplicationException e) { // TODO
	 * Auto-generated catch block e.printStackTrace();
	 * 
	 * System.out.println("exseption in List"); }
	 * request.setAttribute("marksheetList", list);
	 * 
	 * System.out.println("preLoad run sucessfully List id :  " + list); }
	 */

	/**
	 * Contains display logic
	 */

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		List list = null;
		List next = null;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		MarksheetModel model = new MarksheetModel();

		// MarksheetBean bean = (MarksheetBean) populateBean(request);

		try {
			list = model.list(pageNo, pageSize);
			next = model.list(pageNo + 1, pageSize);

			if (next == null || next.size() == 0) {
				request.setAttribute("nextlistsize", 0);
			} else {
				request.setAttribute("nextlistsize", next.size());
			}

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);

	}

	/** loads the data in form of dropdown when page load */
	@Override
	protected void preload(HttpServletRequest request) {

		List list = null;

		MarksheetModel model = new MarksheetModel();

		try {
			list = model.list();

			System.out.println("LIst is :::::::" + list);

			request.setAttribute("listrole", list);

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Contains submit logic
	 */

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// int pageNo = 0;
		// System.out.println("this is from doPost page No=" + pageNo);
		String op = DataUtility.getString(request.getParameter("operation"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		MarksheetModel model = new MarksheetModel();

		// int id = DataUtility.getInt(request.getParameter(arg0))

		if (OP_NEW.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
			return;
		}

		if (OP_DELETE.equalsIgnoreCase(op)) {

			pageNo = 1;

			MarksheetBean bean = new MarksheetBean();
			String[] ids = request.getParameterValues("ids");

			System.out.println("dfghjk,l.;fghnjm" + ids);

			if (ids != null && ids.length > 0) {

				for (String id : ids) {

					long idd = DataUtility.getLong(id);

					bean.setId(idd);
					try {
						model.delete(bean);
					} catch (ApplicationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ServletUtility.setSuccessMessage("Record Sucessfully deleted", request);
				}
			} else {
				ServletUtility.setErrorMessage("Select atlist one record", request);
			}

		}

		if (OP_SEARCH.equalsIgnoreCase(op)) {
			pageNo = 1;

		}

		if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			return;
		}

		if (OP_BACK.equalsIgnoreCase(op)) {
			pageNo = 1;

		}

		if (OP_NEXT.equalsIgnoreCase(op)) {
			if (pageNo >= 1) {

				pageNo++;
				System.out.println("ths is from OP next page number is : " + pageNo);

			}

		}
		if (OP_PREVIOUS.equalsIgnoreCase(op)) {

			pageNo--;
			System.out.println("ths is from OP prenext page number is : " + pageNo);

		}
		List list = null;
		List next = null;
		// List Nextlist = null;

		MarksheetBean bean = (MarksheetBean) populateBean(request);

		try {
			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);
			System.out.println(" firstList size is :" + list.size());
			// Nextlist = model.search(bean, pageNo + 1, pageSize);
			// request.setAttribute("nextList", Nextlist);
			// System.out.println("Second List size is :"+Nextlist.size());
			if (list == null || list.size() == 0) {

				ServletUtility.setErrorMessage("No record found", request);

				System.out.println("list is :" + list.size());
			}

			if (next == null || next.size() == 0) {
				request.setAttribute("nextlistsize", 0);
			} else {
				request.setAttribute("nextlistsize", next.size());
			}

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServletUtility.setPageNo(pageNo, request);

		ServletUtility.setPageSize(pageSize, request);

		ServletUtility.setList(list, request);
		// ServletUtility.setList(Nextlist, request);

		ServletUtility.forward(getView(), request, response);

	}

	/** get the view */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MARKSHEET_LIST_VIEW;
	}

}

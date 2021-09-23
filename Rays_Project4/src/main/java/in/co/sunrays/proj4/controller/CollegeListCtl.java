package in.co.sunrays.proj4.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.internal.util.Lists;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.CollegeBean;
import in.co.sunrays.proj4.bean.MarksheetBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.model.CollegeModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

/**
 * @author Kamal
 *
 */
/** College List functionality Controller. */
@WebServlet(name = "CollegeListCtl", urlPatterns = { "/ctl/CollegeListCtl" })

/** College List functionality Controller. */
public class CollegeListCtl extends BaseCtl {

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		CollegeBean bean = new CollegeBean();

		String name = DataUtility.getString(request.getParameter("name"));
		String city = DataUtility.getString(request.getParameter("city"));

		int id = DataUtility.getInt(request.getParameter("id"));

		System.out.println("this is from College list populate bean name comes from JSP is :" + name);
		System.out.println("this is from College list populate bean City comes from JSP is :" + city);

		bean.setName(name);
		bean.setCity(city);
		bean.setId(id);
		return bean;

	}

	/** loads data in form of Dropdown when page loads */

	@Override
	protected void preload(HttpServletRequest request) {
		CollegeModel model = new CollegeModel();
		List list = null;

		try {
			list = model.list();
			request.setAttribute("clist", list);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Contains display logic
	 */

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("college list do get Started");
		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		System.out.println("Page size is " + pageSize);
		// set city and name in bean
		CollegeBean bean = (CollegeBean) populateBean(request);

		CollegeModel model = new CollegeModel();
		List list = null;
		List next = null;
		try {
			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			if (next == null || next.size() == 0) {

				request.setAttribute("NextPageSize", 0);

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
		if (list == null || list.size() == 0) {

			ServletUtility.setErrorMessage("No Record Found", request);

		}
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Contains submit logic
	 */

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String OP = DataUtility.getString(request.getParameter("operation"));
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		System.out.println("operation in doPost:" + OP);
		List list = null;
		List next = null;
		CollegeBean bean = (CollegeBean) populateBean(request);

		CollegeModel model = new CollegeModel();

		if (OP_SEARCH.equalsIgnoreCase(OP) || OP_NEXT.equalsIgnoreCase(OP) || OP_PREVIOUS.equalsIgnoreCase(OP)
				|| OP_RESET.equalsIgnoreCase(OP) || OP_NEW.equalsIgnoreCase(OP) || OP_DELETE.equalsIgnoreCase(OP)
				|| OP_NEW.equalsIgnoreCase(OP) || OP_BACK.equalsIgnoreCase(OP))

		{

			if (OP_BACK.equalsIgnoreCase(OP)) {

				ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
				return;
			}

			if (OP_NEW.equalsIgnoreCase(OP)) {

				ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
				return;
			}

			if (OP_DELETE.equalsIgnoreCase(OP)) {

				pageNo = 1;

				String[] ids = request.getParameterValues("ids");
				System.out.println("dfghjk+++++++" + ids);
				System.out.println("dfghjk,l.;fghnjm" + ids);

				if (ids != null && ids.length > 0) {
					CollegeBean deletebean = new CollegeBean();
					for (String id : ids) {

						long idd = DataUtility.getLong(id);

						deletebean.setId(idd);
						try {
							model.delete(deletebean);
						} catch (ApplicationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					ServletUtility.setSuccessMessage("College Sucessfully deleted", request);
				} else {
					ServletUtility.setErrorMessage("Select atlist one record", request);
				}

			}

			if (OP_RESET.equalsIgnoreCase(OP)) {

				ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);

				return;

			}

			if (OP_SEARCH.equalsIgnoreCase(OP)) {

				pageNo = 1;

			}
			if (OP_NEXT.equalsIgnoreCase(OP)) {

				pageNo++;

			}

			if (OP_PREVIOUS.equalsIgnoreCase(OP)) {
				pageNo--;

			}

		}

		try {
			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (list.size() == 0 || list == null) {

				System.out.println("in size if block");
				ServletUtility.setErrorMessage("No record found", request);

			}

			if (next == null || next.size() == 0) {

				request.setAttribute("NextPageSize", 0);

			} else {

				request.setAttribute("nextlistsize", next.size());

				System.out.println("nextlistsize" + next.size());
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

	/** get the view */

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.COLLEGE_LIST_VIEW;
	}

}

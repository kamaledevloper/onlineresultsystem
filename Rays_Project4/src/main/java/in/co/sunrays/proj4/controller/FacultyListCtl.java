package in.co.sunrays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.conf.url.FailoverConnectionUrl;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.FacultyBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.model.FacultyModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

/**
 * @author Kamal
 *
 */

/** Faculty List Functionality controller */
@WebServlet(name = "FacultyListCtl", urlPatterns = { "/ctl/FacultyListCtl" })

public class FacultyListCtl extends BaseCtl {

	/** loads the data in form of dropdown when page load */

	@Override
	protected void preload(HttpServletRequest request) {

		System.out.println("in preload");
		FacultyModel model = new FacultyModel();

		List list = null;

		list = model.list();
		System.out.println("LIst is :" + list);
		request.setAttribute("flist", list);

	}

	/** get the data from View and set into bean */

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		FacultyBean bean = new FacultyBean();

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		bean.setId(DataUtility.getInt(request.getParameter("id")));

		return bean;

	}

	/**
	 * Contains display logic
	 */

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FacultyBean bean = new FacultyBean();

		System.out.println("in to  Get ");
		FacultyModel model = new FacultyModel();
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		List list = null;
		List next = null;

		try {
			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			if (next == null || next.size() == 0) {

				request.setAttribute("NextPageSize", 0);

			} else {

				request.setAttribute("nextlistsize", next.size());
				System.out.println("NextPageSize" + next.size());
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

	/**
	 * Contains submit logic
	 */

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		System.out.println("Opration is " + op);

		FacultyModel model = new FacultyModel();
		List list = null;
		List next = null;
		if (OP_BACK.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
			return;
		}

		if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)
				|| OP_RESET.equalsIgnoreCase(op) || OP_DELETE.equalsIgnoreCase(op) || OP_NEW.equalsIgnoreCase(op)) {

			if (OP_NEW.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
				return;
			}

			if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;
				FacultyBean bean1 = new FacultyBean();
				FacultyModel model1 = new FacultyModel();

				String[] ids = request.getParameterValues("ids");
				System.out.println("edfghnjk,l+" + ids);
				System.out.println("under delete  block");
				if (ids != null && ids.length > 0) {
					System.out.println("under delete if block");
					for (String id : ids) {

						long idds = DataUtility.getLong(id);

						bean1.setId(idds);

						try {
							model1.delete(bean1);
						} catch (ApplicationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					ServletUtility.setSuccessMessage("Data deleted sucesfully", request);
				} else {
					ServletUtility.setErrorMessage("Select atlist One Rrecord", request);
				}

			}
			if (OP_SEARCH.equalsIgnoreCase(op)) {

				pageNo = 1;
			}

			if (OP_NEXT.equalsIgnoreCase(op)) {

				pageNo++;

			}

			if (OP_PREVIOUS.equalsIgnoreCase(op)) {

				pageNo--;

			}

			if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
				return;
			}
		}
		FacultyBean bean = new FacultyBean();
		bean = (FacultyBean) populateBean(request);
		try {
			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);
			System.out.println("sdfvgbhn,l.;xcfvhnmk" + list);

			if (list.size() == 0) {

				ServletUtility.setErrorMessage("No record Found", request);
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

		return ORSView.FACULTY_LIST_VIEW;
	}

}

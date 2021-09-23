package in.co.sunrays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.CourseBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.model.CourseModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

/**
 * @author Kamal
 *
 */

/** Course list Functionality */
@WebServlet(name = "CourseListCtl", urlPatterns = { "/ctl/CourseListCtl" })

public class CourseListCtl extends BaseCtl {
	public static Logger log = Logger.getLogger(CourseListCtl.class);

	/** loads the data in form of dropdown when page load */
	protected void preload(HttpServletRequest request) {
		CourseModel model = new CourseModel();
		List<CourseBean> clist = null;

		try {
			clist = model.list();
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>......." + clist.size());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		request.setAttribute("CourseList", clist);
	}

	/** get the data from View and set into bean */

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		CourseBean bean = new CourseBean();
		// bean.setName(DataUtility.getStringData(request.getParameter("")));
		bean.setId(DataUtility.getLong(request.getParameter("id")));

		// bean.setName(DataUtility.getStringData(request.getParameter("cname")));
		// populateDTO(bean, request);
		return bean;
	}

	/**
	 * Contains Display logics
	 */

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("CourseListCtl doGet method started");
		List list = null;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		CourseBean bean = (CourseBean) populateBean(request);
		CourseModel model = new CourseModel();
		try {
			list = model.list(pageNo, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record Found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (Exception e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("do get method of CourseCtl End");
		// ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Contains Submit logics
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// System.out.println("after delete wapas");
		log.debug("CourseCtl doPost Started");
		List list;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		// pageNo = (pageNo == 0) ? 1 : pageNo;
		// pageSize = (pageSize == 0) ?
		// DataUtility.getInt(request.getParameter("page.size")) : pageSize;

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");
		CourseBean bean = (CourseBean) populateBean(request);
		CourseModel model = new CourseModel();

		if (OP_SEARCH.equalsIgnoreCase(op)) {
			pageNo = 1;
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {

			System.out.println("i am in privious ");
			pageNo--;
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			return;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				CourseBean deletebean = new CourseBean();
				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));
					try {
						model.delete(deletebean);
						// ServletUtility.forward(getView(), request, response);
					} catch (Exception e) {
						e.printStackTrace();
						ServletUtility.handleException(e, request, response);
						return;
					}
					ServletUtility.setSuccessMessage("Course Deleted Successfully", request);
				}
			} else {
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
		}
		try {
			// if (!OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println("ttttt" + bean.getName());
			list = model.search(bean, pageNo, pageSize);
			// ServletUtility.setBean(bean, request);

			// System.out.println("(-----------------)"+ list.size());
			// ServletUtility.setList(list, request);
			// }

		} catch (ApplicationException e) {
			e.printStackTrace();
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
			ServletUtility.setErrorMessage("No record Found", request);
		}

		// ServletUtility.setBean(bean, request);

		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Contains display logic
	 */

	@Override
	protected String getView() {
		return ORSView.COURSE_LIST_VIEW;
	}
}

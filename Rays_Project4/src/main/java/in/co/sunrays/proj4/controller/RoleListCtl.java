package in.co.sunrays.proj4.controller;

import java.awt.geom.RectangularShape;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.model.RoleModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.ServletUtility;



/**
 * @author Kamal
 *
 *//**
 *
 *Role functionality Controller.
 **/
@WebServlet(name = "RoleListCtl", urlPatterns = { "/ctl/RoleListCtl" })
public class RoleListCtl extends BaseCtl {
	/** get the data from View and set into bean */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		RoleBean bean = new RoleBean();

		String name = DataUtility.getString(request.getParameter("name"));
		System.out.println("name is :::" + name);
		bean.setName(name);

		return bean;

	}

	/**
	 * Contains display logic
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = 1;
		int pageSize = 8;

		List list = null;
		List next = null;

		RoleModel model = new RoleModel();
		RoleBean bean = new RoleBean();

		try {
			list = model.list(pageNo, pageSize);
			next = model.list(pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {

				ServletUtility.setErrorMessage("NO Record Found", request);
			}

			if (next == null || next.size() == 0) {

				request.setAttribute("NextPageSize", 0);
			}

			else {

				request.setAttribute("NextPageSize", next.size());
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

		String op = request.getParameter("operation");
		List list = null;
		List next = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		System.out.println("page Size ::" + pageSize);
		RoleModel model = new RoleModel();

		RoleBean bean = (RoleBean) populateBean(request);

		if (OP_BACK.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
			return;
		}
		if (OP_SEARCH.equalsIgnoreCase(op)) {

			pageNo = 1;
		}
		try {
			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {

				ServletUtility.setErrorMessage("No Record Found", request);

			}

			if (next == null || next.size() == 0) {

				request.setAttribute("NextPageSize", 0);

			} else {

				request.setAttribute("NextPageSize", next.size());
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
		return ORSView.ROLE_LIST_VIEW;
	}

}

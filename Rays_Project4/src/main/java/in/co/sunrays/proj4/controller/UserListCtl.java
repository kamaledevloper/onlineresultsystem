package in.co.sunrays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.portable.ApplicationException;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.model.RoleModel;
import in.co.sunrays.proj4.model.UserModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

/**
 * UserList List functionality Controller. Performs operation for list, search
 * and delete operations of User
 * 
 * @author Kamal
 *
 */
@WebServlet(name = "UserListCtl", urlPatterns = { "/ctl/UserListCtl" })
public class UserListCtl extends BaseCtl {
	/** get the data from View and set into bean */

	@Override
	// get the perameter from JSP
	protected BaseBean populateBean(HttpServletRequest request) {

		UserBean bean = new UserBean();

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));

		bean.setRoleId(DataUtility.getLong(request.getParameter("roleid")));

		System.out.println("Role id is " + bean.getRoleId());

		return bean;

	}

	/**
	 * Contains display logic
	 */

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("click opration i am running ");

		List list = null;
		List next = null;
		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		// Set the user from JSP to bean object
		UserBean bean = (UserBean) populateBean(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();
		try {
			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);
			// Set the list in request atribute
			// ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			if (next == null || next.size() == 0) {

				request.setAttribute("NextPageSize", 0);

			} else {

				request.setAttribute("nextlistsize", next.size());
				System.out.println("NextPageSize" + next.size());
			}

		} catch (Exception e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
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

		RoleBean bean = new RoleBean();

		RoleModel model = new RoleModel();

		try {
			list = model.list();

			request.setAttribute("listrole", list);

		} catch (in.co.sunrays.proj4.exception.ApplicationException e) {
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

		List list = null;
		List next = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));

		System.out.println("page number in do post:" + pageNo);
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		System.out.println("page Size comes from JSp" + pageSize);
		// pageNo = (pageNo == 0) ? pageNo : 1;

		// pageSize = (pageSize == 0) ?
		// DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		UserBean bean = (UserBean) populateBean(request);

		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("Operation name is :" + op);
		String[] ids = request.getParameterValues("ids");

		UserModel model = new UserModel();
		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)
					|| OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {

				if (OP_BACK.equalsIgnoreCase(op)) {

					pageNo = 1;

				}

				if (OP_RESET.equalsIgnoreCase(op)) {

					ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);

					return;
				}

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;

				}
				if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;

				}
				if (OP_PREVIOUS.equalsIgnoreCase(op)) {

					pageNo--;
					System.out.println("page number in previous :" + pageNo);
				}

			}
			if (OP_NEW.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.USER_CTL, request, response);
				return;

			}
			if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;

				if (ids != null && ids.length > 0) {

					UserBean deletebean = new UserBean();

					for (String id : ids) {

						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);

					}
					ServletUtility.setSuccessMessage("Record Sucessfully Deleted", request);

				} else {

					ServletUtility.setErrorMessage("Select at least one record", request);
				}

			}

			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {

				ServletUtility.setErrorMessage("No record found", request);

			}

			if (next == null || next.size() == 0) {

				request.setAttribute("NextPageSize", 0);

			} else {

				request.setAttribute("nextlistsize", next.size());

				System.out.println("nextlistsize" + next.size());
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);

			ServletUtility.forward(getView(), request, response);

		} catch (

		Exception e) {
			e.printStackTrace();

			return;
		}
	}

	/** get the view */

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.USER_LIST_VIEW;
	}

}

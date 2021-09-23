package in.co.sunrays.proj4.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mysql.cj.xdevapi.Collection;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.RoleModel;
import in.co.sunrays.proj4.model.UserModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

/**
 * @author Kamal
 * 
 * 
 *  
 *
 *
 */

/**User  functionality Controller.*/
@WebServlet(name = "UserCtl", urlPatterns = { "/ctl/UserCtl" })
public class UserCtl extends BaseCtl {

	Logger log = Logger.getLogger(UserCtl.class);
	private static final long serialVersionUID = 1L;

	@Override

	/** loads the data in form of dropdown when page load */
	protected void preload(HttpServletRequest request) {

		System.out.println("tHIS is from User CTL preload");

		RoleModel modle = new RoleModel();

		try {
			List listr = modle.list();
			RoleBean bean = new RoleBean();
			System.out.println("This is from User UserCTL preload list Print :" + listr);

			Iterator it = listr.iterator();

			while (it.hasNext()) {
				bean = (RoleBean) it.next();

				System.out.println(bean.getId());

				System.out.println(bean.getName());

			}

			request.setAttribute("roleList", listr);
		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}

	/** Validate user input data */
	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		String login = request.getParameter("login");
		String dob = request.getParameter("dob");

		if (DataValidator.isNull(request.getParameter("firstName"))) {

			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));

			pass = false;

			if (DataValidator.isNull(request.getParameter("lastName"))) {
				request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
				pass = false;
			}

			if (DataValidator.isNull(login)) {
				request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
				pass = false;
			} else if (!DataValidator.isEmail(login)) {
				request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
				pass = false;
			}

			if (DataValidator.isNull(request.getParameter("password"))) {
				request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
				pass = false;
			}

			if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
				request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
				pass = false;
			}

			if (DataValidator.isNull(request.getParameter("gender"))) {
				request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("rolename"))) {
				request.setAttribute("rolename", PropertyReader.getValue("error.require", "Role"));
				pass = false;
			}
			if (DataValidator.isNull(dob)) {
				request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
				pass = false;
			} else if (!DataValidator.isDate(dob)) {
				request.setAttribute("dob", PropertyReader.getValue("error.date", "Date Of Birth"));
				pass = false;
			}
			if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
					&& !"".equals(request.getParameter("confirmPassword"))) {
				ServletUtility.setErrorMessage("Confirm  Password  should not be matched.", request);
				pass = false;
			}

		}

		log.info("UserCTL Validation Done..! ");
		return pass;
	}

	/** population user input */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRoleId(DataUtility.getLong(request.getParameter("rolename")));

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

		bean.setLogin(DataUtility.getString(request.getParameter("login")));

		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

		bean.setGender(DataUtility.getString(request.getParameter("gender")));

		bean.setDob(DataUtility.getDate(request.getParameter("dob")));

		try {
			InetAddress IP;
			IP = InetAddress.getLocalHost();
			String IPadd = IP.getHostAddress();
			bean.setRegisteredIP(IPadd);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		populateDTO(bean, request);
		log.info("UserCTL Population Done..! ");
		return bean;
	}

	/**
	 * Contains display logic
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("this is from Doget int UserCTL ");
		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {

			System.out.println("in id > 0  condition");
			UserBean bean;
			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;

			}
		}
		ServletUtility.forward(getView(), request, response);
		;
		log.info("UserCtl Doget ended...  ");

	}

	/**
	 * Contains submit logic
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("UserCtl Method doPost Started");

		log.info("UserCtl doPost Started...");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		UserModel model = new UserModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				} else {
					long pk = model.add(bean);
					bean.setId(pk);
				}
				// ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully saved", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			UserBean bean = (UserBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_CTL, request, response);

			return;

			// ServletUtility.forward(getView(), request, response);

		} else if (OP_BACK.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.info("UserCtl doPost ended...");
	}

	/** get the view */

	@Override
	protected String getView() {

		return ORSView.USER_VIEW;
	}

}

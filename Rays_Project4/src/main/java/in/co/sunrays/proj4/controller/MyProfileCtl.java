package in.co.sunrays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.UserModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

/**
 * @author Kamal
 *
 */
/** responsible for show valid user data on view */
@WebServlet(name = "MyProfileCtl", urlPatterns = { "/ctl/MyProfileCtl" })


public class MyProfileCtl extends BaseCtl {

	Logger log = Logger.getLogger(MyProfileCtl.class);

	public static final String OP_CHANGE_MY_PASSWORD = "ChangePassword";


/**Validate user input data*/
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.info("MyProfileCtl Method validate Started");

		boolean pass = true;

		String op = DataUtility.getString(request.getParameter("operation"));

		if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op) || op == null) {

			return pass;
		}

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			System.out.println("firstName" + request.getParameter("firstName"));
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "MobileNo"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		}

		log.info("MyProfileCtl Method validate Ended return " + pass);

		return pass;

	}
	/** get the data from View and set into bean*/
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		// log.debug("MyProfileCtl Method populatebean Started");
		System.out.println("MyProfileCtl Method populatebean Started");
		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setLogin(DataUtility.getString(request.getParameter("login")));

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		bean.setGender(DataUtility.getString(request.getParameter("gender")));

		bean.setDob(DataUtility.getDate(request.getParameter("dob")));

		populateDTO(bean, request);

		return bean;
	}

	/**
	 * Display Concept for viewing profile page view
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		log.info("MyprofileCtl Method doGet Started");

		/* Get the details of user from session */
		UserBean UserBean = (UserBean) session.getAttribute("user");

		/* in which is default generated (primary key ) */
		long id = UserBean.getId();

		log.info("Intarate user id(long type) by getSession ID:" + id);

		String createdby = UserBean.getCreatedBy();
		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		UserModel model = new UserModel();
		if (id > 0 || op != null) {
			// System.out.println("in id > 0 condition");
			UserBean bean;
			try {

				bean = model.findByPK(id);

				/* Set the data of user in request object */
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				// log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);

		log.info("MyProfileCtl Method doGet Ended");
	}

	/**
	 * Contains submit logic
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		// log.debug("MyprofileCtl Method doPost Started");

		UserBean UserBean = (UserBean) session.getAttribute("user");
		long id = UserBean.getId();
		System.out.println("Tis is from doPost mathod  id value is " + id);
		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();

		/* User can modify details */
		if (OP_SAVE.equalsIgnoreCase(op)) {
			log.info("Opration save Started");
			UserBean bean = (UserBean) populateBean(request);
			try {
				if (id > 0) {
					UserBean.setFirstName(bean.getFirstName());
					UserBean.setLastName(bean.getLastName());
					UserBean.setGender(bean.getGender());
					UserBean.setMobileNo(bean.getMobileNo());
					UserBean.setDob(bean.getDob());
					model.update(UserBean);

				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Profile has been updated Successfully", request);
			} catch (ApplicationException e) {

				log.error("Exception", e);

				log.error(e);
				// e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.CHANGE_PASSWORD_CTL, request, response);
			return;

		}

		ServletUtility.forward(getView(), request, response);

		log.info("MyProfileCtl Method doPost Ended");
	}

	/** get the view */
	@Override
	protected String getView() {
		return ORSView.MY_PROFILE_VIEW;
	}

}

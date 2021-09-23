package in.co.sunrays.proj4.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthSpinnerUI;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.RoleBean;
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

/**
 * User functionality Controller.
 */
@WebServlet(name = "UserRegistrationCtl", urlPatterns = { "/UserRegistrationCtl" })

public class UserRegistrationCtl extends BaseCtl {

	Logger log = Logger.getLogger(UserRegistrationCtl.class);
	public static final String OP_SIGN_UP = "Submit";

	/** Validate user input data */

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		String login = request.getParameter("login");
		String dob = request.getParameter("dob");

		Period period = null;

		Date d = null;

		// System.out.println(" years from period is " + period.getYears());

		System.out.println("Date from JSP" + dob);
		System.out.println("this is from user registration ---->>>String date is : " + dob);

		if (DataValidator.isNull(request.getParameter("firstName"))) {

			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {

			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));

			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("login"))) {

			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));

			pass = false;
		}

		else if (!DataValidator.isEmail(login)) {

			request.setAttribute("login", PropertyReader.getValue("error.email", "Login"));

			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("password"))) {

			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));

			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("password"))) {

			request.setAttribute("password", "password contains 8 letters with alpha-numeric & special character");
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
		if (DataValidator.isNull(dob)) {

			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(dob)) {
			System.out.println("this is from data velidator user registration ");

			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date Of Birth"));
			pass = false;

		}
		if (DataValidator.isDate(dob)) {

			d = DataUtility.getDate(dob);

			LocalDate date = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			period = Period.between(date, LocalDate.now());

			if (period.getYears() < 18) {

				ServletUtility.setErrorMessage("Age Should be  18 years", request);
				;
				pass = false;

			}

		}

		if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {

			ServletUtility.setErrorMessage("Confirm  Password  should  be matched", request);
			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {

			request.setAttribute("MobileNo", PropertyReader.getValue("error.require", "Mo.number"));

			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("mobileNo"))) {

			request.setAttribute("MobileNo", "Enter Valid mobile number");

			pass = false;

		}

		log.info("UserRegistrationCtl Valided Started and Return " + pass);

		System.out.println("validation going to return : " + pass);
		return pass;

	}

	/** get the data from View and set into bean */
	protected BaseBean populateBean(HttpServletRequest request) {

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		// Set default role as students
		bean.setRoleId(RoleBean.STUDENT);
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		System.out.println(request.getParameter("dob") + "----------------------------------------ffff---------------");
		System.out.println(DataUtility.getDate(request.getParameter("dob")));
		System.out.println(bean.getDob());
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		try {
			InetAddress IP = InetAddress.getLocalHost();

			String ip = IP.getHostAddress();
			bean.setRegisteredIP(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		log.info("UserRegistrationPopulate Started Data seted in Bean....");

		populateDTO(bean, request);
		return bean;
	}

	/**
	 * Contains display logic
	 */

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletUtility.forward(getView(), request, response);

	}

	/**
	 * Contains submit logic
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		log.info("UserRegistrationCtl doPost Started OPeration:" + op);

		UserModel model = new UserModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SIGN_UP.equalsIgnoreCase(op)) {

			UserBean bean = (UserBean) populateBean(request);

			System.out.println("this is from doPost in if condition");

			long pk;
			try {
				pk = model.registerUser(bean);

				bean.setId(pk);
				request.getSession().setAttribute("UserBean", bean);

				ServletUtility.setSuccessMessage("UserRegistration Sucess", request);

				ServletUtility.forward(getView(), request, response);
				// ServletUtility.redirect(ORSView.LOGIN_CTL, request,
				// response);

				log.info("Registration Sucessfull...");
				return;
			} catch (ApplicationException e) {
				System.out.println("In catch block");
				log.debug("Exception in User registration");
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {

				System.out.println("In catch block");

				log.debug("Exception in User registration");
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
				ServletUtility.forward(getView(), request, response);
			}

		}
		if (UserRegistrationCtl.OP_RESET.equalsIgnoreCase(op)) {

			System.out.println("in reset" + op);
			ServletUtility.forward(getView(), request, response);
			// ServletUtility.redirect(getView(), request, response);
		}

	}

	/** get the view */

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.USER_REGISTRATION_VIEW;

	}

}

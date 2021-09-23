package in.co.sunrays.proj4.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.model.RoleModel;
import in.co.sunrays.proj4.model.UserModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

/**
 * @author Kamal
 *
 */
/** Login functionality Controller.*/
@WebServlet(name = "LoginCtl", urlPatterns = { "/LoginCtl" })



public class LoginCtl extends BaseCtl {

	Logger log = Logger.getLogger(LoginCtl.class);

	public static final String OP_REGISTER = "Register";
	public static final String OP_SIGN_IN = "SignIn";
	public static final String OP_SIGN_UP = "SighUp";
	public static final String OP_LOG_OUT = "logout";

	@Override

	/** Validate user input data */
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		String op = request.getParameter("operation");

		if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {

			return pass;

		}
		String login = request.getParameter("login");
		// System.out.println("this is in Login value of Login is....!!!!" +
		// login);
		if (DataValidator.isNull(login)) {

			request.setAttribute("login", PropertyReader.getValue("error.require", "Login ID"));

			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			System.out.println("this is in Login CTL user else if ....!!!!");
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login"));
			pass = false;

		}

		String password = request.getParameter("password");

		if (DataValidator.isNull(password)) {

			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));

			pass = false;

		}

		log.info("LoginCtl Velidate Started,, Returning " + pass);
		return pass;

	}

	/** loads the data in form of dropdown when page load */
	protected BaseBean populateBean(HttpServletRequest request) {

		UserBean bean = new UserBean();

		bean.setLogin(DataUtility.getString(request.getParameter("login")));

		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		log.info("Login Ctl Poputate Started... ID ,pass sated in bean");
		return bean;
	}

	/**
	 * Contains display logic
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		String op = DataUtility.getString(request.getParameter("operation"));
		log.info("Do get LoginCtl Started..opration:" + op);
		UserModel model = new UserModel();
		RoleModel role = new RoleModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_LOG_OUT.equals(op)) {

			System.out.println("in Doget Opration Logout....!!!!!!!!!!!");

			session.invalidate();
			ServletUtility.setSuccessMessage("User Logout Succesfully", request);

			log.info("Opration Logout Sucess... Session Invelidate....");
			ServletUtility.forward(getView(), request, response);
			return;
		}
		/*
		 * if (id > 0) {
		 * 
		 * UserBean userbean;
		 * 
		 * try { userbean = model.findByPK(id);
		 * 
		 * ServletUtility.setBean(userbean, request); } catch
		 * (ApplicationException e) { ServletUtility.handleException(e, request,
		 * response);
		 * 
		 * return; } }
		 */

		log.info("Doget loginctl Started..... and forward on LoginView");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Contains submit logic
	 */

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		String op = DataUtility.getString(request.getParameter("operation"));

		log.info("LoginCtl doPost Started...opration :" + op);

		UserModel model = new UserModel();
		RoleModel role = new RoleModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("Long id value is " + id);
		if (OP_SIGN_IN.equalsIgnoreCase(op)) {

			InetAddress ipadd = InetAddress.getLocalHost();

			String ip = ipadd.getHostAddress();
			System.out.println("ip address" + ip);

			/* set userID and password in bean */
			UserBean bean = (UserBean) populateBean(request);

			try {
				bean = model.authenticate(bean.getLogin(), bean.getPassword());
				// user authentication success...
				if (bean != null) {
					/*
					 * set all user details in session this is first session on
					 * application
					 */
					session.setAttribute("user", bean);
					/* Get role ID from bean * default role id student */
					long rollld = bean.getRoleId();
					// System.out.println("Roll id is " + rollld);
					RoleBean roleBean = role.findByPK(rollld);

					if (roleBean != null) {
						/* Set Role of User */
						session.setAttribute("role", roleBean.getName());
						log.info("Role Name seted in session");
					}
					String str = (String) session.getAttribute("URI");
					if (str == null || "null".equalsIgnoreCase(str)) {
						ServletUtility.forward(ORSView.WELCOME_VIEW, request, response);
						return;
					} else {
						ServletUtility.redirect(str, request, response);
						return;
					}
				} else {
					bean = (UserBean) populateBean(request);
					log.info("User Athentication failed");
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Invalid LoginId And Password", request);
				}
			} catch (ApplicationException e) {
				// log.error(e);
				ServletUtility.handleException(e, request, response);
			}
			// ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
			System.out.println("after Redairect ");
			// return;
			/* Operation logout Session invalidate */
		} else if (OP_LOG_OUT.equals(op)) {

			session = request.getSession();

			session.invalidate();

			ServletUtility.redirect(ORSView.LOGIN_VIEW, request, response);
			return;

			/* From the loginView SignUp key */
		} else if (OP_SIGN_UP.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

	}

	/** get the view */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.LOGIN_VIEW;
	}

}

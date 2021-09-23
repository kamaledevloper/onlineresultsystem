package in.co.sunrays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.RecordNotFoundException;
import in.co.sunrays.proj4.model.UserModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

/**
 * @author Kamal
 *
 */

/** Forget Password functionality Controller.*/
@WebServlet(name = "ForgetPasswprdCtl", urlPatterns = { "/ForgetPasswordCtl" })


public class ForgetPasswordCtl extends BaseCtl {

	/** get the data from View and set into bean*/
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		UserBean bean = new UserBean();

		String email = DataUtility.getString(request.getParameter("email"));

		bean.setLogin("email");

		return bean;

	}
	/**Validate user input data*/
	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("email"))
				&& !DataValidator.isEmail(request.getParameter("email"))) {

			ServletUtility.setErrorMessage("Enter valid email id ", request);
			pass = false;
		}

		System.out.println("validater running in forgetpassword going to return :" + pass);
		return pass;

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
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserModel model = new UserModel();

		String email = DataUtility.getString(request.getParameter("email"));

		String op = DataUtility.getString(request.getParameter("operation"));

		if (op.equalsIgnoreCase("Go")) {

			try {
				Boolean flag = model.forgetPassword(email);

				ServletUtility.setSuccessMessage("Password Sucessfully sent to your email id", request);
				
				
				
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RecordNotFoundException e) {

				ServletUtility.setErrorMessage("Email id not valid", request);

				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ServletUtility.forward(getView(), request, response);

	}

	/** get the view */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.FORGET_PASSWORD_VIEW;
	}

}

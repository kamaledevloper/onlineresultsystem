
package in.co.sunrays.proj4.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.omg.CORBA.Request;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.CollegeBean;
import in.co.sunrays.proj4.bean.StudentBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.CollegeModel;
import in.co.sunrays.proj4.model.StudentModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

/**
 * @author Kamal
 *
 */

/**
*
*Student functionality Controller.
**/
@WebServlet(name = "StudentCtl", urlPatterns = { "/ctl/StudentCtl" })
public class StudentCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(StudentCtl.class);

	/** loads the data in form of dropdown when page load */

	@Override
	protected void preload(HttpServletRequest request) {

		int pageNo = 0;
		int pageSize = 0;
		List collegeBeanList = null;

		CollegeModel model = new CollegeModel();

		try {
			collegeBeanList = model.list(pageNo, pageSize);

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("College List bean : " + collegeBeanList);
		request.setAttribute("collegeList", collegeBeanList);
		System.out.println("preload calling ");
	}

	/** Validate user input data */
	@Override

	protected boolean validate(HttpServletRequest request) {

		// log.debug("StudentCtl Method validate Started");
		Period period = null;

		Date d = null;
		boolean pass = true;
		String op = DataUtility.getString(request.getParameter("operation"));

		String email = request.getParameter("email");

		String dob = request.getParameter("dob");

		System.out.println("Date from JSP" + dob);

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			System.out.println("in F name ");
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			System.out.println("in L name ");
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			System.out.println("in mobile name ");
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("mobileNo"))) {

			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Enter Valid Mobile No"));
			pass = false;

		}

		if (DataValidator.isNull(email)) {
			System.out.println("in email name ");
			request.setAttribute("email", PropertyReader.getValue("error.require", "Email "));
			pass = false;
		} else if (!DataValidator.isEmail(email)) {
			System.out.println("in else ifemail name ");
			request.setAttribute("email", PropertyReader.getValue("error.email", "Email "));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("collegeId"))) {

			System.out.println("This is from VAlitate mathod College is:" + request.getParameter("collegeId"));
			request.setAttribute("collegeId", PropertyReader.getValue("error.require", "College Name"));
			pass = false;
		}

		if (DataValidator.isNull(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(dob)) {
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

		// log.debug("StudentCtl Method validate Ended");

		System.out.println("this is from Student CTL in velidate going to return pass:" + pass);
		return pass;
	}

	/** get the data from View and set into bean */
	@Override

	protected BaseBean populateBean(HttpServletRequest request) {

		// log.debug("StudentCtl Method populatebean Started");
		StudentBean bean = new StudentBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

		bean.setDob(DataUtility.getDate(request.getParameter("dob")));

		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		bean.setEmail(DataUtility.getString(request.getParameter("email")));

		bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));

		bean.setCollegeName(DataUtility.getString(request.getParameter("collegename")));

		populateDTO(bean, request);

		// log.debug("StudentCtl Method populatebean Ended");

		return bean;

	}

	/**
	 * Contains display logic
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StudentModel model = new StudentModel();
		StudentBean bean = new StudentBean();
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println("this is from StudentCTL id is " + id);
		bean = model.findByPk(id);
		ServletUtility.setBean(bean, request);
		ServletUtility.forward(getView(), request, response);

	}

	/**
	 * Contains Submit logics
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// log.debug("StudentCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("i am from Do post operation is  " + op);
		// get model

		StudentModel model = new StudentModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_BACK.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
			return;

		}

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			System.out.println("This is from dopost if condition");
			StudentBean bean = (StudentBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				} else {
					long pk = model.add(bean);
					bean.setId(pk);

					System.out.println("this is from under if condition");
				}

				// ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully saved", request);

			} catch (ApplicationException e) { // log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Student Email Id already exists", request);
			}

		}

		else if (OP_DELETE.equalsIgnoreCase(op)) {

			StudentBean bean = (StudentBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.forward(getView(), request, response);

			return;

		}

		ServletUtility.forward(getView(), request, response);

		// log.debug("StudentCtl Method doPost Ended");
	}

	/** get the view */
	@Override
	protected String getView() {
		return ORSView.STUDENT_VIEW;
	}

}

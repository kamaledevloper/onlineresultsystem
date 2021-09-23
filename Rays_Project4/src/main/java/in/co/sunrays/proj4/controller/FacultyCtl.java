package in.co.sunrays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.protobuf.BoolValueOrBuilder;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.CourseBean;
import in.co.sunrays.proj4.bean.FacultyBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.CollegeModel;
import in.co.sunrays.proj4.model.CourseModel;
import in.co.sunrays.proj4.model.FacultyModel;
import in.co.sunrays.proj4.model.SubjectModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

/**
 * @author Kamal
 *
 */

/**faculty Functionality controller*/
@WebServlet(name = "FacultyCtl", urlPatterns = { "/ctl/FacultyCtl" })
public class FacultyCtl extends BaseCtl {

	
	/**Validate user input data*/
	
	@Override
	protected boolean validate(HttpServletRequest request) {

		System.out.println("in data velidator");

		boolean pass = true;

		String op = request.getParameter("operation");

		if (OP_BACK.equalsIgnoreCase(op)) {

			pass = true;

			return pass;

		}

		if (DataValidator.isNull(request.getParameter("firstName"))) {

			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));

			// ServletUtility.setErrorMessage(PropertyReader.getValue("error.require",
			// "First Name"), request);

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {

			request.setAttribute("lastname", PropertyReader.getValue("error.require", "Last Name"));

			// ServletUtility.setE
			// ServletUtility.setErrorMessage(PropertyReader.getValue("error.require",
			// "First Name"), request);

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {

			request.setAttribute("mobile", PropertyReader.getValue("error.require", "Mobile No."));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("email"))) {

			request.setAttribute("email", PropertyReader.getValue("error.require", "Email ID"));
			pass = false;
		}

		else if (!DataValidator.isEmail(request.getParameter("email"))) {

			System.out.println("in is email ");

			request.setAttribute("email", "Enter Valid email ID");

			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("dob"))) {

			request.setAttribute("DOB", PropertyReader.getValue("error.require", "Date of Birth"));
			pass = false;
			String college = request.getParameter("collegeid");

			System.out.println("sdf" + college);
		}
		if (DataValidator.isNull(request.getParameter("collegeId"))) {
			request.setAttribute("collegeId", PropertyReader.getValue("error.require", "CollegeName"));
			pass = false;
		}

		String course = request.getParameter("courseid");

		System.out.println("sdf" + course);

		if (DataValidator.isNull(request.getParameter("CourseId"))) {
			request.setAttribute("CourseId", PropertyReader.getValue("error.require", "CourseName"));
			pass = false;
		}

		String subject = request.getParameter("subjectId");

		System.out.println("sdf" + subject);
		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "SubjectName"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("qualifi"))) {

			request.setAttribute("qualifi", PropertyReader.getValue("error.require", "Qualification"));
			pass = false;

		}

		return pass;
	}

	@Override
	protected void preload(HttpServletRequest request) {

		List list = null;
		List list1 = null;
		List list2 = null;

		CollegeModel model = new CollegeModel();

		SubjectModel model1 = new SubjectModel();
		CourseModel model2 = new CourseModel();
		try {
			list = model.list();

			list1 = model1.list();

			list2 = model2.list();

			request.setAttribute("collegeList", list);
			request.setAttribute("subjectList", list1);
			request.setAttribute("courseList", list2);

			// ServletUtility.setList(list, request);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/** get the data from View and set into bean*/
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		System.out.println("In populate bean");
		FacultyBean bean = new FacultyBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		long cid = DataUtility.getLong(request.getParameter("collegeId"));

		bean.setCollegeid(cid);

		System.out.println("College ID :::::: " + cid);
		long coid = DataUtility.getLong(request.getParameter("CourseId"));
		System.out.println("Cours ID :::::: " + coid);
		bean.setCourseId(coid);
		long sid = DataUtility.getLong(request.getParameter("subjectId"));
		bean.setSubjectId(sid);
		System.out.println(cid + "  " + "  " + "" + sid + "   " + coid);
		System.out.println("Subject ID :::::: " + sid);
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		System.out.println(request.getParameter("Data is " + "dob"));

		bean.setDateofJoining(DataUtility.getDate(request.getParameter("dob")));

		bean.setLoginId(DataUtility.getString(request.getParameter("email")));
		bean.setMobileno(DataUtility.getString(request.getParameter("mobileNo")));
		bean.setQualification(DataUtility.getString(request.getParameter("qualifi")));

		bean.setGender(DataUtility.getString(request.getParameter("gender")));

		populateDTO(bean, request);

		return bean;
	}
	/**
	 * Contains display logic
	 */

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FacultyBean bean = new FacultyBean();
		FacultyModel model = new FacultyModel();

		String id = request.getParameter("id");

		long idd = DataUtility.getLong(id);

		bean = model.findByPk(idd);
		ServletUtility.setBean(bean, request);

		System.out.println("asdfghjk" + id);

		System.out.println("in GEt Faculty ");
		ServletUtility.forward(getView(), request, response);

	}
	/**
	 * Contains submit logic
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = request.getParameter("operation");

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op) || OP_RESET.equalsIgnoreCase(op)
				|| OP_BACK.equalsIgnoreCase(op)) {
			long id = DataUtility.getLong(request.getParameter("id"));
			FacultyModel model = new FacultyModel();

			FacultyBean bean = (FacultyBean) populateBean(request);

			if (OP_UPDATE.equalsIgnoreCase(op)) {

				try {
					model.update(bean);
				} catch (ApplicationException e) {
					// TODO Auto-generated catch block

					ServletUtility.handleException(e, request, response);
					e.printStackTrace();
				} catch (DuplicateRecordException e) {
					ServletUtility.setErrorMessage("Details already have", request);
					e.printStackTrace();
				}
				ServletUtility.setSuccessMessage("Details Updated Sucessfully", request);
			}

			if (OP_SAVE.equalsIgnoreCase(op)) {

				try {
					id = model.add(bean);
				} catch (ApplicationException e) {
					ServletUtility.handleException(e, request, response);
					e.printStackTrace();
				} catch (DuplicateRecordException e) {
					ServletUtility.setErrorMessage("Email_Id already registred", request);
					e.printStackTrace();
				}

				ServletUtility.setSuccessMessage("Details Saved", request);
			}

			System.out.println("in Sve Do Post ");

			if (OP_CANCEL.equalsIgnoreCase(op)) {

				ServletUtility.forward(getView(), request, response);
			}
			if (OP_RESET.equalsIgnoreCase(op)) {

				System.out.println("in OPration reset");
				ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);

				return;

			}

			if (OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);

				return;
			}

			ServletUtility.forward(getView(), request, response);

		}

	}

	/** get the view */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.FACULTY_VIEW;
	}

}

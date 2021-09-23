package in.co.sunrays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.chainsaw.Main;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.MarksheetBean;
import in.co.sunrays.proj4.bean.StudentBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.MarksheetModel;
import in.co.sunrays.proj4.model.StudentModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

/**
 * @author Kamal
 *
 */
/** Marksheet functionality Controller. */
@WebServlet(name = "MarksheetCtl", urlPatterns = { "/ctl/MarksheetCtl" })


public class MarksheetCtl extends BaseCtl {

	Logger log = Logger.getLogger(MarksheetCtl.class);

	/** loads the data in form of dropdown when page load */

	@Override
	protected void preload(HttpServletRequest request) {

		StudentModel model = new StudentModel();

		List l = model.list();

		request.setAttribute("studentList", l);

		log.info("prelad MarksheetCTL Started....!!");
	}

	/** Validate user input data */

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("rollNo"))) {

			request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));

			pass = false;

		} else if (!DataValidator.isRollNo(request.getParameter("rollNo"))) {

			request.setAttribute("rollNo", "RollNumber should be in 00EC0000");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("physics"))) {

			request.setAttribute("physics", PropertyReader.getValue("error.require", "physics marks"));
			pass = false;

		} else if (DataUtility.getInt(request.getParameter("physics")) < 0) {
			request.setAttribute("physics", "Marks can Not Less then 0");
			pass = false;
		} else if (DataUtility.getInt(request.getParameter("physics")) > 100) {
			request.setAttribute("physics", "Marks can Not More then 100");
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("physics"))
				&& !DataValidator.isInteger(request.getParameter("physics"))) {
			request.setAttribute("physics", PropertyReader.getValue("error.integer", "Physics marks"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", PropertyReader.getValue("error.require", "Chemistry Mark"));
			pass = false;
		} else if (DataUtility.getInt(request.getParameter("chemistry")) > 100) {
			request.setAttribute("chemistry", "Marks can Not More then 100");
			pass = false;
		} else if (DataUtility.getInt(request.getParameter("chemistry")) < 0) {
			request.setAttribute("chemistry", "Marks can Not less then 0 ");
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("chemistry"))
				&& !DataValidator.isInteger(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", PropertyReader.getValue("error.integer", "Chemistry Marks"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("maths"))) {
			request.setAttribute("maths", PropertyReader.getValue("error.require", "Maths Marks"));
			pass = false;
		} else if (DataUtility.getInt(request.getParameter("maths")) > 100) {
			request.setAttribute("maths", "Marks can Not More then 100");
			pass = false;
		} else if (DataUtility.getInt(request.getParameter("maths")) < 0) {
			request.setAttribute("maths", "Marks can Not less then 0 ");
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("maths"))
				&& !DataValidator.isInteger(request.getParameter("maths"))) {
			request.setAttribute("maths", PropertyReader.getValue("error.integer", "Chemistry Marks"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("studentId"))) {
			request.setAttribute("studentId", PropertyReader.getValue("error.require", "Student Name"));
			pass = false;
		}

		log.info("Valided end and return : " + pass);
		return pass;

	}

	/** get the data from View and set into bean */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		MarksheetBean bean = new MarksheetBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));

		// bean.setName(DataUtility.getString(request.getParameter("name")));

		bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));
		bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
		bean.setMaths(DataUtility.getInt(request.getParameter("maths")));

		bean.setStudentId(DataUtility.getLong(request.getParameter("studentId")));

		populateDTO(bean, request);

		System.out.println("Population done ");

		return bean;
	}

	/**
	 * Contains display logic
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		MarksheetModel model = new MarksheetModel();

		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println("long type ID is: " + id);
		if (id > 0) {

			MarksheetBean bean = new MarksheetBean();

			try {
				bean = model.findByPK(id);
				// System.out.println("Name of Student" + bean.getName());
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();

			}
			// return;
		}
		log.info("doGet MarksheetCtl Started...!!");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Contains submit logic
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		MarksheetModel model = new MarksheetModel();

		Long id = DataUtility.getLong(request.getParameter("id"));

		MarksheetBean bean = (MarksheetBean) populateBean(request);

		if (MarksheetCtl.OP_UPDATE.equalsIgnoreCase(op) || MarksheetCtl.OP_SAVE.equalsIgnoreCase(op)

				|| MarksheetCtl.OP_DELETE.equalsIgnoreCase(op)

		) {
			if (MarksheetCtl.OP_UPDATE.equalsIgnoreCase(op)) {

				try {
					model.update(bean);

					// ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Details Sucessfully Updated", request);
				} catch (ApplicationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DuplicateRecordException e) {
					ServletUtility.setErrorMessage("Roll Number already exists", request);
					e.printStackTrace();
				}

			}

			if (MarksheetCtl.OP_SAVE.equals(op)) {

				long pk;
				try {
					pk = model.add(bean);
					bean.setId(pk);
					// ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Data is successfully saved", request);
					log.info("Sucess Markssheet Added long PK :" + pk);
				} catch (ApplicationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DuplicateRecordException e) {
					// TODO Auto-generated catch block
					ServletUtility.setErrorMessage(
							"Roll number already exists or Marksheet Of This Student already exist", request);
					e.printStackTrace();
				}

			}

			if (MarksheetCtl.OP_DELETE.equalsIgnoreCase(op)) {
				try {
					model.delete(bean);

					ServletUtility.setSuccessMessage("Record Deleted", request);
				} catch (ApplicationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			ServletUtility.forward(getView(), request, response);
		}

		if (MarksheetCtl.OP_CANCEL.equalsIgnoreCase(op))

		{

			System.out.println("under Opration cancel");

			// ServletUtility.redirect(getView(), request, response);
			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);

			// forward(getView(), request, response);

		}
		if (MarksheetCtl.OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);

		}
		log.info("doPost ended...");
	}

	/** Get the View of Marksheet */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MARKSHEET_VIEW;
	}

}

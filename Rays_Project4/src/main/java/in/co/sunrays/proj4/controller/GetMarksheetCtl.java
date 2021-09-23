package in.co.sunrays.proj4.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.MarksheetBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.model.MarksheetModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.ServletUtility;

/**
 * @author Kamal
 *
 */

/** Get Marksheet functionality Controller. */
@WebServlet(name = "GetMarksheetCtl", urlPatterns = { "/ctl/GetMarksheetCtl" })



public class GetMarksheetCtl extends BaseCtl {

	Logger log = Logger.getLogger(GetMarksheetCtl.class);

	/** Validate user input data */
	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		String op = request.getParameter("operation");
		if (OP_BACK.equalsIgnoreCase(op)) {

			System.out.println("in validate go back");

			return true;
		}

		String Rollno = request.getParameter("rollno");
		log.info("roller Number: " + Rollno);
		if (DataValidator.isNull(Rollno) || Rollno.length() == 0) {
			ServletUtility.setErrorMessage(" Roll Number required", request);
			pass = false;

		} else if (!DataValidator.isRollNo(Rollno)) {
			ServletUtility.setErrorMessage(" Rollnumber should be in 00EC0000", request);
			pass = false;
		}
		log.info("Validate end return: " + pass);
		return pass;
	}

	@Override
	protected void preload(HttpServletRequest request) {
		// TODO Auto-generated method stub
		super.preload(request);
	}

	/** get the data from View and set into bean */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		MarksheetBean bean = new MarksheetBean();

		String rollNo = DataUtility.getString(request.getParameter("rollno"));

		bean.setRollNo(rollNo);
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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		if (op.equalsIgnoreCase(OP_GO)) {

			MarksheetBean bean = (MarksheetBean) populateBean(request);

			MarksheetModel model = new MarksheetModel();

			try {
				List list = model.search(bean);

				if (list != null && list.size() != 0) {

					System.out.println("in if condition");

					ServletUtility.setList(list, request);
				} else {

					ServletUtility.setErrorMessage("No record found", request);
				}

				System.out.println("List is ::::" + list);
			} catch (ApplicationException e) {

				System.out.println("in catch block");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServletUtility.forward(getView(), request, response);
		}

		if (op.equalsIgnoreCase(OP_RESET)) {

			System.out.println("in Go back ");
			ServletUtility.redirect(ORSView.GET_MARKSHEET_CTL, request, response);
		}

	}

	/** get the view */

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.GETMARKSHEET_VIEW;
	}
}

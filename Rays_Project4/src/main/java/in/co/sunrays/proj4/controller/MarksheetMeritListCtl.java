package in.co.sunrays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.MarksheetBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.model.MarksheetModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

/**
 * @author Kamal
 *
 */
/**
 * 
 * 
 * Responsible for Merit mark sheet list
 */
@WebServlet(name = "MarksheetMeritListCtl", urlPatterns = { "/ctl/MarksheetMeritListCtl" })


public class MarksheetMeritListCtl extends BaseCtl {
	/** get the view */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MARKSHEET_MERIT_LIST_VIEW;
	}

	/** Validate user input data */
	@Override
	protected boolean validate(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return super.validate(request);
	}

	/** loads the data in form of dropdown when page load */
	@Override
	protected void preload(HttpServletRequest request) {
		// TODO Auto-generated method stub
		super.preload(request);
	}

	/** get the data from View and set into bean */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		MarksheetBean bean = new MarksheetBean();

		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setRollNo(DataUtility.getString(request.getParameter("roleno")));

		return bean;

	}

	@Override
	protected BaseBean populateDTO(BaseBean dto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return super.populateDTO(dto, request);
	}

	/**
	 * Contains display logic
	 */

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {

		List list = null;

		MarksheetModel model = new MarksheetModel();

		int pageSize = 10;

		int pageNo = 1;

		try {
			list = model.getMeritList(pageNo, pageSize);

			ServletUtility.setList(list, request);

			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Contains submit logic
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List list = null;
		String op = DataUtility.getString(request.getParameter("operation"));

		System.out.println("Opration " + op);
		int pageNo = DataUtility.getInt(request.getParameter("pageno"));

		System.out.println("page number in Dopost" + pageNo);
		int pageSize = DataUtility.getInt(request.getParameter("pagesize"));

		MarksheetBean bean = (MarksheetBean) populateBean(request);
		MarksheetModel model = new MarksheetModel();

		if (OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {

			if (OP_NEXT.equalsIgnoreCase(op)) {

				pageNo++;
				System.out.println("in if condition op next" + pageNo);

			}

			if (OP_PREVIOUS.equalsIgnoreCase(op)) {

				pageNo--;
				System.out.println("in if condition op previous" + pageNo);

			}

			try {

				list = model.search(bean, pageNo, pageSize);

				ServletUtility.setList(list, request);

				ServletUtility.setPageNo(pageNo, request);
				ServletUtility.setPageSize(pageSize, request);

				if (list.size() == 0) {

					ServletUtility.setErrorMessage("No record found", request);

				}

				System.out.println("in try block");
			} catch (ApplicationException e) {
				System.out.println("in catch block");

				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ServletUtility.forward(getView(), request, response);

		}
		if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {

			if (OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);

				return;
			}

		}

	}

}

package in.co.sunrays.proj4.controller;

import java.io.IOException;
import java.sql.SQLData;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.TimeTableBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.model.SubjectModel;
import in.co.sunrays.proj4.model.TimeTableModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

/**
 * 
 * Timetable List functionality Controller. Performs operation for list,
 * search and delete operations of Timetable
 *
 *
 * 
 * @author Kamal
 *
 */
@WebServlet(name = "TimeTableListCtl", urlPatterns = { "/ctl/TimeTableListCtl" })
public class TimeTableListCtl extends BaseCtl {

	@Override

	protected void preload(HttpServletRequest request) {

		List list = null;
		SubjectModel model = new SubjectModel();

		try {
			list = model.list();

			request.setAttribute("slist", list);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Logger log = Logger.getLogger(TimeTableListCtl.class);

	/** Validate user input data */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.info("in validate ");
		boolean flag = true;
		/*
		 * if (DataValidator.isNull(DataUtility.getString(request.getParameter(
		 * "examdate"))) &&
		 * DataValidator.isNull(DataUtility.getString(request.getParameter(
		 * "subject")))) {
		 * 
		 * ServletUtility.setErrorMessage("Enter at list one parameter",
		 * request);
		 * 
		 * flag = true;
		 * 
		 * }
		 */
		System.out.println("i am from Velidate going to return flag ::" + flag);
		return flag;

	}

	/** get the data from View and set into bean */
	@Override
	protected TimeTableBean populateBean(HttpServletRequest request) {

		TimeTableBean bean = null;

		Date date = null;

		long id = DataUtility.getLong((request.getParameter("subject")));

		System.out.println("subject issdgfdhdi :" + id);

		String exdate = request.getParameter("examdate");
		System.out.println("Date is " + exdate);
		if (!DataValidator.isNull(exdate))

		{

			/* thisexdate != null || exdate.length() > 0) */

			System.out.println("i am in your if condition");
			date = DataUtility.getDate(exdate);

			// etDate(exdate);
		}

		System.out.println("Date is :" + date);
		if (id != 0 || date != null) {

			bean = new TimeTableBean();

			bean.setSubjectId(id);
			// bean.setSubjectName(subject);

			System.out.println("subject ID : " + id);
			System.out.println("Date is  : " + date);
			bean.setExamDate(date);

			return bean;
		} else {

			return bean;

		}

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
		List next = null;
		TimeTableBean bean = new TimeTableBean();

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		log.info("in get mathd...!!!!");

		bean = (TimeTableBean) populateBean(request);

		TimeTableModel model = new TimeTableModel();

		try {
			list = model.search(bean, pageNo, pageSize);

			next = model.search(bean, pageNo + 1, pageSize);
			log.info("Timetablelist Search sucess....");

			if (list.size() == 0 || list == null) {

				ServletUtility.setErrorMessage(" no record found", request);
			}

			if (next.size() == 0 || next == null) {

				request.setAttribute("nextListSize", 0);
			}

			else {
				request.setAttribute("nextListSize", list.size());
			}

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			log.info("in catch block : " + e);
		}
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);

	}

	/**
	 * Contains submit logic
	 */

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("operation in serch : " + op);

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		TimeTableBean bean = new TimeTableBean();
		TimeTableModel model = new TimeTableModel();
		String[] ids = request.getParameterValues("ids");
		System.out.println("dfghjkl;ccfvgbhnjmk,l.;" + ids);

		if (OP_BACK.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TIMETABLELIST_CTL, request, response);

			return;

		}

		if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)
				|| OP_RESET.equalsIgnoreCase(op) || OP_DELETE.equalsIgnoreCase(op)) {

			System.out.println("in if block opration is :" + op);

			if (TimeTableListCtl.OP_SEARCH.equalsIgnoreCase(op)) {

				pageNo = 1;

			}

			if (TimeTableListCtl.OP_RESET.equalsIgnoreCase(op)) {

				pageNo = 1;

			}

			if (TimeTableListCtl.OP_PREVIOUS.equalsIgnoreCase(op)) {

				pageNo--;

			}

			if (TimeTableListCtl.OP_NEXT.equalsIgnoreCase(op)) {

				pageNo++;
			}

			if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;

				System.out.println("in delete block");

				if (ids != null && ids.length > 0) {

					for (String id : ids) {

						long idss = DataUtility.getLong(id);

						bean.setId(idss);
						try {
							model.delete(bean);
						} catch (ApplicationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ServletUtility.setSuccessMessage("Data delete sucessfully", request);

					}

				} else {
					ServletUtility.setErrorMessage("Select atlist one record", request);

				}

			}
			System.out.println("User serch");

			List list = null;
			List next = null;

			TimeTableBean bean1 = (TimeTableBean) populateBean(request);

			try {
				list = model.search(bean1, pageNo, pageSize);
				next = model.search(bean1, pageNo + 1, pageSize);

				ServletUtility.setList(list, request);

				ServletUtility.setPageNo(pageNo, request);
				ServletUtility.setPageSize(pageSize, request);

				if (list.size() == 0) {

					ServletUtility.setErrorMessage("No record Found", request);

				}
				if (next.size() == 0 || next == null) {

					request.setAttribute("nextListSize", 0);
				}

				else {
					request.setAttribute("nextListSize", list.size());
				}

			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (OP_NEW.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
			return;

		}

		ServletUtility.forward(getView(), request, response);

	}

	/** get the view */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.TIMETABLELIST_VIEW;
	}

}

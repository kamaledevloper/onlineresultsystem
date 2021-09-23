package in.co.sunrays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.sunrays.proj4.bean.CourseBean;
import in.co.sunrays.proj4.util.DataUtility;

import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.CourseModel;
import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

/**
 * @author Kamal
 *
 */
/** Course Functionality controller */
@WebServlet(name = "CourseCtl", urlPatterns = { "/ctl/CourseCtl" })

public class CourseCtl extends BaseCtl {

	/** Validate user input data */
	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("MarksheetCtl validate method started");
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("name"))

		) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}

		log.debug("CourseCtl validate End");
		return pass;
	}

	/** get the data from View and set into bean */

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("CourseCtl populateBean Started");
		CourseBean bean = new CourseBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		System.out.println("Course Name is :::;: " + bean.getName());

		bean.setDescription(DataUtility.getString(request.getParameter("description")));

		System.out.println("Course details  is:::: : " + bean.getDescription());
		populateDTO(bean, request);
		return bean;
	}

	/**
	 * Contains display logic
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BaseBean bean = (CourseBean) populateBean(request);

		if (bean.getId() > 0) {

			CourseModel model = new CourseModel();

			CourseBean cbean = model.findByPk(bean.getId());

			ServletUtility.setBean(cbean, request);

		}

		// ServletUtility.setBean(bean, request);

		ServletUtility.forward(getView(), request, response);

	}

	/**
	 * Contains submit logic
	 */

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("Do Post method of CourseCtl started ");
		String op = DataUtility.getString(request.getParameter("operation"));

		// Get Model
		CourseModel model = new CourseModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			CourseBean bean = (CourseBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
				} else {

					System.out.println("i am in else block under save");
					long pk = model.add(bean);
					// bean.setId(pk);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Course is Successfully saved", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Course Name Already Exist", request);

			}
		} /*
			 * else if (OP_DELETE.equalsIgnoreCase(op)) { CourseBean bean
			 * =(CourseBean) populateBean(request); try { model.delete(bean);;
			 * ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
			 * return; } catch (ApplicationException e) { log.error(e);
			 * ServletUtility.handleException(e, request, response); return ; }
			 * }
			 */
		else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("Do Post method CourseCtl Ended");

	}

	/** get the view */

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.COURSE_VIEW;
	}
}

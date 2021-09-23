package in.co.sunrays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.ServletUtility;

/**
 * @author Kamal
 *
 */

/** Base CTL this for generic flow method and constant  */
public abstract class BaseCtl extends HttpServlet {

	Logger log = Logger.getLogger(BaseCtl.class);

	public static final String OP_SAVE = "Save";
	public static final String OP_CANCEL = "Cancel";
	public static final String OP_DELETE = "Delete";
	public static final String OP_LIST = "List";
	public static final String OP_SEARCH = "Search";
	public static final String OP_VIEW = "View";
	public static final String OP_NEXT = "Next";
	public static final String OP_PREVIOUS = "Previous";
	public static final String OP_NEW = "New";
	public static final String OP_BACK = "Back";
	public static final String OP_LOG_OUT = "Logout";
	public static final String MSG_SUCCESS = "success";
	public static final String OP_RESET = "Reset";
	public static final String OP_GO = "Go";
	public static final String OP_PRINT = "Print";
	public static final String OP_UPDATE = "Update";

	
	/**
	 * Error message key constant
	 */
	
	public static final String MSG_ERROR = "error";

	/**
	 * Validates input data entered by User
	 *
	 * @param request
	 * @return
	 */
	protected boolean validate(HttpServletRequest request) {
		
		System.out.println("validate callll");
		return true;
	}

	/**
	 * Loads list and other data required to display at HTML form
	 *
	 * @param request
	 */
	protected void preload(HttpServletRequest request) {
		
		System.out.println("preload call");
	}

	/** Get the data from View and set in bean  */
	protected BaseBean populateBean(HttpServletRequest request) {
		return null;
	}
	/**
	 * Populates Generic attributes in DTO
	 *
	 */
	protected BaseBean populateDTO(BaseBean dto, HttpServletRequest request) {

		String createdBy = request.getParameter("createdBy");
		String modifideBy = null;

		UserBean userbean = (UserBean) request.getSession().getAttribute("user");

		if (userbean == null) {

			createdBy = "root";
			modifideBy = "root";

		} else {

			/* This condition for User Registration when no one login */
			log.info("Base Ctl PopulateDTO modifide By = user Login ID  ");
			modifideBy = userbean.getLogin();

			if ("null".equalsIgnoreCase(createdBy) || DataValidator.isNull(createdBy)) {

				createdBy = modifideBy;
			}
		}
		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifideBy);

		long cdt = DataUtility.getLong(request.getParameter("createdDatetime"));

		if (cdt > 0) {

			dto.setCreatedDatetime(DataUtility.getTimestamp(cdt));

		} else {

			// This condition for User Registration time when no one login
			dto.setCreatedDatetime(DataUtility.getCurrentTimestamp());

			dto.setModifiedDatetime(DataUtility.getCurrentTimestamp());
		}
		log.info("BaseCtl Populate DTO Started...");
		return dto;
	}

	/** Life Cycle of HTTP Servlet  */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/* Load pre operation required data on HTML */
		preload(request);

		String op = DataUtility.getString(request.getParameter("operation"));
		log.info("BaseCtl Service Started..." + "opration:" + op);

		if (DataValidator.isNotNull(op) && !OP_CANCEL.equalsIgnoreCase(op) && !OP_VIEW.equalsIgnoreCase(op)
				&& !OP_DELETE.equalsIgnoreCase(op) && !OP_RESET.equalsIgnoreCase(op)) {

			if (!validate(request)) {
				log.info("Dedicated Ctl Velidate mathod return:false");
				BaseBean bean = (BaseBean) populateBean(request);
				ServletUtility.setBean(bean, request);

				ServletUtility.forward(getView(), request, response);

				return;
			}
		}

		System.out.println();

		log.info("super service call...");
		super.service(request, response);

	}

	/**
	 * Returns the VIEW page of this Controller
	 *
	 * 
	 */
	protected abstract String getView();
}
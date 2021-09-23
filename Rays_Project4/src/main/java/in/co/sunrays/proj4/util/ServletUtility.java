package in.co.sunrays.proj4.util;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.controller.BaseCtl;
import in.co.sunrays.proj4.controller.ORSView;
import in.co.sunrays.proj4.model.AppRole;

import in.co.sunrays.proj4.model.UserModel;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * This class provides utility operation for Servlet container like forward,
 * redirect, handle generic exception, manage success and error message, manage
 * default Bean and List, manage pagination parameters
 *
 * @author kamal
 * 
 */

public class ServletUtility {

	static Logger log = Logger.getLogger(ServletUtility.class);

	/**
	 * Forward to given JSP/Servlet
	 *
	 * 
	 */
	public static void forward(String page, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);

		log.info("Forwarding to given page ");
	}

	/**
	 * Forward to Layout View
	 *
	 * 
	 */
	public static void forwardView(String page, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		request.setAttribute("bodyPage", page);
		RequestDispatcher rd = request.getRequestDispatcher(ORSView.LAYOUT_VIEW);
		rd.forward(request, response);
	}

	/**
	 * Redirect to given JSP/Servlet
	 *
	 * 
	 */
	public static void redirect(String page, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.info("Get Redairect on GivenPage");
		response.sendRedirect(page);
	}

	/**
	 * Redirect to Application Error Handler Page
	 *
	 * 
	 */
	public static void handleException(Exception e, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setAttribute("exception", e);
		// response.sendRedirect(ORSView.ERROR_CTL);

	}

	/**
	 * Gets error message from request
	 * 
	 */
	public static String getErrorMessage(String property, HttpServletRequest request) {
		System.out.println("Get Error run");
		String val = (String) request.getAttribute(property);

		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	/**
	 * returns all input error messages
	 *
	 * @deprecated Use HTMLUtil method instead
	 * @param request
	 * @return
	 */
	public static String getErrorMessageHtml(HttpServletRequest request) {

		Enumeration<String> e = request.getAttributeNames();

		StringBuffer sb = new StringBuffer("<UL>");
		String name = null;

		while (e.hasMoreElements()) {
			name = e.nextElement();
			if (name.startsWith("error.")) {
				sb.append("<LI class='error'>" + request.getAttribute(name) + "</LI>");
			}
		}
		sb.append("</UL>");
		return sb.toString();
	}

	/**
	 * Gets a message from request
	 *
	 * 
	 */
	public static String getMessage(String property, HttpServletRequest request) {
		String val = (String) request.getAttribute(property);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	/**
	 * Sets error message to request
	 *
	 * 
	 */
	public static void setErrorMessage(String msg, HttpServletRequest request) {
		request.setAttribute(BaseCtl.MSG_ERROR, msg);

		log.info("Error Msg Seted");

	}

	/**
	 * Gets error message from request
	 *
	 * 
	 */
	public static String getErrorMessage(HttpServletRequest request) {
		String val = (String) request.getAttribute(BaseCtl.MSG_ERROR);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	/**
	 * Sets success message to request
	 *
	 * 
	 */
	public static void setSuccessMessage(String msg, HttpServletRequest request) {
		System.out.println("Set success message start ");

		request.setAttribute(BaseCtl.MSG_SUCCESS, msg);
	}

	/**
	 * Gets success message from request
	 *
	 * 
	 */
	public static String getSuccessMessage(HttpServletRequest request) {
		String val = (String) request.getAttribute(BaseCtl.MSG_SUCCESS);

		log.info(" get sucess massage value : " + val);

		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	/**
	 * Sets default Bean to request
	 *
	 * 
	 */
	public static void setBean(BaseBean bean, HttpServletRequest request) {
		request.setAttribute("bean", bean);

		log.info("bean seted for user pre fill details");
	}

	public static void setUserModel(UserModel model, HttpServletRequest request) {

		System.out.println("set User model runnin ");
		request.getSession().setAttribute("user", model);

	}

	/**
	 * Gets default bean from request
	 *
	 * 
	 */

	public static BaseBean getBean(HttpServletRequest request) {
		return (BaseBean) request.getAttribute("bean");
	}

	public static boolean isLoggedIn(HttpServletRequest request) {
		UserModel model = (UserModel) request.getSession().getAttribute("user");
		return model != null;
	}

	/**
	 * Returns logged in user role
	 *
	 * 
	 */

	public static long getRole(HttpServletRequest request) {
		UserModel model = (UserModel) request.getSession().getAttribute("user");
		if (model != null) {
			return model.getRoleId();
		} else {
			return AppRole.GUEST;
		}
	}

	/**
	 * Get request parameter to display. If value is null then return empty
	 * string
	 *
	 * 
	 */

	public static String getParameter(String property, HttpServletRequest request) {
		String val = (String) request.getParameter(property);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	/**
	 * Sets default List to request
	 *
	 * 
	 */
	public static void setList(List list, HttpServletRequest request) {

		request.setAttribute("list", list);
		System.out.println("list seted in requist attribute kry name list");
	}

	/**
	 * Gets default list from request
	 * 
	 */
	public static List getList(HttpServletRequest request) {
		return (List) request.getAttribute("list");
	}

	/**
	 * Sets Page Number for List pages
	 *
	 * 
	 */
	public static void setPageNo(int pageNo, HttpServletRequest request) {
		request.setAttribute("pageNo", pageNo);
	}

	/**
	 * Gets Page Number for List pages
	 *
	 * 
	 */
	public static int getPageNo(HttpServletRequest request) {

		int pageNo = (Integer) request.getAttribute("pageNo");
		System.out.println("this is from Set PageNo.:" + pageNo);
		return pageNo;
	}

	/**
	 * Sets Page Size for list pages
	 *
	 * 
	 */
	public static void setPageSize(int pageSize, HttpServletRequest request) {
		request.setAttribute("pageSize", pageSize);
	}

	/**
	 * Gets Page Size for List pages
	 *
	 * 
	 */
	public static int getPageSize(HttpServletRequest request) {
		return (Integer) request.getAttribute("pageSize");
	}

}
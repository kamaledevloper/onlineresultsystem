package in.co.sunrays.proj4.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.util.ServletUtility;

/**
 * @author Kamal
 *
 */

/**
 * Servlet Filter implementation class FrontController
 */
@WebFilter(filterName = "FrontCtl", urlPatterns = { "/ctl/*", "/doc/*" })

public class FrontController implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain arg2)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		HttpServletResponse res = (HttpServletResponse) response;

		HttpSession session = req.getSession();

		UserBean bean = (UserBean) session.getAttribute("user");

		if (bean == null) {

			request.setAttribute("message", "Your Session has been Expired... Please Login Again");

			String str = req.getRequestURI();
			System.out.println("getRequestURI()>>>>>>>>>>>>>>>...>>>>>>>>>" + str);

			session.setAttribute("URI", str);

			ServletUtility.forward(getView(), req, res);
			return;

		} else {
			arg2.doFilter(req, res);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	public String getView() {

		return ORSView.LOGIN_VIEW;
	}

}

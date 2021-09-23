package in.co.sunrays.proj4.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.DropdownListBean;

/**
 * @author Kamal
 *
 */

/* All the HTML utility method */
public class HTMLUtility {

	static Logger log = Logger.getLogger(HTMLUtility.class);

	public static String getList(String name, String selectedVal, HashMap<String, String> map) {

		System.out.println("SELECTED VALUE IS " + selectedVal);
		StringBuffer sb = new StringBuffer("<select  class='form-control' name='" + name + "'>" + "size=38");

		Set<String> keys = map.keySet();

		// System.out.println("The set is: " + map.keySet());
		String val = null;
		boolean select = true;
		if (select) {

			sb.append("<option selected value=''>-----------------Select-----------------</option>");
		}

		// System.out.println("htmlllll " + selectedVal);
		for (String key : keys) {
			val = map.get(key);
			if (key.trim().equals(selectedVal)) {
				sb.append("<option selected value='" + val + "'>" + val + "</option>");
			} else {
				sb.append("<option value='" + key + "'>" + val + "</option>");
			}
		}

		sb.append("</select>");

		log.info("getList End return : " + sb.toString());

		return sb.toString();
	}

	/**
	 * Create HTML SELECT List from List parameter
	 *
	 * @param name
	 * @param selectedVal
	 * @param list
	 * @return
	 */
	public static String getList(String name, String selectedVal, List list) {
		log.info("3 perameter getList Starter name,selectedVal, List ::" + name + "  " + selectedVal + "  " + list);
		Collections.sort(list);

		// System.out.println("Shorted list is " + list);

		List<DropdownListBean> dd = (List<DropdownListBean>) list;

		StringBuffer sb = new StringBuffer("<select class='form-control' name='" + name + "'>");
		// System.out.println("this is from String buffer" + sb);
		boolean select = true;

		if (select) {

			sb.append("<option  selected value=''>-----------------Select-----------------</option>");
		}

		String key = null;
		String val = null;

		for (DropdownListBean obj : dd) {
			key = obj.getKey();
			val = obj.getValue();

			System.out.println("key any value is " + key + " " + val);

			if (key.trim().equals(selectedVal)) {
				// System.out.println("i am in HTML Utility under if condition
				// selected value is " + selectedVal);
				sb.append("<option selected value='" + key + "'>" + val + "</option>");
			} else {

				// System.out.println("i am from HTML utility under else
				// condition");
				sb.append("<option value='" + key + "'>" + val + "</option>");
			}
		}
		sb.append("</select>");

		log.info("getList ended return:" + sb);

		return sb.toString();
	}

	public static String getList(String name, String selectedVal, HashMap<String, String> map, boolean select) {

		StringBuffer sb = new StringBuffer("<select class='form-control' name='" + name + "'>");

		Set<String> keys = map.keySet();
		String val = null;

		if (select) {

			sb.append("<option selected value=''> --Select-- </option>");
		}

		for (String key : keys) {
			val = map.get(key);
			if (key.trim().equals(selectedVal)) {
				sb.append("<option selected value='" + key + "'>" + val + "</option>");
			} else {
				sb.append("<option value='" + key + "'>" + val + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	public static String getInputErrorMessages(HttpServletRequest request) {

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
	 * Returns Error Message with HTML tag and CSS
	 *
	 * @param request
	 * @return
	 */
	public static String getErrorMessage(HttpServletRequest request) {
		String msg = ServletUtility.getErrorMessage(request);
		if (!DataValidator.isNull(msg)) {
			msg = "<p class='st-error-header'>" + msg + "</p>";
		}
		return msg;
	}

	/**
	 * Returns Success Message with HTML tag and CSS
	 *
	 * @param request
	 * @return
	 */

	public static String getSuccessMessage(HttpServletRequest request) {
		String msg = ServletUtility.getSuccessMessage(request);
		if (!DataValidator.isNull(msg)) {
			msg = "<p class='st-success-header'>" + msg + "</p>";
		}
		return msg;
	}

	/**
	 * Creates submit button if user has access permission.
	 *
	 * @param label
	 * @param access
	 * @param request
	 * @return
	 */
	public static String getSubmitButton(String label, boolean access, HttpServletRequest request) {

		String button = "";

		if (access) {
			button = "<input type='submit' name='operation'    value='" + label + "' >";
		}
		return button;
	}

}

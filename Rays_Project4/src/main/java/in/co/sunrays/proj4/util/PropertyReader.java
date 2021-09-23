package in.co.sunrays.proj4.util;

import java.util.ResourceBundle;


/**
 * @author Kamal
 *
 */
public class PropertyReader {

	private static ResourceBundle rb = ResourceBundle.getBundle("in.co.sunrays.proj4.bundle.System");

	//Giting the page size from System.property file 
	public static String getValue(String key) {

		String val = null;

		val = rb.getString(key);
		//System.out.println(val);
		// val = key;

		return val;
	}

	public static String getValue(String key, String param) {

		String msg = getValue(key);

		msg = msg.replace("{0}", param);
		System.out.println("getValue with 2 parameter" + msg);
		return msg;

	}

	public static String getValue(String key, String[] params) {

		String msg = getValue(key);
		System.out.println(msg);

		for (int i = 0; i < params.length; i++) {

			msg = msg.replace("{" + i + "}", params[i]);

		}
		return msg;
	}

	public static void main(String[] args) {

		String[] params = { "Roll No" };
		String name = PropertyReader.getValue("error.require", params);
		System.out.println(name);
	}

}
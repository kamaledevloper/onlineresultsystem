package in.co.sunrays.proj4.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.util.DataUtility;

/**
 * @author Kamal
 *
 */
public class JDBCDataSource {

	// Static self type variable
	public static JDBCDataSource datasource;

	private JDBCDataSource() {

		// default constructor
	}

	private ComboPooledDataSource cpds = null;

	public static JDBCDataSource getInstance() {

		if (datasource == null) {

			ResourceBundle rb = ResourceBundle.getBundle("in.co.sunrays.proj4.bundle.System");

			datasource = new JDBCDataSource();

			datasource.cpds = new ComboPooledDataSource();

			try {
				// datasource.cpds.setDriverClass(rb.getString("driver"));
				datasource.cpds.setDriverClass(DataUtility.getString(PropertyReader.getValue("driver")));

				datasource.cpds.setJdbcUrl(rb.getString("url"));
				datasource.cpds.setUser(rb.getString("username"));
				datasource.cpds.setPassword(rb.getString("password"));
				datasource.cpds.setInitialPoolSize(new Integer((String) rb.getString("initialPoolSize")));
				datasource.cpds.setAcquireIncrement(new Integer((String) rb.getString("acquireIncrement")));
				datasource.cpds.setMaxPoolSize(new Integer((String) rb.getString("maxPoolSize")));
				datasource.cpds.setMaxIdleTime(DataUtility.getInt(rb.getString("timeout")));
				datasource.cpds.setMinPoolSize(new Integer((String) rb.getString("minPoolSize")));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return datasource;

	}

	public static Connection getConnection() throws Exception {

		return getInstance().cpds.getConnection();
	}

	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
			}
		}
	}

	public static void trnRollback(Connection connection) throws ApplicationException {
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException ex) {
				throw new ApplicationException(ex.toString());
			}
		}
	}

}
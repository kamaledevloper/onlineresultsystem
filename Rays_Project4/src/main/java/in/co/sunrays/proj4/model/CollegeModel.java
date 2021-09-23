package in.co.sunrays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.omg.CORBA.portable.ApplicationException;

import in.co.sunrays.proj4.bean.CollegeBean;
import in.co.sunrays.proj4.exception.DatabaseException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.util.JDBCDataSource;

/**
 * @author Kamal
 *
 */
public class CollegeModel {

	Logger log = Logger.getLogger(CollegeModel.class);

	/**
	 * Find next PK of College
	 * 
	 * @throws DatabaseException
	 */

	public Integer nextPK() throws DatabaseException {
		// log.debug("Model nextPK Started");
		System.out.println("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_COLLEGE");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk + 1;
	}

	/**
	 * Add a College
	 * 
	 * @param bean
	 * 
	 * 
	 */
	public long add(CollegeBean bean)
			throws in.co.sunrays.proj4.exception.ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		CollegeBean duplicateCollegeName = findByName(bean.getName());

		if (duplicateCollegeName != null)
			throw new DuplicateRecordException("College Name already exists");
		{

		}
		try {
			conn = JDBCDataSource.getConnection();

			pk = nextPK();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_COLLEGE VALUES(?,?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getAddress());
			pstmt.setString(4, bean.getState());
			pstmt.setString(5, bean.getCity());
			pstmt.setString(6, bean.getPhoneNo());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit();
			conn.close();
			// conn.rollback();
			// JDBCDataSource.closeConnection(conn);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	/**
	 * Delete a College
	 * 
	 * @param bean
	 * 
	 */
	public void delete(CollegeBean bean) throws in.co.sunrays.proj4.exception.ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_COLLEGE WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			conn.close();
		} catch (Exception e) {

			e.printStackTrace();

			try {
				conn.rollback();
			} catch (Exception e1) {

				throw new in.co.sunrays.proj4.exception.ApplicationException(
						" Delete rollback exception " + e1.getMessage());

			}
			throw new in.co.sunrays.proj4.exception.ApplicationException("Exception : Exception in delete college");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Find User by College
	 * 
	 * @param name
	 *            : get parameter
	 * @return bean
	 * 
	 */
	public CollegeBean findByName(String name) throws in.co.sunrays.proj4.exception.ApplicationException {

		StringBuffer str = new StringBuffer("SELECT * FROM ST_COLLEGE WHERE NAME= ?");
		CollegeBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(str.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CollegeBean();

				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;

	}

	/**
	 * Find User by College
	 * 
	 * @param pk
	 *            : get parameter
	 * @return bean
	 * 
	 */
	public CollegeBean findByPK(long pk) throws in.co.sunrays.proj4.exception.ApplicationException {

		StringBuffer str = new StringBuffer("SELECT * FROM ST_COLLEGE WHERE ID = ? ");

		CollegeBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(str.toString());

			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));

			}

			rs.close();

		} catch (Exception e) {
			throw new in.co.sunrays.proj4.exception.ApplicationException(
					"Exception : Exception in getting College by pk");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		System.out.println("find by name end ");
		return bean;
	}

	/**
	 * Update a College
	 * 
	 * @param bean
	 * 
	 */
	public void upDate(CollegeBean bean)
			throws in.co.sunrays.proj4.exception.ApplicationException, DuplicateRecordException {
		System.out.println("college model update stated ");
		Connection conn = null;

		CollegeBean beanExist = findByName(bean.getName());

		if (beanExist != null && beanExist.getId() != bean.getId()) {

			throw new DuplicateRecordException("College is already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_COLLEGE SET NAME=?,ADDRESS=?,STATE=?,CITY=?,PHONE_NO=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");

			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getAddress());
			pstmt.setString(3, bean.getState());
			pstmt.setString(4, bean.getCity());
			pstmt.setString(5, bean.getPhoneNo());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
			pstmt.setLong(10, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new in.co.sunrays.proj4.exception.ApplicationException(
						"Exception : Delete rollback exception " + e2.getMessage());
			}
			throw new in.co.sunrays.proj4.exception.ApplicationException("Exception in updating College ");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		System.out.println(" college Model update end ");
	}

	/**
	 * Search College with pagination
	 * 
	 * @return list : List of Users
	 * @param bean
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * 
	 * 
	 */
	public List search(CollegeBean bean, int pageNo, int pageSize)// bean
																	// pageNo=1,
																	// pageSize
																	// = 10;
			throws in.co.sunrays.proj4.exception.ApplicationException {
		System.out.println("this is from Serch page No : " + pageNo);
		System.out.println("this is from serch pageSize: " + pageSize);
		// log.debug("model search started");
		System.out.println("model search started");
		StringBuffer str = new StringBuffer("SELECT * FROM ST_COLLEGE WHERE 1=1");

		if (bean != null) {
			System.out.println("name :" + bean.getName() + "city:" + bean.getAddress());

			System.out.println("bean is not null");
			if (bean.getId() > 0) {
				str.append(" And id =" + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {

				System.out.println("i am in this condition");
				str.append(" AND NAME like '" + bean.getName() + "%'");
			}
			if (bean.getAddress() != null && bean.getAddress().length() > 0) {
				str.append(" AND ADDRESS like '" + bean.getAddress() + "%'");
			}
			if (bean.getState() != null && bean.getState().length() > 0) {
				str.append(" AND STATE like '" + bean.getState() + "%'");
			}
			if (bean.getCity() != null && bean.getCity().length() > 0) {
				str.append(" AND CITY like '" + bean.getCity() + "%'");
			}
			if (bean.getPhoneNo() != null && bean.getPhoneNo().length() > 0) {
				str.append(" AND PHONE_NO like '" + bean.getPhoneNo() + "%'");
			}

		}
		if (pageSize > 1) {

			pageNo = (pageNo - 1) * pageSize;

			/*
			 * if (pageNo == -8) { pageNo = 0;
			 * 
			 * System.out.println("page number under if under if condition is :"
			 * + pageNo); str.append(" Limit " + pageNo + ", " + pageSize);
			 * 
			 * }
			 */
			// System.out.println("page number under if condition is :" +
			// pageNo);
			str.append(" Limit " + pageNo + ", " + pageSize);

		}
		ArrayList list = new ArrayList();

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(str.toString());

			System.out.println("this is final str" + str);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				list.add(bean);
			}

			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new in.co.sunrays.proj4.exception.ApplicationException("Exception : Exception in search college");
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}
		System.out.println("college model list end");
		return list;
	}

	/**
	 * Search College
	 * 
	 * @param bean
	 *            : Search Parameters
	 * 
	 */
	public List search(CollegeBean bean) throws in.co.sunrays.proj4.exception.ApplicationException {

		return search(bean, 0, 0);
	}

	/**
	 * Get List of College
	 * 
	 * @return list : List of College
	 * 
	 */
	public List list() throws in.co.sunrays.proj4.exception.ApplicationException {
		return list(0, 0);

	}

	/**
	 * Get List of College with pagination
	 * 
	 * @return list : List of College
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * 
	 */
	public List list(int pageNo, int pageSize) throws in.co.sunrays.proj4.exception.ApplicationException {

		ArrayList list = new ArrayList();

		StringBuffer str = new StringBuffer("select * from ST_College WHERE 1=1");

		if (pageSize > 0) {

			// pageNo = (pageNo - 1) * pageSize;
			str.append(" limit " + pageNo + "," + pageSize);

			System.out.println("fainal STR id : " + str);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(str.toString());

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CollegeBean bean = new CollegeBean();

				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				list.add(bean);

			}
			rs.close();

		} catch (Exception e) {

			e.printStackTrace();

			throw new in.co.sunrays.proj4.exception.ApplicationException(
					"Exception : Exception in getting list of College");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}

}

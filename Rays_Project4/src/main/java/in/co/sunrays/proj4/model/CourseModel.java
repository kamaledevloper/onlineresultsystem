package in.co.sunrays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.sunrays.proj4.bean.CourseBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DatabaseException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.util.JDBCDataSource;

/**
 * 
 * @author Kamal
 *
 */

/**
 * JDBC implements course Model
 * 
 *
 */

public class CourseModel {

	public static int nextPk() {

		int pk = 0;
		Connection con = null;
		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement stmt = con.prepareStatement("SELECT MAX(ID) FROM ST_COURSE");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);

			}
			rs.close();
		}

		catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			JDBCDataSource.closeConnection(con);
		}
		return pk + 1;
	}

	/**
	 * Find User by Course
	 * 
	 * @param name
	 *            : get parameter
	 * @return bean
	 * 
	 */
	public CourseBean findByName(String name) throws ApplicationException {

		CourseBean bean = null;

		StringBuffer str = new StringBuffer("SELECT * FROM ST_COURSE WHERE NAME =?");
		Connection conn = null;
		try {
			System.out.println("I am in try block");
			conn = JDBCDataSource.getConnection();

			PreparedStatement stmt = conn.prepareStatement(str.toString());
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
			}
			rs.close();
		} catch (DatabaseException e) {
			System.out.println("I am in catch block");
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (

		Exception e) {
			e.printStackTrace();
			throw new ApplicationException("exception in fine by name ");
			// TODO Auto-generated catch block

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	/**
	 * Add a Course
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 * 
	 * 
	 */
	public long add(CourseBean bean) throws DuplicateRecordException, ApplicationException {
		int pk = 0;

		Connection conn = null;
		try {
			CourseBean rbean = null;

			rbean = findByName(bean.getName());

			System.out.println("Bean name is :" + bean.getName());
			if (rbean != null) {

				System.out.println("under R bean not null" + rbean.getName());

				throw new DuplicateRecordException("Duplicate entry not aloud");
			}

		} catch (ApplicationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// StringBuffer str = new StringBuffer("INSERT INTO ST_COURCE
		// VALUES(?,?,?,?,?,?,?)");

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();

			System.out.println("Pk is : " + pk);
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO ST_COURSE VALUES(?,?,?,?,?,?,?)");
			conn.setAutoCommit(false);
			stmt.setInt(1, pk);
			stmt.setString(2, bean.getName());
			stmt.setString(3, bean.getDescription());
			stmt.setString(4, bean.getCreatedBy());
			stmt.setString(5, bean.getModifiedBy());
			stmt.setTimestamp(6, bean.getCreatedDatetime());
			stmt.setTimestamp(7, bean.getModifiedDatetime());
			stmt.executeUpdate();
			conn.commit();
			conn.close();

		} catch (Exception e) {
			// TODO: handle exception

			try {
				conn.rollback();
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
				throw new ApplicationException("Exception in roll back");
			}
		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		System.out.println("Pk is ::" + pk);

		return pk;
	}

	/**
	 * Delete a Course
	 * 
	 * @param bean
	 * @throws DatabaseException
	 * 
	 */
	public void delete(CourseBean bean) throws DatabaseException {
		// log.debug("CourseModel method delete started");
		Connection conn = null;
		// int pk = nextPK();
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_COURSE WHERE ID=?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			throw new DatabaseException("Exception: EXception in delete method");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// Flog.debug("CourseModel Delete method endeed");
	}

	public void update(CourseBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;

		CourseBean beanExist = findByName(bean.getName());
		if (beanExist != null && beanExist.getId() != bean.getId()) {
			throw new DuplicateRecordException("Course Already Exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_COURSE SET NAME=?,DESCRIPTION=?,CREATEDBY=?,MODIFIEDBY=?,CREATEDDATETIME=?,MODIFIEDDATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getDescription());
			pstmt.setString(3, bean.getCreatedBy());
			pstmt.setString(4, bean.getModifiedBy());
			pstmt.setTimestamp(5, bean.getCreatedDatetime());
			pstmt.setTimestamp(6, bean.getModifiedDatetime());
			pstmt.setLong(7, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			// log.debug("Database Exception ...", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : Exception in Rollback.." + ex.getMessage());
			}
			throw new ApplicationException("Exception in Updating the Course Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Find User by Course
	 * 
	 * @param pk
	 *            : get parameter
	 * @return bean
	 * 
	 */
	public CourseBean findByPk(long pk) {
		StringBuffer sql = new StringBuffer("SELECT * FROM st_course WHERE ID = ?");

		CourseBean bean = null;
		Connection conn = null;

		try {
			System.out.println("I am in try block" + sql);
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());

			// prepareStatement("SELECT * FROM st_course WHERE ID = ?");

			stmt.setLong(1, pk);

			ResultSet rs = stmt.executeQuery();

			System.out.println(rs);
			while (rs.next()) {
				bean = new CourseBean();

				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List list(int pageNo, int pageSize) {
		// log.debug("CourseModel List method started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE ");
		// if page size is greater than zero than apply pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}
		System.out.println("fainal sql is :" + sql);
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CourseBean bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				// bean.setCreatedDatetime(rs.getTimestamp(6));
				// bean.setModifiedDatetime(rs.getTimestamp(7));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("CourseModel list method ended");
		return list;
	}

	/**
	 * Search Course with pagination
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
	public List search(CourseBean bean, int pageNo, int pageSize) throws ApplicationException {

		// log.debug("CourseModel Search Method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				System.out.println("aassdf" + bean.getId());
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				System.out.println("ghkjl" + bean.getId());
				sql.append(" AND Name like '" + bean.getName() + "%'");
			}
			/*
			 * if(bean.getDescription()!=null && bean.getName().length()>0){
			 * sql.append(" AND Description like '" + bean.getDescription() +
			 * "%'"); }
			 */

		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			System.out.println("........try ke andr in course model..........");

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			System.out.println(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				// bean.setCreatedDatetime(rs.getTimestamp(6));
				// bean.setModifiedDatetime(rs.getTimestamp(7));
				list.add(bean);

			}
			System.out.println(".................close ke uper in course model.......................");
			rs.close();
		} catch (Exception e) {
			System.out.println(".................Exception ke andr in course model.......................");

			e.printStackTrace();
			// log.error("Database Exception ,,,,", e);
			throw new ApplicationException("Exception in the Search Method" + e.getMessage());

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("CourseModel Search Method End");
		System.out.println("----------------------------------->>>>" + list.size());
		return list;
	}

	/**
	 * Get List of Course
	 * 
	 * @return list : List of Course
	 * 
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Search Course
	 * 
	 * @param bean
	 *            : Search Parameters
	 * 
	 */

	public List search(CourseBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

}

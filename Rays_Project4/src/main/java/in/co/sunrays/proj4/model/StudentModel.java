package in.co.sunrays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.sunrays.proj4.bean.CollegeBean;
import in.co.sunrays.proj4.bean.StudentBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DatabaseException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.CollegeModel;
import in.co.sunrays.proj4.util.JDBCDataSource;
import org.apache.log4j.Logger;
import org.apache.log4j.Logger;

/**
 * @author Kamal
 *
 */

/** To manipulate the data of St_Student Table */
public class StudentModel {

	private static Logger log = Logger.getLogger(StudentModel.class);

	/**
	 * Find next PK of Student
	 * 
	 * @throws DatabaseException
	 */

	public Integer nextPK() throws DatabaseException {

		log.debug("next PK starts");
		int pk = 0;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_STUDENT");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);

			}
			rs.close();
		} catch (Exception e) {

			log.error("Data base me dikkat");
			// TODO Auto-generated catch block
			e.printStackTrace();

			throw new DatabaseException("Exeption in getting PK ");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.error("Next Pk end ");
		return pk + 1;
	}

	/**
	 * Add a Student
	 * 
	 * @param bean
	 * 
	 * @return Integer
	 * 
	 * 
	 */

	public int add(StudentBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("add started");
		Connection conn = null;
		CollegeModel cModel = new CollegeModel();
		CollegeBean collegeBean = cModel.findByPK(bean.getCollegeId());

		try {
			bean.setCollegeName(collegeBean.getName());
		} catch (Exception e) {
			// TODO: handle exception
		}

		StudentBean duplicateName = findByEmailId(bean.getEmail());
		int pk = 0;
		if (duplicateName != null) {

			throw new DuplicateRecordException("email he already ");
		}
		try {

			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			System.out.println(pk + "in ModelJDBC");
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_STUDENT VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setLong(2, bean.getCollegeId());
			pstmt.setString(3, bean.getCollegeName());
			pstmt.setString(4, bean.getFirstName());
			pstmt.setString(5, bean.getLastName());
			pstmt.setDate(6, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(7, bean.getMobileNo());
			pstmt.setString(8, bean.getEmail());
			pstmt.setString(9, bean.getCreatedBy());
			pstmt.setString(10, bean.getModifiedBy());
			pstmt.setTimestamp(11, bean.getCreatedDatetime());
			pstmt.setTimestamp(12, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			log.error("Database exseption", e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {

				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}

			// throw new ApplicationException("Exception : Exception in add
			// Student");

		} finally {

			JDBCDataSource.closeConnection(conn);

		}
		log.debug("model add end");

		return pk;
	}

	/**
	 * Delete a Student
	 * 
	 * @param bean
	 * 
	 */

	public void delete(StudentBean bean) throws ApplicationException {
		log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_STUDENT WHERE ID=?");
			pstmt.setLong(1, bean.getId());

			System.out.println("Bean me se ID : " + bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Started");
	}

	/**
	 * Find User by Student
	 * 
	 * @param Email
	 *            : get parameter
	 * @return bean
	 * 
	 */
	public StudentBean findByEmailId(String Email) throws ApplicationException {
		log.debug("Model findBy Email Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_STUDENT WHERE EMAIL=?");
		StudentBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, Email);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new StudentBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setEmail(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));

			}
			rs.close();
		} catch (Exception e) {

			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by Email");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findBy Email End");
		return bean;
	}

	/**
	 * Find Student by PK
	 * 
	 * @param pk
	 *            : get parameter
	 * @return bean
	 * 
	 */
	public StudentBean findByPk(long pk) {

		// StringBuffer str = new StringBuffer();

		StudentBean bean = null;
		// StudentBean bean = new StudentBean();

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM st_student WHERE ID = ?");
			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();
			System.out.println(rs);
			while (rs.next()) {
				bean = new StudentBean();

				bean.setId(rs.getLong(1));
				System.out.println(bean.getId());
				bean.setCollegeId(rs.getLong(2));
				System.out.println(bean.getCollegeId());
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setEmail(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));

			}

			rs.close();
			JDBCDataSource.closeConnection(conn);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bean;
	}

	/**
	 * Update a Student
	 * 
	 * @param bean
	 * 
	 */
	public void update(StudentBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;

		StudentBean beanExist = findByEmailId(bean.getEmail());

		// Check if updated Roll no already exist
		if (beanExist != null && beanExist.getId() != bean.getId()) {
			throw new DuplicateRecordException("Email Id is already exist");
		}

		// get College Name
		// CollegeModel cModel = new CollegeModel();
		// CollegeBean collegeBean = cModel.findByPK(bean.getCollegeId());
		// bean.setCollegeName(collegeBean.getName());

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_STUDENT SET COLLEGE_ID=?,COLLEGE_NAME=?,FIRST_NAME=?,LAST_NAME=?,DATE_OF_BIRTH=?,MOBILE_NO=?,EMAIL=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setLong(1, bean.getCollegeId());
			pstmt.setString(2, bean.getCollegeName());
			pstmt.setString(3, bean.getFirstName());
			pstmt.setString(4, bean.getLastName());
			pstmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(6, bean.getMobileNo());
			pstmt.setString(7, bean.getEmail());
			pstmt.setString(8, bean.getCreatedBy());
			pstmt.setString(9, bean.getModifiedBy());
			pstmt.setTimestamp(10, bean.getCreatedDatetime());
			pstmt.setTimestamp(11, bean.getModifiedDatetime());
			pstmt.setLong(12, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Student ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	/**
	 * Search Student
	 *
	 * @param bean
	 *            : Search Parameters
	 * @throws DatabaseException
	 */

	public List search(StudentBean bean) {

		return search(bean, 0, 0);
	}

	/**
	 * Search Student with pagination
	 * 
	 * @return list : List of Students
	 * @param bean
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * 
	 * 
	 */

	public List search(StudentBean bean, int pageNo, int pageSize) {

		StringBuffer str = new StringBuffer("SELECT * FROM ST_STUDENT WHERE 1=1");

		if (bean != null) {

			System.out.println("in bean not equals to null");
			if (bean.getId() > 0) {

				System.out.println("under If condition 1");
				str.append(" AND id = " + bean.getId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				str.append(" AND FIRST_NAME like '" + bean.getFirstName() + "%'");

				System.out.println("under If condition 2");
				System.out.println(str);
			}
			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				str.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
				System.out.println(str);
				System.out.println("under If condition 3");
			}
			if (bean.getDob() != null && bean.getDob().getDate() > 0) {
				str.append(" AND DOB = " + bean.getDob());
				System.out.println("under If condition 4");

			}
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				str.append(" AND MOBILE_NO like '" + bean.getMobileNo() + "%'");
				System.out.println(str);
				System.out.println("under If condition 5");
			}
			if (bean.getEmail() != null && bean.getEmail().length() > 0) {
				str.append(" AND EMAIL like '" + bean.getEmail() + "%'");
				System.out.println(str);
				System.out.println("under If condition 6");
			}
			if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
				str.append(" AND COLLEGE_NAME = " + bean.getCollegeName());
				System.out.println(str);
				System.out.println("under If condition 7");
			}

			if (bean.getCollegeId() > 0) {
				str.append(" AND COLLEGE_ID = " + bean.getCollegeId());
				System.out.println(str);
				System.out.println("under If condition 7");
			}

		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			System.out.println("this is under is pageSize condition:" + pageNo);
			str.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList list = new ArrayList();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(str.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new StudentBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setEmail(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				list.add(bean);
			}
			rs.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		System.out.println("Student serch started and going to return list ");
		return list;
	}

	/**
	 * Get List of Student
	 * 
	 * @return list : List of Student
	 * 
	 */
	public List list() {

		return list(0, 0);

	}

	/**
	 * Get List of Student with pagination
	 * 
	 * @return list : List of Student
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * 
	 */
	public List list(int pageNo, int pageSize) {

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_STUDENT");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);

			log.info(" Student Model Started final Query :" + sql);

		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				StudentBean bean = new StudentBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setEmail(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				list.add(bean);
			}
			rs.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return list;

	}

}

package in.co.sunrays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.sunrays.proj4.bean.CollegeBean;
import in.co.sunrays.proj4.bean.CourseBean;
import in.co.sunrays.proj4.bean.FacultyBean;
import in.co.sunrays.proj4.bean.SubjectBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DatabaseException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.CollegeModel;
import in.co.sunrays.proj4.util.JDBCDataSource;
import org.apache.log4j.Logger;
import org.apache.log4j.Logger;

/**
 * 
 *  @author Kamal
 */

/** To manipulate the data of st_faculty Table */
public class FacultyModel {

	private static Logger log = Logger.getLogger(FacultyModel.class);

	/**
	 * Find next PK of Faculty
	 * 
	 * 
	 */

	public Integer nextPK() throws DatabaseException {

		log.debug("next PK starts");
		int pk = 0;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM st_faculty");
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
	 * Add a Faculty
	 * 
	 * @param bean
	 * @throws DuplicateRecordException
	 *
	 * 
	 */
	public int add(FacultyBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("add started");
		Connection conn = null;

		CollegeBean cbean = new CollegeBean();
		CollegeModel cmodel = new CollegeModel();

		cbean = cmodel.findByPK(bean.getCollegeid());

		System.out.println("College Name::::::::" + cbean.getName());

		CourseBean cobean = new CourseBean();
		CourseModel comodel = new CourseModel();
		cobean = comodel.findByPk(bean.getCourseId());

		System.out.println("Course Name::::::::" + cobean.getName());

		SubjectBean sbean = new SubjectBean();
		SubjectModel smodel = new SubjectModel();

		sbean = smodel.findByPk(bean.getSubjectId());

		System.out.println("Subject Name::::::::" + sbean.getSubjectName());

		FacultyBean duplicatebean = new FacultyBean();

		duplicatebean = findByEmailId(bean.getLoginId());
		int pk = 0;
		if (duplicatebean != null) {

			throw new DuplicateRecordException("Login ID already exsist");

		} else {

			try {
				pk = nextPK();

				conn = JDBCDataSource.getConnection();

				conn.setAutoCommit(false);
				PreparedStatement pstmt = conn
						.prepareStatement("INSERT INTO st_faculty VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				pstmt.setLong(1, pk);
				pstmt.setString(2, bean.getFirstName());
				pstmt.setString(3, bean.getLastName());
				pstmt.setString(4, bean.getGender());
				pstmt.setString(5, bean.getLoginId());
				pstmt.setDate(6, new java.sql.Date(bean.getDateofJoining().getTime()));
				pstmt.setString(7, bean.getQualification());
				pstmt.setString(8, bean.getMobileno());
				pstmt.setLong(9, bean.getCollegeid());
				pstmt.setString(10, cbean.getName());
				pstmt.setLong(11, bean.getCourseId());
				pstmt.setString(12, cobean.getName());
				pstmt.setLong(13, bean.getSubjectId());
				pstmt.setString(14, sbean.getSubjectName());
				pstmt.setString(15, bean.getCreatedBy());
				pstmt.setString(16, bean.getModifiedBy());
				pstmt.setTimestamp(17, bean.getCreatedDatetime());
				pstmt.setTimestamp(18, bean.getModifiedDatetime());
				pstmt.executeUpdate();
				conn.commit();
				pstmt.close();

			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		try {
			conn.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);

		}

		log.debug("model add end");

		return pk;
	}

	/**
	 * Delete a Faculty
	 * 
	 * @param bean
	 * 
	 */
	public void delete(FacultyBean bean) throws ApplicationException {
		log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM st_faculty WHERE ID=?");
			pstmt.setLong(1, bean.getId());
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
	 * Find User by Faculty name
	 * 
	 * @param EmailId
	 *            : get parameter
	 * @return bean
	 * 
	 */
	public FacultyBean findByEmailId(String loginId) throws ApplicationException {
		log.debug("Model findBy Email Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM st_faculty WHERE loginId=?");
		FacultyBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, loginId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setLoginId(rs.getString(5));

				bean.setDateofJoining(rs.getDate(6));
				bean.setQualification(rs.getString(7));
				bean.setMobileno(rs.getString(8));
				bean.setCollegeid(rs.getLong(9));
				bean.setCollegeName(rs.getString(10));
				bean.setCourseId(rs.getLong(11));
				bean.setCourseName(rs.getString(12));
				bean.setSubjectId(rs.getLong(13));
				bean.setSubjectName(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));
				System.out.println(" faculty  find by name 3");

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
	 * Find User by Faculty PK
	 * 
	 * @param pk
	 *            : get parameter
	 * @return bean
	 * 
	 */
	public FacultyBean findByPk(long pk) {

		// StringBuffer str = new StringBuffer();

		FacultyBean bean = null;
		// FacultyBean bean = new FacultyBean();

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM st_faculty WHERE ID = ?");
			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();
			System.out.println(rs);
			while (rs.next()) {
				bean = new FacultyBean();

				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setLoginId(rs.getString(5));
				bean.setDateofJoining(rs.getDate(6));
				bean.setQualification(rs.getString(7));
				bean.setMobileno(rs.getString(8));
				bean.setCollegeid(rs.getLong(9));
				bean.setCollegeName(rs.getString(10));
				bean.setCourseId(rs.getLong(11));
				bean.setCourseName(rs.getString(12));
				bean.setSubjectId(rs.getLong(13));
				bean.setSubjectName(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));

			}

			rs.close();
			JDBCDataSource.closeConnection(conn);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Find by PK sucess");
		return bean;
	}

	/**
	 * Update a Faculty
	 * 
	 * @param bean
	 * @throws DuplicateRecordException
	 * 
	 */
	public void update(FacultyBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;

		CollegeBean cbean = new CollegeBean();
		CollegeModel cmodel = new CollegeModel();

		cbean = cmodel.findByPK(bean.getCollegeid());

		System.out.println("College Name::::::::" + cbean.getName());

		CourseBean cobean = new CourseBean();
		CourseModel comodel = new CourseModel();
		cobean = comodel.findByPk(bean.getCourseId());
		System.out.println("Course Name::::::::" + cobean.getName());
		SubjectBean sbean = new SubjectBean();
		SubjectModel smodel = new SubjectModel();
		sbean = smodel.findByPk(bean.getSubjectId());
		System.out.println("Subject Name::::::::" + sbean.getSubjectName());
		// FacultyBean beanExist = findByEmailId(bean.getLoginId());

		// Check if updated Roll no already exist

		/*
		 * if (beanExist != null && beanExist.getId() != bean.getId()) { throw
		 * new DuplicateRecordException("Email Id is already exist"); }
		 */

		// get College Name
		// CollegeModel cModel = new CollegeModel();
		// CollegeBean collegeBean = cModel.findByPK(bean.getCollegeId());
		// bean.setCollegeName(collegeBean.getName());

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_FACULTY SET FIRSTNAME=?, LASTNAME=?, GENDER=?, LoginId=?, DATEOFJoining=?, QUALIFICATION=?, MOBILENO=? , COLLEGEID=? , COLLEGENAME=?,COURSEID=?,COURSENAME=?, SUBJECTID=?, SUBJECTNAME=?, CREATEDBY=? , MODIFIEDBY=? , CREATEDDATETIME=? , MODIFIEDDATETIME=? WHERE ID=? ");

			pstmt.setString(1, bean.getFirstName());
			pstmt.setString(2, bean.getLastName());
			pstmt.setString(3, bean.getGender());
			pstmt.setString(4, bean.getLoginId());

			// pstmt.setTimestamp(6, bean.getDateofJoining());

			pstmt.setDate(5, new java.sql.Date(bean.getDateofJoining().getTime()));
			pstmt.setString(6, bean.getQualification());
			pstmt.setString(7, bean.getMobileno());
			pstmt.setLong(8, bean.getCollegeid());
			// pstmt.setString(9, bean.getCollegeName());
			pstmt.setString(9, cbean.getName());
			pstmt.setLong(10, bean.getCourseId());
			pstmt.setString(11, cobean.getName());
			pstmt.setLong(12, bean.getSubjectId());
			// pstmt.setString(13, subjectname);
			pstmt.setString(13, sbean.getSubjectName());
			pstmt.setString(14, bean.getCreatedBy());
			pstmt.setString(15, bean.getModifiedBy());
			pstmt.setTimestamp(16, bean.getCreatedDatetime());

			pstmt.setTimestamp(17, bean.getModifiedDatetime());
			pstmt.setLong(18, bean.getId());

			System.out.println("ID comes from User : " + bean.getId());
			int res = pstmt.executeUpdate();

			System.out.println("result is:" + res);

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
	 * @throws ApplicationException
	 * 
	 */

	public List search(FacultyBean bean) throws ApplicationException {

		return search(bean, 0, 0);
	}

	/**
	 * Search Faculty with pagination
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
	public List search(FacultyBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Faculty Model search  method Started");
		System.out.println("faculty model");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" AND firstName like '" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				sql.append(" AND Last_Name like '" + bean.getLastName() + "%'");
			}
			if (bean.getLoginId() != null && bean.getLoginId().length() > 0) {
				sql.append(" AND loginId like '" + bean.getLoginId() + "%'");
			}
			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" AND Gender like '" + bean.getGender() + "%'");
			}

			/*
			 * if (bean.getGender()!=null && bean.getGender().length()>0) {
			 * sql.append(" AND Gender like '" + bean.getGender() + "%'"); }
			 * 
			 * // date of birth
			 * 
			 * if (bean.getFirstName()!=null && bean.getFirstName().length()>0)
			 * { sql.append(" AND firstname = " + bean.getFirstName() + " % ");
			 * }
			 * 
			 * if (bean.getQualification()!=null &&
			 * bean.getQualification().length()>0) { sql.append(
			 * " AND Qualification like '" + bean.getQualification() + "%'"); }
			 * 
			 * if (bean.getMobileno()!=null && bean.getMobileno().length()>0) {
			 * sql.append(" AND MobileNo like '" + bean.getMobileno() + "%'"); }
			 * if (bean.getCollegeid() > 0) { sql.append(" AND collegeid = '" +
			 * bean.getCollegeid()); } if (bean.getCollegeName()!=null &&
			 * bean.getCollegeName().length()>0) { sql.append(
			 * " AND collegename like '" + bean.getCollegeName() + "%'"); } if
			 * (bean.getCourseId() > 0) { sql.append(" AND courseid = '" +
			 * bean.getCourseId()); } if (bean.getCourseName()!=null &&
			 * bean.getCourseName().length()>0) { sql.append(
			 * " AND coursename like '" + bean.getCourseName() + "%'"); } if
			 * (bean.getSubjectId() > 0) { sql.append(" AND Subjectid = '" +
			 * bean.getSubjectId()); } if (bean.getSubjectName()!=null &&
			 * bean.getSubjectName().length()>0) { sql.append(
			 * " AND subjectname like '" + bean.getSubjectName() + "%'"); }
			 */ }

		// if page no is greater then zero then apply pagination
		System.out.println("model page ........" + pageNo + " " + pageSize);
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}
		System.out.println("final sql  " + sql);
		Connection conn = null;
		ArrayList list = new ArrayList();
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setLoginId(rs.getString(5));
				bean.setDateofJoining(rs.getDate(6));
				bean.setQualification(rs.getString(7));
				bean.setMobileno(rs.getString(8));
				bean.setCollegeid(rs.getLong(9));
				bean.setCollegeName(rs.getString(10));
				bean.setCourseId(rs.getLong(11));
				bean.setCourseName(rs.getString(12));
				bean.setSubjectId(rs.getLong(13));
				bean.setSubjectName(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));
				System.out.println("out whiile");
				list.add(bean);
				System.out.println("list size ----------->" + list.size());
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("database Exception .. ", e);

			throw new ApplicationException("Exception : Exception in Search method of Faculty Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model search  method End");
		// System.out.println("retuen >>>>>>>>>>>>>>>"+list.size());
		return list;

	}

	/**
	 * Get List of Faculty
	 * 
	 * @return list : List of Faculty
	 * 
	 */
	public List list() {

		return list(0, 0);

	}

	/**
	 * Get List of Faculty with pagination
	 * 
	 * @return list : List of Faculty
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 *
	 */
	public List list(int pageNo, int pageSize) {

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_faculty");
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
			System.out.println("in try block ");
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				FacultyBean bean = new FacultyBean();

				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setLoginId(rs.getString(5));
				bean.setDateofJoining(rs.getDate(6));
				bean.setQualification(rs.getString(7));
				bean.setMobileno(rs.getString(8));
				bean.setCollegeid(rs.getLong(9));
				bean.setCollegeName(rs.getString(10));
				bean.setCourseId(rs.getLong(11));
				bean.setCourseName(rs.getString(12));
				bean.setSubjectId(rs.getLong(13));
				bean.setSubjectName(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));
				list.add(bean);
			}
			rs.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block

			System.out.println("in catch block ");
			e.printStackTrace();
		} finally {

			JDBCDataSource.closeConnection(conn);
		}
		System.out.println("List Sucess");
		return list;

	}

}

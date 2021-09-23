package in.co.sunrays.proj4.bean;

import java.util.Date;

/**
 * Contains students attributes.
 * 
 * @author Kamal
 *
 */
public class StudentBean extends BaseBean {

	private String firstName;
	private String lastName;
	private Date Dob;
	private String mobileNo;
	private String email;
	private long collegeId;
	private String collegeName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return Dob;
	}

	public void setDob(Date dob) {
		Dob = dob;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(long collegeid) {
		this.collegeId = collegeid;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public String getKey() {
		// TODO Auto-generated method stub

		// System.out.println("this is from Student Bean getKey going to return
		// ID :" + id);
		return id + "";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		/*
		 * System.out.println("this is from Student" + "" +
		 * " Bean getValue going to return F name and L name :" + firstName +
		 * lastName);
		 */
		return firstName + "  " + lastName;
	}

}

package in.co.sunrays.proj4.bean;


/**
 * @author Kamal
 *
 */
public class CollegeBean extends BaseBean {

	private String name;
	private String address;
	private String state;
	private String city;
	private String phoneNo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	

	public String getKey() {
	//	System.out.println("this is from College Bean getKey going to return ID :" + id);
		// TODO Auto-generated method stub
		return id + "";
	}

	public String getValue() {
		//System.out.println("this is from College" + "" + " Bean getValue going to return value :" + name);
		// TODO Auto-generated method stub
		return name;
	}

}

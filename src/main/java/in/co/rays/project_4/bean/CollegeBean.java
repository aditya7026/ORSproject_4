package in.co.rays.project_4.bean;

/**
 * @author Aditya
 *@version 1.0
 *@Copyright (c) Sunil OS
 */
public class CollegeBean extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
 * Name of College
 */
private String name;
/**
 * Address of College
 */
private String address;
/**
 * State of College
 */
private String state;
/**
 * city of College
 */
private String city;
/**
 * Phone No. of College
 */
private String phNo;
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
public String getPhNo() {
	return phNo;
}
public void setPhNo(String phnoNo) {
	this.phNo = phnoNo;
}
//Added unimplemented methods
public int compareTo(BaseBean o) {
	// TODO Auto-generated method stub
	return 0;
}
public String getKey() {
	return id+"";
}
public String getValue() {
	return name;
}
}

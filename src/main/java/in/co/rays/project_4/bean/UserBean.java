package in.co.rays.project_4.bean;

import java.util.Date;
import java.sql.Timestamp;

/**
 * @author Aditya
 *@version 1.0
 *@Copyright (c) Sunil OS
 */
public class UserBean extends BaseBean {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
 * First Name of User
 */
private String firstName;
/**
 * Last Name of User 
 */
private String lastName;
/**
 * Login Id of User 
 */
private String login;
/**
 * Password of User
 */
private String password;
/**
 * Confirm Password of user
 */
private String confirmPassword;
/**
 * Date of Birth of User
 */
private Date dob;
/**
 * Mobile No of User
 */
private String mobileNo;
/**
 * role Id of User
 */
private long roleId;
/**
 * No. of UnsuccessFull Login Attempts
 */
private int unSuccessfullLogin;
/**
 * Gender of User
 */
private String gender;
/**
 * Last Login of User
 */
private Timestamp lastLogin;
/**
 * lock
 */
private String lock;
/**
 * IP used during registration
 */
private String registeredIP;
/**
 * IP used during last Login
 */
private String lastLoginIP;
//ACTIVE:String Remaining
//INACTIVE : String Remaining
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
public String getLogin() {
	return login;
}
public void setLogin(String login) {
	this.login = login;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getConfirmPassword() {
	return confirmPassword;
}
public void setConfirmPassword(String confirmPassword) {
	this.confirmPassword = confirmPassword;
}
public Date getDob() {
	return dob;
}
public void setDob(Date dob) {
	this.dob = dob;
}
public String getMobileNo() {
	return mobileNo;
}
public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
}
public long getRoleId() {
	return roleId;
}
public void setRoleId(long roleId) {
	this.roleId = roleId;
}
public int getUnSuccessfullLogin() {
	return unSuccessfullLogin;
}
public void setUnSuccessfullLogin(int unSuccessfullLogin) {
	this.unSuccessfullLogin = unSuccessfullLogin;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public Timestamp getLastLogin() {
	return lastLogin;
}
public void setLastLogin(Timestamp lastLogin) {
	this.lastLogin = lastLogin;
}
public String getLock() {
	return lock;
}
public void setLock(String lock) {
	this.lock = lock;
}
public String getRegisteredIP() {
	return registeredIP;
}
public void setRegisteredIP(String registeredIP) {
	this.registeredIP = registeredIP;
}
public String getLastLoginIP() {
	return lastLoginIP;
}
public void setLastLoginIP(String lastLoginIP) {
	this.lastLoginIP = lastLoginIP;
}
//Added Unimplemented Methods
public int compareTo(BaseBean o) {
	// TODO Auto-generated method stub
	return 0;
}
public String getKey() {
	return id+"";
}
public String getValue() {
	return firstName+" "+lastName;
}
}

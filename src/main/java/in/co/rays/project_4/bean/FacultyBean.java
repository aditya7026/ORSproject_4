package in.co.rays.project_4.bean;

import java.util.Date;

/**
 * @author Aditya
 *@version 1.0
 *@Copyright (c) Sunil OS
 */
public class FacultyBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * First Name of Faculty
	 */
	private String firstName;//ok
	/**
	 * Last Name of Faculty
	 */
	private String lastName;//ok
	/**
	 * Gender of Faculty
	 */
	private String gender;//ok
	
	/**
	 * Qualification of Faculty
	 */
	private String qualification;//ok
	/**
	 * Email ID of Faculty
	 */
	private String emailId;//ok
	/**
	 * Mobile No. of Faculty
	 */
	private String mobileNo;//ok
	/**
	 * College Id of Faculty
	 */
	private long collegeId;//ok
	/**
	 * College Name of Faculty
	 */
	private String collegeName;//ok
	/**
	 * Course Id of Faculty
	 */
	private long courseId;//ok
	/**
	 * Course Name of Faculty
	 */
	private String courseName;//ok
	/**
	 * Subject Id of Faculty
	 */
	private long subjectId;//ok
	/**
	 * Subject Id of Faculty
	 */
	private String subjectName;//ok
	/**
	 * Date of Birth of Faculty
	 */
	private Date dob;//ok
	
	
	
	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

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

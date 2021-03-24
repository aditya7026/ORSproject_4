package in.co.rays.project_4.bean;

import java.util.Date;

/**
 * 
 * TimeaTableBean class
 * 
 * @author Aditya
 *@version 1.0
 *@Copyright (c) Sunil OS
 */
public class TimeTableBean extends BaseBean {
	

	private static final long serialVersionUID = 1L;
	
	/**
	 * Course Id for Time Table
	 */
	private long courseId;
	/**
	 * Course Name for Time Table
	 */
	private String courseName;
	/**
	 * Subject Id for Time Table
	 */
	private long subjectId;
	/**
	 * Subject Name for Time Table
	 */
	private String subjectName;
	/**
	 * Semester for Time Table
	 */
	private String semester;
	/**
	 * Exam Time for Time Table
	 */
	private Date examDate;
	/**
	 * Exam Time for Time Table
	 */
	private String time;
	
	
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	

	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return examDate+"";
	}

}

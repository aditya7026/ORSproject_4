package in.co.rays.project_4.bean;

/**
 * @author Aditya
 *@version 1.0
 *@Copyright (c) Sunil OS
 */
public class MarksheetBean extends BaseBean {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
 * RollNo of Marksheet
 */
private String rollNo;
/**
 * Student ID
 */
private long studentId;
/**
 * Name of Student
 */
private String name;
/**
 * Physics Marks
 */
private int physics;
/**
 * Chemistry Marks
 */
private int chemistry;
/**
 * Maths Marks
 */
private int maths;
public String getRollNo() {
	return rollNo;
}
public void setRollNo(String rollNo) {
	this.rollNo = rollNo;
}
public long getStudentId() {
	return studentId;
}
public void setStudentId(long studentId) {
	this.studentId = studentId;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getPhysics() {
	return physics;
}
public void setPhysics(int physics) {
	this.physics = physics;
}
public int getChemistry() {
	return chemistry;
}
public void setChemistry(int chemistry) {
	this.chemistry = chemistry;
}
public int getMaths() {
	return maths;
}
public void setMaths(int maths) {
	this.maths = maths;
}
//Added uninplemented Methods
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

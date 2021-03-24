package in.co.rays.project_4.bean;

/**
 * @author Aditya
 *@version 1.0
 *@Copyright (c) Sunil OS
 */
public class RoleBean extends BaseBean {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public static int ADMIN=1;
public static int STUDENT=2;
public static int COLLEGE,SCHOOL=3;
public static int KIOSK=4;
/**
 * Name of Role
 */
private String name;
/**
 * Description of Role
 */
private String description;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
//Added uninplemented method
public int compareTo(BaseBean o) {
	// TODO Auto-generated method stub
	return 0;
}
public String getKey() {
	return id+"";
}
public String getValue() {
	// TODO Auto-generated method stub
	return name;
}
}

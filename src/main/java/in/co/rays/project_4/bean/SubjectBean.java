package in.co.rays.project_4.bean;

/**
 * @author Aditya
 *@version 1.0
 *@Copyright (c) Sunil OS
 */
public class SubjectBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Name of Subject
	 */
	private String name;
	/**
	 * Description of Subject
	 */
	private String description;
	/**
	 * Course Id of Subject
	 */
	private long courseId;
	/**
	 * Course Name of Subject
	 */
	private String courseName;
	
	
	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		return name+"";
	}

}

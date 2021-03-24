package in.co.rays.project_4.bean;

/**
 * @author Aditya
 *@version 1.0
 *@Copyright (c) Sunil OS
 */
public class CourseBean extends BaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * name of course
	 */
	private String name;
	/**
	 * Description of course
	 */
	private String description;
	/**
	 * Duration of course
	 */
	private String duration;
	
	
	
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

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

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

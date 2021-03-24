package in.co.rays.project_4.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
public abstract class BaseBean implements Serializable,Comparable<BaseBean>,DropDownListBean{
	
	private static final long serialVersionUID = 1L;
	/**
	 * primary key 
	 */
protected long id;

/**
 * Login Id of user who created the database record
 */
protected String createdBy;

/**
 * Login Id of user who modified the database record
 */
protected String modifiedBy;
/**
 * Created Timestamp of database record
 */
protected Timestamp createdDatetime;
/**
 * Modified Timestamp of database record
 */
protected Timestamp modifiedDatetime;
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getCreatedBy() {
	return createdBy;
}
public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}
public String getModifiedBy() {
	return modifiedBy;
}
public void setModifiedBy(String modifiedBy) {
	this.modifiedBy = modifiedBy;
}
public Timestamp getCreatedDatetime() {
	return createdDatetime;
}
public void setCreatedDatetime(Timestamp createdDatetime) {
	this.createdDatetime = createdDatetime;
}
public Timestamp getModifiedDatetime() {
	return modifiedDatetime;
}
public void setModifiedDatetime(Timestamp modifiedDatetime) {
	this.modifiedDatetime = modifiedDatetime;
}
//compareTo(next:BaseBean):int Remaining
}

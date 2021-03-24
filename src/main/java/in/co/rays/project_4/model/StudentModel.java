package in.co.rays.project_4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.CollegeBean;
import in.co.rays.project_4.bean.StudentBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.JdbcDataSource;

/**
 * 
 * JDBC implementation of Student Model
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
public class StudentModel {
	
	private static Logger log = Logger.getLogger(StudentModel.class);
	
	/**
	 * 
	 * finds next pk 
	 * 
	 * @return pk
	 * @throws ApplicationException
	 */
	public int nextPk() throws ApplicationException {
		log.debug(" Model nextPk Started");
		Connection conn = null;
		int pk=0;
		try{
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM st_student");
			ResultSet rs =  ps.executeQuery();
			while(rs.next()){
				pk=rs.getInt(1);
			}
			rs.close();
			ps.close();
		}
		catch(Exception e){
			log.error("Database Exception..",e);
			throw new ApplicationException("Application Exception in nextPK  StudentModel");
		}finally{
			JdbcDataSource.closeConnection(conn);
		}
		log.debug(" Model nextPk End");
		return pk+1;
	}
	/**
	 * 
	 * adds new Student record 
	 * 
	 * @param bean
	 * @return pk of new record
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(StudentBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add started");
		Connection conn = null;
		if(findByEmail(bean.getEmail()) != null){
			throw new DuplicateRecordException("This Email Already Exists");
		}
		else{
			CollegeModel model = new CollegeModel();
			bean.setCollegeName(model.findByPK(bean.getCollegeId()).getName());
		int pk =nextPk();
		try{
			conn=JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps= conn.prepareStatement("INSERT INTO st_student VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setLong(2, bean.getCollegeId());
			ps.setString(3, bean.getCollegeName());
			ps.setString(4, bean.getFirstName());
			ps.setString(5, bean.getLastName());
			ps.setDate(6, new java.sql.Date((bean.getDob()).getTime()));
			ps.setString(7, bean.getMobileNo());
			ps.setString(8, bean.getEmail());
			ps.setString(9, bean.getCreatedBy());
			ps.setString(10, bean.getModifiedBy());
			ps.setTimestamp(11,bean.getCreatedDatetime());
			ps.setTimestamp(12, bean.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
			ps.close();
			
		}
		catch(Exception e){
			log.error("DataBase Exception..",e);
			try {
				conn.rollback();
			} catch (Exception e1) {
				throw new ApplicationException("Database Exception in add UserModel");
				
			}
			throw new ApplicationException("Application Exception in add StudentModel");
		}finally{
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
		}
	}
	/**
	 * 
	 * updates student record
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public void update(StudentBean bean) throws ApplicationException, DuplicateRecordException{
		log.debug("model update start");
		if(!bean.getEmail().equals(findByPk(bean.getId()).getEmail())){
			if(findByEmail(bean.getEmail()) != null){
				throw new DuplicateRecordException("This Email Already Exists");
			}
		}
		
		Connection conn = null;
		try{
			
			CollegeModel model = new CollegeModel();
			bean.setCollegeName(model.findByPK(bean.getCollegeId()).getName());
			
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("UPDATE st_student SET COLLEGE_ID=?,COLLEGE_NAME=?,FIRST_NAME=?,LAST_NAME=?,DATE_OF_BIRTH=?,MOBILE_NO=?,EMAIL=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			
			ps.setLong(1, bean.getCollegeId());
			ps.setString(2, bean.getCollegeName());
			ps.setString(3, bean.getFirstName());
			ps.setString(4, bean.getLastName());
			ps.setDate(5, new java.sql.Date((bean.getDob()).getTime()));
			ps.setString(6, bean.getMobileNo());
			ps.setString(7, bean.getEmail());
			ps.setString(8, bean.getCreatedBy());
			ps.setString(9, bean.getModifiedBy());
			ps.setTimestamp(10, bean.getCreatedDatetime());
			ps.setTimestamp(11, bean.getModifiedDatetime());
			ps.setLong(12, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		}
		catch(Exception e){
			log.error("Database exception",e);
			try {
				conn.rollback();
			} catch (Exception e1) {
				throw new ApplicationException("Database Exception in add UserModel");
				
			}
			throw new ApplicationException("Application Exception in update StudentModel");  
		}finally{
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("Faculty model update end");
	}
	/**
	 * 
	 * deletes student record
	 * 
	 * @param bean
	 * @throws ApplicationException
	 */
	public void delete(StudentBean bean) throws ApplicationException{
		log.debug("model delete start");
		Connection conn = null;
		try{
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_student WHERE ID=?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			
			ps.close();
			}
			catch(Exception e){
				log.error("Database exception..",e);
				try {
					conn.rollback();
				} catch (Exception e1) {
					throw new ApplicationException("Database Exception in add UserModel");	
				}
				throw new ApplicationException("DatabaseException in delete StudentModel");
			}
			finally{
				JdbcDataSource.closeConnection(conn);
			}	
		log.debug("model delete end");
		}
	/**
	 * 
	 * finds Student record by email
	 * 
	 * @param Email
	 * @return StudentBean
	 * @throws ApplicationException
	 */
	public StudentBean findByEmail(String Email) throws ApplicationException{
		log.debug("model findByEmail start");
		Connection conn = null;
		StudentBean bean = null;
		try{
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_student WHERE EMAIL=? ");
			ps.setString(1, Email);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				bean = setToBean(rs);	
			}
			ps.close();
			rs.close();
		}
		catch(Exception e){
			log.error("Database Exception",e);
			throw new ApplicationException("ApplicationException in findbyEmailId in Student Model");
		}
		finally{
			JdbcDataSource.closeConnection(conn);
			}
		log.debug("model findbyEmail end");
		return bean;
	}
	/**
	 * 
	 * finds student record by pk
	 * 
	 * @param pk
	 * @return StudentBean
	 * @throws ApplicationException
	 */
	public StudentBean findByPk(long pk) throws ApplicationException{
		log.debug("model findbypk start");
		Connection conn = null;
		StudentBean bean = null;
		try{
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_student WHERE ID=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				bean = setToBean(rs);
			}
			ps.close();
			rs.close();
		}
		catch(Exception e){
			log.error("Database error ",e);
			throw new ApplicationException("Application exception in findById studentModel");
		}finally{
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("model findbypk ended");
		return bean;
	}
	/**
	 * 
	 * finds list of students with pagination
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return Student list
	 * @throws ApplicationException
	 */
	public List<StudentBean> list(int pageNo,int pageSize) throws ApplicationException{
		log.debug("Model list start");
		Connection conn = null;
		List<StudentBean>list = new ArrayList<StudentBean>();
		try{
		conn = JdbcDataSource.getConnection();
		StringBuffer sql = new StringBuffer("SELECT * FROM st_student ");
		if (pageNo>0){
			pageNo = (pageNo-1)*pageSize;
			sql.append("LIMIT "+pageNo+" , "+pageSize);
		}
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			list.add(setToBean(rs));
		}
		ps.close();
		rs.close();
		}
		catch(Exception e){
			log.error("Database exception..",e);
			throw new ApplicationException("ApplicationException in list StudentModel");
		}finally{
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("model list end");
		return list;
	}
	/**
	 * 
	 * finds list of all student records
	 * 
	 * @return Student List
	 * @throws ApplicationException
	 */
	public List<StudentBean> list() throws ApplicationException{
		return list(0,0);
	}
	/**
	 * 
	 * search student records with pagination
	 * 
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @return StudentBean
	 * @throws ApplicationException
	 */
	public List<StudentBean> search(StudentBean bean,int pageNo,int pageSize) throws ApplicationException{
		log.debug("model search start");
		Connection conn =  null;
		List<StudentBean>list = new ArrayList<StudentBean>();
		try{
			conn = JdbcDataSource.getConnection();
			StringBuffer sql = new StringBuffer("SELECT * FROM st_student WHERE 1=1");
			
			if(bean!=null){
			if(bean.getId()!=0){
				sql.append(" and ID = "+bean.getId()); 
			}
			if(bean.getCollegeId()!=0){
				sql.append(" and COLLEGE_ID = "+bean.getCollegeId());
			}
			if(bean.getCollegeName()!=null  && bean.getCollegeName().trim().length()>0  ){
				sql.append(" and COLLEGE_NAME  like '"+bean.getCollegeName()+"%' ");
			}
			if(bean.getFirstName()!=null  && bean.getFirstName().trim().length()>0  ){
				sql.append(" and FIRST_NAME like '"+bean.getFirstName()+"%' ");
			}
			if(bean.getLastName()!=null  && bean.getLastName().trim().length()>0  ){
				sql.append(" and LAST_NAME like '"+bean.getLastName()+"%' ");
			}
			if(bean.getDob()!=null){
				sql.append(" and DATE_OF_BIRTH like '"+bean.getDob()+"%' ");
			}//check
			if(bean.getMobileNo()!=null  && bean.getMobileNo().trim().length()>0  ){
				sql.append(" and MOBILE_NO like '"+bean.getMobileNo()+"%' ");
			}
			if(bean.getEmail()!=null  && bean.getEmail().trim().length()>0  ){
				sql.append(" and EMAIL like '"+bean.getEmail()+"%' ");
			}
			if(bean.getCreatedBy()!=null){
				sql.append(" and CREATED_BY like '"+bean.getCreatedBy()+"%' ");
			}
			if(bean.getModifiedBy()!=null){
				sql.append(" and MODIFIED_BY like '"+bean.getModifiedBy()+"%' ");
			}
			if(bean.getCreatedDatetime()!=null){
				sql.append(" and CREATED_DATETIME like '"+bean.getCreatedDatetime()+"%' ");
			}
			if(bean.getModifiedDatetime()!=null){
				sql.append(" and MODIFIED_DATETIME like '"+bean.getModifiedBy()+"%' ");
			}
			}
			if(pageSize>0){
				pageNo=(pageNo-1)*pageSize;
				sql.append(" LIMIT "+ pageNo+" , "+pageSize);
			}
			PreparedStatement ps;
			ResultSet rs;
			
				ps = conn.prepareStatement(sql.toString());
				rs = ps.executeQuery();
				
				while(rs.next()){
					list.add(setToBean(rs));
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				log.error("database exception ..",e);
			throw new ApplicationException("Application Exception in search in Student Model");
			}
			finally{
				JdbcDataSource.closeConnection(conn);
			}
		log.debug("model search end");
			return list;
			
		
	}
	
	/**
	 * 
	 * populate Student bean with Result set
	 * 
	 * @param rs
	 * @return StudentBean
	 * @throws ApplicationException
	 */
	private static StudentBean setToBean(ResultSet rs) throws ApplicationException{
		log.debug("model setToBean start");
		StudentBean bean = new StudentBean();
		try{
		bean.setId(rs.getLong(1));
		bean.setCollegeId(rs.getLong(2));
		bean.setCollegeName(rs.getString(3));
		bean.setFirstName(rs.getString(4));
		bean.setLastName(rs.getString(5));
		bean.setDob(rs.getDate(6));
		bean.setMobileNo(rs.getString(7));
		bean.setEmail(rs.getString(8));
		bean.setCreatedBy(rs.getString(9));
		bean.setModifiedBy(rs.getString(10));
		bean.setCreatedDatetime(rs.getTimestamp(11));
		bean.setModifiedDatetime(rs.getTimestamp(12));
	}catch(Exception e){
		log.error("database exception ..",e);
		throw new ApplicationException("ApplicationException in setToBean Student Model");
	}
		log.debug("model setToBean ended");
		return bean;
		}
}

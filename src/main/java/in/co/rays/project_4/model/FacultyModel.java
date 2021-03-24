package in.co.rays.project_4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.metal.MetalIconFactory.FileIcon16;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.CollegeBean;
import in.co.rays.project_4.bean.CourseBean;
import in.co.rays.project_4.bean.FacultyBean;
import in.co.rays.project_4.bean.SubjectBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.util.JdbcDataSource;

/**
 * 
 * 	JDBC Implementation of Faculty Model
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
public class FacultyModel {
	
	private static Logger log = Logger.getLogger(FacultyModel.class);
	
	/**
	 * 
	 * Finds Next pk of st_faculty
	 * 
	 * @return pk
	 * @throws ApplicationException
	 */
	public int nextPk() throws ApplicationException{
		log.debug("Faculty Model nextPk Started");
		Connection conn = null;
		int pk = 0;
		try{
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM st_faculty");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				pk = rs.getInt(1);
			}
			ps.close();
			rs.close();
		}catch(Exception e){
			log.error("Database Exception..",e);
			throw new ApplicationException("Application Exception in faculty model nextpk");
		}finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model nextPk End");
		return pk+1;
		}


	/**
	 * 
	 * Adds a new Faculty record
	 * 
	 * @param bean
	 * @return pk of new record
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(FacultyBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Faculty Model add started");
		if(findByEmailId(bean.getEmailId())!=null){
			throw new DuplicateRecordException("this Email already exists");
		}
		else{
			Connection conn = null;
			long pk= 0 ;
		
		try{
			CollegeModel collegeModel = new CollegeModel();
			bean.setCollegeName(collegeModel.findByPK(bean.getCollegeId()).getName());
			
			CourseModel courseModel = new CourseModel();
			bean.setCourseName(courseModel.findByPk(bean.getCourseId()).getName());
			
			SubjectModel subjectModel = new SubjectModel();
			bean.setSubjectName(subjectModel.findByPk(bean.getSubjectId()).getName());
			
			conn = JdbcDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);
			PreparedStatement ps= conn.prepareStatement("INSERT INTO st_faculty VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, bean.getFirstName());
			ps.setString(3, bean.getLastName());
			ps.setString(4, bean.getGender());
			ps.setDate(5, new java.sql.Date((bean.getDob()).getTime()));
			ps.setString(6, bean.getQualification());
			ps.setString(7, bean.getEmailId());
			ps.setString(8, bean.getMobileNo());
			ps.setLong(9, bean.getCollegeId());
			ps.setString(10, bean.getCollegeName());
			ps.setLong(11, bean.getSubjectId());
			ps.setString(12, bean.getSubjectName());
			ps.setLong(13, bean.getCourseId());
			ps.setString(14, bean.getCourseName());
			ps.setString(15, bean.getCreatedBy());
			ps.setString(16, bean.getModifiedBy());
			ps.setTimestamp(17, bean.getCreatedDatetime());
			ps.setTimestamp(18, bean.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
		}catch(Exception e){
			log.error("DataBase Exception..",e);
				try {
					conn.rollback();
				} catch (Exception e1) {
					e1.printStackTrace();
					throw new ApplicationException("Application Exception in Faculty model add rollback ");
				}
			
			throw new ApplicationException("Application Exception in add faculty Model");
		}finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("Faculty Model add End");
		return pk;
	}
	}
	/**
	 * 
	 * Updates Faculty record
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public void update(FacultyBean bean) throws ApplicationException, DuplicateRecordException{
		log.debug("Faculty model update start");
		if(!bean.getEmailId().equals(findByPk(bean.getId()).getEmailId()))
			if(findByEmailId(bean.getEmailId())!=null)
				throw new DuplicateRecordException("this Email already exists");
		
		
		Connection conn = null;
		try{
			CollegeModel collegeModel = new CollegeModel();
			CollegeBean collegeBean = collegeModel.findByPK(bean.getCollegeId());
			bean.setCollegeName(collegeBean.getName());
			
			CourseModel courseModel = new CourseModel();
			CourseBean courseBean = courseModel.findByPk(bean.getCourseId());
			bean.setCourseName(courseBean.getName());
			
			SubjectModel subjectModel = new SubjectModel();
			SubjectBean subjectBean = subjectModel.findByPk(bean.getSubjectId());
			bean.setSubjectName(subjectBean.getName());
			
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps= conn.prepareStatement("UPDATE st_faculty SET FIRST_NAME=?,LAST_NAME=?,GENDER=?,DOB=?"
					+ ",QUALIFICATION=?,EMAIL_ID=?,MOBILE_NO=?,COLLEGE_ID=?,COLLEGE_NAME=?,SUBJECT_ID=?"
					+ ",SUBJECT_NAME=?,COURSE_ID=?,COURSE_NAME=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID = ? ");
			
			ps.setString(1, bean.getFirstName());
			ps.setString(2, bean.getLastName());
			ps.setString(3, bean.getGender());
			ps.setDate(4, new java.sql.Date((bean.getDob()).getTime()) );
			ps.setString(5, bean.getQualification());
			ps.setString(6, bean.getEmailId());
			ps.setString(7, bean.getMobileNo());
			ps.setLong(8, bean.getCollegeId());
			ps.setString(9, bean.getCollegeName());
			ps.setLong(10, bean.getSubjectId());
			ps.setString(11, bean.getSubjectName());
			ps.setLong(12, bean.getCourseId());
			ps.setString(13, bean.getCourseName());
			ps.setString(14, bean.getCreatedBy());
			ps.setString(15, bean.getModifiedBy());
			ps.setTimestamp(16, bean.getCreatedDatetime());
			ps.setTimestamp(17, bean.getModifiedDatetime());
			ps.setLong(18, bean.getId());
			ps.executeUpdate();
			ps.close();
		}catch(Exception e){
			log.error("Database exception",e);
			try {
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new ApplicationException("Application exception in rollback update faculty Model");
				
			}
			throw new ApplicationException("Application Exception in update faculty model");
		}finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("Faculty model update end");
		}
	/**
	 * 
	 * deletes Faculty record
	 * 
	 * @param bean
	 * @throws ApplicationException
	 */
	public void delete(FacultyBean bean) throws ApplicationException{
		log.debug("Faculty model delete start");
		Connection conn = null;
		try{
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_faculty WHERE ID=?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		}catch(Exception e){
			log.error("Database exception..",e);
			try {
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new ApplicationException("Application exception in faculty model delete rollback");
			}
			throw new ApplicationException("Application Exception in Faculty model");
		}finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("Faculty model delete end");
	}
	/**
	 * 
	 * Finds Faculty by Email Id
	 * 
	 * @param email
	 * @return Faculty Bean 
	 * @throws ApplicationException
	 */
	public FacultyBean findByEmailId(String email) throws ApplicationException{
		log.debug("Faculty model findByEmailId start");
		FacultyBean bean = null;
		Connection conn = null;
		try{
			conn =JdbcDataSource.getConnection();
			PreparedStatement ps= conn.prepareStatement("SELECT * FROM st_faculty WHERE EMAIL_ID=?");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
					bean = setToBean(rs);
			}
			ps.close();
			rs.close();
		}catch(Exception e){
			log.error("Database Exception",e);
			throw new ApplicationException("Application Exception in findByEmail");
		}finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("Faculty model findbyEmailId end");
		return bean;
	}
	/**
	 * 
	 * Finds Faculty by pk
	 * 
	 * @param pk
	 * @return Faculty Bean 
	 * @throws ApplicationException
	 */
	public FacultyBean findByPk(long pk) throws ApplicationException{
		log.debug("Faculty model findbypk start");
		FacultyBean bean = null;
		Connection conn = null;
		try{
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps= conn.prepareStatement("SELECT * FROM st_faculty WHERE ID=? ");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
			bean = setToBean(rs); 
			}
			ps.close();
			rs.close();
		}catch(Exception e){
			log.error("Database error ",e);
			throw new ApplicationException("Application Exception in find by pk Faculty Model");
		}finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("Faculty model findbypk ended");
		return bean;
	}
	/**
	 * 
	 * Finds list of faculty with pagination
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return Faculty List
	 * @throws ApplicationException
	 */
	public List<FacultyBean> list(int pageNo,int pageSize) throws ApplicationException{
		log.debug("Faculty Model list start");
		List<FacultyBean>list = new ArrayList<FacultyBean>();
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = JdbcDataSource.getConnection();
			StringBuffer sb = new StringBuffer("SELECT * FROM st_faculty ");
			if(pageNo>0){
				pageNo = (pageNo-1)*pageSize;
				sb.append("LIMIT "+pageNo+" , "+pageSize);
			}
			ps = conn.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				list.add(setToBean(rs));
			}
		}catch(Exception e){
			log.error("Database exception..",e);
			throw new ApplicationException("Application Exception in list Faculty model");
		}finally {
			JdbcDataSource.closeConnection(conn, ps);
		}
		log.debug("Faculty model list end");
		return list;
	}
	/**
	 * 
	 * search Faculty list with pagination
	 * 
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @return faculty List
	 * @throws ApplicationException
	 */
	public List<FacultyBean> search(FacultyBean bean ,int pageNo,int pageSize) throws ApplicationException{
		log.debug("Faculty model search start");
		List<FacultyBean>list = new ArrayList<FacultyBean>();
		Connection conn = null;
		try{
			conn = JdbcDataSource.getConnection();
			StringBuffer sb= new StringBuffer("SELECT * FROM st_faculty WHERE 1=1 ");
			if(bean!=null){
				if(bean.getId()!=0){
					sb.append(" AND ID LIKE "+bean.getId());
				}
				if(bean.getFirstName()!=null  && bean.getFirstName().trim().length()>0 ){
					sb.append(" AND FIRST_NAME LIKE '"+bean.getFirstName()+"%' ");
				}
				if(bean.getLastName()!=null  && bean.getLastName().trim().length()>0 ){
					sb.append(" AND LAST_NAME LIKE '"+bean.getLastName()+"%' ");
				}
				if(bean.getGender()!=null){
					sb.append(" AND GENDER LIKE '"+bean.getGender()+"%' ");
				}
				if(bean.getDob()!=null){
					sb.append(" AND DOB LIKE '"+bean.getDob()+"%' ");
				}
				if(bean.getQualification()!=null  && bean.getQualification().trim().length()>0  ){
					sb.append(" AND QUALIFICATION LIKE '"+bean.getQualification()+"%' ");
				}
				if(bean.getEmailId()!=null  && bean.getEmailId().trim().length()>0  ){
					sb.append(" AND EMAIL_ID LIKE '"+bean.getEmailId()+"%' ");
				}
				if(bean.getMobileNo()!=null  && bean.getMobileNo().trim().length()>0  ){
					sb.append(" AND MOBILE_NO LIKE '"+bean.getMobileNo()+"%' ");
				}
				if(bean.getCollegeId()!=0){
					sb.append(" AND COLLEGE_ID LIKE "+bean.getCollegeId());
				}
				if(bean.getCollegeName()!=null  && bean.getCollegeName().trim().length()>0 ){
					sb.append(" AND COLLEGE_NAME LIKE '"+bean.getCollegeName()+"%' ");
				}
				if(bean.getSubjectId()!=0){
					sb.append(" AND SUBJECT_ID LIKE "+bean.getSubjectId());
				}
				if(bean.getSubjectName()!=null && bean.getSubjectName().trim().length()>0 ){
					sb.append(" AND SUBJECT_NAME LIKE '"+bean.getSubjectName()+"%' ");
				}
				if(bean.getCourseId()!=0){
					sb.append(" AND COURSE_ID LIKE "+bean.getCourseId());
				}
				if(bean.getCourseName()!=null  && bean.getCourseName().trim().length()>0  ){
					sb.append(" AND COURSE_NAME LIKE '"+bean.getCourseName()+"%' ");
				}
				if(bean.getCreatedBy()!=null){
					sb.append(" AND CREATED_BY LIKE '"+bean.getCreatedBy()+"%' ");
				}
				if(bean.getModifiedBy()!=null){
					sb.append(" AND MODIFIED_BY LIKE '"+bean.getModifiedBy()+"%' ");
				}
				if(bean.getCreatedDatetime()!=null){
					sb.append(" AND CREATED_DATETIME LIKE '"+bean.getCreatedDatetime()+"%' ");
				}
				if(bean.getModifiedDatetime()!=null){
					sb.append(" AND MODIFIED_DATETIME  LIKE '"+bean.getModifiedDatetime()+"%' ");
				}
			}
			if(pageNo>0){
				pageNo=(pageNo-1)*pageSize;
				sb.append(" LIMIT "+pageNo+" , "+pageSize);
			}
			PreparedStatement ps = conn.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				list.add(setToBean(rs));
			}
		}catch(Exception e){
			log.error("database exception ..",e);
			throw new ApplicationException("Application exception in faculty model");
		}finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("Faculty model search end");
		return list;
	}
	/**
	 * 
	 * Populates FacultyBean with Result set
	 * 
	 * @param rs
	 * @return FacultyBean
	 * @throws ApplicationException
	 */
	private static FacultyBean setToBean(ResultSet rs) throws ApplicationException{
		log.debug("Faculty model setToBean start");
		FacultyBean bean = new FacultyBean();
		try{
			bean.setId(rs.getLong(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setGender(rs.getString(4));
			bean.setDob(rs.getDate(5));
			bean.setQualification(rs.getString(6));
			bean.setEmailId(rs.getString(7));
			bean.setMobileNo(rs.getString(8));
			bean.setCollegeId(rs.getLong(9));
			bean.setCollegeName(rs.getString(10));
			bean.setSubjectId(rs.getLong(11));
			bean.setSubjectName(rs.getString(12));
			bean.setCourseId(rs.getLong(13));
			bean.setCourseName(rs.getString(14));
			bean.setCreatedBy(rs.getString(15));
			bean.setModifiedBy(rs.getString(16));
			bean.setCreatedDatetime(rs.getTimestamp(17));
			bean.setModifiedDatetime(rs.getTimestamp(18));
		}catch(Exception e){
			log.error("database exception ..",e);
			throw new ApplicationException("Application Exception in setToBean Faculty Model");
		}
		log.debug("College model setToBean ended");
		return bean;
	}


	/**
	 * 
	 * finds all Faculty records
	 * 
	 * @return Faculty List
	 * @throws ApplicationException
	 */
	public List<FacultyBean> list() throws ApplicationException {
		return list(0,0);
	}
}
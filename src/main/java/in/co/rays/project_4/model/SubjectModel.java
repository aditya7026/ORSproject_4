package in.co.rays.project_4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.SubjectBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.util.JdbcDataSource;

/**
 * 
 * JDBC implementation of Subject Model
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
public class SubjectModel {
	
	private static Logger log = Logger.getLogger(SubjectModel.class);
	
	/**
	 * 
	 * finds next pk for st_subject
	 * 
	 * @return pk
	 * @throws ApplicationException
	 */
	public int nextPk() throws ApplicationException{
		log.debug(" Model nextPk Started");
		int pk= 0;
		Connection conn= null;
		try {
			conn= JdbcDataSource.getConnection();
			PreparedStatement ps= conn.prepareStatement("SELECT MAX(ID) FROM st_subject");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				pk = rs.getInt(1);
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			log.error("Database Exception..",e);
			throw new ApplicationException("Application Exception in Subject model next pk ");
		}
		log.debug(" Model nextPk End");
		return pk+1;
	}
	/**
	 * 
	 * adds new subject record
	 * 
	 * @param bean
	 * @return pk of new record
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(SubjectBean bean) throws ApplicationException, DuplicateRecordException{
		log.debug("Model add start");
		if(findByName(bean.getName())!=null){
			throw new DuplicateRecordException("this Subject Name aleardy exists");
		}
		else{
			
		long pk= 0;
		Connection conn = null;
		try {
			CourseModel model = new CourseModel();
			bean.setCourseName(model.findByPk(bean.getCourseId()).getName());
			
			pk = nextPk();
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO st_subject VALUES (?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, bean.getName());
			ps.setString(3, bean.getDescription());
			ps.setLong(4, bean.getCourseId());
			ps.setString(5, bean.getCourseName());
			ps.setString(6, bean.getCreatedBy());
			ps.setString(7, bean.getModifiedBy());
			ps.setTimestamp(8, bean.getCreatedDatetime());
			ps.setTimestamp(9, bean.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			log.error("DataBase Exception..",e);
			try {
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new ApplicationException("application exception in rollback add student model");
			}
			throw new ApplicationException("Application Exception in add subject model");
		}finally {
			JdbcDataSource.getConnection();
		}
		log.debug("Model add End");
		return pk;
		}
	}
	/**
	 * 
	 * Updates subject record
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public void update(SubjectBean bean) throws ApplicationException, DuplicateRecordException{
		log.debug("model update start");
		if(!bean.getName().equals(findByPk(bean.getId()).getName()))
			if(findByName(bean.getName())!=null)
				throw new DuplicateRecordException("this Subject Name aleardy exists");
		
			Connection conn = null;
		try {
			
			CourseModel model = new CourseModel();
			bean.setCourseName(model.findByPk(bean.getCourseId()).getName());
			
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("UPDATE st_subject SET NAME=?,DESCRIPTION=?,COURSE_ID=?,COURSE_NAME=?,"
					+ "CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID = ?");
					ps.setString(1, bean.getName());
					ps.setString(2, bean.getDescription());
					ps.setLong(3, bean.getCourseId());;
					ps.setString(4, bean.getCourseName());
					ps.setString(5, bean.getCreatedBy());
					ps.setString(6, bean.getModifiedBy());
					ps.setTimestamp(7, bean.getCreatedDatetime());
					ps.setTimestamp(8, bean.getModifiedDatetime());
					ps.setLong(9, bean.getId());
					ps.executeUpdate();
					conn.commit();
					ps.close();
		} catch (Exception e) {
			log.error("Database exception",e);
			try {
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new ApplicationException("Application exception in rollback update Subject Model");
			}
			throw new ApplicationException("Application exception in update subjectModel");
		}finally {
			JdbcDataSource.closeConnection(conn);
		
		}
		log.debug("model update end");
	}
	/**
	 * 
	 * deletes subject record
	 * 
	 * @param bean
	 * @throws ApplicationException
	 */
	public void delete(SubjectBean bean) throws ApplicationException{
		log.debug("model delete start");
		Connection conn = null;
		try{
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_subject WHERE ID=?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		}catch (Exception e) {
			log.error("Database exception..",e);
			try {
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new ApplicationException("Application Exception in delete subject model rollback");
			}
			throw new ApplicationException("Application exception in delete subject model ");
		}finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("model delete end");
	}
	/**
	 * 
	 * finds subject record by pk
	 * 
	 * @param pk
	 * @return SubjectBean
	 * @throws ApplicationException
	 */
	public SubjectBean findByPk(long pk) throws ApplicationException{
		
		log.debug("model findByPk start");
		Connection conn= null;
		SubjectBean bean = null;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_subject WHERE ID=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				bean = setToBean(rs);
			}
		} catch (Exception e) {
			log.error("Database Exception",e);
			throw new ApplicationException("Application exception in find by pk in subject model");
		}finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("model findbyPk end");
		return bean;
	}
	/**
	 * 
	 * finds Subject record by name
	 * 
	 * @param name
	 * @return SubjectBean
	 * @throws ApplicationException
	 */
	public SubjectBean findByName(String name) throws ApplicationException{
		log.debug("model findByName start");
		SubjectBean bean = null;
		Connection conn = null;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_subject WHERE NAME = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				bean = setToBean(rs);
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			log.error("Database Exception",e);
			throw new ApplicationException("Application exception in find by name subject");
		}finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("model findbyName end");
		return bean;
	}
	/**
	 * 
	 * finds subject list with pagination
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return Student List
	 * @throws ApplicationException
	 */
	public List<SubjectBean> list(int pageNo , int pageSize) throws ApplicationException{
		log.debug("Model list start");
		List<SubjectBean>list = new ArrayList<SubjectBean>();
		Connection conn =null;
		try {
			conn = JdbcDataSource.getConnection();
			StringBuffer sql = new StringBuffer("SELECT * FROM st_subject ");
			if(pageNo>0){
				pageNo=(pageNo-1)*pageSize;
				sql.append(" LIMIT "+pageNo+ " , "+pageSize);
			}
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				list.add(setToBean(rs));
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			log.error("Database exception..",e);
			throw new  ApplicationException("Application exception in subject model list");
		}finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("model list end");
		return list;
	}
	/**
	 * 
	 * search subject list with pagination
	 * 
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @return Subject List
	 * @throws ApplicationException
	 */
	public List<SubjectBean> search(SubjectBean bean,int pageNo , int pageSize) throws ApplicationException{
		log.debug("model search start");
		List<SubjectBean>list = new ArrayList<SubjectBean>();
		Connection conn =null;
		try {
			conn = JdbcDataSource.getConnection();
			StringBuffer sql = new StringBuffer("SELECT * FROM st_subject WHERE 1=1 ");
			if(bean!=null){
				if(bean.getId()!=0){
					sql.append(" and ID = "+bean.getId()); 
				}
				if(bean.getName()!=null  && bean.getName().trim().length()>0  ){
					sql.append(" and NAME like '"+bean.getName()+"%' ");
				}
				if(bean.getDescription()!=null  && bean.getDescription().trim().length()>0   ){
					sql.append(" and DESCRIPTION like '"+bean.getDescription()+"%' ");
				}
				if(bean.getCourseId()!=0){
					sql.append(" and  COURSE_ID like "+bean.getCourseId());
				}
				if(bean.getCourseName()!=null  && bean.getCourseName().trim().length()>0  ){
					sql.append(" and COURSE_NAME like '"+bean.getCourseName()+"%' ");
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
			
			if(pageNo>0){
				pageNo=(pageNo-1)*pageSize;
				sql.append(" LIMIT "+pageNo+ " , "+pageSize);
			}
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				list.add(setToBean(rs));
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			log.error("database exception ..",e);
			throw new  ApplicationException("Application exception in subject model search");
		}finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("model search end");
		return list;
	}
	/**
	 * 
	 * populates subejct bean with result set
	 * 
	 * @param rs
	 * @return Subject Bean
	 * @throws ApplicationException
	 */
	private static SubjectBean setToBean(ResultSet rs) throws ApplicationException{
		log.debug("model setToBean start");
		SubjectBean bean = new SubjectBean();
		try {
		bean.setId(rs.getLong(1));
		bean.setName(rs.getString(2));
		bean.setDescription(rs.getString(3));
		bean.setCourseId(rs.getLong(4));
		bean.setCourseName(rs.getString(5));
		bean.setCreatedBy(rs.getString(6));
		bean.setModifiedBy(rs.getString(7));
		bean.setCreatedDatetime(rs.getTimestamp(8));
		bean.setModifiedDatetime(rs.getTimestamp(9));
		} catch (Exception e) {
			log.error("database exception ..",e);
			throw new ApplicationException("Application exception in set to bean Subject Model");
		}
		log.debug("model setToBean ended");
		return bean;
	}
	public List<SubjectBean> list() throws ApplicationException {
		return list(0,0);
	}
}

package in.co.rays.project_4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.CourseBean;
import in.co.rays.project_4.bean.SubjectBean;
import in.co.rays.project_4.bean.TimeTableBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.util.JdbcDataSource;

/**
 * 
 * JDBC implementation of TimeTable Model
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
public class TimeTableModel {
	
	private static Logger log = Logger.getLogger(TimeTableModel.class);
	
	/**
	 * 
	 * finds next pk for st_timetable
	 * 
	 * @return pk
	 * @throws ApplicationException
	 */
	public int nextPk() throws ApplicationException{
		log.debug(" Model nextPk Start");
		Connection conn = null;
		int pk = 0;
		try{
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM st_timetable ");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				pk = rs.getInt(1);
			}
			ps.close();
			rs.close();
			}catch(Exception e){
				log.error("Database Exception..",e);
				throw new ApplicationException("Application Exception in Time Table model"); 
			}finally {
				JdbcDataSource.getConnection();
			}
		log.debug(" Model nextPk End");
			return pk+1;
		}
		/**
		 * 
		 * checks duplicacy for time table
		 * 
		 * @param courseId
		 * @param Semester
		 * @param subjectId
		 * @return TimeTableBean
		 * @throws ApplicationException
		 */
		public TimeTableBean findTimeTableDuplicacy(long courseId,String Semester,Long subjectId) throws ApplicationException{
			log.debug("model findTimeTableDuplicacy start");
			Connection conn = null;
			TimeTableBean bean =  null;
			try {
				conn = JdbcDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_timetable WHERE COURSE_ID=? AND SEMESTER=? AND SUBJECT_ID=?");
				ps.setLong(1, courseId);
				ps.setString(2, Semester);
				ps.setLong(3, subjectId);
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					bean = setToBean(rs);
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				log.error("DataBase Exception..",e);
				throw new ApplicationException("Application exception in findtimetableDuplicacy(courseid,Semester,subject)");
			}finally {
				JdbcDataSource.getConnection();
			}
			log.debug("model findTimeTableDuplicacy end");
			return bean;
		}
		
		/**
		 * 
		 * checks duplicacy for time table
		 * 
		 * @param courseId
		 * @param Semester
		 * @param examdate
		 * @return TimeTableBean
		 * @throws ApplicationException
		 */
		public TimeTableBean findTimeTableDuplicacy(long courseId,String Semester,Date examdate) throws ApplicationException{
			log.debug("model findTimeTableDuplicacy start");
			TimeTableBean bean = null;
			Connection conn = null;
			try {
				conn = JdbcDataSource.getConnection();
				PreparedStatement ps= conn.prepareStatement("SELECT * FROM st_timetable WHERE COURSE_ID=? AND SEMESTER=? AND EXAM_DATE=?");
				ps.setLong(1, courseId);
				ps.setString(2, Semester);
				ps.setDate(3, new java.sql.Date((examdate).getTime()));
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					bean = setToBean(rs);
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				log.error("DataBase Exception..",e);
				throw new ApplicationException("Application exception in findTableTableDuplicacy(courseId, Semester, examdate)");
			}
			log.debug("model findTimeTableDuplicacy end");
			return bean;	
		}
		/**
		 * 
		 * adds new timetable record
		 * 
		 * @param bean
		 * @return pk
		 * @throws ApplicationException
		 * @throws DuplicateRecordException
		 */
		public long add(TimeTableBean bean) throws ApplicationException, DuplicateRecordException{
			log.debug("Model add start");
			long pk = 0;
			Connection conn= null;
			if(findTimeTableDuplicacy(bean.getCourseId(), bean.getSemester(), bean.getExamDate())!=null){
				throw new DuplicateRecordException("Duplicate record exception in examdate");
			}
			if(findTimeTableDuplicacy(bean.getCourseId(), bean.getSemester(), bean.getSubjectId())!=null){
				throw new DuplicateRecordException("Duplicate record exception in subject id");
			}
			try{
			pk = nextPk();
			
			CourseModel courseModel = new CourseModel();
			CourseBean courseBean = courseModel.findByPk(bean.getCourseId());
			bean.setCourseName(courseBean.getName());
			
			SubjectModel subjectModel = new SubjectModel();
			SubjectBean subjectBean = subjectModel.findByPk(bean.getSubjectId());
			bean.setSubjectName(subjectBean.getName());
			
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO  st_timetable values (?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setLong(2, bean.getCourseId());
			ps.setString(3, bean.getCourseName());
			ps.setLong(4, bean.getSubjectId());
			ps.setString(5, bean.getSubjectName());
			ps.setString(6, bean.getSemester());
			ps.setDate(7, new java.sql.Date((bean.getExamDate()).getTime()));
			ps.setString(8, bean.getTime());
			ps.setString(9, bean.getCreatedBy());
			ps.setString(10, bean.getModifiedBy());
			ps.setTimestamp(11, bean.getCreatedDatetime());
			ps.setTimestamp(12, bean.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
			
			ps.close();
			
			}catch (Exception e) {
				log.error("DataBase Exception..",e);
				try {
					conn.rollback();
				} catch (Exception e1) {
					e1.printStackTrace();
					throw new ApplicationException("Application exception in time table model");
				}
				throw new ApplicationException("Application Exception in add timetablemodel");
			}finally {
				JdbcDataSource.getConnection();
			}
			log.debug("Model add End");
			return pk;
			}
			
		/**
		 * 
		 * updates timetable record
		 * 
		 * @param bean
		 * @throws ApplicationException
		 * @throws DuplicateRecordException
		 */
		public void update(TimeTableBean bean) throws ApplicationException, DuplicateRecordException{
			log.debug("model update start");
			Connection conn = null;
			TimeTableBean dBean1 = findTimeTableDuplicacy(bean.getCourseId(), bean.getSemester(), bean.getExamDate()); 
			if(dBean1!=null&&dBean1.getId()!=bean.getId()){
				throw new DuplicateRecordException("Duplicate record exception in examdate");
			}
			TimeTableBean dBean2 = findTimeTableDuplicacy(bean.getCourseId(), bean.getSemester(), bean.getSubjectId());
			if(dBean2!=null&&dBean2.getId()!=bean.getId()){
				throw new DuplicateRecordException("Duplicate record exception in subject id");
			}
			try{
				CourseModel courseModel = new CourseModel();
				CourseBean courseBean = courseModel.findByPk(bean.getCourseId());
				bean.setCourseName(courseBean.getName());
				
				SubjectModel subjectModel = new SubjectModel();
				SubjectBean subjectBean = subjectModel.findByPk(bean.getSubjectId());
				bean.setSubjectName(subjectBean.getName());
				
				conn = JdbcDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement ps= conn.prepareStatement("UPDATE st_timetable SET COURSE_ID=?,COURSE_NAME=?,SUBJECT_ID=?,SUBJECT_NAME=?"
						+ ",SEMESTER=?,EXAM_DATE=?,EXAM_TIME=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
				ps.setLong(1, bean.getCourseId());
				ps.setString(2, bean.getCourseName());
				ps.setLong(3, bean.getSubjectId());
				ps.setString(4, bean.getSubjectName());
				ps.setString(5, bean.getSemester());
				ps.setDate(6,new java.sql.Date((bean.getExamDate()).getTime()));//check
				ps.setString(7, bean.getTime());
				ps.setString(8, bean.getCreatedBy());
				ps.setString(9, bean.getModifiedBy());
				ps.setTimestamp(10, bean.getCreatedDatetime());
				ps.setTimestamp(11, bean.getModifiedDatetime());
				ps.setLong(12, bean.getId());
				ps.executeUpdate();
				conn.commit();
				ps.close();
				
			}catch (Exception e) {
				log.error("Database exception",e);
				try {
					conn.rollback();
				} catch (Exception e1) {
					e1.printStackTrace();
					throw new ApplicationException("Application exception in rollback update in timetable models");
				}
				throw new ApplicationException("Application exception in update in timetable model");
			}finally {
				JdbcDataSource.closeConnection(conn);
			}
			log.debug("model update end");
		}
		/**
		 * 
		 * deletes timetable record
		 * 
		 * @param bean
		 * @throws ApplicationException
		 */
		public void delete(TimeTableBean bean) throws ApplicationException{
			log.debug("model delete start");
			Connection conn = null;
			try {
				conn = JdbcDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement ps = conn.prepareStatement("DELETE FROM st_timetable WHERE ID=?");
				ps.setLong(1, bean.getId());
				ps.executeUpdate();
				conn.commit();
				ps.close();
				
			} catch (Exception e) {
				log.error("Database exception..",e);
				try {
					conn.rollback();
				} catch (Exception e1) {
					e1.printStackTrace();
					throw new ApplicationException("Application Exception in rollback delete  ");
				}
				throw new ApplicationException("Application exception in delete ");
			}finally {
				JdbcDataSource.getConnection();
			}
			log.debug("model delete end");
		}
		/**
		 * 
		 * finds timetable record by pk
		 * 
		 * @param pk
		 * @return timetableBean
		 * @throws ApplicationException
		 */
		public TimeTableBean findByPk(long pk ) throws ApplicationException{
			log.debug("model findByPk start");
			TimeTableBean bean = new TimeTableBean();
			Connection conn = null;
			try {
				conn= JdbcDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_timetable WHERE ID=?");
				ps.setLong(1, pk);
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					bean = setToBean(rs);
				}
				rs.close();
				ps.close();
			} catch (Exception e) {
				log.error("Database Exception",e);
				throw new ApplicationException("Application exception in find by pk");
			} finally {
				JdbcDataSource.getConnection();
			}
			log.debug("model findbyPk end");
			return bean;
		}
		/**
		 * 
		 * finds list of timetable records with pagination
		 * 
		 * @param pageNo
		 * @param pageSize
		 * @return TimeTable List
		 * @throws ApplicationException
		 */
		public List<TimeTableBean> list(int pageNo,int pageSize) throws ApplicationException{
			log.debug("Model list start");
			Connection conn = null;
			List<TimeTableBean>list = new ArrayList<TimeTableBean>();
			try {
				conn = JdbcDataSource.getConnection();
				StringBuffer sql = new StringBuffer("SELECT * FROM st_timetable ");
				if(pageNo>0){
					pageNo = (pageNo-1)*pageSize;
					sql.append(" LIMIT "+pageNo+" , "+pageSize);
				}
				PreparedStatement ps = conn.prepareStatement(sql.toString());
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					list.add(setToBean(rs));
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				log.error("Database exception..",e);
				throw new ApplicationException("Application exception in list time table model");
			}
			log.debug("model list end");
			return list;
		}
		/**
		 * 
		 * search timetable list with pagination
		 * 
		 * @param bean
		 * @param pageNo
		 * @param pageSize
		 * @return List TimeTableBean
		 * @throws ApplicationException
		 */
		public List<TimeTableBean> search(TimeTableBean bean,int pageNo,int pageSize) throws ApplicationException{
			log.debug("model search start");
			Connection conn = null;
			List<TimeTableBean>list = new ArrayList<TimeTableBean>();
			try {
				conn = JdbcDataSource.getConnection();
				StringBuffer sql = new StringBuffer("SELECT * FROM st_timetable	WHERE 1=1 ");
				if(bean!=null){
					if(bean.getId()!=0){
						sql.append(" AND ID = "+bean.getId());
					}
					if(bean.getCourseId()!=0){
						sql.append(" AND COURSE_ID LIKE "+bean.getCourseId());
					}
					if(bean.getCourseName()!=null && bean.getCourseName().trim().length()>0){
						sql.append(" AND COURSE_NAME LIKE '"+bean.getCourseName()+"%' ");
					}
					if(bean.getSubjectId()!=0){
						sql.append(" AND SUBJECT_ID LIKE "+bean.getSubjectId());
					}
					if(bean.getSubjectName()!=null && bean.getSubjectName().trim().length()>0 ){
						sql.append(" AND SUBJECT_NAME LIKE '"+bean.getSubjectName()+"%' ");
					}
					if(bean.getSemester()!=null && bean.getSemester().trim().length()>0){
						sql.append(" AND SEMESTER = '"+bean.getSemester()+"' ");
					}//LIKE CHANGED TO =
					if(bean.getExamDate()!=null && (bean.getExamDate().getDate()>0)){
						java.sql.Date date = new java.sql.Date(bean.getExamDate().getTime());
						sql.append(" AND EXAM_DATE = '"+date+"' ");
						
					} //LIKE CHANGED TO =
					if(bean.getTime()!=null){
						sql.append(" AND EXAM_TIME LIKE '"+bean.getTime()+"%' ");
					}
					if(bean.getCreatedBy()!=null){
						sql.append(" AND CREATED_BY LIKE '"+bean.getCreatedBy()+"%' ");
					}
					if(bean.getModifiedBy()!=null){
						sql.append(" AND MODIFIED_BY LIKE '"+bean.getModifiedBy()+"%' ");
					}
					if(bean.getCreatedDatetime()!=null){
						sql.append(" AND CREATED_DATETIME LIKE '"+bean.getCreatedDatetime()+"%' ");
					}//check
					if(bean.getModifiedDatetime()!=null){
						sql.append(" AND MODIFIED_DATETIME LIKE '"+bean.getModifiedDatetime()+"%' ");
					}//check
				}
				if(pageSize>0){
					pageNo = (pageNo-1)*pageSize;
					sql.append(" LIMIT "+pageNo+" , "+pageSize);
				}
				System.out.println(sql);
				PreparedStatement ps = conn.prepareStatement(sql.toString());
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					list.add(setToBean(rs));
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				log.error("database exception ..",e);
				throw new ApplicationException("Application exception in list time table model");
			}
			log.debug("model search end");
			return list;
		}
		/**
		 * 
		 * populates TimeTableBean with result set
		 * 
		 * @param rs
		 * @return TimeTableBean 
		 * @throws ApplicationException
		 */
		private static TimeTableBean setToBean(ResultSet rs) throws ApplicationException{
			log.debug("model setToBean start");
			TimeTableBean bean = new TimeTableBean();
			try {
				bean.setId(rs.getLong(1));
				bean.setCourseId(rs.getLong(2));
				bean.setCourseName(rs.getString(3));
				bean.setSubjectId(rs.getLong(4));
				bean.setSubjectName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));
				bean.setTime(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			} catch (Exception e) {
				log.error("database exception ..",e);
				throw new ApplicationException("Application exception in set to bean timetable model");
			}
			log.debug("model setToBean ended");
			return bean;
		}
}

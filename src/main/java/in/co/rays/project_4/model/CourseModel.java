package in.co.rays.project_4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.CourseBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.util.JdbcDataSource;

/**
 * 
 * JDBC implementation of Course Model
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
public class CourseModel {
	
	private static Logger log = Logger.getLogger(CourseModel.class);
	
	/**
	 * 
	 * Finds next pk of st_course
	 * 
	 * @return pk
	 * @throws ApplicationException
	 */
	public int nextPK() throws ApplicationException {
		log.debug("Course Model nextPk Start");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM st_course");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..",e);
			throw new ApplicationException("Application Exception in Course Model");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("course Model nextPk End");
		return pk + 1;
	}

	/**
	 * 
	 * Adds new Course
	 * 
	 * @param bean
	 * @return pk of new record
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(CourseBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Course Model add start");
		Connection conn = null;
		long pk = 0;
		if (findByName(bean.getName()) != null) {
			throw new DuplicateRecordException("This Course Name Already exists");
		} else {
			try {
				pk = nextPK();
				conn = JdbcDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement ps = conn.prepareStatement("INSERT INTO st_course VALUES(?,?,?,?,?,?,?,?)");
				ps.setLong(1, pk);
				ps.setString(2, bean.getName());
				ps.setString(3, bean.getDescription());
				ps.setString(4, bean.getDuration());
				ps.setString(5, bean.getCreatedBy());
				ps.setString(6, bean.getModifiedBy());
				ps.setTimestamp(7, bean.getCreatedDatetime());
				ps.setTimestamp(8, bean.getModifiedDatetime());
				ps.executeUpdate();
				conn.commit();
				ps.close();
			} catch (Exception e) {
				log.error("DataBase Exception..",e);
				try {
					conn.rollback();
				} catch (Exception e1) {
					e1.printStackTrace();
					throw new ApplicationException("Application Exception in rollback in course model add");
				}
				throw new ApplicationException("Application exception in Course model add");

			} finally {
				JdbcDataSource.closeConnection(conn);
			}
			log.debug("Course Model add End");
			return pk;
		}
	}

	/**
	 * 
	 * Updates Course Entry
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public void update(CourseBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Course model update start");
		if (!bean.getName().equals(findByPk(bean.getId()).getName()))
			if (findByName(bean.getName()) != null)
				throw new DuplicateRecordException("This Course Name Already exists");

		Connection conn = null;
		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE st_course SET NAME = ? , DESCRIPTION = ? , DURATION = ? , CREATED_BY = ? , MODIFIED_BY =  ? , CREATED_DATETIME = ? , MODIFIED_DATETIME = ? WHERE ID = ? ");
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getDescription());
			ps.setString(3, bean.getDuration());
			ps.setString(4, bean.getCreatedBy());
			ps.setString(5, bean.getModifiedBy());
			ps.setTimestamp(6, bean.getCreatedDatetime());
			ps.setTimestamp(7, bean.getModifiedDatetime());
			ps.setLong(8, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			log.error("Database exception",e);
			try {
				conn.rollback();
			} catch (Exception e1) {

				e1.printStackTrace();
				throw new ApplicationException("Application Exception in Course Model rollback update");
			}
			throw new ApplicationException("Application Exception in Course Model update");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("Course model update end");
	}

	/**
	 * 
	 * Deletes Course Entry
	 * 
	 * @param bean
	 * @throws ApplicationException
	 */
	public void delete(CourseBean bean) throws ApplicationException {
		log.debug("Course model delete start");
		Connection conn = null;
		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_course WHERE ID = ?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			log.error("Database exception",e);
			try {
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new ApplicationException("Application Exception in rollback delete Course Model");
			}
			throw new ApplicationException("Application Exception in Course Model delete");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("course model delete end");
	}

	/**
	 * 
	 * Finds Course by Primary Key
	 * 
	 * @param pk
	 * @return CourseBean
	 * @throws ApplicationException
	 */
	public CourseBean findByPk(long pk) throws ApplicationException {
		log.debug("Course model findbypk start");
		Connection conn = null;
		CourseBean bean = null;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_course WHERE ID = ?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = setToBean(rs);
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error("Database error ",e);
			throw new ApplicationException("Application Exception in find by pk course model");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("Course model findbypk end");
		return bean;
	}

	/**
	 * 
	 * Finds Course by Name
	 * 
	 * @param name
	 * @return CourseBean
	 * @throws ApplicationException
	 */
	public CourseBean findByName(String name) throws ApplicationException {
		log.debug("Course model findByName start");
		Connection conn = null;
		CourseBean bean = null;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_course WHERE NAME = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = setToBean(rs);
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception",e);
			throw new ApplicationException("Application Exception in Course Model find by name");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("Course model findByName end");
		return bean;
	}

	/**
	 * 
	 * Finds List of Course with Pagination
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return Course List
	 * @throws ApplicationException
	 */
	public List<CourseBean> list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Course Model list start");
		Connection conn = null;
		List<CourseBean> list = new ArrayList<CourseBean>();
		try {
			conn = JdbcDataSource.getConnection();
			StringBuffer sql = new StringBuffer("SELECT * FROM st_course ");
			if (pageNo > 0) {
				pageNo = (pageNo - 1) * pageSize;
				sql.append("LIMIT " + pageNo + " , " + pageSize);
			}
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(setToBean(rs));
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error("database exception ..",e);
			throw new ApplicationException("Application Exception in List Course Model");
		}
		log.debug("Course model list end");
		return list;
	}

	/**
	 * 
	 * Finds List of all Courses
	 * 
	 * @return Course List
	 * @throws ApplicationException
	 */
	public List<CourseBean> list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * 
	 * Search List of Course with pagination
	 * 
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @return Course List
	 * @throws ApplicationException
	 */
	public List<CourseBean> search(CourseBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("course model search start");
		Connection conn = null;
		List<CourseBean> list = new ArrayList<CourseBean>();
		try {
			conn = JdbcDataSource.getConnection();
			StringBuffer sql = new StringBuffer("SELECT * FROM st_course WHERE 1=1 ");

			if (bean != null) {
				if (bean.getId() != 0) {
					sql.append(" AND ID LIKE " + bean.getId());
				}
				if (bean.getName() != null && bean.getName().trim().length()>0 ) {
					sql.append(" AND NAME LIKE '" + bean.getName() + "%' ");
				}
				if (bean.getDescription() != null  && bean.getDescription().trim().length()>0  )  {
					sql.append(" AND DESCRIPTION LIKE '" + bean.getDescription() + "%' ");
				}
				if (bean.getDuration() != null) {
					sql.append(" AND DURATION LIKE '" + bean.getDuration() + "%' ");
				}
				if (bean.getCreatedBy() != null) {
					sql.append(" AND CREATED_BY LIKE '" + bean.getCreatedBy() + "%' ");
				}
				if (bean.getModifiedBy() != null) {
					sql.append(" AND MODIFIED_BY LIKE '" + bean.getModifiedBy() + "%' ");
				}
				if (bean.getCreatedDatetime() != null) {
					sql.append(" AND CREATED_DATETIME LIKE '" + bean.getCreatedDatetime() + "%' ");
				}
				if (bean.getModifiedDatetime() != null) {
					sql.append(" AND MODIFIED_DATETIME LIKE '" + bean.getModifiedDatetime() + "%' ");
				}
			}
			if (pageNo > 0) {
				pageNo = (pageNo - 1) * pageSize;
				sql.append(" LIMIT " + pageNo + " , " + pageSize);
			}

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(setToBean(rs));
			}

		} catch (Exception e) {
			log.debug("Database exception",e);
			throw new ApplicationException("Application Exception in search course Model");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("course model search end");
		return list;
	}

	/**
	 * 
	 * populates CollegeBean with result set
	 * 
	 * @param rs
	 * @return CourseBean
	 * @throws ApplicationException
	 */
	private static CourseBean setToBean(ResultSet rs) throws ApplicationException {
		log.debug("course model setToBean started");
		CourseBean bean = new CourseBean();
		try {
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setDescription(rs.getString(3));
			bean.setDuration(rs.getString(4));
			bean.setCreatedBy(rs.getString(5));
			bean.setModifiedBy(rs.getString(6));
			bean.setCreatedDatetime(rs.getTimestamp(7));
			bean.setModifiedDatetime(rs.getTimestamp(8));
		} catch (Exception e) {
			log.error("database exception ..",e);
			throw new ApplicationException("Application Exception in setToBean Course model");
		}
		log.debug("Course model setToBean ended");
		return bean;
	}
}

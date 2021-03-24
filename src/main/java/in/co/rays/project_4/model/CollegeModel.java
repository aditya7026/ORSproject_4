package in.co.rays.project_4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.CollegeBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.util.JdbcDataSource;

/**
 * 
 * JDBC implementation of College Model
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
public class CollegeModel {
	
	
	private static Logger log = Logger.getLogger(CollegeModel.class);
	
	/**
	 * 
	 * Finds next PK of st_college
	 * 
	 * @return pk
	 * @throws ApplicationException
	 */
	public int nextPK() throws ApplicationException {
		log.debug(" Model nextPk Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM st_college");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..",e);
			throw new ApplicationException("Application Exception in next pk CollegeModel");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug(" Model nextPk End");
		return pk + 1;
	}

	/**
	 * 
	 * Adds a new College
	 * 
	 * @param bean
	 * @return pk of new record
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(CollegeBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("College Model add started");
		Connection conn = null;

		if (findByName(bean.getName()) != null) {
			throw new DuplicateRecordException("This College Name Already Exists");
		} else {
			long pk = nextPK();
			try {

				conn = JdbcDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement ps = conn.prepareStatement("INSERT INTO st_college VALUES (?,?,?,?,?,?,?,?,?,?)");
				ps.setLong(1, pk);
				ps.setString(2, bean.getName());
				ps.setString(3, bean.getAddress());
				ps.setString(4, bean.getState());
				ps.setString(5, bean.getCity());
				ps.setString(6, bean.getPhNo());
				ps.setString(7, bean.getCreatedBy());
				ps.setString(8, bean.getModifiedBy());
				ps.setTimestamp(9, bean.getCreatedDatetime());
				ps.setTimestamp(10, bean.getModifiedDatetime());
				ps.executeUpdate();
				conn.commit();
				ps.close();
			} catch (Exception e) {
				log.error("DataBase Exception..",e);
				try {
					conn.rollback();
				} catch (Exception e1) {
					throw new ApplicationException("RollBack Exception in add CollegeModel");

				}
				throw new ApplicationException("Application Exception in add College Model");

			} finally {
				JdbcDataSource.closeConnection(conn);
			}
			log.debug("College Model add End");
			return pk;
		}

	}

	/**
	 * 
	 * Finds College by Primary key
	 * 
	 * @param pk
	 * @return CollegeBean
	 * @throws ApplicationException
	 */
	public CollegeBean findByPK(long pk) throws ApplicationException {
		log.debug("College model findbypk start");
		Connection conn = null;
		CollegeBean bean = null;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_college WHERE ID=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = setToBean(rs);
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error("Database error ",e);
			throw new ApplicationException("Application Exception in findByPk in College Model");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("College model findbypk ended");
		return bean;
	}

	/**
	 * 
	 * Finds College by Name
	 * 
	 * @param name
	 * @return CollegeBean
	 * @throws ApplicationException
	 */
	public CollegeBean findByName(String name) throws ApplicationException {
		log.debug("College model findByName start");
		Connection conn = null;
		CollegeBean bean = null;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_college WHERE NAME=?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = CollegeModel.setToBean(rs);
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception",e);
			throw new ApplicationException("Application Exception in findByName in College Model");
		}
		log.debug("College model findbyname ended");
		return bean;
	}

	/**
	 * 
	 * Deletes College record
	 * 
	 * @param bean
	 * @throws ApplicationException
	 */
	public void delete(CollegeBean bean) throws ApplicationException {
		log.debug("College model delete start");
		Connection conn = null;
		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_college WHERE ID=?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();// verification ?
			conn.commit();
			ps.close();
		} catch (Exception e) {
			log.error("Database exception..",e);
			try {
				conn.rollback();
			} catch (Exception e1) {
				throw new ApplicationException("CollegeModel Exception in delete CollegeModel");

			}
			throw new ApplicationException("Application Exception in delete CollegeModel");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug(" model delete end");
	}

	/**
	 * 
	 * Updates College Entry
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public void update(CollegeBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("College model update start");
		if (!bean.getName().equals(findByPK(bean.getId()).getName()))
			if (findByName(bean.getName()) != null)
				throw new DuplicateRecordException("This College Name Already Exists");

		Connection conn = null;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE st_college SET NAME=?,ADDRESS=?,STATE=?,CITY=?,PHONE_NO=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getAddress());
			ps.setString(3, bean.getState());
			ps.setString(4, bean.getCity());
			ps.setString(5, bean.getPhNo());
			ps.setString(6, bean.getCreatedBy());
			ps.setString(7, bean.getModifiedBy());
			ps.setTimestamp(8, bean.getCreatedDatetime());// to check
			ps.setTimestamp(9, bean.getModifiedDatetime());// to check
			ps.setLong(10, bean.getId());
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			log.error("Database exception",e);
			try {
				conn.rollback();
			} catch (Exception e1) {
				throw new ApplicationException("RollBack Exception in update CollegeModel");
			}
			throw new ApplicationException("Application Exception in update in CollegeModel");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("College model update end");
	}

	/**
	 * 
	 * List of All Colleges
	 * 
	 * @return College List
	 * @throws ApplicationException
	 */
	public List<CollegeBean> list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * 
	 * List of Colleges with Pagination
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return College List
	 * @throws ApplicationException
	 */
	public List<CollegeBean> list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("College Model list start");
		Connection conn = null;
		List<CollegeBean> list = new ArrayList<CollegeBean>();
		try {
			conn = JdbcDataSource.getConnection();
			StringBuffer sql = new StringBuffer("SELECT * FROM st_college ");
			if (pageNo > 0) {
				pageNo = (pageNo - 1) * pageSize;
				sql.append("LIMIT " + pageNo + " , " + pageSize);
			}
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CollegeBean bean = new CollegeBean();
				bean = setToBean(rs);
				list.add(bean);
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error("Database exception..",e);
			throw new ApplicationException("ApplicationException in list CollegeModel");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("college model list end");
		return list;
	}

	/**
	 * 
	 * Search College with pagination
	 * 
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @return College List
	 * @throws ApplicationException
	 */
	public List<CollegeBean> search(CollegeBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("college model search start");
		Connection conn = null;
		List<CollegeBean> list = new ArrayList<CollegeBean>();
		try {
			conn = JdbcDataSource.getConnection();
			StringBuffer sql = new StringBuffer("SELECT * FROM st_college WHERE 1=1");

			if (bean != null) {
				if (bean.getId() != 0) {
					sql.append(" and ID = " + bean.getId());
				}
				if (bean.getName() != null) {
					sql.append(" and NAME LIKE '" + bean.getName() + "%' ");
				}
				if (bean.getAddress() != null) {
					sql.append(" and ADDRESS LIKE '" + bean.getAddress() + "%' ");
				}
				if (bean.getState() != null) {
					sql.append(" and STATE like '" + bean.getState() + "%' ");
				}
				if (bean.getCity() != null) {
					sql.append(" and CITY LIKE '" + bean.getCity() + "%' ");
				}
				if (bean.getPhNo() != null) {
					sql.append(" and PHONE_NO LIKE '" + bean.getPhNo() + "%' ");
				}
				if (bean.getCreatedBy() != null) {
					sql.append(" and CREATED_BY like '" + bean.getCreatedBy() + "%' ");
				}
				if (bean.getModifiedBy() != null) {
					sql.append(" and MODIFIED_BY like '" + bean.getModifiedBy() + "%' ");
				}
				if (bean.getCreatedDatetime() != null) {
					sql.append(" and CREATED_DATETIME like '" + bean.getCreatedDatetime() + "%' ");
				}
				if (bean.getModifiedDatetime() != null) {
					sql.append(" and MODIFIED_DATETIME like '" + bean.getModifiedBy() + "%' ");
				}
			}
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				sql.append(" LIMIT " + pageNo + " , " + pageSize);
			}
			PreparedStatement ps;
			ResultSet rs;

			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				CollegeBean getBean = new CollegeBean();
				getBean = setToBean(rs);
				list.add(getBean);
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error("database exception ..",e);
			throw new ApplicationException("Application Exception in search in College Model");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("College model search end");
		return list;

	}

	/**
	 * 
	 * populates Bean with Result Set
	 * 
	 * @param rs
	 * @return CollegeBean
	 * @throws ApplicationException
	 */
	private static CollegeBean setToBean(ResultSet rs) throws ApplicationException {
		log.debug("College model setToBean started");
		CollegeBean bean = new CollegeBean();
		try {
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setAddress(rs.getString(3));
			bean.setState(rs.getString(4));
			bean.setCity(rs.getString(5));
			bean.setPhNo(rs.getString(6));
			bean.setCreatedBy(rs.getString(7));
			bean.setModifiedBy(rs.getString(8));
			bean.setCreatedDatetime(rs.getTimestamp(9));
			bean.setModifiedDatetime(rs.getTimestamp(10));

		} catch (Exception e) {
			log.error("database exception ..",e);
			throw new ApplicationException("Application Exception in setToBean in CollegeModel");
		}
		log.debug("College model setToBean ended");
		return bean;
	}

}

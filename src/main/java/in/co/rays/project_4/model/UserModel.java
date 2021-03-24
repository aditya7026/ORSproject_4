package in.co.rays.project_4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.UserBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.exception.RecordNotFoundException;
import in.co.rays.project_4.util.EmailBuilder;
import in.co.rays.project_4.util.EmailMessage;
import in.co.rays.project_4.util.EmailUtility;
import in.co.rays.project_4.util.JdbcDataSource;

/**
 * 
 * JDBC implementation of User Model
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
public class UserModel {

	private static Logger log = Logger.getLogger(UserModel.class);

	/**
	 * 
	 * finds next pk for st_user
	 * 
	 * @return pk
	 * @throws ApplicationException
	 */
	public int nextPK() throws ApplicationException {
		log.debug(" Model nextPk Start");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM st_user ");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Application Exception in nextPk UserModel");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug(" Model nextPk End");
		return pk + 1;
	}

	/**
	 * 
	 * adds new user record
	 * 
	 * @param bean
	 * @return pk of new record
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(UserBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add start");
		Connection conn = null;
		if (findByLogin(bean.getLogin()) != null) {
			throw new DuplicateRecordException("Login id already exists");
		} else {
			int pk = 0;

			try {
				conn = JdbcDataSource.getConnection();
				conn.setAutoCommit(false);
				pk = nextPK();
				PreparedStatement ps = conn
						.prepareStatement("INSERT INTO st_user VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				ps.setLong(1, pk);
				ps.setString(2, bean.getFirstName());
				ps.setString(3, bean.getLastName());
				ps.setString(4, bean.getLogin());
				ps.setString(5, bean.getPassword());
				ps.setDate(6, new java.sql.Date((bean.getDob()).getTime()));
				ps.setString(7, bean.getMobileNo());
				ps.setLong(8, bean.getRoleId());
				ps.setInt(9, bean.getUnSuccessfullLogin());
				ps.setString(10, bean.getGender());
				ps.setTimestamp(11, bean.getLastLogin());
				ps.setString(12, bean.getRegisteredIP());
				ps.setString(13, bean.getLastLoginIP());
				ps.setString(14, bean.getCreatedBy());
				ps.setString(15, bean.getModifiedBy());
				ps.setTimestamp(16, bean.getCreatedDatetime());
				ps.setTimestamp(17, bean.getModifiedDatetime());
				ps.executeUpdate();
				conn.commit();

				ps.close();
			} catch (Exception e) {
				log.error("DataBase Exception..", e);
				try {
					conn.rollback();
				} catch (Exception e1) {
					throw new ApplicationException("Rollback Exception in add UserModel");
				}
				throw new ApplicationException("Application Exception in add User Model");

			} finally {
				JdbcDataSource.closeConnection(conn);
			}
			log.debug("Model add End");
			return pk;
		}
	}

	/**
	 * 
	 * Finds user record by login
	 * 
	 * @param login
	 * @return UserBean
	 * @throws ApplicationException
	 */
	public UserBean findByLogin(String login) throws ApplicationException {
		log.debug("model findByLogin start");
		Connection conn = null;
		UserBean bean = null;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_user WHERE LOGIN=?");
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = UserModel.setToBean(rs);
			}
			ps.close();

		} catch (Exception e) {
			log.error("Database exception..", e);
			throw new ApplicationException("ApplicationException in findbyLogin UserModel");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("model findByLogin end");
		return bean;
	}

	/**
	 * 
	 * finds user record by pk
	 * 
	 * @param pk
	 * @return UserBean
	 * @throws ApplicationException
	 */
	public UserBean findByPk(long pk) throws ApplicationException {
		log.debug("model findByPk start");
		Connection conn = null;
		UserBean bean = null;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_user WHERE ID=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = UserModel.setToBean(rs);
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new ApplicationException("ApplicationException in findByPk UserModel");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("model findbyPk end");
		return bean;
	}

	/**
	 * 
	 * updates user record
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public void update(UserBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("model update start");
		if (!bean.getLogin().equals((findByPk(bean.getId())).getLogin()))
			if (findByLogin(bean.getLogin()) != null)
				throw new DuplicateRecordException("Login id already exists");

		Connection conn = null;
		try {
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("UPDATE st_user "
					+ "SET FIRST_NAME=?,LAST_NAME=?,LOGIN=?,PASSWORD=?,DOB=?,MOBILE_NO=?,ROLE_ID=?,UNSUCCESSFUL_LOGIN=?,GENDER=?,"
					+ "LAST_LOGIN=?,REGISTERED_IP=?,LAST_LOGIN_IP=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? "
					+ "WHERE ID=?");
			ps.setString(1, bean.getFirstName());
			ps.setString(2, bean.getLastName());
			ps.setString(3, bean.getLogin());
			ps.setString(4, bean.getPassword());
			ps.setDate(5, new java.sql.Date((bean.getDob()).getTime()));
			ps.setString(6, bean.getMobileNo());
			ps.setLong(7, bean.getRoleId());
			ps.setInt(8, bean.getUnSuccessfullLogin());
			ps.setString(9, bean.getGender());
			ps.setTimestamp(10, bean.getLastLogin());// to check
			ps.setString(11, bean.getRegisteredIP());
			ps.setString(12, bean.getLastLoginIP());
			ps.setString(13, bean.getCreatedBy());
			ps.setString(14, bean.getModifiedBy());
			ps.setTimestamp(15, bean.getCreatedDatetime());// to check
			ps.setTimestamp(16, bean.getModifiedDatetime());// to check
			ps.setLong(17, bean.getId());
			ps.executeUpdate();
			conn.commit();

			ps.close();

		} catch (Exception e) {
			log.error("Database exception", e);
			try {
				conn.rollback();
			} catch (Exception e1) {
				throw new ApplicationException("Rollback Exception in add RoleModel");

			}

			throw new ApplicationException("Application Exception in update User Model");

		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("model update end");
		return;

	}

	/**
	 * 
	 * 
	 * 
	 * @param login
	 * @param password
	 * @return UserBean
	 * @throws ApplicationException
	 * @throws RecordNotFoundException 
	 */
	public UserBean authenticate(String login, String password) throws ApplicationException, RecordNotFoundException {
		log.debug("model authenticate start");
		Connection conn = null;
		UserBean bean = null;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_user WHERE LOGIN = ? AND PASSWORD = ? ");
			ps.setString(1, login);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = setToBean(rs);
			}
			ps.close();
			rs.close();
			
		} catch (Exception e) {
			log.error("Database exception..", e);
			throw new ApplicationException("Exception in authenticate Userbean");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		if (bean == null) {
			throw new RecordNotFoundException("this login id does not exists");
		}
		log.debug("model authenticate start");
		return bean;
	}

	/**
	 * 
	 * deletes user record
	 * 
	 * @param bean
	 * @throws ApplicationException
	 */
	public void delete(UserBean bean) throws ApplicationException {
		log.debug("model delete start");
		Connection conn = null;
		try {
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_user WHERE ID=?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();// verification ?
			ps.close();
		} catch (Exception e) {
			log.error("Database exception..", e);
			try {
				conn.rollback();
			} catch (Exception e1) {
				throw new ApplicationException("Rollback Exception in add RoleModel");

			}
			throw new ApplicationException("Application Exception in delete UserModel");

		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("model delete end");
	}

	/**
	 * 
	 * registers new user and sends confirmation mail
	 * 
	 * @param bean
	 * @return pk of new record
	 * @throws DuplicateRecordException
	 * @throws ApplicationException
	 */
	public long registerUser(UserBean bean) throws DuplicateRecordException, ApplicationException {
		log.debug("model registerUser start");
		UserModel model = new UserModel();
		long pk = model.add(bean);
		// MAIL START
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", bean.getLogin());
		map.put("password", bean.getPassword());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(bean.getLogin());
		msg.setSubject("Registration is successfull for ORS Project");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);
		// MAIL END
		log.debug("model register user end");
		return pk;

	}

	/**
	 * 
	 * changes password of user.
	 * 
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * @return boolean
	 * @throws DuplicateRecordException
	 * @throws ApplicationException
	 */
	public boolean changePassword(long id, String oldPassword, String newPassword)
			throws DuplicateRecordException, ApplicationException {
		log.debug("model changePassword start");
		Connection conn = null;
		Boolean flag = false;
		try {
			conn = JdbcDataSource.getConnection();
			UserBean bean = findByPk(id);
			if (bean != null) {
				System.out.println("ok");
				if (bean.getPassword().equals(oldPassword)) {
					System.out.println("ok");
					bean.setPassword(newPassword);
					update(bean);
					// MAIL START
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("login", bean.getLogin());
					map.put("password", bean.getPassword());
					map.put("firstName", bean.getFirstName());
					map.put("lastName", bean.getLastName());
					String message = EmailBuilder.getChangePasswordMessage(map);

					EmailMessage msg = new EmailMessage();

					msg.setTo(bean.getLogin());
					msg.setSubject("Your Password has been changed");
					msg.setMessage(message);
					msg.setMessageType(EmailMessage.HTML_MSG);

					EmailUtility.sendMail(msg);
					// MAIL END
					flag = true;
				} else {
					flag = false;
				}
			} else {
				flag = false;
			}

		} catch (ApplicationException e) {
			log.error("Database exception..", e);
			throw new ApplicationException("Application exception in change Password user model");
		} catch (DuplicateRecordException e) {
			log.error("Database exception..", e);
			throw new DuplicateRecordException("This Login Id Exists");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("model changePassword end");
		return flag;
	}

	/**
	 * 
	 * finds list of users with pagination
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return user list
	 * @throws ApplicationException
	 */
	public List<UserBean> list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list start");
		Connection conn = null;
		List<UserBean> list = new ArrayList<UserBean>();
		try {
			conn = JdbcDataSource.getConnection();
			StringBuffer sql = new StringBuffer("SELECT * FROM st_user ");
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
			log.error("Database exception..", e);
			throw new ApplicationException("ApplicationException in list UserModel");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("model list end");
		return list;
	}

	/**
	 * 
	 * search user list with pagination
	 * 
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @return user List
	 * @throws ApplicationException
	 */
	public List<UserBean> search(UserBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("model search start");
		Connection conn = JdbcDataSource.getConnection();
		StringBuffer sql = new StringBuffer("SELECT * FROM st_user WHERE 1=1");
		// search
		if (bean != null) {
			if (bean.getId() != 0) {
				sql.append(" and ID = " + bean.getId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().trim().length() > 0) {
				sql.append(" and FIRST_NAME like '" + bean.getFirstName() + "%' ");
			}
			if (bean.getLastName() != null && bean.getLastName().trim().length() > 0) {
				sql.append(" and LAST_NAME like '" + bean.getLastName() + "%' ");
			}
			if (bean.getLogin() != null && bean.getLogin().trim().length() > 0) {
				sql.append(" and LOGIN like '" + bean.getLogin() + "%' ");
			}
			if (bean.getPassword() != null) {
				sql.append(" and PASSWORD like '" + bean.getPassword() + "%' ");
			}
			if (bean.getDob() != null) {
				sql.append(" and DOB like '" + bean.getDob() + "%' ");
			} // check
			if (bean.getMobileNo() != null) {
				sql.append(" and MOBILE_NO LIKE '" + bean.getMobileNo() + "%' ");
			}
			if (bean.getRoleId() != 0) {
				sql.append(" and ROLE_ID = " + bean.getRoleId());
			}
			if (bean.getUnSuccessfullLogin() != 0) {
				sql.append(" and UNSUCCESSFULL_LOGIN = " + bean.getUnSuccessfullLogin());
			}
			if (bean.getGender() != null) {
				sql.append(" and GENDER like '" + bean.getGender() + "%' ");
			}
			if (bean.getLastLogin() != null) {
				sql.append(" and LAST_LOGIN like '" + bean.getRoleId() + "%' ");
			}
			if (bean.getRegisteredIP() != null) {
				sql.append(" and REGISTERED_IP like '" + bean.getRegisteredIP() + "%' ");
			}
			if (bean.getLastLoginIP() != null) {
				sql.append(" and LAST_LOGIN_IP like '" + bean.getLastLoginIP() + "%' ");
			}
			if (bean.getCreatedBy() != null) {
				sql.append(" and CREATED_BY like '" + bean.getCreatedBy() + "%' ");
			}
			if (bean.getModifiedBy() != null) {
				sql.append(" and MODIFIED_BY like '" + bean.getModifiedBy() + "%' ");
			}
			if (bean.getCreatedDatetime() != null) {
				sql.append(" and CREATED_DATETIME like '" + bean.getCreatedDatetime() + "%' ");
			} // check
			if (bean.getModifiedDatetime() != null) {
				sql.append(" and MODIFIED_DATETIME like '" + bean.getModifiedDatetime() + "%' ");
			} // check
		}
		// Pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + " , " + pageSize);
		}
		// System.out.println(sql);
		PreparedStatement ps;
		ResultSet rs;
		List<UserBean> list = new ArrayList<UserBean>();
		try {
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(setToBean(rs));
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error("database exception ..", e);
			throw new ApplicationException("Application Exception in search in User Model");
		} finally {
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("model search end");
		return list;
	}

	/**
	 * 
	 * sends password recovery mail for login
	 * 
	 * @param login
	 * @return boolean
	 * @throws ApplicationException
	 * @throws RecordNotFoundException
	 */
	public boolean forgetPassword(String login) throws ApplicationException, RecordNotFoundException {
		log.debug("model forgetPassword start");
		UserBean bean = findByLogin(login);
		if (bean == null) {
			throw new RecordNotFoundException("this login id does not exists");
		}
		// MAIL START
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", bean.getLogin());
		map.put("password", bean.getPassword());
		map.put("firstName", bean.getFirstName());
		map.put("lastName", bean.getLastName());
		String message = EmailBuilder.getForgotPasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(bean.getLogin());
		msg.setSubject("Password recovery for ORS Project");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);
		// MAIL END
		log.debug("model forgetpassword end");
		return false;
	}

	/**
	 * 
	 * populate UserBean with result set
	 * 
	 * @param ResultSet
	 * @return UserBean
	 * @throws ApplicationException
	 */
	private static UserBean setToBean(ResultSet rs) throws ApplicationException {
		log.debug("model setToBean start");
		UserBean bean = new UserBean();
		try {
			bean.setId(rs.getLong(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setLogin(rs.getString(4));
			bean.setPassword(rs.getString(5));
			bean.setDob(rs.getDate(6));
			bean.setMobileNo(rs.getString(7));
			bean.setRoleId(rs.getLong(8));
			bean.setUnSuccessfullLogin(rs.getInt(9));
			bean.setGender(rs.getString(10));
			bean.setLastLogin(rs.getTimestamp(11));
			bean.setRegisteredIP(rs.getString(12));
			bean.setLastLoginIP(rs.getString(13));
			bean.setCreatedBy(rs.getString(14));
			bean.setModifiedBy(rs.getString(15));
			bean.setCreatedDatetime(rs.getTimestamp(16));
			bean.setModifiedDatetime(rs.getTimestamp(17));

		} catch (Exception e) {
			log.error("database exception ..", e);
			throw new ApplicationException("Application Exception in SetToBean User Model");
		}
		log.debug("model setToBean ended");
		return bean;
	}

}

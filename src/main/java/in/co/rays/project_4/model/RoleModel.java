package in.co.rays.project_4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.RoleBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.util.JdbcDataSource;

/**
 * 
 * JDBC implementation of Role Model
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
public class RoleModel {
	
	private static Logger log = Logger.getLogger(RoleModel.class);
	
/**
 * 
 * finds next PK of st_role
 * 
 * @return pk
 * @throws ApplicationException
 */
public int nextPK() throws ApplicationException {
	log.debug(" Model nextPk Started");
	Connection conn = null;
	int pk=0;
	try{
		conn = JdbcDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM st_role");
		ResultSet rs =  ps.executeQuery();
		while(rs.next()){
			pk=rs.getInt(1);
		}
		rs.close();
		ps.close();
	}
	catch(Exception e){
		log.error("Database Exception..",e);
		throw new ApplicationException("Application Exception in nextPK  RoleModel");
	}finally{
		JdbcDataSource.closeConnection(conn);
	}
	log.debug(" Model nextPk End");
	return pk+1;
}
/**
 * 
 * Adds new role record 
 * 
 * @param bean
 * @return pk of new record
 * @throws ApplicationException
 * @throws DuplicateRecordException
 */
public long add(RoleBean bean) throws ApplicationException, DuplicateRecordException {
	log.debug("Model add started");
	Connection conn = null;
	if(findByName(bean.getName())!=null){
		throw new DuplicateRecordException("This Role Already exists ");
	}else{
	int pk =nextPK();
	try{
		conn=JdbcDataSource.getConnection();
		PreparedStatement ps= conn.prepareStatement("INSERT INTO st_role VALUES (?,?,?,?,?,?,?)");
		ps.setLong(1, pk);
		ps.setString(2, bean.getName());
		ps.setString(3, bean.getDescription());
		ps.setString(4, bean.getCreatedBy());
		ps.setString(5, bean.getModifiedBy());
		ps.setTimestamp(6,bean.getCreatedDatetime());
		ps.setTimestamp(7, bean.getModifiedDatetime());
		ps.executeUpdate();
	}
	catch(Exception e){
		log.error("DataBase Exception..",e);
		try {
			conn.rollback();
		} catch (Exception e1) {
			throw new ApplicationException("Rollback Exception in add RoleModel");
			
		}
		throw new ApplicationException("Database Exception in add RoleModel");
	}finally{
		JdbcDataSource.closeConnection(conn);
	}
	log.debug("Model add End");
	return pk;
	
	}
}
/**
 * 
 * Updates role record
 * 
 * @param bean
 * @throws ApplicationException
 * @throws DuplicateRecordException
 */
public void update(RoleBean bean) throws ApplicationException, DuplicateRecordException{
	log.debug("model update start");
	if(!bean.getName().equals((findByPk(bean.getId())).getName()))
		if(findByName(bean.getName())!=null)
			throw new DuplicateRecordException("This Role Already exists ");
	
	
	
	Connection conn = null;
	try{
		conn = JdbcDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement("UPDATE st_role SET NAME=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
		ps.setString(1, bean.getName());
		ps.setString(2, bean.getDescription());
		ps.setString(3, bean.getCreatedBy());
		ps.setString(4, bean.getModifiedBy());
		ps.setTimestamp(5, bean.getCreatedDatetime());
		ps.setTimestamp(6, bean.getModifiedDatetime());
		ps.setLong(7, bean.getId());
		ps.executeUpdate();
		conn.commit();
		
	ps.close();
	}
	catch(Exception e){
		log.error("Database exception",e);
		try {
			conn.rollback();
		} catch (Exception e1) {
			throw new ApplicationException("Database Exception in update RoleModel");
			
		}
		throw new ApplicationException("Database Exception in update RoleBean");  
	}finally{
		JdbcDataSource.closeConnection(conn);
	
	}
	log.debug("model update end");
}
/**
 * 
 * deletes role record
 * 
 * @param bean
 * @throws ApplicationException
 */
public void delete(RoleBean bean) throws ApplicationException{
	log.debug("model delete start");
	Connection conn = null;
	try{
		conn = JdbcDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement("DELETE FROM st_role WHERE ID=?");
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
				throw new ApplicationException("Rollback Exception in delete RoleModel");
				
			}
			throw new ApplicationException("DatabaseException in delete RoleBean");
		
		}
		finally{
			JdbcDataSource.closeConnection(conn);
		}	
	log.debug("model delete end");
	}
/**
 * 
 * finds role by name
 * 
 * @param name
 * @return RoleBean
 * @throws ApplicationException
 */
public RoleBean findByName(String name) throws ApplicationException{
	log.debug("model findByName start");
	Connection conn = null;
	RoleBean bean = null;
	try{
		conn = JdbcDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_role WHERE NAME=? ");
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
		bean = setToBean(rs);	
		}
		rs.close();
		ps.close();
	}
	catch(Exception e){
		log.error("Database Exception",e);
		throw new ApplicationException("DatabaseException in find by Name in Role Model");
	}
	finally{
		JdbcDataSource.closeConnection(conn);
		}
	log.debug("model findbyName end");
	return bean;
}
/**
 * 
 * finds role with pk
 * 
 * @param pk
 * @return RoleBean
 * @throws ApplicationException
 */
public RoleBean findByPk(long pk) throws ApplicationException{
	log.debug("model findbypk start");
	Connection conn = null;
	RoleBean bean = null;
	try{
		conn = JdbcDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_role WHERE ID=?");
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
		throw new ApplicationException("record not found");
	}
	log.debug("model findbypk ended");
	return bean;
}
/**
 * 
 * finds list of all roles
 * 
 * @return role List
 * @throws ApplicationException
 */
public List<RoleBean> list() throws ApplicationException {
	return list(0,0);
}
/**
 * 
 * finds role with pagination
 * 
 * @param pageNo
 * @param pageSize
 * @return role List
 * @throws ApplicationException
 */
public List<RoleBean> list(int pageNo,int pageSize) throws ApplicationException{
	log.debug("Model list start");
	Connection conn = null;
	List<RoleBean>list = new ArrayList<RoleBean>();
	try{
	conn = JdbcDataSource.getConnection();
	StringBuffer sql = new StringBuffer("SELECT * FROM st_role ");
	if (pageNo>0){
		pageNo = (pageNo-1)*pageSize;
		sql.append("LIMIT "+pageNo+" , "+pageSize);
	}
	PreparedStatement ps = conn.prepareStatement(sql.toString());
	ResultSet rs = ps.executeQuery();
	while(rs.next()){
		RoleBean bean = new RoleBean();
		bean = setToBean(rs);
		list.add(bean);
	}
	ps.close();
	rs.close();
	}
	catch(Exception e){
		log.error("Database exception..",e);
		throw new ApplicationException("ApplicationException in list CollegeModel");
	}finally{
		JdbcDataSource.closeConnection(conn);
	}
	log.debug("model list end");
	return list;
}
/**
 * 
 * search role List with pagination
 * 
 * @param bean
 * @param pageNo
 * @param pageSize
 * @return RoleBean
 * @throws ApplicationException
 */
public List<RoleBean> search(RoleBean bean,int pageNo,int pageSize) throws ApplicationException{
	log.debug("model search start");
	Connection conn =  null;
	List<RoleBean>list = new ArrayList<RoleBean>();
	try{
		conn = JdbcDataSource.getConnection();
		StringBuffer sql = new StringBuffer("SELECT * FROM st_role WHERE 1=1");
		
		if(bean!=null){
		if(bean.getId()!=0){
			sql.append(" and ID = "+bean.getId()); 
		}
		if(bean.getName()!=null && bean.getName().trim().length()>0 ){
			sql.append(" and NAME LIKE '"+bean.getName()+"%' ");
		}
		if(bean.getDescription()!=null  && bean.getDescription().trim().length()>0  ){
			sql.append(" and DESCRIPTION LIKE '"+bean.getDescription()+"%' ");
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
				RoleBean getBean = new RoleBean();
				getBean = setToBean(rs);
				list.add(getBean);
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error("database exception ..",e);
		throw new ApplicationException("Application Exception in search in College Model");
		}
		finally{
			JdbcDataSource.closeConnection(conn);
		}
	log.debug("model search end");
		return list;
		
	
}
/**
 * 
 * populate bean with result set
 * 
 * @param rs
 * @return RoleBean
 * @throws ApplicationException
 */
private static RoleBean setToBean(ResultSet rs) throws ApplicationException{
	log.debug("model setToBean start");
	RoleBean bean = new RoleBean();
	try{
	bean.setId(rs.getLong(1));
	bean.setName(rs.getString(2));
	bean.setDescription(rs.getString(3));
	bean.setCreatedBy(rs.getString(4));
	bean.setModifiedBy(rs.getString(5));
	bean.setCreatedDatetime(rs.getTimestamp(6));
	bean.setModifiedDatetime(rs.getTimestamp(7));
	
}catch(Exception e){
	log.error("database exception ..",e);
	throw new ApplicationException("ApplicationException in setToBean RoleModel");
}
	log.debug("model setToBean ended");
	return bean;
	}
}

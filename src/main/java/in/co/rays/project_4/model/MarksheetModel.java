package in.co.rays.project_4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.MarksheetBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.exception.RecordNotFoundException;
import in.co.rays.project_4.util.JdbcDataSource;

/**
 * 
 * JDBC implementation of Marksheet Model
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
public class MarksheetModel {
	
	private static Logger log = Logger.getLogger(MarksheetModel.class);
	
	/**
	 * 
	 * finds next pk of st_marksheet
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
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM st_marksheet");
			ResultSet rs =  ps.executeQuery();
			while(rs.next()){
				pk=rs.getInt(1);
			}
			rs.close();
			ps.close();
		}
		catch(Exception e){
			log.error("Database Exception..",e);
			throw new ApplicationException("Application Exception in nextPK  MarksheetModel");
		}finally{
			JdbcDataSource.closeConnection(conn);
		}
		log.debug(" Model nextPk End");
		return pk+1;
	}
	/**
	 * 
	 * Adds new Marksheet record
	 * 
	 * @param bean
	 * @return pk of new record
	 * @throws ApplicationException
	 * @throws DuplicateRecordException 
	 */
	public long add(MarksheetBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add started");
		Connection conn = null;
		if(findByRollNo(bean.getRollNo())!=null){
			throw new DuplicateRecordException("this RollNo already exists");
		}else{
		int pk =nextPK();
		try{
			StudentModel model = new  StudentModel();
			bean.setName((model.findByPk(bean.getStudentId())).getValue());
			conn=JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps= conn.prepareStatement("INSERT INTO st_marksheet VALUES (?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, bean.getRollNo());
			ps.setLong(3, bean.getStudentId());
			ps.setString(4, bean.getName());
			ps.setInt(5, bean.getPhysics());
			ps.setInt(6, bean.getChemistry());
			ps.setInt(7, bean.getMaths());
			ps.setString(8, bean.getCreatedBy());
			ps.setString(9, bean.getModifiedBy());
			ps.setTimestamp(10,bean.getCreatedDatetime());
			ps.setTimestamp(11, bean.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
			ps.close();
			
		}
		catch(Exception e){
			log.error("DataBase Exception..",e);
			try {
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new ApplicationException("RollBack Exception in add MarksheetModel");
				
			}
			throw new ApplicationException("Database Exception in add MarksheetModel");
		}finally{
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
		}
	}
	/**
	 * 
	 * Updates Marksheet record
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public void update(MarksheetBean bean) throws ApplicationException, DuplicateRecordException{
		log.debug("Marksheet model update start");
		if(!bean.getRollNo().equals((findByPk(bean.getId()).getRollNo())))
			if(findByRollNo(bean.getRollNo()) != null)
				throw new DuplicateRecordException("this RollNo already exists");
		
		
		Connection conn = null;
		try{
			StudentModel model = new  StudentModel();
			bean.setName(model.findByPk(bean.getId()).getValue());
			
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("UPDATE st_marksheet SET ROLL_NO=?,STUDENT_ID=?,NAME=?,PHYSICS=?,CHEMISTRY=?,MATHS=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			
			ps.setString(1, bean.getRollNo());
			ps.setLong(2, bean.getStudentId());
			ps.setString(3, bean.getName());
			ps.setInt(4, bean.getPhysics());
			ps.setInt(5, bean.getChemistry());
			ps.setInt(6, bean.getMaths());
			ps.setString(7, bean.getCreatedBy());
			ps.setString(8, bean.getModifiedBy());
			ps.setTimestamp(9, bean.getCreatedDatetime());
			ps.setTimestamp(10, bean.getModifiedDatetime());
			ps.setLong(11, bean.getId());
			ps.executeUpdate();
			conn.commit();
			
			ps.close();
		}
		catch(Exception e){
			log.error("Database exception",e);
			try {
				conn.rollback();
			} catch (Exception e1) {
				throw new ApplicationException("RollBack Exception in update MarksheetModel");
				
			}
			throw new ApplicationException("Database Exception in update MarksheetBean");  
		}finally{
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("Faculty model update end");
	}
	/**
	 * 
	 * deletes Marksheet Record
	 * 
	 * @param bean
	 * @throws ApplicationException
	 */
	public void delete(MarksheetBean bean) throws ApplicationException{
		log.debug("College model delete start");
		Connection conn = null;
		try{
			conn = JdbcDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_marksheet WHERE ID=?");
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
					throw new ApplicationException("RollBack Exception in delete MarksheetModel");
					
				}
				throw new ApplicationException("DatabaseException in delete MarksheetBean");
			}
			finally{
				JdbcDataSource.closeConnection(conn);
			}	
		log.debug("Faculty model delete end");
		}
	/**
	 * 
	 * finds Marksheet Record by Roll No
	 * 
	 * @param rollNo
	 * @return MarksheetBean
	 * @throws ApplicationException
	 */
	public MarksheetBean findByRollNo(String rollNo) throws ApplicationException {
		log.debug("model findByRollNo start");
		Connection conn = null;
		MarksheetBean bean = null;
		try{
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_marksheet WHERE ROLL_NO=? ");
			ps.setString(1, rollNo);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
			bean = setToBean(rs);	
			}
			ps.close();
		}
		catch(Exception e){
			log.error("Database Exception",e);
			throw new ApplicationException("DatabaseException in findbyRollNo in Role Model");
		}
		finally{
			JdbcDataSource.closeConnection(conn);
			}
		log.debug("model findbyRollNo end");
		return bean;
	}
	/**
	 * 
	 * Finds Marksheet Record by Pk
	 * 
	 * @param pk
	 * @return MarksheetBean
	 * @throws ApplicationException
	 */
	public MarksheetBean findByPk(long pk) throws ApplicationException{
		log.debug("Marksheet model findbypk start");
		Connection conn = null;
		MarksheetBean bean = null;
		try{
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_marksheet WHERE ID=?");
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
		}finally{
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("model findbypk ended");
		return bean;
	}
	/**
	 * 
	 * finds Marksheet List with pagination
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return Marksheet List
	 * @throws ApplicationException
	 */
	public List<MarksheetBean> list(int pageNo,int pageSize) throws ApplicationException{
		log.debug("Marksheet Model list start");
		Connection conn = null;
		List<MarksheetBean>list = new ArrayList<MarksheetBean>();
		try{
		conn = JdbcDataSource.getConnection();
		StringBuffer sql = new StringBuffer("SELECT * FROM st_marksheet ");
		if (pageNo>0){
			pageNo = (pageNo-1)*pageSize;
			sql.append("LIMIT "+pageNo+" , "+pageSize);
		}
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			MarksheetBean bean = new MarksheetBean();
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
		log.debug("Faculty model list end");
		return list;
	}
	/**
	 * 
	 * Search Marksheet List with Pagination
	 * 
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @return Marksheet List
	 * @throws ApplicationException
	 */
	public List<MarksheetBean> search(MarksheetBean bean,int pageNo,int pageSize) throws ApplicationException{
		log.debug("model search start");
		Connection conn =  null;
		List<MarksheetBean>list = new ArrayList<MarksheetBean>();
		try{
			conn = JdbcDataSource.getConnection();
			StringBuffer sql = new StringBuffer("SELECT * FROM st_marksheet WHERE 1=1");
			
			if(bean!=null){
			if(bean.getId()!=0){
				sql.append(" and ID = "+bean.getId()); 
			}
			if(bean.getRollNo()!=null  && bean.getRollNo().trim().length()>0  ){
				sql.append(" and ROLL_NO LIKE '"+bean.getRollNo()+"%' ");
			}
			if(bean.getStudentId()!=0){
				sql.append(" and STUDENT_ID = "+bean.getStudentId());
			}
			if(bean.getName()!=null  && bean.getName().trim().length()>0  ){
				sql.append(" and NAME like '"+bean.getName()+"%' ");
			}
			if(bean.getPhysics()!=0){
				sql.append("and PHYSICS = "+bean.getPhysics());
			}//check 
			if(bean.getChemistry()!=0){
				sql.append("and CHEMISTRY ="+bean.getChemistry());
			}//check
			if(bean.getMaths()!=0){
				sql.append("and MATHS ="+bean.getChemistry());
			}//check for zero
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
					MarksheetBean getBean = new MarksheetBean();
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
	 * Finds the Merit list
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return Marksheet List
	 * @throws ApplicationException
	 */
	public List<MarksheetBean> getMeritList(int pageNo,int pageSize) throws ApplicationException{
		log.debug("model get Merit List start");
		List<MarksheetBean>list = new ArrayList<MarksheetBean>();
		StringBuffer sql = new StringBuffer("SELECT `ID`,`ROLL_NO`, `NAME`, `PHYSICS`, `CHEMISTRY`, `MATHS` , (PHYSICS + CHEMISTRY + MATHS) as total"
				+ " from `ST_MARKSHEET` "
				+ "where PHYSICS >= 35 and CHEMISTRY >= 35 and MATHS >= 35 "
				+ "order by total desc ");
		if(pageSize>0){
			pageNo = (pageNo - 1)*pageSize;
			sql.append(" limit "+pageNo+" , "+pageSize);
		}
		Connection conn = null;
		try{
			conn = JdbcDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				MarksheetBean bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
                bean.setRollNo(rs.getString(2));
                bean.setName(rs.getString(3));
                bean.setPhysics(rs.getInt(4));
                bean.setChemistry(rs.getInt(5));
                bean.setMaths(rs.getInt(6));
                list.add(bean);	
			}
			ps.close();
			rs.close();
		}catch(Exception e){
			log.error("database exception ..",e);
			throw new ApplicationException("Exception in getMeritList Marksheet Model");
		}finally{
			JdbcDataSource.closeConnection(conn);
		}
		log.debug("model getmeritlist end");
		return list;
	}
	/**
	 * 
	 * populates MarksheetBean with Result Set
	 * 
	 * @param rs
	 * @return MarksheetBean
	 * @throws ApplicationException
	 */
	private static MarksheetBean setToBean(ResultSet rs) throws ApplicationException{
		log.debug("model setToBean start");
		MarksheetBean bean = new MarksheetBean();
		try{
		bean.setId(rs.getLong(1));
		bean.setRollNo(rs.getString(2));
		bean.setStudentId(rs.getLong(3));
		bean.setName(rs.getString(4));
		bean.setPhysics(rs.getInt(5));
		bean.setChemistry(rs.getInt(6));
		bean.setMaths(rs.getInt(7));
		bean.setCreatedBy(rs.getString(8));
		bean.setModifiedBy(rs.getString(9));
		bean.setCreatedDatetime(rs.getTimestamp(10));
		bean.setModifiedDatetime(rs.getTimestamp(11));
	}catch(Exception e){
		log.error("database exception ..",e);
		throw new ApplicationException("ApplicationException in setToBean RoleModel");
	}
		log.debug("model setToBean ended");
		return bean;
		}
	public List<MarksheetBean> list() throws ApplicationException {
		return list(0,0);
	}
}

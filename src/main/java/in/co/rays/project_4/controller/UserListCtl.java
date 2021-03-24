package in.co.rays.project_4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletSecurityElement;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.BaseBean;
import in.co.rays.project_4.bean.RoleBean;
import in.co.rays.project_4.bean.UserBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.model.RoleModel;
import in.co.rays.project_4.model.UserModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;

/**
 * 
 * User List functionality Controller. Performs operation for list, search and
 * delete operations of User
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
@ WebServlet("/ctl/UserListCtl")
public class UserListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	
	private static Logger log = Logger.getLogger(UserListCtl.class);
       
     
    /**
	 * Loads list and other data required to display at HTML form
	 * 
	 * @param request(HttpServletRequest)
	 */
    @Override
    protected void preload(HttpServletRequest request) {
    	log.debug("UserListCtl Method preload start");
    	RoleModel model = new RoleModel();
    	try {
			List<RoleBean> list = model.list();
			request.setAttribute("roleList", list);
		} catch (ApplicationException e) {
			log.error(e);
		}
    	log.debug("UserListCtl Method preload start");
    }
    
    
    /**
	 * Populates UserBean object from request parameters
	 * 
	 * @param request(HttpServletRequest)
	 * @return bean(UserBean)
	 */
    @Override
	protected BaseBean populateBean(HttpServletRequest request){
		log.debug("UserListCtl Method preload start");
    	UserBean userbean = new UserBean();
		userbean.setRoleId(DataUtility.getInt(request.getParameter("role")));
		userbean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		userbean.setLogin(DataUtility.getString(request.getParameter("login")));
		log.debug("UserListCtl Method preload start");
		return userbean;
	}
    
    
    /**
	 * Contains display logic
	 * 
	 * @param request(HttpServletRequest)
	 * @param response(HttpServletResponse)
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("UserListCtl Method doGet start");
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		UserModel model = new UserModel();
		List<UserBean>list = new ArrayList<UserBean>();
		List<UserBean>next = new ArrayList<UserBean>();
		int pageNo = 1;
		try {
			list  = model.list( pageNo, pageSize);
			next = model.list( pageNo+1, pageSize);
			if(list==null||list.size()==0){
				ServletUtility.setErrorMessage("no records found", request);
			}
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
		}
		if(next==null||next.size()==0){
			request.setAttribute("nextlistsize", "0");
		}else{
			request.setAttribute("nextlistsize", next.size());
		}
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.setList(list, request);
		ServletUtility.forward(ORSView.USER_LIST_VIEW, request, response);		
		log.debug("UserListCtl Method doGet end");
		return;
	}

	/**
	 * Contains Submit logics
	 * 
	 * @param request(HttpServletRequest)
	 * @param response(HttpServletResponse)
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("UserListCtl Method doPost start");
		String op = DataUtility.getString(request.getParameter("operation"));
		if(op.equals(OP_RESET)||op.equals(OP_BACK)){
			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			log.debug("UserListCtl Method doPost end");
			return;
		}
		UserModel model = new UserModel();
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		UserBean bean = new UserBean();
		List<UserBean>list = new ArrayList<UserBean>();
		List<UserBean>next = new ArrayList<UserBean>();
		bean = (UserBean)populateBean(request);
		try {
		if(op.equals(OP_NEW)){
			ServletUtility.redirect(ORSView.USER_CTL, request, response);
			log.debug("UserListCtl Method doPost end");
			return;
		}
		else if(op.equals(OP_SEARCH)){
			pageNo=1;
		} else if (op.equals(OP_NEXT)){
			pageNo++;
		} else if (op.equals(OP_PREVIOUS)){
			pageNo--;
		}
		else if(op.equals(OP_DELETE)){
			String ids[] = request.getParameterValues("ids");
			UserBean dBean = new UserBean();
			if(ids!=null&&ids.length>0){
				int i = 0;
				while(i<ids.length){
				dBean.setId(Integer.parseInt((ids[i])));
				model.delete(dBean);
				i++;
				}	
				ServletUtility.setSuccessMessage("Records deleted successfully", request);
			}if(ids==null||ids.length==0){
				ServletUtility.setErrorMessage("No Record Selected ", request);
			}
			
		}
	
		list  = model.search(bean, pageNo, pageSize);
		next = model.search(bean, pageNo+1, pageSize);
		if(list == null || list.size() == 0 ){
			ServletUtility.setErrorMessage("No record found", request);
		}
		
		if(next==null||next.size()==0){
			request.setAttribute("nextlistsize", "0");
			
			
		}else{
			request.setAttribute("nextlistsize", next.size());
		}
		request.setAttribute("firstName", bean.getFirstName());
		request.setAttribute("login",bean.getLogin());
		request.setAttribute("role", bean.getRoleId());
		ServletUtility.setBean(bean, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.setList(list, request);
		ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.debug(e);
			ServletUtility.handleException(e, request, response);
		}
		log.debug("UserListCtl Method doPost end");
		return;
	}
	
	
	/** 
	 * 
	 * Returns the view page of UserListCtl
     * 
     * @return UserListView.jsp:View page of UserListCtl
	 * 
	 */
	@Override
	protected String getView() {
		return ORSView.USER_LIST_VIEW;
	}

}

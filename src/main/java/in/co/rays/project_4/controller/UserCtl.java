package in.co.rays.project_4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.loading.PrivateClassLoader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.BaseBean;
import in.co.rays.project_4.bean.RoleBean;
import in.co.rays.project_4.bean.UserBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.RoleModel;
import in.co.rays.project_4.model.UserModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.DataValidator;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;


/**
 * 
 * User functionality Controller. Performs operation for add, update and get User
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
@WebServlet("/ctl/UserCtl")
public class UserCtl extends BaseCtl {
		private static final long serialVersionUID = 1L;
    
		private static Logger log = Logger.getLogger(UserCtl.class);
    
    public UserCtl() {
        super();
        // TODO Auto-generated constructor stub
    }
 
    /**
	 * Loads list and other data required to display at HTML form
	 * 
	 * @param request(HttpServletRequest)
	 */
    @Override
	protected void preload(HttpServletRequest request){
    	log.debug("UserCtl Method preload start");
		RoleModel model = new RoleModel(); 
		List<RoleBean> list = new ArrayList<RoleBean>();
		try {
			list = model.list();
			request.setAttribute("list", list);
		} catch (ApplicationException e) {
			log.debug(e);
		}	
		log.debug("UserCtl Method preload end");
	}
    
    
    /**
	 * 
	 * validates input data entered by user 
	 * 
	 * @param request(HttpServletRequest)
	 * @return pass(boolean)
	 *  
	 */
    @Override
	protected boolean validate(HttpServletRequest request){
		log.debug("UserCtl Method validate start");
    	boolean pass = true;
		if(DataValidator.isNull(request.getParameter("firstName"))){
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass=false;
		}else if(!DataValidator.isFirstName(request.getParameter("firstName"))){
			request.setAttribute("firstName", PropertyReader.getValue("error.name", "First Name"));
			pass=false;	
		}
		/*if(!DataValidator.isLength(request.getParameter("firstName"),3,48)){
			String[] params = {"First Name","3","48"};
			request.setAttribute("firstName", PropertyReader.getValue("error.length",params ));
			pass=false;
		}*/
		if(DataValidator.isNull(request.getParameter("lastName"))){
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass=false;
		}else if(!DataValidator.isLastName(request.getParameter("lastName"))){
			request.setAttribute("lastName", PropertyReader.getValue("error.name", "Last Name"));
			pass=false;	
		}
		/*if(!DataValidator.isLength(request.getParameter("firstName"),3,48)){
			String[] params = {"First Name","3","48"};
			request.setAttribute("firstName", PropertyReader.getValue("error.length",params ));
			pass=false;
		}*/
		if(DataValidator.isNull(request.getParameter("login"))){
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login"));
			pass=false;
		} else if(!DataValidator.isEmail(request.getParameter("login"))){
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("mobileNo"))){
				request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No"));
				pass=false;
		}else if(!DataValidator.isMobileNo(request.getParameter("mobileNo"))){
			request.setAttribute("mobileNo", PropertyReader.getValue("error.mobileNo", "Mobile No"));
			pass=false;
		}
		if(DataValidator.isNull(request.getParameter("password"))){
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass=false;
		}else if(!DataValidator.isLength(request.getParameter("password"),8,18)){
			String[] params = {"Password","8","18"};
			request.setAttribute("password", PropertyReader.getValue("error.length",params ));
			pass=false;
		}
		else if(!DataValidator.isPassword(request.getParameter("password"))){
			request.setAttribute("password", PropertyReader.getValue("error.password", "Password"));
			pass=false;
		}
		if(DataUtility.getLong(request.getParameter("id"))==0){
			if(DataValidator.isNull(request.getParameter("confirmPassword"))){
				request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
				pass=false;
			}else if(!DataUtility.getString(request.getParameter("password")).equals(DataUtility.getString(request.getParameter("confirmPassword")))){
				//Passwords Matching
				request.setAttribute("confirmPassword", "Passwords do not match");
				pass = false;
			}
		}
		if(DataValidator.isNull(request.getParameter("gender"))){
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("dob"))){
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass=false;
		}//dob validation
		else if(!DataValidator.isDate(request.getParameter("dob"))){
			request.setAttribute("dob", PropertyReader.getValue("error.invalid", "Date Of Birth"));
			pass=false;
		}
		if(DataValidator.isNull(request.getParameter("roleId"))){
			request.setAttribute("roleId", PropertyReader.getValue("error.require", "Role"));
			pass  = false;
		}
		log.debug("UserCtl Method validate end");
		return pass;
	}
	
    /**
	 * Populates UserBean object from request parameters
	 * 
	 * @param request(HttpServletRequest)
	 * @return bean(UserBean)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request){
		log.debug("UserCtl Method populateBean start");
		UserBean bean = new UserBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRoleId(DataUtility.getLong(request.getParameter("roleId")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		populateDTO(bean, request);
		log.debug("UserCtl Method polulateBean end");
		return bean;
		
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
		log.debug("UserCtl Method doGet start");
		long id =  DataUtility.getLong(request.getParameter("id"));
		UserBean bean = new UserBean();
		if(id>0){
			UserModel model = new UserModel();
			try {
				bean = model.findByPk(id);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			}
			ServletUtility.setBean(bean, request);
		}
		
		ServletUtility.forward(getView(), request, response);
		log.debug("UserCtl Method doPost end");
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
		log.debug("UserCtl Method doPost start");
		String op = DataUtility.getString(request.getParameter("operation"));
		UserBean bean = new UserBean();
		UserModel model= new UserModel();
		 if(op.equals(OP_UPDATE)){
			 bean = (UserBean)populateBean(request);
			 try {
				model.update(bean);
				ServletUtility.setSuccessMessage("Record Updated Successfully", request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				log.equals(e);
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				ServletUtility.setErrorMessage("This Roll no already exists", request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			} 
		 }
		else if(op.equals(OP_SAVE)){
			bean = (UserBean)populateBean(request);
			try {
			     model.add(bean);
			     ServletUtility.setBean(bean, request);
			     ServletUtility.setSuccessMessage("Record Added Successfully", request);
				ServletUtility.forward(getView(), request, response);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("This Login ID already exists.", request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			}
		}
		else if(op.equals(OP_CANCEL)){
			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
		}else if(op.equals(OP_RESET)){
			ServletUtility.redirect(ORSView.USER_CTL, request, response);
		}
		 log.debug("UserCtl Method doPost start");
		return;
	}
	
	/** 
	 * 
	 * Returns the view page of UserCtl
     * 
     * @return UserView.jsp:View page of UserCtl
	 * 
	 */
	@Override
	protected String getView() {
		return ORSView.USER_VIEW;
	}

}

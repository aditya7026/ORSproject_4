package in.co.rays.project_4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.mysql.cj.x.protobuf.MysqlxCrud.FindOrBuilder;

import in.co.rays.project_4.bean.BaseBean;
import in.co.rays.project_4.bean.RoleBean;
import in.co.rays.project_4.bean.UserBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.UserModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.DataValidator;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;

/**
 * 
 * My Profile functionality Controller. Performs operation for update your
 * Profile
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
@WebServlet("/ctl/MyProfileCtl")
public class MyProfileCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	
	public static final String OP_CHANGE_MY_PASSWORD = "Change Password";
   
	private static Logger log = Logger.getLogger(MyProfileCtl.class);
    public MyProfileCtl() {
        super();
        // TODO Auto-generated constructor stub
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
    	log.debug("MyprofileCtl Method validate start ");
		boolean pass=true;
		if(DataValidator.isNull(request.getParameter("firstName"))){
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass=false;
		}else if(!DataValidator.isFirstName(request.getParameter("firstName"))){
			request.setAttribute("firstName", PropertyReader.getValue("error.name", "First Name"));
			pass=false;
		}
		if(DataValidator.isNull(request.getParameter("lastName"))){
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass=false;
		}else if(!DataValidator.isLastName(request.getParameter("lastName"))){
			request.setAttribute("lastName", PropertyReader.getValue("error.name", "Last Name"));
			pass=false;
		}
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
		if(DataValidator.isNull(request.getParameter("gender"))){
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("dob"))){
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass=false;
		}
		log.debug("MyprofileCtl Method validate end ");
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
    	log.debug("MyprofileCtl Method populateBean start");
    	UserBean bean = new UserBean();
    	bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRoleId(DataUtility.getLong(request.getParameter("role")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		
		log.debug("MyprofileCtl Method populateBean end");
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
		log.debug("MyprofileCtl Method doGet start");
		HttpSession session = request.getSession();
		UserBean bean = (UserBean)session.getAttribute("user");
		
		ServletUtility.setBean(bean, request);
		ServletUtility.forward(getView(), request, response);
		log.debug("MyprofileCtl Method doGet End");
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
		log.debug("MyprofileCtl Method doPost start");
		String op = DataUtility.getString(request.getParameter("operation"));
		UserModel model = new UserModel();
		if(op.equals(OP_CHANGE_MY_PASSWORD)){
			
			ServletUtility.redirect(ORSView.CHANGE_PASSWORD_CTL, request, response);
			return;
		}
		else if(op.equals(OP_SAVE)){
			
			try {
				UserBean bean = (UserBean)populateBean(request);
				//update
				model.update(bean);
				//session update
				HttpSession session = request.getSession(false);
				session.setAttribute("user", bean);
				ServletUtility.setSuccessMessage("Record updated", request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				e.printStackTrace();
			} catch (DuplicateRecordException e) {
				request.setAttribute("Login", "login id exists");
				//ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			} 
			
		}
		log.debug("MyprofileCtl Method doPost end");
		return;
	}
	
	
	/**
     * Returns the view page of MyProfileCtl
     * 
     * @return MyProfileView.jsp:View page of MyProfileCtl
     */
	@Override
	protected String getView() {
		return ORSView.MY_PROFILE_VIEW;
	}

}

package in.co.rays.project_4.controller;

import java.io.IOException;
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
import in.co.rays.project_4.model.UserModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.DataValidator;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;

/**
 * 
 * User registration functionality Controller. Performs operation for User
 * Registration
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
@WebServlet("/UserRegistrationCtl")
public class UserRegistrationCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	public static final String OP_SIGN_UP = "SignUp";

	private static Logger log = Logger.getLogger(UserRegistrationCtl.class);

	/**
	 * 
	 * validates input data entered by user
	 * 
	 * @param request(HttpServletRequest)
	 * @return pass(boolean)
	 * 
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("UserRegistration Method validate start");
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		} // else
			// if(!DataValidator.isFirstName(request.getParameter("firstName"))){
		/*
		 * String[] params = null ; params[0] = "First Name";
		 * if(DataValidator.hasNum(request.getParameter("firstName"))){
		 * params[1] = "Numbers "; }
		 * if(DataValidator.hasSpecialChar(request.getParameter("firstName"))){
		 * params[2] = "Symbols "; }
		 * if(DataValidator.hasWhiteSpace(request.getParameter("firstName"))){
		 * params[3] = "Whitespace "; }
		 */
		// request.setAttribute("firstName",
		// PropertyReader.getValue("error.name", "First Name"));
		// pass=false;

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} else if (!DataValidator.isLastName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.name", "Last Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login"));
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.mobileNo", "Mobile No"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		} else if(!DataValidator.isLength(request.getParameter("password"),8,18)){
			String[] params = {"Password","8","18"};
			request.setAttribute("password", PropertyReader.getValue("error.length",params ));
			pass=false;
		} else if(!DataValidator.isPassword(request.getParameter("password"))){
			request.setAttribute("password", PropertyReader.getValue("error.password", "Password"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		} else if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))) {
			//Passwords matching
			request.setAttribute("confirmPassword", "Passwords do not match");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		}else if(!DataValidator.isDate(request.getParameter("dob"))){
			request.setAttribute("dob", PropertyReader.getValue("error.invalid", "Date Of Birth"));
			pass=false;
		}
		
		log.debug("UserRegistrationCtl method validate end");
		return pass;
	}

	/**
	 * Populates UserBean object from request parameters
	 * 
	 * @param request(HttpServletRequest)
	 * @return bean(UserBean)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("UserRegistrationCtl Method populateBean start");
		UserBean userbean = new UserBean();
		userbean.setId(DataUtility.getLong(request.getParameter("id")));
		userbean.setRoleId(RoleBean.STUDENT);
		userbean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		userbean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		userbean.setLogin(DataUtility.getString(request.getParameter("login")));
		userbean.setPassword(DataUtility.getString(request.getParameter("password")));
		userbean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
		userbean.setGender(DataUtility.getString(request.getParameter("gender")));
		userbean.setDob(DataUtility.getDate(request.getParameter("dob")));
		userbean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		populateDTO(userbean, request);
		log.debug("UserRegistrationCtl Method populateBean end");
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("UserRegistration Method doGet start ");
		ServletUtility.forward(getView(), request, response);
		log.debug("UserRegistration Method doGet end");
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("UserRegistration Method doPost start");
		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println(op);
		if (OP_RESET.equals(op)) {
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			log.debug("UserRegistrationCtl Method doPost end");
			return;
		}

		UserModel model = new UserModel();
		UserBean bean = new UserBean();

		if (op.equals(OP_SIGN_UP)) {
			bean = (UserBean) populateBean(request);

			try {
				model.registerUser(bean);
				ServletUtility.setSuccessMessage("Registration Successfull Please login.", request);
				//ServletUtility.redirect(ORSView.LOGIN_CTL, request, response);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				log.debug(e);
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("This Login ID already exists.", request);
				ServletUtility.forward(getView(), request, response);
			}
		}

		log.debug("UserRegistrationCtl Method doPost end");
		return;
	}

	/**
	 * 
	 * Returns the view page of UserRegistrationCtl
	 * 
	 * @return UserRegistrationView.jsp:View page of UserRegistrationCtl
	 * 
	 */
	@Override
	protected String getView() {
		return ORSView.USER_REGISTRATION_VIEW;
	}

}

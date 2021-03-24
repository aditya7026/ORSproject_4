package in.co.rays.project_4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.BaseBean;
import in.co.rays.project_4.bean.UserBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.RecordNotFoundException;
import in.co.rays.project_4.model.UserModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.DataValidator;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;

/**
 * 
 * Forget Password functionality Controller. Performs operation for Forget
 * Password
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
@ WebServlet("/ForgetPasswordCtl")
public class ForgetPasswordCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	public static final String OP_GO = "Go";
    
    public ForgetPasswordCtl() {
        super();
        
    }
    
    private static Logger log = Logger.getLogger(ForgetPasswordCtl.class);
    
    
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
		log.debug("ForgetPasswordCtl Method validate start");
		boolean pass = true;
		String login = request.getParameter("login");
		if(DataValidator.isNull(login)){
			request.setAttribute("login", PropertyReader.getValue("error.require","Email ID"));
			pass = false;
		}
		else if(!DataValidator.isEmail(login)){
			request.setAttribute("login", PropertyReader.getValue("error.valid","Email ID"));
			pass = false;
		}
		log.debug("ForgetPasswordCtl Method validate end");
		return pass;
	}
	
	
	/**
	 * Populates UserBean object from request parameters
	 * 
	 * @param request(HttpServletRequest)
	 * @return bean(UserBean)
	 */
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("ForgetPasswordCtl Method populateBean start");
		UserBean bean = new UserBean();
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		log.debug("ForgetPasswordCtl Method populateBean end");
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
		log.debug("ForgetPasswordCtl Method doGet start");
		ServletUtility.forward(ORSView.FORGET_PASSWORD_VIEW, request, response);
		log.debug("ForgetPasswordCtl Method doGet end");
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
		log.debug("ForgetPasswordCtl Method doPost start");
		String op = DataUtility.getString(request.getParameter("operation"));
		UserModel model = new UserModel();
		if(OP_RESET.equals(op)){
		ServletUtility.redirect(ORSView.FORGET_PASSWORD_CTL, request, response);
		log.debug("ForgetPasswordCtl Method doPost end");
		return;
		}
		if(OP_GO.equals(op)){
			try {
				
				model.forgetPassword(((UserBean)populateBean(request)).getLogin());
				ServletUtility.setSuccessMessage("Password has been sent to your email id", request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			} catch (RecordNotFoundException e) {
				ServletUtility.setBean((UserBean)populateBean(request), request);
				ServletUtility.setErrorMessage("This Email ID is not registered", request);
				ServletUtility.forward(getView(), request, response);
			}
		}
		log.debug("ForgetPasswordCtl Method doPost end");
		return;
	}
	
	
	/**
	 * Returns the VIEW page of ForgrtPasswordCtl
	 * 
	 * @return ForgetPasswordView.jsp: View page of ForgetPasswordCtl
	 */
	@Override
	protected String getView() {
		return ORSView.FORGET_PASSWORD_VIEW;
	}

}

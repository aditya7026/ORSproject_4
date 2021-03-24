package in.co.rays.project_4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.BaseBean;
import in.co.rays.project_4.bean.UserBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.UserModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.DataValidator;
import in.co.rays.project_4.util.EmailUtility;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;

/**
 * 
 * Provides password change functionality for user
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
@WebServlet("/ctl/ChangePasswordCtl")
public class ChangePasswordCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	
       /**
     * declaration and definition of OP_PROFILE
     */
    public static final String OP_PROFILE = "My Profile";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePasswordCtl() {
        super();
    }

	private static Logger log = Logger.getLogger(ChangePasswordCtl.class);
    
    
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
    	log.debug("ChangePasswordCtl method validate start");
    	boolean pass= true;
		if(OP_PROFILE.equals(request.getParameter("operation"))){
			log.debug("ChangePasswordCtl method validate end");
			return true;
		}
		if(DataValidator.isNull(request.getParameter("oldPass"))){
			request.setAttribute("oldPass", PropertyReader.getValue("error.require", "Old Password"));
			pass=false;
		}
		if(DataValidator.isNull(request.getParameter("newPass"))){
			request.setAttribute("newPass", PropertyReader.getValue("error.require", "New Password"));
			pass=false;
		} else if(!DataValidator.isLength(request.getParameter("newPass"),8,18)){
			String[] params = {"Password","8","18"};
			request.setAttribute("newPass", PropertyReader.getValue("error.length",params ));
			pass=false;
		} else if(!DataValidator.isPassword(request.getParameter("newPass"))){
			request.setAttribute("newPass", PropertyReader.getValue("error.password", "Password"));
			pass=false;
		}
		if(DataValidator.isNull(request.getParameter("conPass"))){
			request.setAttribute("conPass", PropertyReader.getValue("error.require", "Confirm Password"));
			pass=false;
		}
		if(!request.getParameter("newPass").equals(request.getParameter("conPass"))){
			//Passwords Matching
			request.setAttribute("conPass", "Passwords do not match");
			pass=false;
		}
		log.debug("ChangePasswordCtl method validate end");
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
		log.debug("ChangePasswordCtl method populateBean start");
    	UserBean bean = new UserBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setPassword(DataUtility.getStringData(request.getParameter("newPass")));
		bean.setConfirmPassword(DataUtility.getStringData(request.getParameter("conPass")));
		
    	log.debug("ChangePasswordCtl method populateBean end");
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
		log.debug("ChangePasswordCtl method doGet Start");
		HttpSession session = request.getSession();
		UserBean bean = (UserBean)session.getAttribute("user");
		ServletUtility.setBean(bean, request);
		ServletUtility.forward(getView(), request, response); 
		log.debug("ChangePasswordCtl method doGet End");
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
		log.debug("ChangePasswordCtl method doPost start");
		String op = DataUtility.getString(request.getParameter("operation"));
		if(op.equals(OP_PROFILE)){
			ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);
			log.debug("ChangePasswordCtl method doPost End");
			return;
		}
		if(op.equals(OP_SAVE)){
			UserModel model = new UserModel();
			UserBean bean = (UserBean)populateBean(request);
			String oldPass = DataUtility.getStringData(request.getParameter("oldPass"));
			try {
				if(model.changePassword(bean.getId(), oldPass, bean.getPassword())){
					HttpSession session = request.getSession(false);
					if(session!=null){
					 UserBean nBean =  (UserBean)session.getAttribute("user");
					 nBean.setPassword(bean.getPassword());
					 session.setAttribute("user", nBean);
					}
					ServletUtility.setSuccessMessage("Password Was changed successfully", request);
					ServletUtility.forward(getView(), request, response);
				}else{
					ServletUtility.setErrorMessage("Old Password incorrect", request);;
					ServletUtility.forward(getView(), request, response);
				}
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			}
			}
		
		log.debug("ChangePasswordCtl method doPost End");
		return;
		
	}

	/**
	 * Returns Change Password View
	 * 
	 * @return ChangePasswordView.jsp: VIEW page of ChangePasswordCtl
	 */
	@Override
	protected String getView() {
		return ORSView.CHANGE_PASSWORD_VIEW;
	}

}

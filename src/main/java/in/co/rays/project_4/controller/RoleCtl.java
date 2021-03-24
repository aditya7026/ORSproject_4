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
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.RoleModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.DataValidator;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;


/**
 * 
 * Role functionality Controller. Performs operation for add, update and get Role
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
@WebServlet("/ctl/RoleCtl")
public class RoleCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	
   private static Logger log = Logger.getLogger(RoleCtl.class);
	
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
    	log.debug("RoleCtl Method validate start ");
    	boolean pass = true;
    	if(DataValidator.isNull(request.getParameter("roleName"))){
    		request.setAttribute("roleName", PropertyReader.getValue("error.require", "Role Name"));
    		pass = false;
    	}
    	if(DataValidator.isNull(request.getParameter("desc"))){
    		request.setAttribute("desc", PropertyReader.getValue("error.require","Description"));
    		pass = false;
    	}
    	log.debug("RoleCtl Method validate end ");
		return pass;
	}
    
    
    /**
	 * Populates RoleBean object from request parameters
	 * 
	 * @param request(HttpServletRequest)
	 * @return bean(RoleBean)
	 */
    @Override
    protected BaseBean populateBean(HttpServletRequest request){
    	log.debug("RoleCtl Method polulateBean start ");
    	RoleBean bean = new RoleBean();
    	bean.setId(DataUtility.getLong(request.getParameter("id")));
    	bean.setName(DataUtility.getStringData(request.getParameter("roleName")));
    	bean.setDescription(DataUtility.getStringData(request.getParameter("desc")));
    	populateDTO(bean, request);
    	log.debug("RoleCtl Method populateBean end ");
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
		log.debug("RoleCtl Method doGet start ");
		long i = DataUtility.getLong(request.getParameter("id"));
		
		if(i>0){
		RoleModel model = new RoleModel();
		RoleBean bean  = new RoleBean();
		try {
			bean = model.findByPk(i);
		} catch (ApplicationException e) {
			log.error(e);
		}
		ServletUtility.setBean(bean, request);
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("RoleCtl Method doGet end ");
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
		log.debug("RoleCtl Method doPost start ");
		String op = DataUtility.getString(request.getParameter("operation"));
		RoleModel model = new RoleModel();
		RoleBean bean = new RoleBean();
		if(op.equals(OP_SAVE)){
			try {
				bean = (RoleBean) populateBean(request);
				 model.add(bean);
				
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Data Saved Sucessfully", request);
					ServletUtility.forward(getView(), request, response);
				
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				ServletUtility.setErrorMessage("this Role Name already exists", request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			}
		}else if(op.equals(OP_UPDATE)){
			try {
				bean = (RoleBean) populateBean(request);
				model.update(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data Updated Sucessfully", request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				ServletUtility.setErrorMessage("this Role Name already exists", request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			}
		}else if(op.equals(OP_CANCEL)){
			ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
		}else if(op.equals(OP_RESET)){
			
			ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
		}
		log.debug("RoleCtl Method doPost end");
		return;
	}

	/** 
	 * 
	 * Returns the view page of RoleCtl
     * 
     * @return RoleView.jsp:View page of RoleCtl
	 * 
	 */
	@Override
	protected String getView() {
		return ORSView.ROLE_VIEW;
	}

}

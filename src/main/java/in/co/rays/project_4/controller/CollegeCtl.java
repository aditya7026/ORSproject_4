package in.co.rays.project_4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.BaseBean;
import in.co.rays.project_4.bean.CollegeBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.CollegeModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.DataValidator;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;

/**
 * 
 * College functionality Controller. Performs operation for add, update, delete
 * and get College
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
@ WebServlet("/ctl/CollegeCtl")
public class CollegeCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log = Logger.getLogger(CollegeCtl.class);
    
    public CollegeCtl() {
        super();
        
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
    	log.debug("CollegeCtl method validate start");
    	boolean pass = true;
    	if(DataValidator.isNull(request.getParameter("name"))){
    		request.setAttribute("name", PropertyReader.getValue("error.require", "College Name"));
    		pass=false;
    	}
    	if(DataValidator.isNull(request.getParameter("address"))){
    		request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
    		pass=false;
    	}
    	if(DataValidator.isNull(request.getParameter("state"))){
    		request.setAttribute("state", PropertyReader.getValue("error.require", "State"));
    		pass=false;
    	}
    	if(DataValidator.isNull(request.getParameter("city"))){
    		request.setAttribute("city", PropertyReader.getValue("error.require", "City"));
    		pass=false;
    	}
    	if(DataValidator.isNull(request.getParameter("phNo"))){
    		request.setAttribute("phNo", PropertyReader.getValue("error.require","Phone No"));
    		pass=false;
    	}

    	log.debug("CollegeCtl method validate end");
		return pass;
	}
    
    
    /**
	 * Populates CollegeBean object from request parameters
	 * 
	 * @param request(HttpServletRequest)
	 * @return bean(CollegeBean)
	 */
    @Override
    protected BaseBean populateBean(HttpServletRequest request){
    	log.debug("CollegeCtl method populateBean start");
		CollegeBean bean =  new CollegeBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setAddress(DataUtility.getString(request.getParameter("address")));
		bean.setState(DataUtility.getString(request.getParameter("state")));
		bean.setCity(DataUtility.getString(request.getParameter("city")));
		bean.setPhNo(DataUtility.getString(request.getParameter("phNo")));
		bean.setCreatedBy(DataUtility.getString(request.getParameter("createdBy")));
		bean.setModifiedBy(DataUtility.getString(request.getParameter("modifiedBy")));
		populateDTO(bean, request);
		log.debug("CollegeCtl method populateBean end");
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
		log.debug("CollegeCtl method doGet Start");	
		long pk = DataUtility.getLong(request.getParameter("id"));
			if(pk>0){
				CollegeBean bean = new CollegeBean();
				CollegeModel model = new CollegeModel();
				try {
					bean  = model.findByPK(pk);
				} catch (ApplicationException e) {
					log.error(e);
					ServletUtility.handleException(e, request, response);
				}	
				ServletUtility.setBean(bean, request);
			}
			ServletUtility.forward(getView(), request, response);
			log.debug("CollegeCtl method doGet End");
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
		log.debug("CollegeCtl method doPost start");
		String op =request.getParameter("operation");
		
		CollegeModel model = new CollegeModel();
		
		if(op.equals(OP_SAVE)){
			CollegeBean bean = (CollegeBean) populateBean(request);
			try {
				model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Record added Successfully", request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("This College name already exists", request);
				ServletUtility.forward(getView(), request, response);
			}
			
		}else if(op.equals(OP_UPDATE)){
			CollegeBean bean = (CollegeBean) populateBean(request);
			try {
				model.update(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Record Updated Successfully", request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				
			} catch (DuplicateRecordException e) {
				ServletUtility.setErrorMessage("This College name already exists", request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			}
			
		}else if(op.equals(OP_CANCEL)){
			ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
		}else if(op.equals(OP_RESET)) {
			ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
		}
		log.debug("CollegeCtl method doPost End");
		return;
	}
	
	
	/** 
	 * 
	 * Returns the VIEW page of CollegeCtl
	 * 
	 * @return CollegeView.jsp: View page of CollegeCtl
	 */
	@Override
	protected String getView(){
		return ORSView.COLLEGE_VIEW;
	}

}

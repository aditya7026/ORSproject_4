package in.co.rays.project_4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.BaseBean;
import in.co.rays.project_4.bean.CourseBean;
import in.co.rays.project_4.bean.UserBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.CourseModel;
import in.co.rays.project_4.model.UserModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.DataValidator;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;

/**
 * Course functionality Controller. Performs operation for add, update, delete
 * and get Course
 *  
 * @author Aditya
 * @version 1.0
 * @Copyright (c) SunilOS
 *
 */
@WebServlet("/ctl/CourseCtl")
public class CourseCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CourseCtl() {
        // TODO Auto-generated constructor stub
    }
    

    private static Logger log = Logger.getLogger(CourseCtl.class);
    
    /**
	 * Populates CourseBean object from request parameters
	 * 
	 * @param request(HttpServletRequest)
	 * @return bean(CourseBean)
	 */
    @Override
    protected BaseBean populateBean(HttpServletRequest request){
    	log.debug("CourseCtl method populateBean end");
    	CourseBean bean = new CourseBean();
    	bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("courseName")));
		bean.setDuration(DataUtility.getString(request.getParameter("duration")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		populateDTO(bean, request);
		log.debug("CourseCtl method populateBean start");
		return bean;
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
    	log.debug("CourseCtl method validate end");
    	boolean pass = true;
    	
    	if(DataValidator.isNull(request.getParameter("courseName"))){
    		
    		request.setAttribute("courseName", PropertyReader.getValue("error.require", "Course Name"));
    		pass= false;
    	}
    	if(DataValidator.isNull(request.getParameter("description"))){
    		
    		request.setAttribute("description", PropertyReader.getValue("error.require","Description"));
    		pass = false;
    	}
    	if(DataValidator.isNull(request.getParameter("duration"))){
    		
    		request.setAttribute("duration", PropertyReader.getValue("error.require","Duration"));
    		pass = false;
    	}

    	log.debug("CourseCtl method validate start");
		return pass;
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
		
		log.debug("CourseCtl method doGet Start");
		long id =  DataUtility.getLong(request.getParameter("id"));
		CourseBean bean = new CourseBean();
		if(id>0){
			CourseModel model = new CourseModel();
			try {
				bean = model.findByPk(id);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			}
			ServletUtility.setBean(bean, request);
		}
		
		ServletUtility.forward(getView(), request, response);

		log.debug("CourseCtl method doGet End");
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
		log.debug("CourseCtl Method doPost Start");
		String op = DataUtility.getString(request.getParameter("operation"));
		 if(op.equals(OP_UPDATE)){
			 CourseBean bean = new CourseBean();
				CourseModel model= new CourseModel();
			 bean = (CourseBean)populateBean(request);
			 try {
				model.update(bean);
				ServletUtility.setSuccessMessage("Record Updated Successfully", request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				ServletUtility.setErrorMessage("This Course Name already exists", request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			} 
		 }
		else if(op.equals(OP_SAVE)){
			CourseBean bean = new CourseBean();
			CourseModel model= new CourseModel();
			bean = (CourseBean)populateBean(request);
			try {
			     model.add(bean);	
			     ServletUtility.setBean(bean, request);
			     ServletUtility.setSuccessMessage("Record Added Successfully", request);
				ServletUtility.forward(getView(), request, response);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("This Course Name already exists.", request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			}
		}
		else if(op.equals(OP_CANCEL)){
			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
		}else if(op.equals(OP_RESET)){
			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
		}
		
		log.debug("CourseCtl Method doPost end");
		return;
	}
	
	
	/**
	 * Returns the VIEW page of CourseCtl
	 * 
	 * @return CourseView.jsp:View page of CourseCtl
	 */
	@Override
	protected String getView() {
		return ORSView.COURSE_VIEW;
	}

}

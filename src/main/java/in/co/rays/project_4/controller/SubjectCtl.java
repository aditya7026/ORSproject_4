package in.co.rays.project_4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.BaseBean;
import in.co.rays.project_4.bean.CourseBean;
import in.co.rays.project_4.bean.SubjectBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.CourseModel;
import in.co.rays.project_4.model.SubjectModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.DataValidator;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;


/**
 * 
 * Subject functionality Controller. Performs operation for add, update, delete
 * and get Subject
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
@WebServlet("/ctl/SubjectCtl")
public class SubjectCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log = Logger.getLogger(SubjectCtl.class);
	
    public SubjectCtl() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    /**
	 * Loads list and other data required to display at HTML form
	 * 
	 * @param request(HttpServletRequest)
	 */
    @Override
    protected void preload(HttpServletRequest request) {
    	log.debug("SubjectCtl Method preload start");
    	CourseModel model = new CourseModel();
    	try {
			List<CourseBean> list = model.list();
			request.setAttribute("courseList", list);
		} catch (ApplicationException e) {
			log.error(e);
		}
    	log.debug("SubjectCtl Method preload End");
    }
    
    /**
	 * Populates StudentBean object from request parameters
	 * 
	 * @param request(HttpServletRequest)
	 * @return bean(StudentBean)
	 */
    @Override
    protected BaseBean populateBean(HttpServletRequest request){
    	log.debug("SubjectCtl Method polulateBean start");
    	SubjectBean bean = new SubjectBean();
    	bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("subjectName")));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		populateDTO(bean, request);
		log.debug("SubjectCtl Method polulateBean end");
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
    	log.debug("SubjectCtl Method validate start");
    	boolean pass = true;
    	
    	if(DataValidator.isNull(request.getParameter("subjectName"))){
    		
    		request.setAttribute("subjectName", PropertyReader.getValue("error.require", "Subject Name"));
    		pass= false;
    	}
    	if(DataValidator.isNull(request.getParameter("description"))){
    		
    		request.setAttribute("description", PropertyReader.getValue("error.require","Description"));
    		pass = false;
    	}
    	if(DataValidator.isNull(request.getParameter("courseId"))){
    		request.setAttribute("courseId", PropertyReader.getValue("error.require","Course"));
    		pass= false;
    	}
    	log.debug("SubjectCtl Method validate end");
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
		log.debug("SubjectCtl Method doGet Start");
		long id =  DataUtility.getLong(request.getParameter("id"));
		
		if(id>0){
			SubjectBean bean = new SubjectBean();
			SubjectModel model = new SubjectModel();
			try {
				bean = model.findByPk(id);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			}
			ServletUtility.setBean(bean, request);
		}
		
		ServletUtility.forward(getView(), request, response);
		log.debug("SubjectCtl Method doGet End");
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
		log.debug("SubjectCtl Method doPost Start");
		String op = DataUtility.getString(request.getParameter("operation"));
		 if(op.equals(OP_UPDATE)){
			 SubjectBean bean = new SubjectBean();
				SubjectModel model= new SubjectModel();
			 bean = (SubjectBean)populateBean(request);
			 try {
				model.update(bean);
				ServletUtility.setSuccessMessage("Record Updated Successfully", request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				ServletUtility.setErrorMessage("This Subject Name already exists", request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			} 
		 }
		else if(op.equals(OP_SAVE)){
			SubjectBean bean = new SubjectBean();
			SubjectModel model= new SubjectModel();
			bean = (SubjectBean)populateBean(request);
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
			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
		}else if(op.equals(OP_RESET)){
			ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
		}
		 log.debug("SubjectCtl Method doPost End");
		return;
	}
	
	/** 
	 * 
	 * Returns the view page of SubjectCtl
     * 
     * @return SubjectView.jsp:View page of SubjectCtl
	 * 
	 */
	@Override
	protected String getView() {
		return ORSView.SUBJECT_VIEW;
	}

}

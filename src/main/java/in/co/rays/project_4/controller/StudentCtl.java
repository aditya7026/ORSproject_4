package in.co.rays.project_4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.BaseBean;
import in.co.rays.project_4.bean.CollegeBean;
import in.co.rays.project_4.bean.StudentBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.CollegeModel;
import in.co.rays.project_4.model.StudentModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.DataValidator;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;


/**
 * 
 * Student functionality Controller. Performs operation for add, update, delete
 * and get Student
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
@WebServlet("/ctl/StudentCtl")
public class StudentCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
    
	private static Logger log = Logger.getLogger(StudentCtl.class);
	
    public StudentCtl() {
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
    	log.debug("StudentCtl Method preload start ");
    	CollegeModel model = new CollegeModel();
    	List<CollegeBean>list = new ArrayList<CollegeBean>();
    	try {
			list = model.list();
		} catch (ApplicationException e) {
			log.error(e);
		}
    	ServletUtility.setList(list, request);
    	log.debug("StudentCtl Method preload end ");
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
    	log.debug("StudentCtl Method validate start ");
    	boolean pass = true;
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
		if(DataValidator.isNull(request.getParameter("mobileNo"))){
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No"));
			pass=false;
		}else if(!DataValidator.isMobileNo(request.getParameter("mobileNo"))){
			request.setAttribute("mobileNo", PropertyReader.getValue("error.mobileNo", "Mobile No"));
			pass=false;
		}
		if(DataValidator.isNull(request.getParameter("dob"))){
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass=false;
		} else if(!DataValidator.isDate(request.getParameter("dob"))){
			request.setAttribute("dob", PropertyReader.getValue("error.invalid", "Date Of Birth"));
			pass=false;
		}
		if(DataValidator.isNull(request.getParameter("email"))){
			request.setAttribute("email", PropertyReader.getValue("error.require", "Email ID "));
			pass = false;
		}else if(!DataValidator.isEmail(request.getParameter("email"))){
			request.setAttribute("email", PropertyReader.getValue("error.invalid", "Email Id"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("collegeId"))){
			request.setAttribute("collegeId",PropertyReader.getValue("error.require", "College Name"));
		}
		log.debug("StudentCtl Method validate end ");
		return pass;
	}
    
    
    /**
	 * Populates StudentBean object from request parameters
	 * 
	 * @param request(HttpServletRequest)
	 * @return bean(StudentBean)
	 */
    @Override
    protected BaseBean populateBean(HttpServletRequest request){
    	log.debug("StudentCtl Method populateBean start ");
    	StudentBean bean = new StudentBean();
    	bean.setId(DataUtility.getLong(request.getParameter("id")));
    	bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
    	bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		populateDTO(bean, request);
		log.debug("StudentCtl Method polulateBean end ");
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
		log.debug("StudentCtl Method doGet start ");
		long id = DataUtility.getLong(request.getParameter("id"));
		if(id>0){
			StudentModel model = new StudentModel();
			StudentBean bean = new StudentBean();
			try {
				bean = model.findByPk(id);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			}
			
			ServletUtility.setBean(bean, request);
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("StudentCtl Method doGet end ");
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
		log.debug("StudentCtl Method doPost start ");
		String op = DataUtility.getString(request.getParameter("operation"));
		StudentModel model = new StudentModel();
		StudentBean bean = new StudentBean();
		if(op.equals(OP_SAVE)){
			bean = (StudentBean) populateBean(request);
			try {
				bean = (StudentBean) populateBean(request);
				model.add(bean);
				ServletUtility.setSuccessMessage("Record Added Successfully", request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				request.setAttribute("email", "This Email ID already exists");
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			}
		}else if(op.equals(OP_UPDATE)){
			try {
				bean = (StudentBean) populateBean(request);
				model.update(bean);
				ServletUtility.setSuccessMessage("Record Updated Successfully", request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				request.setAttribute("email", "This Email ID already exists");
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			}
		}else if(op.equals(OP_CANCEL)){
			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
		}else if(op.equals(OP_RESET)){
			ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
		}
		log.debug("StudentCtl Method doPost end ");
		return;
	}
	
	/** 
	 * 
	 * Returns the view page of StudentCtl
     * 
     * @return StudentView.jsp:View page of StudentCtl
	 * 
	 */
	@Override
	protected String getView() {
		return ORSView.STUDENT_VIEW;
	}

}

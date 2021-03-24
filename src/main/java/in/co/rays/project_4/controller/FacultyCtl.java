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
import in.co.rays.project_4.bean.CourseBean;
import in.co.rays.project_4.bean.FacultyBean;
import in.co.rays.project_4.bean.StudentBean;
import in.co.rays.project_4.bean.SubjectBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.CollegeModel;
import in.co.rays.project_4.model.CourseModel;
import in.co.rays.project_4.model.FacultyModel;
import in.co.rays.project_4.model.StudentModel;
import in.co.rays.project_4.model.SubjectModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.DataValidator;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;


/**
 * 
 * Faculty functionality Controller. Performs operation for add, update and
 * get Faculty
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
@WebServlet("/ctl/FacultyCtl")
public class FacultyCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
    
    public FacultyCtl() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private static Logger log = Logger.getLogger(FacultyCtl.class);
    
    
    
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
    	log.debug("FacultyCtl Method validate start");
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
			pass= false;
		}
		if(DataValidator.isNull(request.getParameter("subjectId"))){
    		
    		request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject"));
    		pass= false;
    	}
    	
    	if(DataValidator.isNull(request.getParameter("courseId"))){
    		request.setAttribute("courseId", PropertyReader.getValue("error.require","Course"));
    		pass= false;
    	}
    	if(DataValidator.isNull(request.getParameter("gender"))){
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
    	if(DataValidator.isNull(request.getParameter("qualification"))){
    		request.setAttribute("qualification", PropertyReader.getValue("error.require", "Qualification"));
    		pass= false;
    	}
    	log.debug("FacultyCtl Method Validate End");
		return pass;
	}
    
    
    /**
	 * Populates FacultyBean object from request parameters
	 * 
	 * @param request(HttpServletRequest)
	 * @return bean(FacultyBean)
	 */
    @Override
    protected BaseBean populateBean(HttpServletRequest request){
    	log.debug("FacultyCtl Method populateBean Start");
    	FacultyBean bean = new FacultyBean();
    	bean.setId(DataUtility.getLong(request.getParameter("id")));
    	bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
    	bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
    	bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		bean.setEmailId(DataUtility.getString(request.getParameter("email")));
		bean.setQualification(DataUtility.getString(request.getParameter("qualification")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		populateDTO(bean, request);
		log.debug("FacultyCtl Method populateBean Start");
		return bean;
	}
    
    /**
	 * Loads list and other data required to display at HTML form
	 * 
	 * @param request(HttpServletRequest)
	 */
    @Override
    protected void preload(HttpServletRequest request){
    	log.debug("FacultyCtl Method preload start");
    	CollegeModel model = new CollegeModel();
    	CourseModel cmodel = new CourseModel();
    	SubjectModel smodel = new SubjectModel();
    	List<CollegeBean>list = new ArrayList<CollegeBean>();
    	List<CourseBean>clist = new ArrayList<CourseBean>();
    	List<SubjectBean>slist = new ArrayList<SubjectBean>();
    	try {
			list = model.list();
			clist = cmodel.list();
			slist = smodel.list();
		} catch (ApplicationException e) {
			log.error(e);
		}
    	request.setAttribute("collegeList", list);
    	request.setAttribute("courseList", clist);
    	request.setAttribute("subjectList", slist);
    	log.debug("FacultyCtl Method preload end");
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
		log.debug("FacultyCtl Method doGet start");
		long id = DataUtility.getLong(request.getParameter("id"));
		if(id>0){
			FacultyModel model = new FacultyModel();
			FacultyBean bean = new FacultyBean();
			try {
				bean = model.findByPk(id);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			}
			
			ServletUtility.setBean(bean, request);
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("FacultyCtl Method doGet Start");
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
		log.debug("FacultyCtl Method doPost start");
		String op = DataUtility.getString(request.getParameter("operation"));
		FacultyModel model = new FacultyModel();
		FacultyBean bean = new FacultyBean();
		if(op.equals(OP_SAVE)){
			bean = (FacultyBean) populateBean(request);
			try {
				bean = (FacultyBean) populateBean(request);
				model.add(bean);
				ServletUtility.setSuccessMessage("Record Added Successfully", request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				request.setAttribute("email", "This Email ID already exists");
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			}
		}else if(op.equals(OP_UPDATE)){
			try {
				bean = (FacultyBean) populateBean(request);
				model.update(bean);
				ServletUtility.setSuccessMessage("Record Updated Successfully", request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				request.setAttribute("email", "This Email ID already exists");
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			}
		}else if(op.equals(OP_CANCEL)){
			ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
		}else if(op.equals(OP_RESET)){
			ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
		}
		log.debug("FacultyCtl Method doPost end");
		return;
	}
	
	
	/**
	 * Returns the VIEW page of FacultyCtll
	 * 
	 * @return FacultyView.jsp: View page of FacultyCtl
	 */
	@Override
	protected String getView() {
		return ORSView.FACULTY_VIEW;
	}

}

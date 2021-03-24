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
import in.co.rays.project_4.bean.TimeTableBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.CourseModel;
import in.co.rays.project_4.model.SubjectModel;
import in.co.rays.project_4.model.TimeTableModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.DataValidator;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;


/**
 * 
 * TimeTable functionality Controller. Performs operation for add, update, delete
 * and get TimeTable
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
@WebServlet("/ctl/TimeTableCtl")
public class TimeTableCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
    
	private static Logger log = Logger.getLogger(TimeTableCtl.class);
	
    public TimeTableCtl() {
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
    	log.debug("TimeTableCtl Method preload start");
    	CourseModel cModel = new CourseModel();
    	SubjectModel sModel = new SubjectModel();
    	try {
			List<CourseBean> cList = cModel.list();
			List<SubjectBean> sList = sModel.list();
			request.setAttribute("courseList", cList);
			request.setAttribute("subjectList", sList);
		} catch (ApplicationException e) {
			log.error(e);
		}
    	log.debug("TimeTableCtl Method preload end");
    }
    
    
    /**
	 * Populates TimeTableBean object from request parameters
	 * 
	 * @param request(HttpServletRequest)
	 * @return bean(TimeTableBean)
	 */
    @Override
    protected BaseBean populateBean(HttpServletRequest request){
    	log.debug("TimeTableCtl Method poplulateBean start");
    	TimeTableBean bean = new TimeTableBean();
    	bean.setId(DataUtility.getLong(request.getParameter("id")));
    	//System.out.println(bean.getId());
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
		bean.setSemester(DataUtility.getString(request.getParameter("semester")));
		bean.setExamDate(DataUtility.getDate(request.getParameter("examDate")));
		bean.setTime(DataUtility.getString(request.getParameter("examTime")));
		populateDTO(bean, request);
		log.debug("TimeTableCtl Method poplulateBean end");
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
    	log.debug("TimeTableCtl Method validate start");
    	boolean pass = true;
    	
    	if(DataValidator.isNull(request.getParameter("subjectId"))){
    		
    		request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject"));
    		pass= false;
    	}
    	
    	if(DataValidator.isNull(request.getParameter("courseId"))){
    		request.setAttribute("courseId", PropertyReader.getValue("error.require","Course"));
    		pass= false;
    	}
    	
    	if(DataValidator.isNull(request.getParameter("examDate"))){
    		request.setAttribute("examDate", PropertyReader.getValue("error.require", "Exam Date"));
    		pass=false;
    	} else if(!DataValidator.isDate(request.getParameter("examDate"))){
			request.setAttribute("examDate", PropertyReader.getValue("error.invalid", "Exam Date"));
			pass=false;
		}
    	
    	if(DataValidator.isNull(request.getParameter("examTime"))){
    		request.setAttribute("examTime", PropertyReader.getValue("error.require", "Exam Time"));
    		pass = false;
    	}
    	
    	if(DataValidator.isNull(request.getParameter("semester"))){
    		request.setAttribute("semester", PropertyReader.getValue("error.require", "Semester"));
    		pass = false;
    	}
    	log.debug("TimeTableCtl Method validate end");
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
		log.debug("TimeTableCtl Method doGet start");
		long id =  DataUtility.getLong(request.getParameter("id"));
		
		if(id>0){
			TimeTableBean bean = new TimeTableBean();
			TimeTableModel model = new TimeTableModel();
			try {
				bean = model.findByPk(id);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			}
			ServletUtility.setBean(bean, request);
		}
		      
		ServletUtility.forward(getView(), request, response);
		log.debug("TimeTableCtl Method doGet end");
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
		log.debug("TimeTableCtl Method doPost start");
		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println(op);
		 if(op.equals(OP_UPDATE)){
			 System.out.println("update");
			 TimeTableBean bean = new TimeTableBean();
			 TimeTableModel model= new TimeTableModel();
			 bean = (TimeTableBean)populateBean(request);
			 try {
				model.update(bean);
				ServletUtility.setSuccessMessage("Record Updated Successfully", request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				ServletUtility.setErrorMessage("This Time Table already exists", request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			} 
		 }
		else if(op.equals(OP_SAVE)){
			TimeTableBean bean = new TimeTableBean();
			TimeTableModel model= new TimeTableModel();
			bean = (TimeTableBean)populateBean(request);
			try {
			     model.add(bean);	
			     ServletUtility.setBean(bean, request);
			     ServletUtility.setSuccessMessage("Record Added Successfully", request);
				ServletUtility.forward(getView(), request, response);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("This Time Table already exists.", request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			}
		}
		else if(op.equals(OP_CANCEL)){
			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
		}else if(op.equals(OP_RESET)){
			ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
		}
		 log.debug("TimeTableListCtl Method doPost End");
		return;
	}
	
	
	/** 
	 * 
	 * Returns the view page of TimeTableCtl
     * 
     * @return TimeTableView.jsp:View page of TimeTableCtl
	 * 
	 */
	@Override
	protected String getView() {
		return ORSView.TIMETABLE_VIEW;
	}

}

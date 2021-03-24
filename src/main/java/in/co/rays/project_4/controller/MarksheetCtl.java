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
import in.co.rays.project_4.bean.MarksheetBean;
import in.co.rays.project_4.bean.StudentBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.exception.RecordNotFoundException;
import in.co.rays.project_4.model.MarksheetModel;
import in.co.rays.project_4.model.StudentModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.DataValidator;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;


/**
 * 
 * Marksheet functionality Controller. Performs operation for add, update,
 * delete and get Marksheet
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
@WebServlet("/ctl/MarksheetCtl")
public class MarksheetCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
    
	private static Logger log = Logger.getLogger(MarksheetCtl.class);
	
    public MarksheetCtl() {
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
    	log.debug("MarksheetCtl Method preload start ");
   		StudentModel model = new StudentModel(); 
   		List<StudentBean> list = new ArrayList<StudentBean>();
   		try {
   			list = model.list();
   			request.setAttribute("list", list);
   		} catch (ApplicationException e) {
   			log.error(e);
   		}	
   		log.debug("MarksheetCtl Method preload end ");
   	}
    
    
    /**
	 * Populates MarksheetBean object from request parameters
	 * 
	 * @param request(HttpServletRequest)
	 * @return bean(MarksheetBean)
	 */
    @Override
    protected BaseBean populateBean(HttpServletRequest request){
    	log.debug("MarksheetCtl Method populateBean start ");
		MarksheetBean bean = new MarksheetBean();
    	bean.setId(DataUtility.getLong(request.getParameter("id")));
    	bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
    	bean.setStudentId(DataUtility.getLong(request.getParameter("studentId")));
    	bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));
    	bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
    	bean.setMaths(DataUtility.getInt(request.getParameter("maths")));
    	populateDTO(bean, request);
    	log.debug("MarksheetCtl Method populateBean end ");
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
    	log.debug("MarksheetCtl Method validate start ");
    	boolean pass = true;
    	if(DataValidator.isNull(request.getParameter("studentId"))){
    		request.setAttribute("studentId", PropertyReader.getValue("error.require", "Name"));
    		pass=false;
    	}
    	if(DataValidator.isNull(request.getParameter("rollNo"))){
    		request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll No"));
    		pass=false;
    	}else if(!DataValidator.isRollNo(request.getParameter("rollNo"))){
    		request.setAttribute("rollNo", PropertyReader.getValue("error.invalid", "Roll No"));
    		pass=false;
    	}
    	if(DataValidator.isNull(request.getParameter("physics"))){
    		request.setAttribute("physics", PropertyReader.getValue("error.require","Physics"));
    		pass = false;
    	}else if(!DataValidator.isMarks(request.getParameter("physics"))){
    		request.setAttribute("physics", PropertyReader.getValue("error.integer","Physics"));
    		pass = false;
    	}
    	if(DataValidator.isNull(request.getParameter("chemistry"))){
    		request.setAttribute("chemistry", PropertyReader.getValue("error.require","Chemistry"));
    		pass = false;
    	}else if(!DataValidator.isMarks(request.getParameter("chemistry"))){
    		request.setAttribute("chemistry", PropertyReader.getValue("error.integer","Chemistry"));
    		pass = false;
    	}
    	if(DataValidator.isNull(request.getParameter("maths"))){
    		request.setAttribute("maths", PropertyReader.getValue("error.require","Maths"));
    		pass = false;
    	}else if(!DataValidator.isMarks(request.getParameter("maths"))){
    		request.setAttribute("maths", PropertyReader.getValue("error.integer","Maths"));
    		pass = false;
    	}
    	log.debug("MarksheetCtl Method validate end ");
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
		log.debug("MarksheetCtl Method doGet start");
		long id= DataUtility.getLong(request.getParameter("id"));
		MarksheetBean bean = new MarksheetBean();
		if(id>0){
			MarksheetModel model = new MarksheetModel();	
			try {
				 bean =model.findByPk(id);	
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			}
			ServletUtility.setBean(bean, request);
		}
			ServletUtility.forward(getView(), request, response);
			log.debug("MarksheetCtl Method doGet end ");
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
		log.debug("MarksheetCtl Method doPost start ");
		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println(op);
		 if(op.equals(OP_UPDATE)){
			 MarksheetBean bean = new MarksheetBean();
				MarksheetModel model= new MarksheetModel();
			 bean = (MarksheetBean)populateBean(request);
			 try {
				model.update(bean);
				ServletUtility.setSuccessMessage("Record Updated Successfully", request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				ServletUtility.setErrorMessage("This Roll No already exists", request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			} 
		 }
		else if(op.equals(OP_SAVE)){
			MarksheetBean bean = new MarksheetBean();
			MarksheetModel model= new MarksheetModel();
			System.out.println("save start");
			bean = (MarksheetBean)populateBean(request);
			try {
			     model.add(bean);	
			     ServletUtility.setBean(bean, request);
			     ServletUtility.setSuccessMessage("Record Added Successfully", request);
				ServletUtility.forward(getView(), request, response);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("This Roll No already exists", request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			} 
		}
		else if(op.equals(OP_CANCEL)){
			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
		}else if(op.equals(OP_RESET)){
			ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
		}
		 log.debug("MarksheetCtl Method doPost end");
		return;
	}
	
	
	/**
     * Returns the VIEW page of MarksheetCtl
     * 
     * @return MarksheetView.jsp:View page of MarksheetCtl
     */
	@Override
	protected String getView() {
		return ORSView.MARKSHEET_VIEW;
	}

}

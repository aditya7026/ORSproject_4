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
import in.co.rays.project_4.bean.UserBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.model.CollegeModel;
import in.co.rays.project_4.model.StudentModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;


/**
 * 
 * Student List functionality Controller. Performs operation for list, search
 * and delete operations of Student
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
@WebServlet("/ctl/StudentListCtl")
public class StudentListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(SubjectListCtl.class);
	
    public StudentListCtl() {
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
    	log.debug("StudentListCtl Method preload start ");
    	List<CollegeBean>list = new ArrayList<CollegeBean>();
    	CollegeModel model = new CollegeModel();
    	try {
			list= model.list();
		} catch (ApplicationException e) {
			log.error(e);
		}
    	request.setAttribute("collegeList", list);
    	log.debug("StudentListCtl Method preload end ");
	}
    
    
    /**
	 * Populates StudentBean object from request parameters
	 * 
	 * @param request(HttpServletRequest)
	 * @return bean(StudentBean)
	 */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
    	log.debug("StudentListCtl Method polulateBean start ");
    	StudentBean bean = new StudentBean();
    	bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
    	bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
    	bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
    	log.debug("StudentListCtl Method polulateBean End");
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
		log.debug("StudentListCtl Method doGet start ");
		StudentModel model = new StudentModel();
		List<StudentBean>list = null;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		int pageNo = 1;
		
		try {
			list = model.list(pageNo, pageSize);
			List<StudentBean>next = model.list(pageNo+1, pageSize);
			if(list==null||list.size()==0){
				ServletUtility.setErrorMessage("No record found", request);
			}
			if(next.size()==0||next==null){
				request.setAttribute("nextlistsize", "0");
			}
			else{
				request.setAttribute("nextlistsize", next.size());
			}
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
		}
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
		log.debug("StudentListCtl Method doGet end ");
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
		log.debug("StudentListCtl Method doPost start ");
		String op = DataUtility.getString(request.getParameter("operation"));
		StudentModel model = new StudentModel();
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		try {
		if(op.equals(OP_NEXT)){
			pageNo++;
		}else if(op.equals(OP_PREVIOUS)){
			pageNo--;
		}else if(op.equals(OP_SEARCH)){
			pageNo = 1;
		}else if(op.equals(OP_DELETE)){
			String ids[] = request.getParameterValues("ids");
			StudentBean dBean = new StudentBean();
			if(ids!=null&&ids.length>0){
				int i = 0;
				while(i<ids.length){
				dBean.setId(Integer.parseInt((ids[i])));
				model.delete(dBean);
				i++;
				}	
				ServletUtility.setSuccessMessage("Records deleted successfully", request);
			}if(ids==null||ids.length==0){
				ServletUtility.setErrorMessage("No record Selected", request);
			}
		}
		else if(op.equals(OP_RESET)||op.equals(OP_BACK)){
			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
			log.debug("StudentListCtl Method doPost End ");
			return;
		}else if(op.equals(OP_NEW)){
			ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
			log.debug("StudentListCtl Method doPost End ");
			return;
		}
		StudentBean bean = (StudentBean) populateBean(request);
		
			List<StudentBean>list = model.search(bean, pageNo, pageSize);
			List<StudentBean>next = model.search(bean, pageNo+1, pageSize);
			if(list==null||list.size()==0){
				ServletUtility.setErrorMessage("No record found", request);
			}
			if(next.size()==0||next==null){
				request.setAttribute("nextlistsize", "0");
			}
			else{
				request.setAttribute("nextlistsize", next.size());
			}
			request.setAttribute("firstName", bean.getFirstName());
			request.setAttribute("lastName", bean.getLastName());
			request.setAttribute("collegeId", bean.getCollegeId());
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
		}
		log.debug("StudentListCtl Method doPost End ");
		return;
	}
	
	/** 
	 * 
	 * Returns the view page of StudentListCtl
     * 
     * @return StudentListView.jsp:View page of StudentListCtl
	 * 
	 */
	@Override
	protected String getView() {
		return ORSView.STUDENT_LIST_VIEW;
	}

}

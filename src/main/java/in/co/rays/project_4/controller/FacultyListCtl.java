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
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.model.CollegeModel;
import in.co.rays.project_4.model.CourseModel;
import in.co.rays.project_4.model.FacultyModel;
import in.co.rays.project_4.model.StudentModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;


/**
 * 
 * Faculty List functionality Controller. Performs operation for list, search
 * and delete operations of Faculty
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
@WebServlet("/ctl/FacultyListCtl")
public class FacultyListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
   
		
    public FacultyListCtl() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    
    
    private static Logger log = Logger.getLogger(FacultyListCtl.class);
    
    
    /**
	 * Loads list and other data required to display at HTML form
	 * 
	 * @param request(HttpServletRequest)
	 */
    @Override
    protected void preload(HttpServletRequest request){
    	log.debug("FacultyListCtl Method preload start");
    	List<FacultyBean>flist = new ArrayList<FacultyBean>();
    	FacultyModel fmodel = new FacultyModel();
    	List<CourseBean>clist = new ArrayList<CourseBean>();
    	CourseModel cmodel = new CourseModel();
    	try {
			flist= fmodel.list();
			clist= cmodel.list();
		} catch (ApplicationException e) {
			log.error(e);
		}
    	request.setAttribute("facultyList", flist);
    	request.setAttribute("courseList", clist);
    	log.debug("FacultyListCtl Method preload end");
	}
    
    
    /**
	 * Populates FacultyBean object from request parameters
	 * 
	 * @param request(HttpServletRequest)
	 * @return bean(FacultyBean)
	 */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
    	log.debug("FacultyListCtl method populateBean start"); 
    	FacultyBean bean = new FacultyBean();
    	//bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
    	bean.setId(DataUtility.getLong(request.getParameter("facultyId")));
    	bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
    	bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
    	bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
    	log.debug("FacultyListCtl method populateBean end");
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
		log.debug("FacultyListCtl method doGet start");
		FacultyModel model = new FacultyModel();
		List<FacultyBean>list = null;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		int pageNo = 1;
		
		try {
			list = model.list(pageNo, pageSize);
			List<FacultyBean>next = model.list(pageNo+1, pageSize);
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
		log.debug("FacultyListCtl Method doGet end");
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
		log.debug("FacultyListCtl Method doPost start");
		String op = DataUtility.getString(request.getParameter("operation"));
		FacultyModel model = new FacultyModel();
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
			FacultyBean dBean = new FacultyBean();
			if(ids!=null&&ids.length>0){
				int i = 0;
				while(i<ids.length){
				dBean.setId(Integer.parseInt((ids[i])));
				model.delete(dBean);
				i++;
				}	
				ServletUtility.setSuccessMessage("Records deleted successfully", request);
			}else if(ids==null||ids.length==0){
				ServletUtility.setErrorMessage("No record Selected", request);
			}
		}
		else if(op.equals(OP_RESET)||op.equals(OP_BACK)){
			ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
			log.debug("FacultyListCtl Method doPost end");
			return;
		}else if(op.equals(OP_NEW)){
			ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
			log.debug("FacultyListCtl Method doPost End");
			return;
		}
		FacultyBean bean = (FacultyBean) populateBean(request);
		
			List<FacultyBean>list = model.search(bean, pageNo, pageSize);
			List<FacultyBean>next = model.search(bean, pageNo+1, pageSize);
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
			request.setAttribute("facultyId", bean.getId());
			request.setAttribute("courseId", bean.getCourseId());
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
		}
		log.debug("FacultyListCtl Method doPost End");
		return;
	}
	
	
	/**
	 * Returns the VIEW page of FacultyListCtl
	 * 
	 * @return FacultyListView.jsp: View page of FacultyListCtl
	 */
	@Override
	protected String getView(){
		return ORSView.FACULTY_LIST_VIEW;
	}

}

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
import in.co.rays.project_4.bean.CourseBean;
import in.co.rays.project_4.bean.RoleBean;
import in.co.rays.project_4.bean.SubjectBean;
import in.co.rays.project_4.bean.UserBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.model.CourseModel;
import in.co.rays.project_4.model.RoleModel;
import in.co.rays.project_4.model.SubjectModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.JdbcDataSource;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;


/**
 * 
 * College List functionality Controller. Performs operation for list, search
 * and delete operations of College
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
@WebServlet("/ctl/SubjectListCtl")
public class SubjectListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log = Logger.getLogger(SubjectListCtl.class);
	
    public SubjectListCtl() {
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
    	log.debug("SubjectListCtl Method preload start");
    	CourseModel model = new CourseModel();
    	try {
			List<CourseBean> list = model.list();
			request.setAttribute("courseList", list);
		} catch (ApplicationException e) {
			log.error(e);
		}
    	log.debug("SubjectListCtl Method preload end");
    }
    
    /**
	 * Populates StudentBean object from request parameters
	 * 
	 * @param request(HttpServletRequest)
	 * @return bean(StudentBean)
	 */
    @Override
	protected BaseBean populateBean(HttpServletRequest request){
    	log.debug("SubjectListCtl Method populateBean start");
		SubjectBean bean = new SubjectBean();
		bean.setCourseId(DataUtility.getInt(request.getParameter("courseId")));
		bean.setName(DataUtility.getString(request.getParameter("subjectName")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		log.debug("SubjectListCtl Method polulateBean end");
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
		log.debug("SubjectListCtl Method doGet start");
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		SubjectModel model = new SubjectModel();
		List<SubjectBean>list = new ArrayList<SubjectBean>();
		List<SubjectBean>next = new ArrayList<SubjectBean>();
		int pageNo = 1;
		try {
			list  = model.list( pageNo, pageSize);
			next = model.list( pageNo+1, pageSize);
			if(list==null||list.size()==0){
				ServletUtility.setErrorMessage("No Records Found", request);
			}
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
		}
		if(next==null||next.size()==0){
			request.setAttribute("nextlistsize", "0");
		}else{
			request.setAttribute("nextlistsize", next.size());
		}
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.setList(list, request);
		ServletUtility.forward(getView(), request, response);
		log.debug("SubjectListCtl Method doGet End");
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
		log.debug("SubjectListCtl Method doPost start");
		String op = DataUtility.getString(request.getParameter("operation"));
		if(op.equals(OP_RESET)||op.equals(OP_BACK)){
			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
			log.debug("SubjectListCtl Method doPost end");
			return;
		}
		if(op.equals(OP_NEW)){
			ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
			log.debug("SubjectListCtl Method doPost end");
			return;
		}
		SubjectModel model = new SubjectModel();
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		SubjectBean bean = new SubjectBean();
		List<SubjectBean>list = new ArrayList<SubjectBean>();
		List<SubjectBean>next = new ArrayList<SubjectBean>();
		bean = (SubjectBean)populateBean(request);
		try {
		if(op.equals(OP_SEARCH)){
			pageNo=1;
		} else if (op.equals(OP_NEXT)){
			pageNo++;
		} else if (op.equals(OP_PREVIOUS)){
			pageNo--;
		}
		else if(op.equals(OP_DELETE)){
			String ids[] = request.getParameterValues("ids");
			SubjectBean dBean = new SubjectBean();
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
	
		list  = model.search(bean, pageNo, pageSize);
		next = model.search(bean, pageNo+1, pageSize);
		if(list == null || list.size() == 0 ){
			ServletUtility.setErrorMessage("No record found", request);
		}
		
		if(next==null||next.size()==0){
			request.setAttribute("nextlistsize", "0");
		}else{
			request.setAttribute("nextlistsize", next.size());
		}
		request.setAttribute("subjectName", bean.getName());
		request.setAttribute("courseId",bean.getCourseId());
		request.setAttribute("description", bean.getDescription());
		ServletUtility.setBean(bean, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.setList(list, request);
		ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
		}
		log.debug("SubjectListCtl Method doPost end");
		return;
	}
	
	
	/** 
	 * 
	 * Returns the view page of SubjectListCtl
     * 
     * @return SubjectListView.jsp:View page of SubjectListCtl
	 * 
	 */
	@Override
	protected String getView() {
		return ORSView.SUBJECT_LIST_VIEW;
	}

}

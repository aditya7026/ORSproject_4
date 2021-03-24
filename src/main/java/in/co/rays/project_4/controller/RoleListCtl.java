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
import in.co.rays.project_4.bean.RoleBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.model.RoleModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;


/**
 * 
 * Role List functionality Controller. Performs operation for list, search
 * operations of Role
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
@WebServlet("/ctl/RoleListCtl")
public class RoleListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
    
		private static Logger log = Logger.getLogger(RoleListCtl.class);
		
    public RoleListCtl() { 
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
    	log.debug("RoleListCtl Method preload start ");
    	RoleModel model = new RoleModel();
    	try {
			List<RoleBean>list = model.list();
			request.setAttribute("roleList", list);
		} catch (ApplicationException e) {
			log.error(e);
		}
    	log.debug("RoleListCtl Method preload end ");
	}
    
    /**
	 * Populates RoleBean object from request parameters
	 * 
	 * @param request(HttpServletRequest)
	 * @return bean(RoleBean)
	 */
    @Override
    protected BaseBean populateBean(HttpServletRequest request){
    	log.debug("RoleListCtl Method poplulateBean start ");
    	RoleBean  bean = new RoleBean();
    	bean.setId(DataUtility.getLong(request.getParameter("roleId")));
    	bean.setDescription(DataUtility.getString(request.getParameter("desc")));
    	log.debug("RoleListCtl Method polulatebean end ");
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
		log.debug("RoleListCtl Method doGet Start ");
		RoleModel model = new RoleModel();
		List<RoleBean>list = new ArrayList<RoleBean>();
		List<RoleBean>next = new ArrayList<RoleBean>();
		int pageNo = 1;
		try {
			list = model.list(pageNo, pageSize);
			next = model.list(pageNo+1,pageSize);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
		}
		if(list==null||list.size()==0){
			ServletUtility.setErrorMessage("No record found", request);
		}
		if(next.size()==0||next==null){
			request.setAttribute("nextlistsize", "0");
		}
		else{
			request.setAttribute("nextlistsize", next.size());
		}
		
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.setList(list, request);
		ServletUtility.forward(getView(), request, response);
		log.debug("RoleListCtl Method doGet end ");
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
		log.debug("RoleListCtl Method doPost start");
		String op = request.getParameter("operation");
		if(op.equals(OP_RESET)||op.equals(OP_BACK)){
			ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
			log.debug("RoleListCtl Method doPost end ");
			return;
		}
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		List<RoleBean>list = new ArrayList<RoleBean>();
		List<RoleBean>next = new ArrayList<RoleBean>();
		RoleModel model = new RoleModel();
		RoleBean bean = new RoleBean();
		
		try {
		if(op.equals(OP_NEXT)){
			pageNo++;
		}else if(op.equals(OP_PREVIOUS)){
			pageNo--;
		}else if(op.equals(OP_NEW)){
			ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
			log.debug("RoleListCtl Method doPost end ");
			return; 
		}else if(op.equals(OP_DELETE)){
			String ids[] = request.getParameterValues("ids");
			RoleBean dbean = new RoleBean();
			if(ids.length>0&&ids!=null){
				int i = 0; 
				while(i<ids.length){
				dbean.setId(Integer.parseInt(ids[i]));
				model.delete(dbean);
				i++;
				}
				}
			ServletUtility.setSuccessMessage("Records Deleted Successfully", request);
		}
		else if(op.equals(OP_SEARCH)){
			bean = (RoleBean)populateBean(request);
			pageNo = 1; 	
		}
			list = model.search(bean, pageNo, pageSize);
			next  = model.search(bean, pageNo+1, pageSize);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
		}
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.setList(list, request);
		request.setAttribute("roleId", bean.getId());
		request.setAttribute("desc", bean.getDescription());
		if(list==null||list.size()==0){
			ServletUtility.setErrorMessage("No record found", request);
		}
		if(next.size()==0||next==null){
			request.setAttribute("nextlistsize", "0");
		}
		else{
			request.setAttribute("nextlistsize", next.size());
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("RoleListCtl Method doPost end ");
		return;
	}
	
	
	/** 
	 * 
	 * Returns the view page of RoleListCtl
     * 
     * @return RoleListView.jsp:View page of RoleListCtl
	 * 
	 */
	@Override
	protected String getView() {
		return ORSView.ROLE_LIST_VIEW;
	}
}

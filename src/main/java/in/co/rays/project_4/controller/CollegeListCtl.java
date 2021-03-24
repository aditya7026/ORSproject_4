package in.co.rays.project_4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletSecurityElement;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.BaseBean;
import in.co.rays.project_4.bean.CollegeBean;
import in.co.rays.project_4.bean.RoleBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.model.CollegeModel;
import in.co.rays.project_4.model.RoleModel;
import in.co.rays.project_4.util.DataUtility;
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
@WebServlet("/ctl/CollegeListCtl")
public class CollegeListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CollegeListCtl() {
        super();
        // TODO Auto-generated constructor stub
    }

    private static Logger log = Logger.getLogger(CollegeListCtl.class);
    
    /**
	 * Loads list and other data required to display at HTML form
	 * 
	 * @param request(HttpServletRequest)
	 */
    @Override
    protected void preload(HttpServletRequest request){
    	log.debug("CollegeListCtl method preload start");
    	CollegeModel model = new CollegeModel();
    	try {
			List<CollegeBean> list = model.list();
			request.setAttribute("collegeList", list);
		} catch (ApplicationException e) {
			log.error(e);
		}
    	log.debug("CollegeListCtl method preload end");
    } 
    
    /**
	 * Populates CollegeBean object from request parameters
	 * 
	 * @param request(HttpServletRequest)
	 * @return bean(CollegeBean)
	 */
    @Override
    protected BaseBean populateBean(HttpServletRequest request){
    	log.debug("CollegeListCtl method populateBean start");
    	CollegeBean bean = new CollegeBean();
    	bean.setId(DataUtility.getLong(request.getParameter("collegeId")));
    	bean.setState(request.getParameter("state"));
    	bean.setCity(request.getParameter("city"));
    	log.debug("CollegeListCtl method populateBean end");
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
		log.debug("CollegeListCtl method doGet Start");
		CollegeModel model = new CollegeModel();
		List<CollegeBean> list = new ArrayList<CollegeBean>();
		List<CollegeBean> next = new ArrayList<CollegeBean>();
		int pageNo = 1;
		try {
			list  = model.list(pageNo, pageSize);
			next = model.list(pageNo+1, pageSize);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
		}
		if(list==null||list.size()==0){
			ServletUtility.setErrorMessage("No Record found", request);
		}
		if(next==null||next.size()==0){
			request.setAttribute("nextlistsize", "0");
		}else{
			request.setAttribute("nextlistsize", next.size());
		}
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
		log.debug("CollegeListCtl method doGet End");
	}
	/**
	 * Contains submit logic
	 * 
	 * @param request(HttpServletRequest)
	 * @param response(HttpServletResponse)
	 * @throws ServletException
	 * @throws IOException
	 * */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log.debug("CollegeListCtl method doPost start");
		String op = DataUtility.getString(request.getParameter("operation"));
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		CollegeModel model = new  CollegeModel();
		CollegeBean bean = new CollegeBean();
		List<CollegeBean>list = new ArrayList<CollegeBean>();
		List<CollegeBean>next = new ArrayList<CollegeBean>();
		bean = (CollegeBean) populateBean(request);
		try{
		if(op.equals(OP_NEXT)){
			pageNo++;
		}else if(op.equals(OP_PREVIOUS)){
			pageNo--;
		}else if(op.equals(OP_NEW)){
			ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
			log.debug("CollegeListCtl method doPost End");
			return;
		}else if(op.equals(OP_DELETE)){
			String[] ids = request.getParameterValues("ids");
			CollegeBean dbean = new CollegeBean();
			int i = 0;
			while(i<ids.length){
			dbean.setId(Integer.parseInt(ids[0]));
			model.delete(dbean);
			i++;
			}
			ServletUtility.setSuccessMessage("Data Deleted Successfully", request);
			
			
		}else if(op.equals(OP_SEARCH)){
			pageNo = 1;
		}else if(op.equals(OP_RESET)||op.equals(OP_BACK)){
			ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
			log.debug("CollegeListCtl method doPost End");
			return;
		}
		
			list  = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo+1, pageSize);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
		}
		if(list == null || list.size() == 0 ){
			ServletUtility.setErrorMessage("No record found", request);
		}
		if(next==null||next.size()==0){
			request.setAttribute("nextlistsize", "0");
		}else{
			request.setAttribute("nextlistsize", next.size());
		}
		request.setAttribute("collegeId", bean.getId());
		request.setAttribute("state", bean.getState());
		request.setAttribute("city", bean.getCity());
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
		log.debug("CollegeListCtl method doPost End");
	}
	
	/**
	 * Returns the VIEW page of CollegeListCtl
	 * 
	 * @return CollegeListView.jsp: View page of CollegeListCtl
	 */
	@Override
	protected String getView() {
		return ORSView.COLLEGE_LIST_VIEW;
	}

}

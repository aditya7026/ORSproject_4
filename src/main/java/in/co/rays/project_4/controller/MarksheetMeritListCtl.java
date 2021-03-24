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

import in.co.rays.project_4.bean.MarksheetBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.model.MarksheetModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;


/**
 * 
 * Marksheet Merit List functionality Controller. Performance operation of
 * Marksheet Merit List
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
@WebServlet("/ctl/MarksheetMeritListCtl")
public class MarksheetMeritListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
    
	private static Logger log = Logger.getLogger(MarksheetMeritListCtl.class);
	
    public MarksheetMeritListCtl() {
        super();
        // TODO Auto-generated constructor stub
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
		log.debug("MarksheetMeritListCtl Method doGet start");
		MarksheetModel model = new  MarksheetModel();
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		List<MarksheetBean>list  = new ArrayList<MarksheetBean>();
		
		try {
			list = model.getMeritList(pageNo, pageSize);
			if(list==null||list.size()==0){
				ServletUtility.setErrorMessage("No record found", request);
			}
			
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
		}
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
		log.debug("MarksheetMeritListCtl Method doGet end");
		return;
	}

	/**
	 * * Contains Submit logics
	 * 
	 * @param request(HttpServletRequest)
	 * @param response(HttpServletResponse)
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("MarksheetMeritListCtl Method doPost start");
		String op = DataUtility.getString(request.getParameter("operation"));
		if(op.equals(OP_BACK)){
			ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
			log.debug("MarksheetMeritListCtl Method doPost end");
			return;
		}
	}
	
	
	/**
	 * Returns the View page of MarksheetMeritListCtl
	 * 
	 * @return MarksheetMeritListView.jsp: View page of MarksheetMeritListCtl
	 */
	@Override
	protected String getView() {
		return ORSView.MARKSHEET_MERIT_LIST_VIEW;
	}

}

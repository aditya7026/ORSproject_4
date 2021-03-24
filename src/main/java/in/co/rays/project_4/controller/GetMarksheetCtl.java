package in.co.rays.project_4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.MarksheetBean;
import in.co.rays.project_4.bean.StudentBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.RecordNotFoundException;
import in.co.rays.project_4.model.MarksheetModel;
import in.co.rays.project_4.model.StudentModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.DataValidator;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;


/**
 * 
 * Get Marksheet functionality Controller. Performs operation for Get Marksheet
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
@WebServlet("/ctl/GetMarksheetCtl")
public class GetMarksheetCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	
	private static Logger log = Logger.getLogger(GetMarksheetCtl.class);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMarksheetCtl() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    protected boolean validate(HttpServletRequest request){
    	log.debug("GetMarksheetCtl Method validate start ");
    	boolean pass = true;
    	if(DataValidator.isNull(request.getParameter("rollNo"))){
    		request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll No"));
    		pass=false;
    	}else if(!DataValidator.isRollNo(request.getParameter("rollNo"))){
    		request.setAttribute("rollNo", PropertyReader.getValue("error.invalid", "Roll No"));
    		pass=false;
    	}
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
		log.debug("GetMarksheetCtl Method doGet start");
		ServletUtility.forward(getView(), request, response);
		log.debug("GetMarksheetCtl Method doPost End");
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
		log.debug("GetMarksheetCtl Method doPost start");
		String op = DataUtility.getString(request.getParameter("operation"));
		if(op.equals(OP_GO)){
			String rollNo = DataUtility.getString(request.getParameter("rollNo"));
			MarksheetModel model = new MarksheetModel();
			MarksheetBean bean = new MarksheetBean();
			StudentModel sModel = new StudentModel();
			StudentBean sBean = new StudentBean();
			try {
				bean = model.findByRollNo(rollNo);
				if(bean == null){
					request.setAttribute("rollNo", PropertyReader.getValue("error.notfound","Roll No" ));
					ServletUtility.forward(getView(), request, response);
				}				
				sBean = sModel.findByPk(bean.getStudentId());
				request.setAttribute("sBean", sBean);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
			} 
			catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
			} 
			
		}
		else if(op.equals(OP_RESET)){
			ServletUtility.redirect(ORSView.GET_MARKSHEET_CTL, request, response);
		}
		log.debug("GetMarksheetCtl Method doPost end");
		return;
	}
	
	
	/**
     * Returns the VIEW page of GetMarksheetCtl
     * 
     * @return GetMarksheetView.jsp:View page of GetMarksheetCtl
     */
	@Override
	protected String getView() {
		return ORSView.GET_MARKSHEET_VIEW;
	}

}

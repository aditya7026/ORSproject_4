package in.co.rays.project_4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_4.util.ServletUtility;
/**
 * 
 * Welcome functionality Controller. Performs operation for Show Welcome page
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
@ WebServlet("/WelcomeCtl")
public class WelcomeCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	
	
	private static Logger log = Logger.getLogger(WelcomeCtl.class);
	
	/**
	 * Contains display logic
	 * 
	 * @param request(HttpServletRequest)
	 * @param response(HttpServletResponse)
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("WelcomeCtl Method doGet Start");
		ServletUtility.forward(getView(), request, response);
		log.debug("WelcomeCtl Method doPost End");
		return;
	}
	
	/** 
	 * 
	 * Returns the view page of WelcomeCtl
     * 
     * @return WelcomeView.jsp:View page of WelcomeCtl
	 * 
	 */
	@Override
	protected String getView() {
		
		return ORSView.WELCOME_VIEW;
	}

}

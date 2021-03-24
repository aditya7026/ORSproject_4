package in.co.rays.project_4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_4.util.ServletUtility;

/**
 * 
 * Servlet implementation class ErrorCtl. performs Error handling
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "ErrorCtl", urlPatterns = {"/ErrorCtl"})
public class ErrorCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
    
    public ErrorCtl() {
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
		ServletUtility.forward(getView(), request, response);
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
		ServletUtility.forward(getView(), request, response);
		return;
	}

	/**
	 * Returns the VIEW page of ErrorCtl
	 * 
	 * @return CourseListView.jsp:View page of ErrorCtl
	 */
	@Override
	protected String getView() {
		return ORSView.ERROR_VIEW;
	}

}

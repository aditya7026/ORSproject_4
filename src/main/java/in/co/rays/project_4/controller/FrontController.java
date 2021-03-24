package in.co.rays.project_4.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.project_4.util.ServletUtility;

/**
 * 
 * Main Controller performs session checking and logging operations before
 * calling any application controller. It prevents any user to access
 * application without login.
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
@WebFilter(urlPatterns={"/ctl/*","/doc/*"})
public class FrontController implements Filter {
	
	
	public void init(FilterConfig conf) throws ServletException {
	}
	
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(true);  
        //String path = request.getRequestURI();
        if (session.getAttribute("user") == null) {
        	ServletUtility.setErrorMessage("Session Expired..Please Login..!!", request);
        	String path = request.getRequestURI();
        	if(request.getQueryString()!=null)
        		path += "?" + request.getQueryString();
        	System.out.println("path------------------"+ path);
            request.setAttribute("repath", path);
            ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
            /* 
        	 * } else  if(path.equals("/LoginCtl")||path.equals("/ForgetPasswordCtl")||path.equals("/UserRegistrationCtl")){
        	 * ServletUtility.forward(ORSView.WELCOME_VIEW); 
        	 */
        } else {
        	
            chain.doFilter(request, response);
        }
		
		
	}
	
	
	public void destroy() {
	}
	
	

}

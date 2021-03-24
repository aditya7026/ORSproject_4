package in.co.rays.project_4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_4.bean.BaseBean;
import in.co.rays.project_4.bean.UserBean;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.DataValidator;
import in.co.rays.project_4.util.ServletUtility;

/**
 * 
 * Base Controller
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
public abstract class BaseCtl extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	
	public static final String OP_SAVE ="Save";
	public static final String OP_CANCEL="Cancel";
	public static final String OP_RESET = "Reset";
	public static final String OP_UPDATE = "Update";
	public static final String OP_DELETE = "Delete";
	public static final String OP_LIST = "List";
	public static final String OP_SEARCH = "Search";
	public static final String OP_VIEW = "View";
	public static final String OP_NEXT = "Next";
	public static final String OP_PREVIOUS ="Previous";
	public static final String OP_NEW ="New";
	public static final String OP_LOG_OUT = "Logout";
	public static final String OP_BACK = "Back";
	public static final String OP_GO ="Go";
	
	
	public static final String MSG_SUCCESS="success";
	
	
	public static final String MSG_ERROR = "error";
	
	
	
	/**
	 * 
	 * Validates input data entered by User
	 * 
	 * @param HttpServletRequest
	 * @return boolean
	 */
	protected boolean validate(HttpServletRequest request){
		return true;
	}
	
	/**
	 * 
	 * Loads list and other data required to display at HTML form
	 * 
	 * @param HttpServletRequest
	 */
	protected void preload(HttpServletRequest request){
	} 
	
	/**
	 * 
	 * populate Generic attributes in Bean
	 * 
	 * @param HttpServletRequest
	 * @return BaseBean
	 */
	protected BaseBean populateBean(HttpServletRequest request){
		return null;
	}
	
	/**
	 * 
	 * populates generic attributes in DTO
	 * 
	 * @param dto
	 * @param request
	 * @return
	 */
	protected BaseBean populateDTO(BaseBean dto,HttpServletRequest request){
		String createdBy = request.getParameter("createdBy");
		String modifiedBy = null;
		UserBean userbean = (UserBean)request.getSession().getAttribute("user");
		if(userbean==null){
			createdBy="root";
			modifiedBy="root";
		}else {
			modifiedBy = userbean.getLogin();
			if("null".equalsIgnoreCase(createdBy)||DataValidator.isNull(createdBy)){
				createdBy=modifiedBy;
			}
		}
		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);
		long cdt = DataUtility.getLong(request.getParameter("createdDatetime"));
		if(cdt>0){
			dto.setCreatedDatetime(DataUtility.getTimestamp(cdt));
		}else{
			dto.setCreatedDatetime(DataUtility.getCurrentTimestamp());
		}
		dto.setModifiedDatetime(DataUtility.getCurrentTimestamp());
	
	return dto;
	}

	
@Override
 protected void service(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
	 preload(request);
	 String op = DataUtility.getString(request.getParameter("operation"));
	 if(DataValidator.isNotNull(op)&&!OP_CANCEL.equalsIgnoreCase(op)&&!OP_VIEW.equalsIgnoreCase(op)
			 &&!OP_DELETE.equalsIgnoreCase(op)&&!OP_RESET.equalsIgnoreCase(op)){
		 if(!validate(request)){
			 BaseBean bean = (BaseBean)populateBean(request);
			 ServletUtility.setBean(bean, request);
			 ServletUtility.forward(getView(), request, response);
			 return;
		 }
	 }
	 super.service(request, response);
 }
 /**
  * 
  * return the view page of controller
  * 
 * @return String
 */
protected abstract String getView();
	 
}

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
import in.co.rays.project_4.bean.UserBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.model.CourseModel;
import in.co.rays.project_4.model.UserModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;

/**
 * 
 * Course List functionality Controller. Performs operation for list, search and
 * delete operations of Course
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet("/ctl/CourseListCtl")
public class CourseListCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	private static Logger log = Logger.getLogger(CourseListCtl.class);

	public CourseListCtl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Populates CourseBean object from request parameters
	 * 
	 * @param request(HttpServletRequest)
	 * @return bean(CourseBean)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("CourseListCtl method populateBean start");
		CourseBean bean = new CourseBean();
		bean.setDuration(request.getParameter("duration"));
		bean.setName(request.getParameter("courseName"));
		bean.setDescription(request.getParameter("description"));
		log.debug("CourseListCtl method populateBean end");
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("CourseListCtl method doGet Start");
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		CourseModel model = new CourseModel();
		List<CourseBean> list = new ArrayList<CourseBean>();
		List<CourseBean> next = new ArrayList<CourseBean>();
		int pageNo = 1;
		try {
			list = model.list(pageNo, pageSize);
			next = model.list(pageNo + 1, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No Records Found", request);
			}
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
		}
		if (next == null || next.size() == 0) {
			request.setAttribute("nextlistsize", "0");
		} else {
			request.setAttribute("nextlistsize", next.size());
		}
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.setList(list, request);
		ServletUtility.forward(getView(), request, response);
		log.debug("CourseListCtl method doGet End");
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("CourseListCtl Method doPost Start");
		String op = DataUtility.getString(request.getParameter("operation"));
		if (op.equals(OP_RESET) || op.equals(OP_BACK)) {
			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			log.debug("CourseListCtl Method doPost End");
			return;
		}
		if (op.equals(OP_NEW)) {
			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
			log.debug("CourseListCtl Method doPost End");
			return;
		}
		CourseModel model = new CourseModel();
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		CourseBean bean = new CourseBean();
		List<CourseBean> list = new ArrayList<CourseBean>();
		List<CourseBean> next = new ArrayList<CourseBean>();
		bean = (CourseBean) populateBean(request);
		try {
			if (op.equals(OP_SEARCH)) {
				pageNo = 1;
			} else if (op.equals(OP_NEXT)) {
				pageNo++;
			} else if (op.equals(OP_PREVIOUS)) {
				pageNo--;
			} else if (op.equals(OP_DELETE)) {
				String ids[] = request.getParameterValues("ids");
				CourseBean dBean = new CourseBean();
				if (ids != null && ids.length > 0) {
					int i = 0;
					while (i < ids.length) {
						dBean.setId(Integer.parseInt((ids[i])));
						model.delete(dBean);
						i++;
					}
					ServletUtility.setSuccessMessage("Records deleted successfully", request);
				}
				if (ids == null || ids.length == 0) {
					ServletUtility.setErrorMessage("No record Selected", request);
				}

			}

			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			if (next == null || next.size() == 0) {
				request.setAttribute("nextlistsize", "0");
			} else {
				request.setAttribute("nextlistsize", next.size());
			}
			request.setAttribute("courseName", bean.getName());
			request.setAttribute("duration", bean.getDuration());
			request.setAttribute("description", bean.getDescription());
			ServletUtility.setBean(bean, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setList(list, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.debug(e);
			ServletUtility.handleException(e, request, response);
		}
		log.debug("CourseListCtl Method doPost end");
	}

	/**
	 * Returns the VIEW page of CourseListCtl
	 * 
	 * @return CourseListView.jsp:View page of CourseListCtl
	 */
	@Override
	protected String getView() {
		return ORSView.COURSE_LIST_VIEW;
	}

}

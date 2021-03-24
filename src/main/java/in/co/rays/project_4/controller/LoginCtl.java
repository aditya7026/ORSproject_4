package in.co.rays.project_4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import in.co.rays.project_4.bean.BaseBean;
import in.co.rays.project_4.bean.RoleBean;
import in.co.rays.project_4.bean.UserBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.RecordNotFoundException;
import in.co.rays.project_4.model.RoleModel;
import in.co.rays.project_4.model.UserModel;
import in.co.rays.project_4.util.DataUtility;
import in.co.rays.project_4.util.DataValidator;
import in.co.rays.project_4.util.PropertyReader;
import in.co.rays.project_4.util.ServletUtility;

/**
 * 
 * Login functionality Controller. Performs operation for Login
 * 
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 * 
 */
@WebServlet("/LoginCtl")
public class LoginCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	public static final String OP_SIGN_IN = "SignIn";
	public static final String OP_SIGN_UP = "SignUp";

	private static Logger log = Logger.getLogger(LoginCtl.class);

	/**
	 * 
	 * validates input data entered by user
	 * 
	 * @param request(HttpServletRequest)
	 * @return pass(boolean)
	 * 
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("LoginCtl Method validate start");
		boolean pass = true;
		String op = DataUtility.getString(request.getParameter("operation"));
		if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
			return pass;
		}
		String login = request.getParameter("login");
		if (DataValidator.isNull(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login Id"));
			pass = false;
		}
		if (DataValidator.isNull(DataUtility.getString(request.getParameter("password")))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		} else if (!DataValidator.isPassword(DataUtility.getString(request.getParameter("password")))) {
			request.setAttribute("password", PropertyReader.getValue("error.password", "Password"));
			pass = false;
		}
		log.debug("LoginCtl Method validate end");
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("LoginCtl Method doGet Start");
		String op = request.getParameter("operation");
		if (op != null) {
			if (op.equals(OP_LOG_OUT)) {
				HttpSession session = request.getSession();

				session.invalidate();
				ServletUtility.setSuccessMessage("Logout Successfull", request);
				ServletUtility.forward(getView(), request, response);
				log.debug("LoginCtl Method doGet End");
				return;
			}
		}
		/*
		 * long id = DataUtility.getLong(request.getParameter("id")); if (id >
		 * 0) { UserModel model = new UserModel(); UserBean userbean; try {
		 * userbean = model.findByPk(id); ServletUtility.setBean(userbean,
		 * request); } catch (ApplicationException e) { log.error(e);
		 * ServletUtility.handleException(e, request, response); return; } }
		 */
		ServletUtility.forward(getView(), request, response);
		log.debug("LoginCtl Method doGet End");
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
		log.debug("LoginCtl Method doPost start");

		String op = DataUtility.getString(request.getParameter("operation"));

		if (OP_SIGN_UP.equals(op)) {
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;
		} else if (OP_SIGN_IN.equals(op)) {

			// session
			HttpSession session = request.getSession(true);
			session.setMaxInactiveInterval(-1);

			// get model
			UserModel model = new UserModel();
			RoleModel role = new RoleModel();

			String path = request.getParameter("repath");

			UserBean bean = (UserBean) populateBean(request);

			try {
				bean = model.authenticate(bean.getLogin(), bean.getPassword());
				if (bean != null) {
					session.setAttribute("user", bean);
					long rollId = bean.getRoleId();

					RoleBean rolebean = role.findByPk(rollId);

					if (rolebean != null) {
						session.setAttribute("role", rolebean.getName());
					}

					if (path == null || path.length() <= 0) {
						ServletUtility.forward(ORSView.WELCOME_VIEW, request, response);
					} else {
						ServletUtility.redirect(path, request, response);
					}

				}
				
			} catch (RecordNotFoundException e) {
				ServletUtility.setBean((UserBean)populateBean(request), request);
				ServletUtility.setErrorMessage("Invalid LoginId and Password", request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
			}

		}
		log.debug("LoginCtl Method doPost end");
		return;
	}

	/**
	 * Returns the VIEW page of LoginCtl
	 * 
	 * @return LoginView.jsp: View page of LoginCtl
	 */
	@Override
	protected String getView() {
		return ORSView.LOGIN_VIEW;
	}

	/**
	 * Populates UserBean object from request parameters
	 * 
	 * @param request(HttpServletRequest)
	 * @return bean(UserBean)
	 */
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("LoginCtl Method populateBean start ");
		UserBean bean = new UserBean();
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		log.debug("LoginCtl Method populateBean end ");
		return bean;

	}
}

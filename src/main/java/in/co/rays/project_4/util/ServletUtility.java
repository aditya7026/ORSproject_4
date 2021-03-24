package in.co.rays.project_4.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_4.bean.BaseBean;
import in.co.rays.project_4.controller.BaseCtl;
import in.co.rays.project_4.controller.ORSView;
import in.co.rays.project_4.model.UserModel;

public class ServletUtility {
	
    /**
     * 
     * forwards request
     * 
     * @param page
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public static void forward(String page, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher rd = request.getRequestDispatcher(page);
        rd.forward(request, response);
    }

    /**
     * Redirect to given JSP/Servlet
     *
     * @param page
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public static void redirect(String page, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect(page);
    }

    /**
     * Redirect to Application Error Handler Page
     *
     * @param Exception
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public static void handleException(Exception e, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("exception", e);
        response.sendRedirect(ORSView.ERROR_CTL);

    }

    /**
     * Gets error message from request
     *
     * @param property
     * @param request
     * @return String
     */
    public static String getErrorMessage(String property,
            HttpServletRequest request) {

        String val = (String) request.getAttribute(property);
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

    

    /**
     * Gets a message from request
     *
     * @param property
     * @param request
     * @return
     */
    public static String getMessage(String property, HttpServletRequest request) {
        String val = (String) request.getAttribute(property);
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

    /**
     * Sets error message to request
     *
     * @param msg
     * @param request
     */
    public static void setErrorMessage(String msg, HttpServletRequest request) {
        request.setAttribute(BaseCtl.MSG_ERROR, msg);
    }

    /**
     * Gets error message from request
     *
     * @param request
     * @return
     */
    public static String getErrorMessage(HttpServletRequest request) {
        String val = (String) request.getAttribute(BaseCtl.MSG_ERROR);
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

    /**
     * Sets success message to request
     *
     * @param msg
     * @param request
     */
    public static void setSuccessMessage(String msg, HttpServletRequest request) {
        request.setAttribute(BaseCtl.MSG_SUCCESS, msg);
    }

    /**
     * Gets success message from request
     *
     * @param request
     * @return
     */
    public static String getSuccessMessage(HttpServletRequest request) {
        String val = (String) request.getAttribute(BaseCtl.MSG_SUCCESS);
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

    /**
     * Sets default Bean to request
     *
     * @param bean
     * @param request
     */
    public static void setBean(BaseBean bean, HttpServletRequest request) {
        request.setAttribute("bean", bean);
    }

    public static void setUserModel(UserModel model, HttpServletRequest request) {
        request.getSession().setAttribute("user", model);
    }

    /**
     * Gets default bean from request
     *
     * @param request
     * @return
     */

    public static BaseBean getBean(HttpServletRequest request) {
        return (BaseBean) request.getAttribute("bean");
    }

    public static boolean isLoggedIn(HttpServletRequest request) {
        UserModel model = (UserModel) request.getSession().getAttribute("user");
        return model != null;
    }

    /*
     * Returns logged in user role
     *
     * @param request
     * @return
     */

   /* public static long getRole(HttpServletRequest request) {
        UserModel model = (UserModel) request.getSession().getAttribute("user");
        if (model != null) {
            return model.getRoleId();
        } else {
            return AppRole.GUEST;
        }
    }*/

    

    /**
     * Get request parameter to display. If value is null then return empty
     * string
     *
     * @param property
     * @param request
     * @return
     */

    public static String getParameter(String property,
            HttpServletRequest request) {
        String val = (String) request.getParameter(property);
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

    /**
     * 
     * Sets List for List pages
     * 
     * @param list
     * @param 
     */
    public static void setList(List list, HttpServletRequest request) {
        request.setAttribute("list", list);
    }


	/**
	 * 
	 * Gets list for List Pages
	 * 
	 * @param request
	 * @return
	 */
	public static List getList(HttpServletRequest request) {
        return (List) request.getAttribute("list");
    }

    /**
     * 
     * Sets page no for list pages
     * 
     * @param pageNo
     * @param 
     */
    public static void setPageNo(int pageNo, HttpServletRequest request) {
        request.setAttribute("pageNo", pageNo);
    }


    /**
     * 
     * Gets page no for list pages
     * 
     * @param request
     * @return
     */
    public static int getPageNo(HttpServletRequest request) {
        return   Integer.parseInt(request.getAttribute("pageNo").toString());
    }


    /**
     * 
     * Sets page Size for list pages
     * 
     * @param pageSize
     * @param 
     */
    public static void setPageSize(int pageSize, HttpServletRequest request) {
        request.setAttribute("pageSize", pageSize);
    }

    /**
     * 
     * Gets Page Size for list pages
     * 
     * @param request
     * @return
     */
    public static int getPageSize(HttpServletRequest request) {
        return  Integer.parseInt(request.getAttribute("pageSize").toString());
    }
}

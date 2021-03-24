package in.co.rays.project_4.util;

import java.util.HashMap;

/**
 * @author Aditya
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class EmailBuilder {
	 /**
	  * 
	  * Returns Successful User Registration Message
	  * 
	 * @param HashMap
	 * @return String
	 */
	public static String getUserRegistrationMessage(HashMap<String, String>map){
		 StringBuilder msg = new StringBuilder();
		 
		 	msg.append("<HTML><BODY>");
	        msg.append("Registration is successful for ORS Project SunilOS");
	        msg.append("<H1>Hi! Greetings from SunilOS!</H1>");
	        msg.append("<P>Congratulations for registering on ORS! You can now access your ORS account online - anywhere, anytime and enjoy the flexibility to check the Marksheet Details.</P>");
	        msg.append("<P>Log in today at http://localhost:8080/project_4/ with your following credentials:</P>");
	        msg.append("<P><B>Login Id : " + map.get("login") + "<BR>" + " Password : " + map.get("password") + "</B></p>");

	        msg.append("<P> As a security measure, we recommended that you change your password after you first log in.</p>");
	        msg.append("<p>For any assistance, please feel free to call us at our helpline numbers.</p>");
	        msg.append("<p>You may also write to us at orsrays@yahoo.com</p>");
	        msg.append("<p>We assure you the best service at all times and look forward to a warm and long-standing association with you.</p>");
	        msg.append("<P><a href='http://www.sunrays.co.in' >-SUNRAYS Technolgies</a></P>");
	        msg.append("<p>This is a test mail</p>");
	        msg.append("</BODY></HTML>");
	        
	        return msg.toString();
	 }
	 /**
	  * Returns Email message of Forget Password
	  * 
	 * @param HashMap
	 * @return String
	 */
	public static String getForgotPasswordMessage(HashMap<String, String>map){
		 StringBuilder msg = new  StringBuilder();
		 
		 msg.append("<HTML><BODY>");
	        msg.append("<H1>Your password is reccovered !! " + map.get("firstName")
	                + " " + map.get("lastName") + "</H1>");
	        msg.append("<P><B>To access account user Login Id : "
	                + map.get("login") + "<BR>" + " Password : "
	                + map.get("password") + "</B></p>");
	        msg.append("<p>This is a test mail</p>");
	        msg.append("</BODY></HTML>");
	        
		 return msg.toString();
	 }
	 /**
	  * Returns Email message of Change Password
	  * 
	 * @param HashMap
	 * @return String
	 */
	public static String getChangePasswordMessage(HashMap<String, String>map){
		 StringBuilder msg = new StringBuilder();
		 
		 msg.append("<HTML><BODY>");
	        msg.append("<H1>Your Password has been changed Successfully !! "
	                + map.get("firstName") + " " + map.get("lastName") + "</H1>");
	        msg.append("<P><B>To access account user Login Id : "
	                + map.get("login") + "<BR>" + " Password : "
	                + map.get("password") + "</B></p>");
	        msg.append("<p>This is a test mail</p>");
	        msg.append("</BODY></HTML>");
	        
		 return msg.toString();
	 }
}

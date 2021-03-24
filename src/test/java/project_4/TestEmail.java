package project_4;

import java.util.HashMap;

import in.co.rays.project_4.bean.UserBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.util.EmailBuilder;
import in.co.rays.project_4.util.EmailMessage;
import in.co.rays.project_4.util.EmailUtility;

public class TestEmail {
	public static void main(String[] args) {
		
		UserBean bean = new UserBean();
		bean.setLogin("aditya7026@gmail.com");
		bean.setPassword("password");
		HashMap<String, String>map = new HashMap<String, String>();
		map.put("login", bean.getLogin());
		map.put("password", bean.getPassword());
		
		String message = EmailBuilder.getUserRegistrationMessage(map);
		
		EmailMessage msg = new EmailMessage();
		
		msg.setTo(bean.getLogin());
		msg.setSubject("Registration is successfull for ORS Project");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		
		try {
			EmailUtility.sendMail(msg);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

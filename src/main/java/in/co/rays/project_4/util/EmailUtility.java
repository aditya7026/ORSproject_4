package in.co.rays.project_4.util;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.model.CollegeModel;

/**
 * Email Utility provides Email Services
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public class EmailUtility {
	
	private static Logger log = Logger.getLogger(EmailUtility.class);
	
	
	/**
	 * Create Resource Bundle to read properties file
	 */
	static ResourceBundle rb= ResourceBundle.getBundle("in.co.rays.project_4.properties.System");
	/**
	 * Email Server
	 */
	private static final String SMTP_HOST_NAME = rb.getString("smtp.server");
	/**
	 * Email Server Port
	 */
	private static final String SMTP_PORT = rb.getString("smtp.port");
	/**
	 *  Session Factory, A session is a connection to email server.
	 */
	private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	/**
	 * Administrator's email id by which all messages are sent
	 */
	private static final String emailFromAddress = rb.getString("email.login");
	/**
	 * Administrator email's password
	 */
	private static final String emailPassword = rb.getString("email.pwd");
	
	/**
	 * Email server properties
	 */
	private static Properties ppty = new Properties();
	/**
	 * Static block to initialize static parameters
	 */
	static{
		ppty.put("mail.smtp.host", SMTP_HOST_NAME);
		ppty.put("mail.smtp.auth", "true");
		ppty.put("mail.debug", "true");
		ppty.put("mail.smtp.port", SMTP_PORT);
		ppty.put("mail.smtp.starttls.enable", "true");
		//ppty.put("mail.smtp.socketFactory.port", SMTP_PORT);
		//ppty.put("mail.smtp.socketFactory.class", SSL_FACTORY);
		//ppty.put("mail.smtp.socketFactory.fallback", "false");
	}
	
	/**
	 * @param emailMessageDTO
	 * 						: Email message
	 * @throws ApplicationException
	 */
	public static void sendMail(EmailMessage emailMessageDTO) throws ApplicationException{
		log.debug("send email start");
		try {
			Session session = Session.getDefaultInstance(ppty, new javax.mail.Authenticator(){
				protected PasswordAuthentication getPasswordAuthentication(){
					return new PasswordAuthentication(emailFromAddress, emailPassword);
				}
			}  );
			session.setDebug(true);
			Message msg  = new MimeMessage(session);
			//email from
			InternetAddress addressFrom = new InternetAddress(emailFromAddress);
			msg.setFrom(addressFrom);
			// email TO
			String[] emailIds = new String[0];
			
			if(emailMessageDTO.getTo()!=null){
				emailIds= emailMessageDTO.getTo().split(",");
			}
			
			InternetAddress[] addressTo = new InternetAddress[emailIds.length]; 
			
			for(int i = 0 ; i<emailIds.length;i++){
				addressTo[i] = new InternetAddress(emailIds[i]);
			}
			
			if(addressTo.length>0){
			msg.setRecipients(Message.RecipientType.TO, addressTo);
			}
			// email CC
			String[] emailIdsCc = new String[0];
			
			if(emailMessageDTO.getCc()!=null){
				emailIdsCc = emailMessageDTO.getCc().split(",");
			}
			
			InternetAddress[] addressCc = new InternetAddress[emailIdsCc.length];
			
			for(int i= 0;i<emailIdsCc.length;i++){
				addressCc[i] = new InternetAddress(emailIdsCc[i]);
			}
			
			if(addressCc.length>0){
				msg.setRecipients(Message.RecipientType.CC, addressCc);
			}
			
			// email BCC
			
			String[] emailIdsBcc = new String[0];
			
			if(emailMessageDTO.getBcc()!=null){
				emailIdsBcc = emailMessageDTO.getBcc().split(",");
			}
			
			InternetAddress[] addressBcc = new InternetAddress[emailIdsBcc.length];
			
			for(int i = 0;i<emailIdsBcc.length;i++){
				addressBcc[i] = new InternetAddress(emailIdsBcc[i]); 
			}
			
			if(addressBcc.length>0){
				msg.setRecipients(Message.RecipientType.BCC, addressBcc);
			}
			
			// subject
			
			msg.setSubject(emailMessageDTO.getSubject());
			
			// MIME Type
			
			switch (emailMessageDTO.getMessageType()) {
            case EmailMessage.HTML_MSG:
                msg.setContent(emailMessageDTO.getMessage(), "text/html");
                break;
            case EmailMessage.TEXT_MSG:
                msg.setContent(emailMessageDTO.getMessage(), "text/plain");
                break;

            }
			
			// MAIL SEND
			Transport.send(msg);		
			
		} catch (Exception e) {
			log.error("Email Error",e);
			throw new ApplicationException("Application exception in email"+e.getMessage());
		}
		log.debug("send mail end");
	}
}

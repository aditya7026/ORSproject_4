package in.co.rays.project_4.exception;

/**
 * 
 * ApplicationException is propogated from Service classes when an business
 * logic exception occurered.
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
public class ApplicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param msg 
	 * 				: Error msg
	 */
	public ApplicationException(String msg){
		super(msg);
	};
}

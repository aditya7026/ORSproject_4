package in.co.rays.project_4.exception;

/**
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 */
public class DatabaseException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param msg
	 * 				: error msg
	 */
	public DatabaseException(String msg){
		super(msg);
	};
}

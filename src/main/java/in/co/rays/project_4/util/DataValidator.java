package in.co.rays.project_4.util;

/**
 * 
 * DataValidator class is used to perform input validations
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class DataValidator {
	/**
	 * 
	 * checks if String is empty
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean isNull(String val) {
		if (val == null || val.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * checks if string is not null
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean isNotNull(String val) {
		return !isNull(val);
	}

	/**
	 * 
	 * checks if string is integer or not
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean isInteger(String val) {

		if (isNotNull(val)) {
			try {
				Integer.parseInt(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}

	/**
	 * 
	 * checks if string is Long
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean isLong(String val) {
		if (isNotNull(val)) {
			try {
				Long.parseLong(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}

	/**
	 * 
	 * checks if string is valid Email Id
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean isEmail(String val) {

		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		if (isNotNull(val)) {
			try {
				return val.matches(emailreg);
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}

	/**
	 * 
	 * checks if string is valid mobileNo
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean isMobileNo(String val) {
		/*
		 * if(val.length()!=10){ return false; }
		 */
		if (!val.matches("^[6-9]{1}[0-9]{9}$")) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * checks if string is valid RollNo
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean isRollNo(String val) {
		/*
		 * if(val.length()!=6){ return false; }
		 */
		if (!val.matches("^[0-9]{2}[a-zA-Z]{2}[0-9]{2}$")) {
			return false;
		}

		return true;
	}// check

	/*
	 * public static boolean haslength(String val, int min,int max){
	 * if(val.length()<=max){ if(val.length()>=min){ return true; } } return
	 * false;
	 * 
	 * }
	 */
	/*
	 * /**
	 * 
	 * @param val
	 * 
	 * @return
	 */
	/*
	 * public static boolean isName(String val){
	 * if(!val.matches("^[a-zA-Z]*$")){ return false; } return true; }
	 */
	/**
	 * 
	 * checks if string is valid firstName
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean isFirstName(String val) {
		if (!val.trim().matches("(?i)(^[a-z]+)[a-z .,-]((?! .,-)$){3,25}$")) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * checks if string is valid Last Name
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean isLastName(String val) {
		if (!val.trim().matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * checks if string is valid Password
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean isPassword(String val) {
		if (val.trim().matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})")) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * checks if Given String is valid a Date
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean isDate(String val) {

		if (val.matches("^[0-9]{2}/[0-9]{2}/[0-9]{4}")) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * checks if given string is valid marks
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean isMarks(String val) {
		if (isInteger(val)) {
			int i = DataUtility.getInt(val);
			if ( (i <= 100)  &&  (i >= 0)) {
				return true;
			} else {
				return false;
			}

		}/* else if (val.matches("[aA]")) {
			return true;
		}*/
		return false;
	}

	/*
	 * public static boolean hasWhiteSpace(String val){
	 * if(val.trim().matches("[^ ]+")){ return false; } return true; } public
	 * static boolean hasNum(String val){ if(val.trim().matches("[^0-9]+")){
	 * return false; } return true; } public static boolean hasAlphabets(String
	 * val){ if(val.trim().matches("[^a-zA-Z]+")){ return false; } return true;
	 * } public static boolean hasSpecialChar(String val){
	 * if(val.trim().matches("[0-9a-zA-Z]+")){ return false; } return true; }
	 */

	/**
	 * 
	 * checks if the string length is in between min & max
	 * 
	 * @param parameter
	 * @param integer
	 * @param integer
	 * @return boolean
	 */
	public static boolean isLength(String parameter, int i, int j) {
		if (parameter.length() >= i && parameter.length() <= j) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * Test Method mail
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// System.out.println(isPassword("Password@123"));
		// System.out.println(isRollNo("29ayd78"));
		// System.out.println(isMobileNo("4589635217"));
		// System.out.println(isName("Aditya"));
		// System.out.println(hasWhiteSpace("aditya "));
		// System.out.println(hasAlphabets("45"));
		// System.out.println(hasNum("f"));
		// System.out.println(hasSpecialChar("d"));
		// System.out.println(isFirstName("Aditya"));
		// System.out.println(isLastName("Joshi"));
		System.out.println(isMarks("99"));
		// System.out.println(isDate("444"));
	}

}

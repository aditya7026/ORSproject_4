package in.co.rays.project_4.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * Converts data into other data formats
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) Sunil OS
 *
 */
public class DataUtility {
	
	
	
	//date formats
	
	
	public static final String APP_DATE_FORMAT = "dd/MM/yyyy";
	
	public static final String SERCH_DATE_FORMAT = "yyyy-MM-dd";

    public static final String APP_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            APP_DATE_FORMAT);

    private static final SimpleDateFormat timeFormatter = new SimpleDateFormat(
            APP_TIME_FORMAT);

    private static final SimpleDateFormat formatter2 = new SimpleDateFormat(SERCH_DATE_FORMAT);
    
    /**
     * 
     * trims leading and tailing spaces 
     * 
     * @param String
     * @return String
     */
    
    public static String getString(String val) {
        if (DataValidator.isNotNull(val)) {
            return val.trim();
        } else {
            return val;
        }
    }

    /**
     * 
     * Converts object to string
     * 
     * @param Object
     * @return Sring
     */
    public static String getStringData(Object val) {
        if (val != null) {
            return val.toString();
        } else {
            return "";
        }
    }

   
    /**
     * 
     * Converts String to integer
     * 
     * @param String
     * @return integer
     */
    public static int getInt(String val) {
        if (DataValidator.isInteger(val)) {
            return Integer.parseInt(val);
        } else {
            return 0;
        }
    }

    
    /**
     * 
     * Converts String to Long
     * 
     * @param String
     * @return long 
     */
    public static long getLong(String val) {
        if (DataValidator.isLong(val)) {
            return Long.parseLong(val);
        } else {
            return 0;
        }
    }
    
    /**
     * 
     * Converts String to Date
     * 
     * @param String
     * @return Date
     */
    public static Date getDate(String val) {
        Date date = null;
        try {
            date = formatter.parse(val);
        } catch (Exception e) {

        }
        return date;
    }

   
    /**
     * 
     * Converts String to date
     * 
     * @param date
     * @return String
     */
    public static String getDateString(Date date) {
        try {
            return formatter.format(date);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 
     * specific Date Pattern for search operations
     * 
     * @param String
     * @return Date
     */
    public static Date getSDate(String val) {
        Date date = null;
        try {
            date = formatter2.parse(val);
        } catch (Exception e) {

        }
        return date;
    }
    
    /**
     * 
     * returns String for date 
     * 
     * @param date
     * @return String
     */
    public static String getSDateString(Date date) {
        try {
            return formatter2.format(date);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 
     * Converts Sting to Timestamp
     * 
     * @param String
     * @return Timestamp
     */
    public static Timestamp getTimestamp(String val) {

        Timestamp timeStamp = null;
        try {
            timeStamp = new Timestamp((timeFormatter.parse(val)).getTime());
        } catch (Exception e) {
            return null;
        }
        return timeStamp;
    }

    /**
     * 
     * converts long to Timestamp
     * 
     * @param long
     * @return Timestamp
     */
    public static Timestamp getTimestamp(long l) {

        Timestamp timeStamp = null;
        try {
            timeStamp = new Timestamp(l);
        } catch (Exception e) {
            return null;
        }
        return timeStamp;
    }

    /**
     * 
     * Returns current timestamp
     * 
     * @return
     */
    public static Timestamp getCurrentTimestamp() {
        Timestamp timeStamp = null;
        try {
            timeStamp = new Timestamp(new Date().getTime());
        } catch (Exception e) {
        }
        return timeStamp;

    }

    /**
     * 
     * converts timestamp to long
     * 
     * @param Timestamp
     * @return Long
     */
    public static long getTimestamp(Timestamp tm) {
        try {
            return tm.getTime();
        } catch (Exception e) {
            return 0;
        }
    }
    /**
     * 
     * finds if student has passed or not overall
     * 
     * @param percentage
     * @param physics
     * @param chemistry
     * @param math
     * @return String
     */
    public static String result(double percentage,int physics,int chemistry,int math){
    	if(percentage>=35.0 && physics>=35  && chemistry >= 35 && math >= 35 ){
    		return "<font color=\"green\" ><b>PASS</b></font>";
    	}
    	else {
    		return "<font color=\"red\" ><b>FAIL</b></font>";
    	}
    }
    /**
     * 
     * finds if student has passed in individual subjects
     * 
     * @param int marks
     * @return String
     */
    public static String subResult(int marks){
    	if(marks>=35){
    		return "<font color=\"green\" ><b>PASS</b></font>";
    	}
    	else {
    		return "<font color=\"red\" ><b>FAIL</b></font>";
    	}
    }
    /**
     * 
     * Test Class main
     * 
     * @param args
     */
    public static void main(String[] args) {
        //System.out.println(getInt("124"));
        System.out.println(getDate("10/12/1990"));
    }
}

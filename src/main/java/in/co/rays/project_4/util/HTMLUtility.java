package in.co.rays.project_4.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import in.co.rays.project_4.bean.BaseBean;
import in.co.rays.project_4.bean.DropDownListBean;

import java.util.Collections;


/**
 * 
 * provides functionality like dropdownlist in jsp pages
 * 
 * @author Aditya
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class HTMLUtility {

	/**
	 * @param name
	 * @param selectedVal
	 * @param map
	 * @return String
	 */
	public static String getList(String name,String selectedVal, HashMap<String, String> map){
		StringBuffer sb = new StringBuffer("<select style='width:164.8px; height:22px' name='"+name +"'>");
		
		Set<String> keys = map.keySet();
		String val = null;
		boolean select= true;
		if(select){
			sb.append("<option  value=''>Select</option>");
		}
		for(String key:keys){
			val = map.get(key);
			if(key.trim().equals(selectedVal)){
				sb.append("<option selected value='"+key+"'>"+val + "</option>");
			}else {
				sb.append("<option value='"+key+"'>"+val+"</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

    
    /**
     * @param name
     * @param selectedVal
     * @param list
     * @return
     */
    public static String getList(String name,String selectedVal,List list){
    	
    	Collections.sort(list);
    	
    	List<DropDownListBean> dd = (List<DropDownListBean>)list;
    	
    	StringBuffer sb = new StringBuffer("<select style='width:164.8px; height:22px' name='"+name+"'>");
    	
    	String key = null;
    	String val = null;
    	boolean select = true;
    	if(select){
    		sb.append("<option selected value=''>Select</option>");
    	}
    	for(DropDownListBean obj:dd){
    		key = obj.getKey();
    		val = obj.getValue();
    		
    			if(key.trim().equals(selectedVal)){
    				sb.append("<option selected value='"+key+"'>"+val+"</option>");
    			} else {
    				sb.append("<option value='"+key+"'>"+val+"</option>");
    			}
    		}
    		sb.append("</select>");
    		return sb.toString();
    	
    }
    
    /**
     * @param name
     * @param selectedVal
     * @param map
     * @return
     */
    public static String getSList(String name,String selectedVal, HashMap<String, String> map){
		StringBuffer sb = new StringBuffer("<select style=' width:164.8px;font-size: 14px;height:32.8px' name='"+name +"'>");
		
		Set<String> keys = map.keySet();
		String val = null;
		boolean select= true;
		if(select){
			sb.append("<option  value=''>Select</option>");
		}
		for(String key:keys){
			val = map.get(key);
			if(key.trim().equals(selectedVal)){
				sb.append("<option selected value='"+key+"'>"+val + "</option>");
			}else {
				sb.append("<option value='"+key+"'>"+val+"</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

    
    /**
     * @param name
     * @param selectedVal
     * @param list
     * @return
     */
    public static String getSList(String name,String selectedVal,List list){
    	
    	Collections.sort(list);
    	
    	List<DropDownListBean> dd = (List<DropDownListBean>)list;
    	
    	StringBuffer sb = new StringBuffer("<select style=' width:164.8px;font-size: 14px;height:32.8px' name='"+name+"'>");
    	
    	String key = null;
    	String val = null;
    	boolean select = true;
    	if(select){
    		sb.append("<option selected value=''>Select</option>");
    	}
    	for(DropDownListBean obj:dd){
    		key = obj.getKey();
    		val = obj.getValue();
    		
    			if(key.trim().equals(selectedVal)){
    				sb.append("<option selected value='"+key+"'>"+val+"</option>");
    			} else {
    				sb.append("<option value='"+key+"'>"+val+"</option>");
    			}
    		}
    		sb.append("</select>");
    		return sb.toString();
    	
    }
    
    /**
     * @param name
     * @param selectedVal
     * @param map
     * @param coustom
     * @return
     */
    public static String getCList(String name,String selectedVal, HashMap<String, String> map,String coustom){
		StringBuffer sb = new StringBuffer("<select "+coustom+" name='"+name +"'>");
		
		Set<String> keys = map.keySet();
		String val = null;
		boolean select= true;
		if(select){
			sb.append("<option  value=''>Select</option>");
		}
		for(String key:keys){
			val = map.get(key);
			if(key.trim().equals(selectedVal)){
				sb.append("<option selected value='"+key+"'>"+val + "</option>");
			}else {
				sb.append("<option value='"+key+"'>"+val+"</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

}

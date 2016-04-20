package com.qind.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class StringUtils {

	/**
	 * 判断字符串是否为空
	 * @param str 
	 * @return boolean
	 * @date：2014-11-19 下午08:04:17
	 * @author QinDong
	 * history
	 */
	
	public static boolean isEmpty(String str){
		
		return str == null || str.length() == 0;
		
	}
	
	/**
	 * 判断字符串是否为JSON格式
	 * @param str 
	 * @return boolean
	 * @date：2014-11-19 下午08:04:17
	 * @author QinDong
	 * history
	 */
	
	public static boolean isJSON(String str){
		
		if(StringUtils.isEmpty(str))
			return false;
		
		try { 
			JSON.parseObject(str);
		} catch (JSONException e) { 
			return false; 
		} 
		
		return true; 
		
	}
	
	
}

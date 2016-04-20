package com.qind.common.jsoup;

import java.util.Map;

import org.jsoup.nodes.Element;

/**
 * Jsoup工厂类
 * @author QinDong
 * @version 1.1, 2015/12/29
 * 
 */
public class JsoupFactory {
	
	/**
	 * 获取加载目标体
	 * @param
	 * @author QinDong
	 * @return 返回加载目标体
	 */
	public static Element getBody(Map<String,Object> property){
		
		JsoupLoad jsoup = new JsoupLoad();
		jsoup.loadProperty(property);
		//jsoup.setCookies(userCookies());
		
		Element body = jsoup.loadBody();
		if(body == null) 
			return null; 
		return body;
	}
	
}

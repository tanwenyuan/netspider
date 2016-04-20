package com.qind.common.jsoup;

import java.io.File;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.qind.common.base.ConstanceLoad;
import com.qind.common.util.StringUtils;

/**
 * @describe：<p>使用Jsoup获取网页内容</p>
 * @author QinDong
 * @date  2015-09-23 17:10:00
 */
public class JsoupLoad {

	private static  Log log = LogFactory.getLog(JsoupLoad.class);
	
	/**
	 * 加载网页内容
	 * @param loadObject
	 * @param loadMode
	 * @return
	 */
	public Document loadDocument(){
		
		//加载网页对象
		Document doc = null;
		try {
			
			if(StringUtils.isEmpty(loadMode))
				throw new Exception("Please set the loadMode");
			if(StringUtils.isEmpty(loadObject))
				throw new Exception("Please set the loadObject");
			
			if(ConstanceLoad.LOAD_MODE_URL.equals(loadMode))//通过url载入
				doc = connection.get();
			else if(ConstanceLoad.LOAD_MODE_STR.equals(loadMode))//通过字符串载入
				doc = Jsoup.parse(loadObject);
			else if(ConstanceLoad.LOAD_MODE_FILE.equals(loadMode))//通过文件载入
				doc = Jsoup.parse(new File(loadObject), "UTF-8");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("*******************************网页对象加载失败**********************************");
			e.printStackTrace();
		}
		
		return doc;
	}
	
	/**
	 * 加载网页Body
	 * @param loadObject
	 * @param loadMode
	 * @return
	 */
	public Element loadBody(){
		
		Document document = loadDocument(); 
		if(document == null)
			return null;
		return document.body();
		
	}
	
	/**
	 * 加载网页链接
	 * @param loadObject
	 * @param loadMode
	 * @return
	 */
	public Connection loadConnection(){
		
		connection = Jsoup.connect(loadObject).timeout(ConstanceLoad.LOAD_TIME_OUT);
		return connection;
		
	}
	
	/**
	 * 设置加载属性
	 * @param 
	 * @return
	 */
	public void loadProperty(Map<String,Object> property){
		
		loadObject = (String) property.get("loadObject");
		loadMode = (String) property.get("loadMode");
		if(ConstanceLoad.LOAD_MODE_URL.equals(loadMode))
			loadConnection();
		
	}
	
	/**
	 * 加载网页链接
	 * @param loadObject
	 * @param loadMode
	 * @return
	 */
	public void setCookies(Map<String,String> cookies){
		
		connection = connection.cookies(cookies);
		
	}
	
	
	private Connection connection ;
	private String loadObject ;
	private String loadMode ;

}

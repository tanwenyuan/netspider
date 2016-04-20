package com.qind.spider.zhihu.backup;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.write.WritableCellFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qind.common.base.ConstanceLoad;
import com.qind.common.base.ConstanceProperties;
import com.qind.common.jsoup.JsoupLoad;
import com.qind.common.queue.AllUrlQueue;
import com.qind.common.util.HttpClientUtil;
import com.qind.common.util.jxl.Excel;
import com.qind.common.util.jxl.ExcelCell;
import com.qind.common.util.jxl.ExcelSheet;
import com.qind.common.util.jxl.JexcelUtil;
import com.qind.common.util.properties.OperateProperties;

/**
 * @describe：<p>获取知乎某名称用户资料</p>
 * @author QinDong
 * @date  2015-09-23 17:10:00
 */
public class ZhiHuAccess {

	private static Log log = LogFactory.getLog(ZhiHuAccess.class);
	private static Properties normalProperties;
	
	static {
		String name = "com.qind.common.util.properties.normalProperties";
        setNormalProperties(OperateProperties.loadProperties(name, OperateProperties.BY_RESOURCEBUNDLE));
	}

	public static void main(String[] args) {
		
		//writeUserInfo();
		//requireLinks();
		ZhiHuSaveLinks.requireAllLinks();
		System.out.println(AllUrlQueue.size());
		
	}
	
	public static void writeUserInfo(){
		
		//字体设置
		WritableCellFormat cf = null;
		WritableCellFormat cf1 = ExcelCell.cellTitle;
		WritableCellFormat cf2 = ExcelCell.cellContent;
		
		List<Map<String,Object>> allUserInfo = allUserInfo();
		Map<String,Object> eachUserInfo = null;
		ExcelCell[][] allCell = new ExcelCell[allUserInfo.size()][7];
		for(int i = 0;i<allUserInfo.size();i++){
			eachUserInfo = allUserInfo.get(i);
			log.info("**************************************************写入用户["+eachUserInfo.get("eachInfo0")+"]数据");
			cf = cf2;
			if(i == 0)
				cf = cf1;
			for(int j = 0;j<7;j++){
				allCell[i][j] = new ExcelCell(i, j, eachUserInfo.get("eachInfo"+j), cf);
			}
		}
		
		log.info("**********************************************写入用户信息  Start**************************************************");
		String targetFile = normalProperties.getProperty(ConstanceProperties.WRITEXLSFILE);
		File file = new File(targetFile);
		if(file.exists()) 
			file.delete();
		
		ExcelSheet excelSheet = new ExcelSheet();
		excelSheet.setSheetName("爬取知乎用户内容");
		excelSheet.setExcelCell(allCell);
		
		ExcelSheet[] allSheet = new ExcelSheet[1];
		allSheet[0] = excelSheet;
		
		Excel excel = new Excel();
		excel.setUrlPath(targetFile);
		excel.setExcelSheet(allSheet);
		
		JexcelUtil.writeExcel(excel);
		log.info("**********************************************写入用户信息      End**************************************************");
		
	}
	
	public static List<Map<String,Object>> allUserInfo(){
		
		List<Map<String,Object>> allUserInfo =new ArrayList<Map<String,Object>>();
		Map<String,Object> eachUserInfo = null;
		allUserInfo.add(eachUserTitle());
		
		List<String>  addrList = requireAllLinks();
		log.info("***********************************获取用户数据   Start**********************************************");
		for(String linkAddr:addrList){
			log.info("***********************************获取用户URL :"+linkAddr);
			eachUserInfo = eachUserInfo(linkAddr);
			log.info("***********************************获取用户数据 :"+eachUserInfo.toString());
			if(eachUserInfo != null)
				allUserInfo.add(eachUserInfo);
			
		}
		log.info("***********************************获取用户数据      End**********************************************");
		
		return allUserInfo;
	}
	
	public static Map<String,Object> eachUserInfo(String linkAddr){
		
		/*********************************************参数配置区    Start***********************************************/
		/*通过url载入    Start*/
		String loadObject = linkAddr;
		String loadMode = ConstanceLoad.LOAD_MODE_URL;
		Map<String,Object> property = new HashMap<String,Object>();
		property.put("loadObject", loadObject);
		property.put("loadMode", loadMode);
		
		JsoupLoad jsoup = new JsoupLoad();
		jsoup.loadProperty(property);
		jsoup.setCookies(userCookies());
		/*通过url载入      End*/
		
		Element body = jsoup.loadBody();
		if(body == null) 
			return null; 
		/*********************************************参数配置区      End***********************************************/
		
		/*********************************************用户信息区    Start***********************************************/
		//用户名称
		String userName = null;
		//用户签名
		String userSign = null;
		//用户居住地址
		String userAddr = null;
		//用户工作
		String userJob = null;
		//用户职业经历
		String userProHistory = null;
		//用户职业经历
		String userEduHistory = null;
		//用户关注话题
		List<String> userCareTopic = new ArrayList<String>();
		
		userName = body.select("div.title-section").select(".ellipsis").select("a.name").text();
		userSign = body.select("div.title-section").select(".ellipsis").select("span.bio").text();
		userAddr = body.select("div.item").select(".editable-group").select("span.info-wrap").select("span.location").select("span.item").text();
		userJob  = body.select("div.item").select(".editable-group").select("span.info-wrap").select("span.business").select("span.item").text();
		userProHistory = body.select(".zm-profile-module").select(".zg-clear").get(0).select(".zm-profile-module-desc").select(".zg-gray").text();
		userEduHistory = body.select(".zm-profile-module").select(".zg-clear").get(2).select(".zm-profile-module-desc").select(".zg-gray").text();
		
		Iterator<Element> elements = body.select("#zh-profile-following-topic").select("a").select("img").iterator();
		while(elements.hasNext()){
			userCareTopic.add(elements.next().attr("title"));
		}
		/*********************************************用户信息区        End***********************************************/
		
		Map<String,Object> eachMap = new HashMap<String,Object>();
		eachMap.put("eachInfo0", userName);//用户名称
		eachMap.put("eachInfo1", userSign);//用户签名
		eachMap.put("eachInfo2", userAddr);//用户居住地
		eachMap.put("eachInfo3", userJob);//用户工作
		eachMap.put("eachInfo4", userProHistory);//用户职业经历
		eachMap.put("eachInfo5", userEduHistory);//用户教育经历
		eachMap.put("eachInfo6", userCareTopic.toString());//用户关注话题
		
		return eachMap;
		
	}
	
	public static Map<String,Object> eachUserTitle(){
		
		Map<String,Object> eachMap = new HashMap<String,Object>();
		eachMap.put("eachInfo0", "用户名称");//用户名称
		eachMap.put("eachInfo1", "用户签名");//用户签名
		eachMap.put("eachInfo2", "用户居住地");//用户居住地
		eachMap.put("eachInfo3", "用户工作");//用户工作
		eachMap.put("eachInfo4", "用户职业经历");//用户职业经历
		eachMap.put("eachInfo5", "用户教育经历");//用户教育经历
		eachMap.put("eachInfo6", "用户关注话题");//用户关注话题
		
		return eachMap;
		
	}
	
	public static List<String> requireAllLinks(){
		
		List<String> allLinkList = new ArrayList<String>();
		for(int i = 1;i<100;i++){
			allLinkList.addAll(requireLinks(i*10));
		}
		return allLinkList;
	}
	
	/**
	 * @param url
	 * @return
	 */
	public static List<String> requireLinks(int offset){
		
		List<String> linkList = null;
		String url = "http://www.zhihu.com/r/search?q=%E5%BE%90%E5%90%9B&type=people&offset="+offset;
		String reponseContent = HttpClientUtil.doGet(url);
		JSONObject json = JSON.parseObject(reponseContent);
		try {
			String newStr = new String(json.get("htmls").toString().getBytes(), "UTF-8");
			log.info("***********************************获取目标区的内容   Start**********************************************");
			log.info(newStr);
			log.info("***********************************获取目标区的内容       End**********************************************");
			
			//加载网页对象
			Document doc = Jsoup.parse(newStr);
			Elements resultLinks = doc.select("a");
			String userLink = null;
			linkList = new ArrayList<String>();
			for (Element link : resultLinks) {
				userLink = link.attr("href");
				userLink = userLink.replace("\"", "");
				userLink = userLink.replace("\\", "");
				String regEx="-[0-9]{2,3}$";
				String linkHead = "http://www.zhihu.com";
		        Pattern pattern = Pattern.compile(regEx);  
		        Matcher matcher = pattern.matcher(userLink);
		        boolean matchFlag = matcher.find();
		        String linkAddr = linkHead+userLink+"/about";
		        if(matchFlag&&!linkList.contains(linkAddr)) 
		        	linkList.add(linkAddr);
	        }
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		return linkList;
		
	}
	
	/**
	 * 设置当前用户cookie
	 * @param
	 * @return
	 */
	public static Map<String,String> userCookies(){
		
		Map<String,String> cookies = new HashMap<String,String>();
		//存放登录用户Cookie

		return cookies;
		
	}

	public static Properties getNormalProperties() {
		return normalProperties;
	}

	public static void setNormalProperties(Properties normalProperties) {
		ZhiHuAccess.normalProperties = normalProperties;
	}
	
}

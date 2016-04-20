package com.qind.spider.zhihu.title.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;

import com.qind.common.base.ConstanceLoad;
import com.qind.common.jsoup.JsoupFactory;
import com.qind.common.util.HttpClientUtil;
import com.qind.common.util.StringUtils;
import com.qind.spider.zhihu.title.STitleService;

public class STitleServiceImpl implements STitleService{

	public List<String> toppicTitle(String linkAddr ,int page) {
		// TODO Auto-generated method stub
		
		//String url = "https://www.zhihu.com/topic/19629329/top-answers?page="+page;
		String url = linkAddr + page;
		String groupTitle = HttpClientUtil.doGet(url);
		
		if(StringUtils.isEmpty(groupTitle))
			return null;
		
		/*通过url载入    Start*/
		String loadObject = groupTitle;
		String loadMode = ConstanceLoad.LOAD_MODE_STR;
		Map<String,Object> property = new HashMap<String,Object>();
		property.put("loadObject", loadObject);
		property.put("loadMode", loadMode);
		/*通过url载入      End*/
		
		Element body = JsoupFactory.getBody(property);
		
		//用户关注话题
		List<String> userCareTopic = new LinkedList<String>();
		Iterator<Element> elements = body.select(".feed-main").select(".content").select(".question_link").iterator();
		while(elements.hasNext()){
			userCareTopic.add(elements.next().text());
		}
		
		return userCareTopic;
	}

}

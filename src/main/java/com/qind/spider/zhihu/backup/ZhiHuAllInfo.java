package com.qind.spider.zhihu.backup;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ZhiHuAllInfo {
	
	private static LinkedList<Map<String,Object>> allInfo = new LinkedList<Map<String,Object>>();
	
	public synchronized static void addInfo(Map<String,Object> eachInfo){

		allInfo.add(eachInfo);
		
	}
	
	public synchronized static Map<String,Object> outInfo(){
		
		return allInfo.removeFirst();
		
	}
	
	public synchronized static int size(){
		
		return allInfo.size();
		
	}
	
	public static List<Map<String,Object>> allElement(){
		
		return allInfo;
		
	}
}

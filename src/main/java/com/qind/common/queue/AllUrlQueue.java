package com.qind.common.queue;

import java.util.LinkedList;
import java.util.List;

/**
 * @describe：<p>所有URL集合队列</p>
 * @author QinDong
 * @date  2015-09-23 17:10:00
 */
public class AllUrlQueue {
	
	private static LinkedList<String>  allUrlQueue = new LinkedList<String>();
	
	public synchronized static void addUrl(String url){
		
		allUrlQueue.add(url);
		
	}
	
	public synchronized static void addFirtUrl(String url){
		
		allUrlQueue.addFirst(url);
		
	} 
	
	public synchronized static String outUrl(){
		
	   return allUrlQueue.removeFirst();
		
	}
	
	public synchronized static boolean isEmpty(){
		
		return allUrlQueue.isEmpty();
		
	}
	
	public static List<String> allUrlQueue(){
		
		return allUrlQueue;
		
	}
	
	public synchronized static int size(){
		
		return allUrlQueue.size();
		
	}
	
	public synchronized static boolean isContains(String url){
		
		return allUrlQueue.contains(url);
		
	}
}

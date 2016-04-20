package com.qind.spider.zhihu.backup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ZhiHuThread implements Runnable{
	
	private static Log log = LogFactory.getLog(ZhiHuThread.class);
	
	public ZhiHuThread(String threadName){
		this.threadName = threadName;
	}
	

	public void run() {
		// TODO Auto-generated method stub
		log.info("***********************************线程"+threadName+"开始工作");
		ZhiHuOper.saveUserInfo();
		
	}
	
	String threadName;
	
}

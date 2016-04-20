package com.qind.spider.zhihu.backup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ZhiHuSaveLinks {

	private static Log log = LogFactory.getLog(ZhiHuSaveLinks.class);
	
	public static void requireAllLinks(){
		
		for(int i = 1;i<2;i++)
			ZhiHuOper.requireLinks(i*10);
	}
	
}

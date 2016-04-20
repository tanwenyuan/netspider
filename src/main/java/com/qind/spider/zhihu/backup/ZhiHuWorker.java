package com.qind.spider.zhihu.backup;

import java.util.Date;


public class ZhiHuWorker {

	public static void main(String[] args) {
		
		ZhiHuSaveLinks.requireAllLinks();
		// 创建worker线程并启动
		for(int i = 1; i <= 1; i++){
			new Thread(new ZhiHuThread(new Date().toString())).start();
		}
		
		ZhiHuOper.writeUserInfo();
	}
	
}

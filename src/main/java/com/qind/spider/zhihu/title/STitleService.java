package com.qind.spider.zhihu.title;

import java.util.List;

/**
 * 标题接口类
 * @author QinDong
 * @version 1.1, 2015/12/29
 * 
 */
public interface STitleService {
	
	/**
	 * 获取所有标题接口方法
	 * @param page 页码
	 * @author QinDong
	 * @return 返回所有标题
	 */
	public List<String> toppicTitle(String linkAddr ,int page);
	
}

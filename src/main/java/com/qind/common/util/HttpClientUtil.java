package com.qind.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @describe：<p>GET POST 请求获取内容 公用方法</p>
 * @author:QinDong
 * @date:2014-11-19 下午08:07:18
 * @version:1.0
 */
public class HttpClientUtil {

	private static  Log log = LogFactory.getLog(HttpClientUtil.class);

	/**
	 * @param url 请求地址
	 * @param paramMap 请求参数 paramName->paramValue
	 * @param reqCoding 请求的编码方式
	 * @param resCoding 响应的编码方式
	 * @return responseStr
	 */
	public static void doPost(String url,Map<String,String> paramMap,String reqCoding,String resCoding) {

		// 创建HttpClient实例
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建HttpPost实例
		HttpPost httpPost = new HttpPost(url);
		// 初始化参数
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		if(paramMap!=null)
		{
			for(String paramName :paramMap.keySet()){
				formParams.add(new BasicNameValuePair(paramName, String.valueOf(paramMap.get(paramName))));
			}
		}
		//模拟表单数据提交
		UrlEncodedFormEntity uefEntity = null;
		try {
			uefEntity = new UrlEncodedFormEntity(formParams, reqCoding);
			httpPost.setEntity(uefEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					log.info("********************************Response content: "+ EntityUtils.toString(entity, resCoding));
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static String doGet(String url){
		
		return doGet(url,null);
		
	}
	
	/**
	 * @param url 请求地址
	 * @param paramMap 请求参数 paramName->paramValue
	 * @param reqCoding 请求的编码方式
	 * @param resCoding 响应的编码方式
	 * @return responseStr
	 */
	public static String doGet(String url,Map<String,Object> headers) {

		//返回内容
		String responseStr = null;
		
		// 创建HttpClient实例
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(url);
			
			//设置header
			if(headers != null &&headers.size() != 0)
				setHeaders(httpGet,headers);
			
			//执行GET请求
			CloseableHttpResponse response = httpClient.execute(httpGet);
			
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				// 打印响应状态
				if (entity != null) {
					responseStr = EntityUtils.toString(entity);
					// 打印响应内容长度
					//log.info("********************************"+"Response content length: "+ entity.getContentLength());
					// 打印响应内容
					//log.info("********************************"+"Response content: "+ responseStr);
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return responseStr;

	}


	/**
	 * @describe 设置http头部参数
	 * @param HttpGet
	 * @param headers
	 * @return
	 */
	public static void setHeaders(HttpGet httpGet,Map<String,Object> headers) {
		
		for(String header :headers.keySet())
			httpGet.addHeader(new BasicHeader(header,(String) headers.get(header)));
		
	}
	
}

/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu
 */
package com.soledede.classfy.bayes.client;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.protocol.HTTP;

/**
 * @Title: Test Bayes
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年6月27日
 * @Version:1.1.0
 */
public class ClientBayesPost {
	public static void main(String[] args) throws Exception {
		httpClientTest();
	}

	public void testsss() {
		/*
		 * CloseableHttpClient httpclient = HttpClients.createDefault(); String
		 * url = "http://localhost:8080/bct/cloudspace"; try { HttpPost httpPost
		 * = new HttpPost(url); List <NameValuePair> nvps = new ArrayList
		 * <NameValuePair>(); nvps.add(new BasicNameValuePair("folderName",
		 * "media")); nvps.add(new BasicNameValuePair("subFolderName", "美丽"));
		 * httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		 * CloseableHttpResponse response2 = httpclient.execute(httpPost);
		 * 
		 * try { System.out.println(response2.getStatusLine()); HttpEntity
		 * entity2 = response2.getEntity(); // do something useful with the
		 * response body // and ensure it is fully consumed
		 * EntityUtils.consume(entity2); } finally { response2.close(); } }
		 * finally { httpclient.close(); }
		 */
	}

	public static void httpClientTest() {
		HttpClient httpClient = new HttpClient();
		// HttpHeaders headers = new HttpHeaders();
		// 设置ContentType标明数据是JSON数据,否则报415(Unsupported Media Type)
		// 此处必须和REST接口的RequestMapping的ContentType对应
		// headers.setContentType(MediaType.APPLICATION_JSON);
		// HttpEntity<Object> requestEntity = new HttpEntity<Object>(userJson,
		// headers);
		// System.out.println(requestEntity.getBody().toString());
		// rt.exchange(urlPost, HttpMethod.POST, requestEntity, null);
		String url = "http://localhost:8080/bct/cloudspace";
		PostMethod postMethod = new PostMethod(url);
		// 填入各个表单域的值
		NameValuePair[] data = { new NameValuePair("folderName", "media"),
				new NameValuePair("subFolderName", "美丽"), };
		// 将表单的值放入postMethod中
		// postMethod.setRequestHeader();
		// 执行postMethod
		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(postMethod);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
		// 301或者302
		if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
				|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
			// 从头中取出转向的地址
			Header locationHeader = postMethod.getResponseHeader("location");
			String location = null;
			if (locationHeader != null) {
				location = locationHeader.getValue();
				System.out.println("The page was redirected to:" + location);
			} else {
				System.err.println("Location field value is null.");
			}
			return;
		} else {
			System.out.println(postMethod.getStatusLine());
			String str = "";
			try {
				str = postMethod.getResponseBodyAsString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(str);
		}
		postMethod.releaseConnection();
		return;
	}
}

/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu
 */
package com.soledede.classfy.bayes.client;

/**
 * @Title: Test Bayes
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年6月27日
 * @Version:1.1.0
 */
public class ClientBayes {
	public static void main(String[] args) throws Exception {
		/*
		 * CloseableHttpClient httpclient = HttpClients.createDefault(); String
		 * url = "http://localhost:8080/bct/bayes"; try { HttpGet httpGet = new
		 * HttpGet(url); CloseableHttpResponse response1 =
		 * httpclient.execute(httpGet); // The underlying HTTP connection is
		 * still held by the response object // to allow the response content to
		 * be streamed directly from the network socket. // In order to ensure
		 * correct deallocation of system resources // the user MUST call
		 * CloseableHttpResponse#close() from a finally clause. // Please note
		 * that if response content is not fully consumed the underlying //
		 * connection cannot be safely re-used and will be shut down and
		 * discarded // by the connection manager. try {
		 * System.out.println(response1.getStatusLine());
		 * 
		 * HttpEntity entity1 = response1.getEntity(); BufferedReader bi=new
		 * BufferedReader(new InputStreamReader(entity1.getContent())); String r
		 * =""; while((r=bi.readLine())!=null){ System.out.println(r); } // do
		 * something useful with the response body // and ensure it is fully
		 * consumed EntityUtils.consume(entity1); } finally { response1.close();
		 * }
		 * 
		 * HttpPost httpPost = new HttpPost(url); List <NameValuePair> nvps =
		 * new ArrayList <NameValuePair>(); nvps.add(new
		 * BasicNameValuePair("media", "media")); httpPost.setEntity(new
		 * UrlEncodedFormEntity(nvps)); httpPost.getParams().setParameter(
		 * "http.protocol.cookie-policy",CookiePolicy.BROWSER_COMPATIBILITY);
		 * CloseableHttpResponse response2 = httpclient.execute(httpPost);
		 * 
		 * try { System.out.println(response2.getStatusLine()); HttpEntity
		 * entity2 = response2.getEntity(); // do something useful with the
		 * response body // and ensure it is fully consumed
		 * EntityUtils.consume(entity2); } finally { response2.close(); } }
		 * finally { httpclient.close(); }
		 */
	}
}

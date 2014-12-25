/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.soledede.classfy.bayes.model.UserInputHelp;
import com.soledede.classfy.bayes.util.MyJsonUtil;

/**
 * @Title:
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年6月27日
 * @Version:1.1.0
 */
public class TestPostUserInputUTF8 {
	public static String urlPost1 = "http://localhost:8080/bct/bayes/UserInput";
//http://192.168.4.70:8080/bct/bayes/UserInput?input=%E6%88%91%E6%83%B3%E6%89%BE%E9%A4%90%E5%8E%85&sessionId=1&ctyID=4&lastNodeNo=0&candidateNodeLength=5&candidateResLength=5
	// public static String urlPost1 =
	// "http://192.168.4.70:8080/bct/bayes/UserInput";
	public static void main(String[] args) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(urlPost1);
		post.setHeader("Connection", "close");
		try {
			MyJsonUtil jsonUtil = new MyJsonUtil();

			// 请问下陆家嘴正大广场附近有没有烤鱼的地方 --》我有小孩儿的，而且我喜欢安静点的地方
			// efd094dd-a0bf-44bf-ad12-59782559a381
			// 我想订酒店
			// 上海有好吃的吗 --》上海徐汇，就是徐家汇附近4da6ed8d-2848-4180-b679-12024e8470a4
			// 我们10个人订什么合适啊，我们是湖北人-->浦东新区，八佰伴
			// c984fb1b-3d48-47c1-adfa-fd0c04d3412d
			// 294f080d-8bc0-4cb2-bf8e-b905970ce0e6-->20个人左右，需要安静点的地方--》请问有什么菜系了，我们系那个吃点辣的
			//请问下陆家嘴正大广场附近有没有烤鱼的地方
			String jstring = jsonUtil.fromObject(
					new UserInputHelp("1", 4, 0, "价格多少钱呢", 5, 5))
					.toString();
			System.out.println(jstring);
			StringEntity s = new StringEntity(jstring, "UTF-8"); // 中文乱码在此解决
			s.setContentType("application/json");
			post.setEntity(s);

			HttpResponse res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				String charset = EntityUtils.getContentCharSet(entity);
				String jsonString = EntityUtils.toString(res.getEntity());
				System.out.println(jsonString);
				// JSONObject jsonObj = JSONObject.fromObject(jsonString);

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			client.clearResponseInterceptors();
			post.releaseConnection();
			client.close();
		}
	}

	public static void readResponse(InputStream in) throws Exception {

		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = null;
		while ((line = reader.readLine()) != null) {
			System.out.println(line + "return%%%%%%%%%%%%%%%%");
		}
	}
}

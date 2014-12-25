/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.soledede.classfy.bayes.model.AgentInputHelp;
import com.soledede.classfy.bayes.model.CandidateRes;
import com.soledede.classfy.bayes.model.Reason;
import com.soledede.classfy.bayes.util.MyJsonUtil;

/**
 * @Title:
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年6月27日
 * @Version:1.1.0
 */
public class TestPostUserAgentUTF8 {
	//public static String urlPost1 = "http://localhost:8080/bct/bayes/AgentInput";

	 public static String urlPost1 =
	 "http://192.168.4.70:8080/bct/bayes/AgentInput";
	public static void main(String[] args) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(urlPost1);
		try {
			MyJsonUtil jsonUtil = new MyJsonUtil();

			List<CandidateRes> candidateResList = new ArrayList<CandidateRes>();

			List<Reason> reasonList = new ArrayList<Reason>();
			Reason reason = new Reason(0, "湘菜", "1", "味美,值得推荐",
					"http://xiaomishu.com", 1.0);
			reasonList.add(reason);
			CandidateRes candidateRes = new CandidateRes("res1", 1.2,
					reasonList);
			candidateResList.add(candidateRes);
			// 你要吃烤鱼餐厅吗
			// 请问是在哪个区 --》给您推荐湘菜可以不4da6ed8d-2848-4180-b679-12024e8470a4
			// 请问您需要订那个市区的？==>好的，给您推荐香菜可以吧 c984fb1b-3d48-47c1-adfa-fd0c04d3412d
			// 请问您几个人？
			AgentInputHelp agentInputHelp = new AgentInputHelp("1", 4, 2,
					" 请问是在哪个区？", candidateResList, 1);
			String jstring = jsonUtil.fromObject(agentInputHelp).toString();
			//System.out.println(jstring);
			StringEntity s = new StringEntity(jstring, "UTF-8"); // 中文乱码在此解决
			s.setContentType("application/json");
			post.setEntity(s);
			
			//System.out.println(s.toString());

			HttpResponse res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// HttpEntity entity = res.getEntity();
				// String charset = EntityUtils.getContentCharSet(entity);
				// System.out.println(entity.getContent()+"#########");
				String jsonString = EntityUtils.toString(res.getEntity());
				System.out.println(jsonString);
				System.out.println();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

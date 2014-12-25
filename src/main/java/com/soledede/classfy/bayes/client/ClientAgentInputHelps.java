/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.soledede.classfy.bayes.model.AgentInputHelp;
import com.soledede.classfy.bayes.model.AgentInputRslt;
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
public class ClientAgentInputHelps {
	public static String urlGet = "http://localhost:8080/users/{id}";
	public static String urlPost = "http://localhost:8080/bct/cloudspace/add";
	public static String urlPost1 = "http://localhost:8080/bct/bayes/AgentInput";
	public static RestTemplate rt = new RestTemplate();

	// 从Rest接口获取数据
	public static void getUser(long userId) {
		HttpHeaders headers = new HttpHeaders();
		// 设置Accept表示浏览器支持的MIME类型,此处意思是要返回的类型
		headers.setAccept(MediaType
				.parseMediaTypes(MediaType.APPLICATION_XML_VALUE));
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);

		System.out.println(rt.exchange(urlGet, HttpMethod.GET, requestEntity,
				String.class, userId));
		System.out.println(rt.getForObject(urlGet, String.class, userId));
	}

	// 传JSON数据到REST接口,自动将JSON数据转化成类
	public static void addUserByJson(String userJson) {
		HttpHeaders headers = new HttpHeaders();
		// 设置ContentType标明数据是JSON数据,否则报415(Unsupported Media Type)
		// 此处必须和REST接口的RequestMapping的ContentType对应
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(userJson,
				headers);
		// System.out.println(requestEntity.getBody().toString());
		ResponseEntity<AgentInputRslt> s = rt.exchange(urlPost1,
				HttpMethod.POST, requestEntity, AgentInputRslt.class);
		AgentInputRslt ar = s.getBody();
		System.out.println(ar.getRsltCode() + "\t and \t " + ar.getMessage());
	}

	public static void main(String[] args) {
		// getUser(1);
		// addUserByJson("{\"id\":1,\"username\":\"root\",\"password\":\"root123\"}");
		MyJsonUtil jsonUtil = new MyJsonUtil();

		List<CandidateRes> candidateResList = new ArrayList<CandidateRes>();

		List<Reason> reasonList = new ArrayList<Reason>();
		Reason reason = new Reason(1, "理由1", "理由", "测试推荐理由",
				"http://baidu.com", 2.3);
		reasonList.add(reason);
		CandidateRes candidateRes = new CandidateRes("res1", 1.2, reasonList);
		candidateResList.add(candidateRes);
		AgentInputHelp agentInputHelp = new AgentInputHelp("", 1, 2, "烤鱼餐厅",
				candidateResList, 1);

		String jstring = jsonUtil.fromObject(agentInputHelp).toString();
		System.out.println(jstring);
		addUserByJson(jstring);

	}
}

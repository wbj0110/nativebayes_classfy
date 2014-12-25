/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.model;

import java.io.Serializable;

/**
 * @Title:
 * @Description: 客户输入以及客服的答案 对象
 * @Author:wengbenjue
 * @Since:2014年6月30日
 * @Version:1.1.0
 */
public class QuestionAnswer implements Serializable {

	private String answer; // 返回的问题节点答案
	private String question;// 客户端所输入的咨询内容

	public QuestionAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuestionAnswer(String answer, String question) {
		super();
		this.answer = answer;
		this.question = question;
	}

	public QuestionAnswer(String question) {
		super();
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

}

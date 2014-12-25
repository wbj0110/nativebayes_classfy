/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.model;

import java.io.Serializable;

/**
 * @Title: 分类推荐Input
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年6月27日
 * @Version:1.1.0
 */
public class UserInputHelp implements Serializable {

	private String sessionId;
	private int ctyID;
	private int lastNodeNo = 0;
	private String input;
	private int candidateNodeLength = 10;
	private int candidateResLength = 10;
	private String word ="";//维度关键词

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public int getCtyID() {
		return ctyID;
	}

	public void setCtyID(int ctyID) {
		this.ctyID = ctyID;
	}

	public int getLastNodeNo() {
		return lastNodeNo;
	}

	public void setLastNodeNo(int lastNodeNo) {
		this.lastNodeNo = lastNodeNo;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public int getCandidateNodeLength() {
		return candidateNodeLength;
	}

	public void setCandidateNodeLength(int candidateNodeLength) {
		this.candidateNodeLength = candidateNodeLength;
	}

	public int getCandidateResLength() {
		return candidateResLength;
	}

	public void setCandidateResLength(int candidateResLength) {
		this.candidateResLength = candidateResLength;
	}
	
	

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public UserInputHelp() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public UserInputHelp(String sessionId, int ctyID, int lastNodeNo,
			String input, int candidateNodeLength, int candidateResLength) {
		super();
		this.sessionId = sessionId;
		this.ctyID = ctyID;
		this.lastNodeNo = lastNodeNo;
		this.input = input;
		this.candidateNodeLength = candidateNodeLength;
		this.candidateResLength = candidateResLength;
	}

}

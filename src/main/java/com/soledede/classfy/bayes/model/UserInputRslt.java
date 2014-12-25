/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu   
 */
package com.soledede.classfy.bayes.model;

import java.io.Serializable;

/**
 * @Title: 返回用户实体bean
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年6月27日
 * @Version:1.1.0
 */
public class UserInputRslt implements Serializable {

	private int rsltCode;// 调用成功以否状态码。调用成功，该值为0
	private String message;// 调用不成功，返回不成功原因
	private RsltDetail rslt;// 调用不成功，返回不成功原因
	private String sessionId;// 返回sessionId

	public UserInputRslt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserInputRslt(int rsltCode, String message, RsltDetail rslt) {
		super();
		this.rsltCode = rsltCode;
		this.message = message;
		this.rslt = rslt;
	}

	public UserInputRslt(int rsltCode, String message, RsltDetail rslt,
			String sessionId) {
		super();
		this.rsltCode = rsltCode;
		this.message = message;
		this.rslt = rslt;
		this.sessionId = sessionId;
	}

	public int getRsltCode() {
		return rsltCode;
	}

	public void setRsltCode(int rsltCode) {
		this.rsltCode = rsltCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public RsltDetail getRslt() {
		return rslt;
	}

	public void setRslt(RsltDetail rslt) {
		this.rslt = rslt;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}

/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.model;

import java.io.Serializable;

/**
 * @Title: 返回code
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年7月11日
 * @Version:1.1.0
 */
public class Rslt implements Serializable {

	private int rsltCode; // 调用成功以否状态码。
	private String message; // 调用成功以否状态码。

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

}

/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.model;

/**
 * @Title:
 * @Description: 1001 SessionId不能为null! 1002 分类成功! 1003 分类器分类失败!
 * @Author:wengbenjue
 * @Since:2014年6月30日
 * @Version:1.1.0
 */
public enum ReturnCode {
	SUCCESS(0), ERROR(1), NOSESSIONID(1001), CLLASSFI_SUCESS(1002), CLASSFI_ERROR(
			1003);

	private int code;

	private ReturnCode(int code) {
		this.code = code;
	}

	public int value() {
		return this.code;
	}

	@Override
	public String toString() {
		return String.valueOf(this.code);
	}

}

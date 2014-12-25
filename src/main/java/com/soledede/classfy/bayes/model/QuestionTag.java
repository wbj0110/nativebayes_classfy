/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.model;

/**
 * @Title: 问题标签
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年6月27日
 * @Version:1.1.0
 */
public class QuestionTag {

	private String tag; // 标签
	private int tagType; // 标签类型

	public QuestionTag() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuestionTag(String tag, int tagType) {
		super();
		this.tag = tag;
		this.tagType = tagType;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getTagType() {
		return tagType;
	}

	public void setTagType(int tagType) {
		this.tagType = tagType;
	}

}

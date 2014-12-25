/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.model;

/**
 * @Title:
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年6月27日
 * @Version:1.1.0
 */
public class Reason {

	private int reasonId; // 推荐理由Id
	private String tag; // 推荐理由标签
	private String tagType; // 推荐理由标签类型
	private String reasonDetail; // 推荐具体文字
	private String picUrl; // 推荐理由相应的图片地址，有UI模块传入
	private Double possibility; // 概率

	public Reason() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Reason(int reasonId, String tag, String tagType,
			String reasonDetail, String picUrl, Double possibility) {
		super();
		this.reasonId = reasonId;
		this.tag = tag;
		this.tagType = tagType;
		this.reasonDetail = reasonDetail;
		this.picUrl = picUrl;
		this.possibility = possibility;
	}

	public int getReasonId() {
		return reasonId;
	}

	public void setReasonId(int reasonId) {
		this.reasonId = reasonId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTagType() {
		return tagType;
	}

	public void setTagType(String tagType) {
		this.tagType = tagType;
	}

	public String getReasonDetail() {
		return reasonDetail;
	}

	public void setReasonDetail(String reasonDetail) {
		this.reasonDetail = reasonDetail;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Double getPossibility() {
		return possibility;
	}

	public void setPossibility(Double possibility) {
		this.possibility = possibility;
	}

}

/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu   
 */
package com.soledede.classfy.bayes.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Title: 推荐的餐厅
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年6月27日
 * @Version:1.1.0
 */
public class CandidateRes implements Serializable {

	private String resId; // 餐厅Id
	private Double possibility; // 概率
	private List<Reason> reasonList; // 推荐理由列表

	public CandidateRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CandidateRes(String resId, Double possibility,
			List<Reason> reasonList) {
		super();
		this.resId = resId;
		this.possibility = possibility;
		this.reasonList = reasonList;
	}

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public Double getPossibility() {
		return possibility;
	}

	public void setPossibility(Double possibility) {
		this.possibility = possibility;
	}

	public List<Reason> getReasonList() {
		return reasonList;
	}

	public void setReasonList(List<Reason> reasonList) {
		this.reasonList = reasonList;
	}

}

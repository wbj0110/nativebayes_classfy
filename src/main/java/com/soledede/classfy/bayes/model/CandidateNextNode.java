/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.model;

import java.io.Serializable;

/**
 * @Title: 问题节点
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年6月27日
 * @Version:1.1.0
 */
public class CandidateNextNode implements Serializable {

	private int nodeId; // 节点id
	private String message; // 节点内容
	private Double possibility; // 概率

	public CandidateNextNode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CandidateNextNode(int nodeId, String message, Double possibility) {
		super();
		this.nodeId = nodeId;
		this.message = message;
		this.possibility = possibility;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Double getPossibility() {
		return possibility;
	}

	public void setPossibility(Double possibility) {
		this.possibility = possibility;
	}

}

/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.model;

/**
 * @Title: 返回给客服调用是否成功信息
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年6月27日
 * @Version:1.1.0
 */
public class AgentInputRslt {

	private int rsltCode; // 调用成功以否状态码。
	private String message; // 调用不成功，返回不成功原因
	private int nodeId; // 如果客服人员选择了下一节点，则为该流程节点Id ，否则为新节点Id

	public AgentInputRslt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AgentInputRslt(int rsltCode, String message, int nodeId) {
		super();
		this.rsltCode = rsltCode;
		this.message = message;
		this.nodeId = nodeId;
	}

	public AgentInputRslt(int rsltCode, String message) {
		super();
		this.rsltCode = rsltCode;
		this.message = message;
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

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

}

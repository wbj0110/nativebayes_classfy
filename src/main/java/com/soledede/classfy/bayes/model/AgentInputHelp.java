/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Title:
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年6月27日
 * @Version:1.1.0
 */
public class AgentInputHelp implements Serializable {

	private String sessionId; // 标识当前会话的全局唯一编号，所有接口均包含该Id。
	private int cityId; // 对话所处城市
	private int nodeId; // 如果客服人员选择了下一节点，则该流程节点Id，如果没选择，则为0
	private String message; // 如果客服人员没选择流程节点，则有效，为客服人员输入，需建立新流程节点
	private List<CandidateRes> candidateResList; // 客服人员推荐的餐厅列表。特别的，如果Reason对象的reasonId>0，则客服人员选择了推荐的推荐理由。否则为新的推荐理由
	private int tagType; // 客服人员询问客户希望得到的标签类型；如“请问需要什么菜系？”，则tagType为“菜系”

	public AgentInputHelp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AgentInputHelp(String sessionId, int cityId, int nodeId,
			String message, List<CandidateRes> candidateResList, int tagType) {
		super();
		this.sessionId = sessionId;
		this.cityId = cityId;
		this.nodeId = nodeId;
		this.message = message;
		this.candidateResList = candidateResList;
		this.tagType = tagType;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	

	

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
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

	public List<CandidateRes> getCandidateResList() {
		return candidateResList;
	}

	public void setCandidateResList(List<CandidateRes> candidateResList) {
		this.candidateResList = candidateResList;
	}

	public int getTagType() {
		return tagType;
	}

	public void setTagType(int tagType) {
		this.tagType = tagType;
	}

}

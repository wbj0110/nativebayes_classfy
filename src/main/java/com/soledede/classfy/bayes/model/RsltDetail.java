/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Title: 返回用户推荐列表
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年6月27日
 * @Version:1.1.0
 */
public class RsltDetail implements Serializable {

	private List<CandidateNextNode> nodeList; // 推荐的下一跳节点列表，按概率降序排列
	private List<CandidateRes> resList; // 推荐的餐厅列表，按概率降序排列 a
	private List<QuestionTag> qTagList; // 用户输入解析的标签列表
	private List<CandidateRes> resNewList; //根据标签所得到的餐厅列表 b
	private List<CandidateRes> combineResList; //合并a,b所得到的推荐餐厅列表  取交集

	public RsltDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RsltDetail(List<CandidateNextNode> nodeList,
			List<CandidateRes> resList, List<QuestionTag> qTagList) {
		super();
		this.nodeList = nodeList;
		this.resList = resList;
		this.qTagList = qTagList;
	}

	public List<CandidateNextNode> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<CandidateNextNode> nodeList) {
		this.nodeList = nodeList;
	}

	public List<CandidateRes> getResList() {
		return resList;
	}

	public void setResList(List<CandidateRes> resList) {
		this.resList = resList;
	}

	public List<QuestionTag> getqTagList() {
		return qTagList;
	}

	public void setqTagList(List<QuestionTag> qTagList) {
		this.qTagList = qTagList;
	}

	public List<CandidateRes> getResNewList() {
		return resNewList;
	}

	public void setResNewList(List<CandidateRes> resNewList) {
		this.resNewList = resNewList;
	}

	public List<CandidateRes> getCombineResList() {
		return combineResList;
	}

	public void setCombineResList(List<CandidateRes> combineResList) {
		this.combineResList = combineResList;
	}

}

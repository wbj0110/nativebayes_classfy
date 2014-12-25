/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.model;

/**
 * @Title:
 * @Description: //类别对应的概率
 * @Author:wengbenjue
 * @Since:2014年7月14日
 * @Version:1.1.0
 */
public class NodeClassfyScore {
	private int categoryId;
	private Double score;

	public NodeClassfyScore() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NodeClassfyScore(int categoryId, Double score) {
		super();
		this.categoryId = categoryId;
		this.score = score;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}
}

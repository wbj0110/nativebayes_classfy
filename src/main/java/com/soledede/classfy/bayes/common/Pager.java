package com.soledede.classfy.bayes.common;

import java.util.List;

/**
 * 
 *@Title:  
 *@Description:  
 *@Author:wengbenjue  
 *@Since:2014年7月15日  
 *@Version:1.1.0
 */
public class Pager<T> {
	// 总数
	private int totalNum;
	// 每页显示的具体内容
	private List<T> pageList;

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public List<T> getPageList() {
		return pageList;
	}

	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
	}

}

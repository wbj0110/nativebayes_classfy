/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.model;

/**
 * @Title:
 * @Description: 词典维护接口
 * @Author:wengbenjue
 * @Since:2014年7月11日
 * @Version:1.1.0
 */
public class DictionaryManagerHelp {

	private String word; // 单词
	private Integer tagType; // 单词的标签类型id
	private Integer cityId; // 城市Id
	private Boolean isDelete=false; // 是否删除该词

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}


	public Integer getTagType() {
		return tagType;
	}

	public void setTagType(Integer tagType) {
		this.tagType = tagType;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

}

/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Title:
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年7月11日
 * @Version:1.1.0
 */
@Entity(name = "T_DIC")
public class Dictiona {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "word")
	private String word;

	@Column(name = "tag_type_id")
	private Integer tagTypeId;

	@Column(name = "city_id")
	private Integer cityId;

	
	
	
	public Dictiona() {
		super();  
		// TODO Auto-generated constructor stub
	}

	
	
	public Dictiona(String word, Integer tagTypeId, Integer cityId) {
		super();
		this.word = word;
		this.tagTypeId = tagTypeId;
		this.cityId = cityId;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Integer getTagTypeId() {
		return tagTypeId;
	}

	public void setTagTypeId(Integer tagTypeId) {
		this.tagTypeId = tagTypeId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

}

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
 * @Since:2014年7月10日
 * @Version:1.1.0
 */
@Entity(name = "users")
public class Users {

	public Users() {
		super();
	}

	public Users(String user_name, Integer age, String nice_name) {
		super();
		this.user_name = user_name;
		this.age = age;
		this.nice_name = nice_name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "user_name", length = 32)
	private String user_name;

	@Column(name = "age")
	private Integer age;

	@Column(name = "nice_name", length = 32)
	private String nice_name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getNice_name() {
		return nice_name;
	}

	public void setNice_name(String nice_name) {
		this.nice_name = nice_name;
	}

}

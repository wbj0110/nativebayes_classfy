package com.soledede.classfy.bayes.service;

import org.springframework.transaction.annotation.Transactional;

import com.soledede.classfy.bayes.entity.Users;

public interface UserService {

	/**
	 * @return
	 * @Description:
	 */
	public int userCount();

	@Transactional
	public void saveUser(Users user);

}

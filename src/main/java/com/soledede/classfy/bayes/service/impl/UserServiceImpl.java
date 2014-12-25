/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.service.impl;

import org.springframework.stereotype.Service;

import com.soledede.classfy.bayes.base.AbstractBaseDao;
import com.soledede.classfy.bayes.entity.Users;
import com.soledede.classfy.bayes.service.UserService;

/**
 * @Title:
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年7月10日
 * @Version:1.1.0
 */
@Service
public class UserServiceImpl extends AbstractBaseDao<Users, Integer> implements
		UserService {

	@Override
	public int userCount() {
		Users sf = findById(1);
		if (sf != null)
			return 1;
		return 0;
	}

	@Override
	public void saveUser(Users user) {
		save(user);
	}

}

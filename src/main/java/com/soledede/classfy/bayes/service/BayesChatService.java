/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.service;

import com.soledede.classfy.bayes.model.AgentInputHelp;
import com.soledede.classfy.bayes.model.AgentInputRslt;
import com.soledede.classfy.bayes.model.UserInputHelp;
import com.soledede.classfy.bayes.model.UserInputRslt;

/**
 * @Title:
 * @Description: 与UI交互总类
 * @Author:wengbenjue
 * @Since:2014年6月27日
 * @Version:1.1.0
 */
public interface BayesChatService {

	/**
	 * @param userInputHelp
	 * @Description: 分类
	 */
	public UserInputRslt userInput(UserInputHelp userInputHelp);

	/**
	 * @param agentInputHelp
	 * @return
	 * @Description: 收集数据训练
	 */
	public AgentInputRslt agentInput(AgentInputHelp agentInputHelp);

}

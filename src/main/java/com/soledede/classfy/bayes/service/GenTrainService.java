/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.service;

import com.soledede.classfy.bayes.model.AgentInputRslt;

/**
 * @Title:
 * @Description: 生成训练数据
 * @Author:wengbenjue
 * @Since:2014年6月30日
 * @Version:1.1.0
 */
public interface GenTrainService {

	/**
	 * 
	 * @param question
	 * @param answer
	 * @param agentInputRslt
	 *            返回结果
	 * @return
	 * @Description:将客户训练所得数据写入文件，方便异步训练
	 */
	public Boolean generateDirFile2Train(String question, String answer,
			String pathName, AgentInputRslt agentInputRslt);

}

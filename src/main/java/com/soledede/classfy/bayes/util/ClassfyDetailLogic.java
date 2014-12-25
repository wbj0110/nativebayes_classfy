/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.util;

import java.util.List;
import java.util.Map;

import com.soledede.classfy.bayes.model.CandidateRes;
import com.soledede.classfy.bayes.model.NodeClassfyScore;
import com.soledede.classfy.bayes.model.UserInputHelp;
import com.soledede.classfy.bayes.model.UserInputRslt;

/**
 * @Title:
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年7月14日
 * @Version:1.1.0
 */
public interface ClassfyDetailLogic {

	/**
	 * 
	 * @param sortScoreList
	 * @Description:分类适配，为回调使用
	 */
	public void classifyNd(List<NodeClassfyScore> sortScoreList,
			UserInputHelp userInputHelp, UserInputRslt userInputRslt,
			String sessionId, Map<Integer, String> labels,
			CandidateRes candidateRes,int loopTime,List<CandidateRes> resNewList);

}

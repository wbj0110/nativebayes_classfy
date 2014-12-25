/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.soledede.classfy.bayes.model.QuestionAnswer;

/**
 * @Title:
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年6月30日
 * @Version:1.1.0
 */
public class BayesConfigUtil {

	// 分类器相关配置缓存 加载配置文件
	public static Map<String, String> classfiConfigMap;

	// 问题、答案 根据sessionId的缓存池
	public static Map<String, LinkedList<QuestionAnswer>> questionAnswerQuene = new HashMap<String, LinkedList<QuestionAnswer>>();

	// sessionId所对应的上一批次推荐的问题节点列表 id即为其list所对应的下标
	public static Map<String, List<String>> sessionTempNodeCacheList = new HashMap<String, List<String>>();

	// 会话启动 问候语
	public static final String GREETING = "请问有什么可以帮您?";

	// 会话累积input值
	public static Map<String, StringBuilder> lastSessionInputSum = new HashMap<String, StringBuilder>();

	//推荐分类数量增加数
	public static final int RECOMENDNUM = 0;
	
	//推荐输入--标签分类数量
	public static  int I_L_N = 4;
}

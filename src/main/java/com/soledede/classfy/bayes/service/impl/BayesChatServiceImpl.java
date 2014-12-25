/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.hash.Hash;
import org.apache.log4j.Logger;
import org.hsqldb.lib.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soledede.classfy.bayes.entity.Dictiona;
import com.soledede.classfy.bayes.model.AgentInputHelp;
import com.soledede.classfy.bayes.model.AgentInputRslt;
import com.soledede.classfy.bayes.model.CandidateNextNode;
import com.soledede.classfy.bayes.model.CandidateRes;
import com.soledede.classfy.bayes.model.NodeClassfyScore;
import com.soledede.classfy.bayes.model.QuestionAnswer;
import com.soledede.classfy.bayes.model.Reason;
import com.soledede.classfy.bayes.model.ReturnCode;
import com.soledede.classfy.bayes.model.RsltDetail;
import com.soledede.classfy.bayes.model.UserInputHelp;
import com.soledede.classfy.bayes.model.UserInputRslt;
import com.soledede.classfy.bayes.service.BayesChatService;
import com.soledede.classfy.bayes.service.DicService;
import com.soledede.classfy.bayes.service.GenTrainService;
import com.soledede.classfy.bayes.util.BayesConfigUtil;
import com.soledede.classfy.bayes.util.BayesRecomend;
import com.soledede.classfy.bayes.util.ClassfyDetailLogic;
import com.soledede.classfy.bayes.util.PathName;

/**
 * @Title:
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年6月30日
 * @Version:1.1.0
 */
@Service
public class BayesChatServiceImpl implements BayesChatService {
	
	Configuration configuration = new Configuration();

	private static final Logger log = Logger
			.getLogger(BayesChatServiceImpl.class);

	@Autowired
	private GenTrainService genTrainService;
	
	@Autowired 
    private DicService dicService;  
	

	@Override
	public UserInputRslt userInput(UserInputHelp userInputHelp) {
		UserInputRslt userInputRslt = new UserInputRslt();
		userInputRslt.setMessage("请先训练数据!");
		userInputRslt.setRsltCode(ReturnCode.ERROR.value());
		if (userInputHelp.getInput() != null
				&& !"".equals(userInputHelp.getInput())) {// 如果客户输入不为空
			String sessionId = userInputHelp.getSessionId();
			// 判断是否需生成SessionId, 该逻辑为扩充使用
			if (userInputHelp.getSessionId() == null
					|| "".equals(userInputHelp.getSessionId().trim())) {
				userInputRslt.setRsltCode(ReturnCode.NOSESSIONID.value());
				userInputRslt.setMessage("SessionId不能为null!");
				return userInputRslt;
				/*
				 * LinkedList<QuestionAnswer> questionAnswerList = new
				 * LinkedList<QuestionAnswer>(); questionAnswerList.offer(new
				 * QuestionAnswer(userInputHelp.getInput())); sessionId =
				 * UUID.randomUUID().toString();
				 * BayesConfigUtil.questionAnswerQuene.put(sessionId,
				 * questionAnswerList);
				 */
			} else {
				if (BayesConfigUtil.questionAnswerQuene.get(sessionId) == null) {// 如果该会话为null，则创建会话（第一次访问）
					LinkedList<QuestionAnswer> questionAnswerList = new LinkedList<QuestionAnswer>();
					questionAnswerList.offer(new QuestionAnswer(userInputHelp
							.getInput()));
					BayesConfigUtil.questionAnswerQuene.put(sessionId,
							questionAnswerList);
					if (userInputHelp.getLastNodeNo() == 0) {// 如果第一次进来，客服引起会话
						userInputRslt.setMessage("欢迎...");
						userInputRslt.setRsltCode(ReturnCode.SUCCESS.value());
						List<CandidateNextNode> cNexNos = new ArrayList<CandidateNextNode>();
						cNexNos.add(new CandidateNextNode(0,
								BayesConfigUtil.GREETING, 1.0));
						userInputRslt.setRslt(new RsltDetail(cNexNos, null,
								null));
						userInputRslt
								.setSessionId(userInputHelp.getSessionId());
						//累积输入值，方便餐厅分类
						if(BayesConfigUtil.lastSessionInputSum.get(sessionId)==null) BayesConfigUtil.lastSessionInputSum.put(sessionId, new StringBuilder());
						else BayesConfigUtil.lastSessionInputSum.get(sessionId).append(",").append(userInputHelp.getInput());
						List<Dictiona> dL =null;
						try {
							dL = dicService.findByCityId(userInputHelp.getCtyID());
						} catch (Exception e) {
							// TODO Auto-generated catch block 
							log.debug("读取数据库，根据城市获取字典关键词出错");
							e.printStackTrace();
						}
						if(dL!=null && dL.size()>0){
							for(Dictiona d : dL){
								BayesConfigUtil.lastSessionInputSum.get(sessionId).append(d.getWord()).append(",");
							}
						}
						
						return userInputRslt;
					}
				} else {
					BayesConfigUtil.questionAnswerQuene.get(sessionId).offer(
							new QuestionAnswer(userInputHelp.getInput()));
				}
			}
			log.debug("SessionId" + sessionId);
			userInputRslt.setSessionId(sessionId);

			userInputRslt.setRslt(new RsltDetail());//初始化
			//累积输入值，方便餐厅分类
			if(BayesConfigUtil.lastSessionInputSum.get(sessionId)==null) BayesConfigUtil.lastSessionInputSum.put(sessionId, new StringBuilder().append(userInputHelp.getInput()));
			else BayesConfigUtil.lastSessionInputSum.get(sessionId).append(userInputHelp.getInput()).append(",");
			
			
			// 类别索引
			Map<Integer, String> labels = null;
			// 问题节点开始分类
			// classfyNodeService.classifyQuestionNode(userInputHelp,userInputRslt,sessionId,PathName.MODELPATH,PathName.LABELINDEXPATH,PathName.DICTIONARYPATH,PathName.DOCUMENTFREQUENCYPATH);
			BayesRecomend.getInstance().classfy2Recomend(userInputHelp,
					userInputHelp.getInput(), userInputRslt,
					PathName.MODELPATH, PathName.LABELINDEXPATH,
					PathName.DICTIONARYPATH, PathName.DOCUMENTFREQUENCYPATH,
					configuration, labels, sessionId, null,0,null,
					new ClassfyDetailLogic() {
						@Override
						public void classifyNd(
								List<NodeClassfyScore> sortScoreList,
								UserInputHelp userInputHelp,
								UserInputRslt userInputRslt, String sessionId,
								Map<Integer, String> labels,
								CandidateRes candidateRes,int loopTime,List<CandidateRes> resNewList) {
							
							List<CandidateNextNode> candidateNextNodeList = new ArrayList<CandidateNextNode>();
							// 设置推荐节点
							int scoreListSize = sortScoreList.size();
							int candidateNodeLeangth = userInputHelp
									.getCandidateNodeLength();
							if (userInputHelp.getCandidateNodeLength() != 0
									&& scoreListSize < userInputHelp
											.getCandidateNodeLength())
								candidateNodeLeangth = scoreListSize;
							List<String> tempNodeList = new ArrayList<String>();
							for (int i = 0; i < candidateNodeLeangth; i++) {
								CandidateNextNode candidateNextNode = new CandidateNextNode();
								candidateNextNode.setNodeId(i + 1);// 供页面选择的节点id
								String labelContent = labels.get(sortScoreList
										.get(i).getCategoryId());
								candidateNextNode.setMessage(labelContent);// 设置标签
								tempNodeList.add(labelContent);
								candidateNextNode.setPossibility(sortScoreList
										.get(i).getScore());// 设置概率
								candidateNextNodeList.add(candidateNextNode);
							}
							BayesConfigUtil.sessionTempNodeCacheList.put(
									sessionId, tempNodeList);// 将推荐节点添加到临时缓存
							userInputRslt.getRslt().setNodeList(candidateNextNodeList);
							userInputRslt
									.setRsltCode(ReturnCode.CLLASSFI_SUCESS
											.value());
							userInputRslt.setMessage("分类成功!");
						}
					});

			// 餐厅节点开始分类 -》每个餐厅嵌套推荐理由
			BayesRecomend.getInstance().classfy2Recomend(userInputHelp,
					BayesConfigUtil.lastSessionInputSum.get(sessionId).toString(), userInputRslt,
					PathName.MODELPATH_RES, PathName.LABELINDEXPATH_RES,
					PathName.DICTIONARYPATH_RES,
					PathName.DOCUMENTFREQUENCYPATH_RES, configuration, labels,
					sessionId, null,0,null, new ClassfyDetailLogic() {
						@Override
						public void classifyNd(
								List<NodeClassfyScore> sortScoreList,
								UserInputHelp userInputHelp,
								UserInputRslt userInputRslt, String sessionId,
								Map<Integer, String> labels,
								CandidateRes candidateRes,int loopTime,List<CandidateRes> resNewList) {
							List<CandidateRes> resList = new ArrayList<CandidateRes>();
							// 设置推荐节点
							int candidateNodeLeangth = userInputHelp
									.getCandidateNodeLength()+BayesConfigUtil.RECOMENDNUM;
							int scoreListSize = sortScoreList.size();
							if (userInputHelp.getCandidateNodeLength() != 0
									&& scoreListSize < userInputHelp
											.getCandidateNodeLength()+BayesConfigUtil.RECOMENDNUM)
								candidateNodeLeangth = scoreListSize;
							for (int i = 0; i < candidateNodeLeangth; i++) {
								CandidateRes candidateRes1 = new CandidateRes();
								String labelContent = labels.get(sortScoreList
										.get(i).getCategoryId());
								candidateRes1.setResId(labelContent);// 设置餐厅id
								candidateRes1.setPossibility(sortScoreList.get(
										i).getScore());// 设置概率

								// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

								// 推荐理由节点开始分类
								BayesRecomend.getInstance().classfy2Recomend(
										userInputHelp, labelContent,
										userInputRslt, PathName.MODELPATH_SON,
										PathName.LABELINDEXPATH_SON,
										PathName.DICTIONARYPATH_SON,
										PathName.DOCUMENTFREQUENCYPATH_SON,
										configuration, labels, sessionId, candidateRes1,0,null,
										new ClassfyDetailLogic() {

											@Override
											public void classifyNd(
													List<NodeClassfyScore> sortScoreList,
													UserInputHelp userInputHelp,
													UserInputRslt userInputRslt,
													String sessionId,
													Map<Integer, String> labels,
													CandidateRes candidateRes,int loopTime,List<CandidateRes> resNewList) {
												// 设置推荐节点
												int candidateNodeLeangth = userInputHelp
														.getCandidateNodeLength();
												int scoreListSize = sortScoreList
														.size();
												if (userInputHelp
														.getCandidateNodeLength() != 0
														&& scoreListSize < userInputHelp
																.getCandidateNodeLength())
													candidateNodeLeangth = scoreListSize;
												List<Reason> reasonList = new ArrayList<Reason>();
												for (int i = 0; i < candidateNodeLeangth; i++) {
													Reason reason = new Reason();
													reason.setReasonId(i + 1);
													String labelContent = labels
															.get(sortScoreList
																	.get(i)
																	.getCategoryId());
													reason.setReasonDetail(labelContent);// 设置标签
													reason.setPossibility(sortScoreList
															.get(i).getScore());// 设置概率
													reasonList.add(reason);
												}
												candidateRes
														.setReasonList(reasonList); // 嵌套设置
											}
										});
								// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

								resList.add(candidateRes1);
							}
							userInputRslt.getRslt().setResList(resList); // 设置推荐餐厅列表
							userInputRslt
									.setRsltCode(ReturnCode.CLLASSFI_SUCESS
											.value());
							userInputRslt.setMessage("分类成功!");
						}
					});
			
			//输入 -->标签分类   标签餐厅分类
			BayesRecomend.getInstance().classfy2Recomend(userInputHelp,
					userInputHelp.getInput(), userInputRslt,
					PathName.MODELPATH_LABEL_I, PathName.LABELINDEXPATH_LABEL_I,
					PathName.DICTIONARYPATH_LABEL_I, PathName.DOCUMENTFREQUENCYPATH_LABEL_I,
					configuration, labels, sessionId, null,0,null,
					new ClassfyDetailLogic() {
						@Override
						public void classifyNd(
								List<NodeClassfyScore> sortScoreList,
								UserInputHelp userInputHelp,
								UserInputRslt userInputRslt, String sessionId,
								Map<Integer, String> labels,
								CandidateRes candidateRes,int loopTime,List<CandidateRes> resNewList) {
							List<CandidateRes> resList = new ArrayList<CandidateRes>();//根据标签推荐的餐厅类表
							//List<QuestionTag> qTagList = new ArrayList<QuestionTag>();//问题标签
							// 设置推荐节点
							if(BayesConfigUtil.I_L_N>sortScoreList.size()) BayesConfigUtil.I_L_N = sortScoreList.size();
							for (int i = 0; i < BayesConfigUtil.I_L_N-1; i++) {
								String labelContent = labels.get(sortScoreList
										.get(i).getCategoryId());
								
									//根据标签--取得餐厅列表
								// 标签--》餐厅节点开始分类 -》每个餐厅嵌套推荐理由  (BayesConfigUtil.I_L_N-i)*2 循环组合算法
								BayesRecomend.getInstance().classfy2Recomend(userInputHelp,
										labelContent, userInputRslt,
										PathName.MODELPATH_LABEL_R, PathName.LABELINDEXPATH_LABEL_R,
										PathName.DICTIONARYPATH_LABEL_R,
										PathName.DOCUMENTFREQUENCYPATH_LABEL_R, configuration, labels,
										sessionId, null,(BayesConfigUtil.I_L_N-i)*2,resList, new ClassfyDetailLogic() {
											@Override
											public void classifyNd(
													List<NodeClassfyScore> sortScoreList,
													UserInputHelp userInputHelp,
													UserInputRslt userInputRslt, String sessionId,
													Map<Integer, String> labels,
													CandidateRes candidateRes,int loopTime,List<CandidateRes> resNewList) {
												// 设置推荐节点
												if(loopTime>sortScoreList.size()) loopTime = sortScoreList.size();
												for (int i = 0; i < loopTime; i++) {
													CandidateRes candidateRes1 = new CandidateRes();
													String labelContent = labels.get(sortScoreList
															.get(i).getCategoryId());
													candidateRes1.setResId(labelContent);// 设置餐厅id
													candidateRes1.setPossibility(sortScoreList.get(
															i).getScore());// 设置概率

													// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

													// 推荐理由节点开始分类
													BayesRecomend.getInstance().classfy2Recomend(
															userInputHelp, labelContent,
															userInputRslt, PathName.MODELPATH_SON,
															PathName.LABELINDEXPATH_SON,
															PathName.DICTIONARYPATH_SON,
															PathName.DOCUMENTFREQUENCYPATH_SON,
															configuration, labels, sessionId, candidateRes1,0,null,
															new ClassfyDetailLogic() {

																@Override
																public void classifyNd(
																		List<NodeClassfyScore> sortScoreList,
																		UserInputHelp userInputHelp,
																		UserInputRslt userInputRslt,
																		String sessionId,
																		Map<Integer, String> labels,
																		CandidateRes candidateRes,int loopTime,List<CandidateRes> resNewList) {
																	// 设置推荐节点
																	int candidateNodeLeangth = userInputHelp
																			.getCandidateNodeLength();
																	int scoreListSize = sortScoreList
																			.size();
																	if (userInputHelp
																			.getCandidateNodeLength() != 0
																			&& scoreListSize < userInputHelp
																					.getCandidateNodeLength())
																		candidateNodeLeangth = scoreListSize;
																	List<Reason> reasonList = new ArrayList<Reason>();
																	for (int i = 0; i < candidateNodeLeangth; i++) {
																		Reason reason = new Reason();
																		reason.setReasonId(i + 1);
																		String labelContent = labels
																				.get(sortScoreList
																						.get(i)
																						.getCategoryId());
																		reason.setReasonDetail(labelContent);// 设置标签
																		reason.setPossibility(sortScoreList
																				.get(i).getScore());// 设置概率
																		reasonList.add(reason);
																	}
																	candidateRes
																			.setReasonList(reasonList); // 嵌套设置
																}
															});
													// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
													resNewList.add(candidateRes1);
												}
											}
										});
							}
							userInputRslt.getRslt().setResNewList(resList);
							userInputRslt
									.setRsltCode(ReturnCode.CLLASSFI_SUCESS
											.value());
							userInputRslt.setMessage("分类成功!");
						}
					});
			
			//餐厅组合返回给UI
			List<CandidateRes> cCb = intersectionRes(userInputRslt);
			userInputRslt.getRslt().setCombineResList(cCb);//组合推荐

		}else{
			userInputRslt.setMessage("请问有什么可以帮您?请输入...");
			userInputRslt.setRsltCode(ReturnCode.ERROR.value());
		}
		return userInputRslt;
	}


	/**  
	 * @param userInputRslt
	 * @return  
	 * @Description:  求标签推荐以及输入推荐的组合推荐餐厅  交集
	 */
	private List<CandidateRes> intersectionRes(UserInputRslt userInputRslt) {
		List<CandidateRes> cR1 = userInputRslt.getRslt().getResList();
		List<CandidateRes> cRCopy = new ArrayList<CandidateRes>();
		cRCopy.addAll(userInputRslt.getRslt().getResNewList());
		List<CandidateRes> cCb = new ArrayList<CandidateRes>();
		//求交集
		if(cR1!=null && cR1.size()>0 && cRCopy!=null && cRCopy.size()>0 ){
		for(CandidateRes c1 :cR1){
			for(CandidateRes c2 :cRCopy){
				if(c1.getResId().equals(c2.getResId())){
					cCb.add(c2);
					cRCopy.remove(c2);
					break;
				}
			}
			}
		}
		return cCb;
	}

	
	/**
	 * 为训练数据做准备
	 */
	@Override
	public AgentInputRslt agentInput(AgentInputHelp agentInputHelp) {
		if (agentInputHelp.getSessionId() == null
				|| "".equals(agentInputHelp.getSessionId().trim()))
			return new AgentInputRslt(ReturnCode.NOSESSIONID.value(),
					"SessionId不能为null!");
		AgentInputRslt agentInputRslt = new AgentInputRslt();
		String answer = null;
		if (agentInputHelp.getNodeId() <= 0) {// 如果客服没采用推荐节点，取message内容
			answer = agentInputHelp.getMessage();
		} else {// 如果选择了推荐节点,去临时缓存取得该节点所对应的内容，并清空临时缓存
			answer = BayesConfigUtil.sessionTempNodeCacheList.get(
					agentInputHelp.getSessionId()).get(
					agentInputHelp.getNodeId() - 1);
			BayesConfigUtil.sessionTempNodeCacheList.remove(agentInputHelp
					.getSessionId());// 清空对应sessionid的临时缓存
		}
		LinkedList<QuestionAnswer> questionLinkedList = BayesConfigUtil.questionAnswerQuene
				.get(agentInputHelp.getSessionId());
		QuestionAnswer questinAnswer = questionLinkedList.getLast();
		questinAnswer.setAnswer(answer);
		questionLinkedList.set(questionLinkedList.size() - 1, questinAnswer);
		
		
		
		//获取标签关键字,进行关键字-->餐厅 以及 输入内容-->标签分类
		List<Dictiona> dL = null;
		try {
			 dL = dicService.findByCityIdOrTagTypeId(agentInputHelp.getCityId(), agentInputHelp.getTagType());
		} catch (Exception e) {
			log.debug("获取关键词出错"+e.getMessage());
			// TODO Auto-generated catch block  
			e.printStackTrace();
		}

		Boolean candiateFlag = false;// 餐厅推荐
		Boolean resonFlag = false;// 推荐理由分类
		Boolean labelResFlag = false;//标签-餐厅分类
		Boolean inputLabelFlag =false;//输入--标签分类
		
		Boolean localinputLabelFlag =true;
		if (agentInputHelp.getCandidateResList() != null
				&& agentInputHelp.getCandidateResList().size() > 0) {
			// 训练 输入-->餐厅 分类
			int flagC = 0;
			Boolean localreasonFlag = true;
			Boolean locallabelTrainFlag = true; 
			

			for (CandidateRes candidateRes : agentInputHelp
					.getCandidateResList()) {
				if (genTrainService.generateDirFile2Train(BayesConfigUtil.lastSessionInputSum
						.get(agentInputHelp.getSessionId()).toString(),candidateRes
						.getResId() ,
						PathName.CADIDATERESPATH, agentInputRslt))
					flagC++;
				// 餐厅--》推荐理由训练
				if (candidateRes.getReasonList() != null
						&& candidateRes.getReasonList().size() > 0) {
					for (Reason reason : candidateRes.getReasonList()) {
						if (reason.getReasonId() <= 0) {// 如果没有该推荐理由则训练
							if (!genTrainService.generateDirFile2Train(
									candidateRes.getResId(),
									reason.getReasonDetail(),
									PathName.RESONPATH, agentInputRslt))
								localreasonFlag = false;
						}
					}
				}
				
				//按标签---》餐厅进行训练
				if(dL!=null && dL.size()>0){
					for(Dictiona d : dL){
						if(!genTrainService.generateDirFile2Train(
								d.getWord(),
								candidateRes.getResId(),
								PathName.LABELRESPATH, agentInputRslt)) locallabelTrainFlag = false;
					}
				}
			}
			if (flagC == agentInputHelp.getCandidateResList().size())
				candiateFlag = true;
			if (!localreasonFlag)   
				resonFlag = true;  //餐厅--》推荐理由分类
			if(!locallabelTrainFlag) labelResFlag = true; //标签餐厅分类
		}
		
		
		//按输入--》标签分类训练
		if(dL!=null && dL.size()>0){
			for(Dictiona d : dL){
				if(!genTrainService.generateDirFile2Train(
						answer,
						d.getWord(),
						PathName.INPUTLABELPATH, agentInputRslt)) localinputLabelFlag = false;
			}
			if(!localinputLabelFlag) localinputLabelFlag = true; //输入--标签分类
		}
		

		if (genTrainService.generateDirFile2Train(questinAnswer.getQuestion(),
				answer, PathName.QUESTIONNODEPATH, agentInputRslt) // 训练问题节点
				&& candiateFlag // 输入 - 》训练餐厅分类
				&& resonFlag // 餐厅 -》 训练推荐理由分类
				&& labelResFlag //训练标签 - 》餐厅分类
				&& localinputLabelFlag //输入 -》 标签 分类
		)
			agentInputRslt.setRsltCode(ReturnCode.SUCCESS.value());
		else
			agentInputRslt.setRsltCode(ReturnCode.ERROR.value());
		return agentInputRslt;
	}

}

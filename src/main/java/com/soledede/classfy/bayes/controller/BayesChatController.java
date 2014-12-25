package com.soledede.classfy.bayes.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.soledede.classfy.bayes.model.AgentInputHelp;
import com.soledede.classfy.bayes.model.AgentInputRslt;
import com.soledede.classfy.bayes.model.DictionaryManagerHelp;
import com.soledede.classfy.bayes.model.ReturnCode;
import com.soledede.classfy.bayes.model.Rslt;
import com.soledede.classfy.bayes.model.UserInputHelp;
import com.soledede.classfy.bayes.model.UserInputRslt;
import com.soledede.classfy.bayes.service.BayesChatService;
import com.soledede.classfy.bayes.service.DicService;

/**
 * 
 * @Title: 贝叶斯对UI接口
 * @Description: sessionId:标识当前会话的全局唯一编号，所有接口均包含该Id。 cityID:对话所处城市。
 *               lastNodeId:引发当前用户输入的流程节点Id。如果是用户输入的第一句话，该Id为0 input：用户输入
 *               candidateNodeLength:推荐的问题节点数量 candidateResLength:推荐的餐厅数量
 * @Author:wengbenjue
 * @Since:2014年6月27日
 * @Version:1.1.0
 */
@RestController
@RequestMapping(value = "/bayes", produces = "application/json;charset=UTF-8")
public class BayesChatController {
	private static final Logger log = Logger
			.getLogger(BayesChatController.class);
	@Autowired
	private BayesChatService bayesChatService;
	
	@Autowired
	private DicService dicService;

	/**
	 * 
	 * @param sessionId
	 *            标识当前会话的全局唯一编号，所有接口均包含该Id
	 * @param ctyID
	 *            对话所处城市
	 * @param lastNodeNo
	 *            引发当前用户输入的流程节点Id。如果是用户输入的第一句话，该Id为0
	 * @param input
	 *            用户输入
	 * @param candidateNodeLength
	 *            推荐的节点数量
	 * @param candidateResLength
	 *            推荐的餐厅数量
	 * @return
	 * @Description: 用户输入接口，用户在应答系统中输入问题后，UI调用本接口，取得相应的下一流程推荐列表及餐厅答案推荐列表
	 */
	// http://localhost:8080/bct/bayes/UserInput?sessionId=%22%22&ctyID=0&lastNodeNo=1&input=%22hello%22&candidateNodeLength=10&candidateResLength=5
	@RequestMapping(value = "/testuserInput", method = RequestMethod.GET)
	public UserInputRslt testUserInput(
			@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
			@RequestParam(value = "ctyID") int ctyID,
			@RequestParam(value = "lastNodeNo", defaultValue = "0") int lastNodeNo,
			@RequestParam(value = "input") String input,
			@RequestParam(value = "candidateNodeLength", defaultValue = "10") int candidateNodeLength,
			@RequestParam(value = "candidateResLength", defaultValue = "10") int candidateResLength) {

		log.debug("UserInput-starting...................");
		Long startTime = System.currentTimeMillis();
		UserInputHelp userInputHelp = new UserInputHelp(sessionId, ctyID,
				lastNodeNo, input, candidateNodeLength, candidateResLength);
		UserInputRslt userInputRst = bayesChatService.userInput(userInputHelp);
		Long endTime = System.currentTimeMillis();
		log.debug("UserInput-ending...................");
		log.warn("cost total time" + (endTime - startTime));
		return userInputRst;
	}

	/**
	 * 
	 * @param sessionId
	 *            标识当前会话的全局唯一编号，所有接口均包含该Id
	 * @param ctyID
	 *            对话所处城市
	 * @param lastNodeNo
	 *            引发当前用户输入的流程节点Id。如果是用户输入的第一句话，该Id为0
	 * @param input
	 *            用户输入
	 * @param candidateNodeLength
	 *            推荐的节点数量
	 * @param candidateResLength
	 *            推荐的餐厅数量
	 * @return
	 * @Description: 用户输入接口，用户在应答系统中输入问题后，UI调用本接口，取得相应的下一流程推荐列表及餐厅答案推荐列表
	 */
	// produces = "application/json;charset=UTF-8" if [post] then add
	// @RequestBody UserInputHelp
	@RequestMapping(value = "/UserInput", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public UserInputRslt UserInput(UserInputHelp userInputHelp) {

		log.debug("UserInput-starting...................");
		Long startTime = System.currentTimeMillis();
		UserInputRslt userInputRst = bayesChatService.userInput(userInputHelp);
		Long endTime = System.currentTimeMillis();
		log.debug("UserInput-ending...................");
		log.warn("cost total time" + (endTime - startTime));
		return userInputRst;
	}

	/**
	 * 
	 * @param sessionId
	 *            标识当前会话的全局唯一编号，所有接口均包含该Id。
	 * @param ctyID
	 *            对话所处城市
	 * @param nodeId
	 *            如果客服人员选择了下一节点，则该流程节点Id，如果没选择，则为0
	 * @param message
	 *            如果客服人员没选择流程节点，则有效，为客服人员输入，需建立新流程节点
	 * @param candidateResList
	 *            客服人员推荐的餐厅列表。特别的，如果Reason对象的reasonId>0，则客服人员选择了推荐的推荐理由。
	 *            否则为新的推荐理由
	 * @param tagType
	 *            客服人员询问客户希望得到的标签类型；如“请问需要什么菜系？”，则tagType为“菜系”
	 * @return
	 * @Description: 客服人员应答接口。客服人员对用户输入进行应答后，UI调用本接口，将客服人员的应答信息，选择的流程节点，
	 *               选择的推荐餐厅列表或输入的推荐餐厅列表传给核心引擎
	 */
	@RequestMapping(value = "AgentInput", method = RequestMethod.POST, headers = "Content-Type=application/json;charset=UTF-8")
	@ResponseStatus(HttpStatus.OK)
	public AgentInputRslt AgentInput(@RequestBody AgentInputHelp agentInputHelp) {
		return bayesChatService.agentInput(agentInputHelp);
	}

	/**
	 * 
	 * @param dictionaryManagerHelp
	 * @return
	 * @Description:词典维护接口
	 */
	@RequestMapping(value = "/DictionaryManage", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Rslt DictionaryManage(DictionaryManagerHelp dictionaryManagerHelp) {
		Rslt r = new Rslt();
		if(dictionaryManagerHelp.getIsDelete()==false && dictionaryManagerHelp.getWord()==null && (dictionaryManagerHelp.getCityId()==null ||dictionaryManagerHelp.getCityId()==0) && (dictionaryManagerHelp.getTagType()==null ||dictionaryManagerHelp.getTagType()==0)){
			r.setMessage("参数不正确！");
			r.setRsltCode(ReturnCode.ERROR.value());
			return r;
		}
			
		try {
			dicService.dictionaryManage(dictionaryManagerHelp.getWord(), dictionaryManagerHelp.getTagType(), dictionaryManagerHelp.getCityId(), dictionaryManagerHelp.getIsDelete());
			r.setMessage("操作成功!");
			r.setRsltCode(ReturnCode.SUCCESS.value());
		} catch (Exception e) {
			log.error(e.getMessage());
			r.setMessage("操作失败!");
			r.setRsltCode(ReturnCode.ERROR.value());
			e.printStackTrace();
		}
		return r;
	}

}

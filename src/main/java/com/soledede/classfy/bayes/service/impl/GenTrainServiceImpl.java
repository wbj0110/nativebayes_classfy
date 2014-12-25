/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.soledede.classfy.bayes.model.AgentInputRslt;
import com.soledede.classfy.bayes.service.GenTrainService;
import com.soledede.classfy.bayes.util.BayesConfigUtil;

/**
 * @Title:
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年6月30日
 * @Version:1.1.0
 */
@Service
public class GenTrainServiceImpl implements GenTrainService {
	private static final Logger log = Logger
			.getLogger(GenTrainServiceImpl.class);

	@Override
	public Boolean generateDirFile2Train(String question, String answer,
			String pathName, AgentInputRslt agentInputRslt) {
		String questionNodePath = "d:\\";
		if (BayesConfigUtil.classfiConfigMap.get(pathName) != null) {
			questionNodePath = BayesConfigUtil.classfiConfigMap.get(pathName);
		}
		if(question!=null && !question.trim().equals(""))
			question = question.replaceAll("\\,", "，").replaceAll("\\.", "。").replaceAll("\\?", "？").replaceAll("\\!", "！");
		if(answer!=null && !answer.trim().equals(""))
			answer = answer.replaceAll("\\,", "，").replaceAll("\\.", "。").replaceAll("\\?", "？").replaceAll("\\!", "！");
		
		File filePath = new File(questionNodePath);
		File[] files = filePath.listFiles();
		Boolean flag = false;
		String dirName = "";
		if(files!=null && files.length>0){
		for (int i = 0; i < files.length; i++) {// 判断类别
			if (answer.trim().equals(files[i].getName().trim())) {
				flag = true;
				dirName = files[i].getName();
				break;
			}
		}
		}
		if (flag) { // 该类目已经存在
			questionNodePath = questionNodePath + File.separator + dirName;// 进入类目
		} else {// 创建该类目
			questionNodePath = questionNodePath + File.separator + answer;
			filePath = new File(questionNodePath);
			filePath.mkdirs();
		}

		// 遍历类目下的文件，取得最大值文件名作为文件名
		filePath = new File(questionNodePath);
		files = filePath.listFiles();
		String labelDocName = String.valueOf(1);
		if (files.length > 0) {
			labelDocName = String.valueOf(Integer
					.valueOf(files[files.length - 1].getName()) + 1);
		}
		questionNodePath = questionNodePath + File.separator + labelDocName;
		// 生成文件
		File curDocFile = new File(questionNodePath);
		File parent = curDocFile.getParentFile();
		if (parent != null && !parent.exists())
			parent.mkdirs();
		try {
			curDocFile.createNewFile();
		} catch (IOException e) {
			log.error("创建文件失败!" + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
			agentInputRslt.setMessage("创建文件失败");
			return false;
		}
		FileOutputStream fos = null;
		try {
			// byte bytes[]=new byte[1024];
			byte bytes[] = question.getBytes(Charset.forName("UTF-8"));
			// int b = question.length();
			fos = new FileOutputStream(curDocFile);
			fos.write(bytes);
			// while(true){
			// fos.write(bytes,0,b);
			// }

		} catch (FileNotFoundException e) {
			log.error("文件不存在!" + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
			agentInputRslt.setMessage("文件不存在!");
			return false;
		} catch (IOException e) {
			log.error("写文件出错!" + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
			agentInputRslt.setMessage("写文件出错!");
			return false;
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("写入文件失败!" + e.getMessage());
				e.printStackTrace();
				agentInputRslt.setMessage("写入文件失败!");
				return false;
			}
		}

		return true;
	}

}

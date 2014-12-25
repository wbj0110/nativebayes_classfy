/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.mahout.classifier.naivebayes.AbstractNaiveBayesClassifier;
import org.apache.mahout.classifier.naivebayes.BayesUtils;
import org.apache.mahout.classifier.naivebayes.NaiveBayesModel;
import org.apache.mahout.math.Vector;

import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.Multiset;
import com.soledede.classfy.bayes.model.CandidateRes;
import com.soledede.classfy.bayes.model.NodeClassfyScore;
import com.soledede.classfy.bayes.model.ReturnCode;
import com.soledede.classfy.bayes.model.UserInputHelp;
import com.soledede.classfy.bayes.model.UserInputRslt;

/**
 * @Title:
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年7月14日
 * @Version:1.1.0
 */
public class BayesRecomend {
	private static final Logger log = Logger.getLogger(BayesRecomend.class);

	private static BayesRecomend uniqueInstance = null;

	private BayesRecomend() {
		// Exists only to defeat instantiation.
	}

	public static BayesRecomend getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new BayesRecomend();
		}
		return uniqueInstance;
	}

	/**
	 * 
	 * @param input
	 * @param userInputRslt
	 * @param modelPathName
	 * @param labelIndexPathName
	 * @param dictionaryPathName
	 * @param documentFrequencyPathName
	 * @param configuration
	 * @param labels
	 * @param sessionId
	 * @param candidateRes
	 *            餐厅推荐原因列表使用
	 * @param classfyDetailLogic
	 * @param loopTime 循环次数  为 标签 推荐餐厅组合循环算法使用
	 * @param resNewList  推荐的新餐厅的引用
	 * @return 
	 * @Description:分类主逻辑
	 */
	public void classfy2Recomend(UserInputHelp userInputHelp, String input,
			UserInputRslt userInputRslt, String modelPathName,
			String labelIndexPathName, String dictionaryPathName,
			String documentFrequencyPathName, Configuration configuration,
			Map<Integer, String> labels, String sessionId,
			CandidateRes candidateRes, int loopTime,List<CandidateRes> resNewList,ClassfyDetailLogic classfyDetailLogic) {

		List<NodeClassfyScore> nodeClassfyScoreList = null;
		// 根据输入分类 问题 节点
		Boolean complementary = false;
		Boolean enAnlyzer = true;
		complementary = BayesConfigUtil.classfiConfigMap.get("complementary") == null ? complementary
				: Boolean.valueOf(BayesConfigUtil.classfiConfigMap.get(
						"complementary").trim());
		enAnlyzer = BayesConfigUtil.classfiConfigMap.get("enAnlyzer") == null ? enAnlyzer
				: Boolean.valueOf(BayesConfigUtil.classfiConfigMap.get(
						"enAnlyzer").trim());

		// 初始化分类器路径
		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// TODO Auto-generated method stub
		String modelPath = "D:\\work\\seq_bayes_get\\model\\";
		String labelIndexPath = "D:\\work\\seq_bayes_get\\labelindex";
		String dictionaryPath = "D:\\work\\seq_bayes_get\\dictionary.file-0";
		// String documentFrequencyPath =
		// "D:\\work\\seq_bayes_get\\df-count\\df-count-merge";
		String documentFrequencyPath = "D:\\work\\seq_bayes_get\\df-count\\part-r-00001";

		if (BayesConfigUtil.classfiConfigMap != null) {
			modelPath = BayesConfigUtil.classfiConfigMap.get(modelPathName);
			labelIndexPath = BayesConfigUtil.classfiConfigMap
					.get(labelIndexPathName);
			dictionaryPath = BayesConfigUtil.classfiConfigMap
					.get(dictionaryPathName);
			documentFrequencyPath = BayesConfigUtil.classfiConfigMap
					.get(documentFrequencyPathName);
			log.debug("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%modelPath"+modelPath+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			log.debug("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%labelIndexPath"+labelIndexPath+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			log.debug("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%dictionaryPath"+dictionaryPath+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			log.debug("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%documentFrequencyPath"+documentFrequencyPath+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		}

		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// 加载分类器，方便分类
		NaiveBayesModel model;
		try {
			model = NaiveBayesModel.materialize(new Path(modelPath),
					configuration);

			AbstractNaiveBayesClassifier classifier;
			classifier = BayesOperUtils.chooseClassifiet(complementary, model);

			// labels is a map label => classId 类别 索引文件
			labels = BayesUtils.readLabelIndex(configuration, new Path(
					labelIndexPath));
			// 字典文件
			Map<String, Integer> dictionary = BayesOperUtils.readDictionnary(
					configuration, new Path(dictionaryPath));

			// 文档 特征 频率
			Map<Integer, Long> documentFrequency = BayesOperUtils
					.readDocumentFrequency(configuration, documentFrequencyPath);

			// analyzer used to extract word from input 加载分词器
			Analyzer analyzer;
			analyzer = BayesOperUtils.loadAnalyzer(enAnlyzer);
			// analyzer = new WhitespaceAnalyzer(Version.LUCENE_43);
			// analyzer = new MMSegAnalyzer(Version.LUCENE_43);

			int labelCount = labels.size();
			int documentCount = documentFrequency.get(-1).intValue();

			System.out.println("Number of labels: " + labelCount);
			System.out.println("Number of documents in training set: "
					+ documentCount);

			// 分词
			// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			int wordCount = 0;
			Multiset<String> words = ConcurrentHashMultiset.create();
			BayesOperUtils.cutWordsByInputVector(input, wordCount, words,
					analyzer, dictionary);

			// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			// create vector wordId => weight using tfidf 计算输入向量的TF-IDF
			Vector vector = BayesOperUtils.caclulateVectorInputTFIDF(
					dictionary, documentFrequency, documentCount, wordCount,
					words);
			// 排序测试文本分类并按score降序排序
						// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			nodeClassfyScoreList = BayesOperUtils.classifyAndScoreDesc(
					classifier, vector);
			
			classfyDetailLogic.classifyNd(nodeClassfyScoreList, userInputHelp,
					userInputRslt, sessionId, labels, candidateRes,loopTime,resNewList);
			
		} catch (IOException e) {
			log.error("读取分类器模型出错" + e.getMessage());
			userInputRslt.setMessage("分类器分类失败!");
			userInputRslt.setRsltCode(ReturnCode.CLASSFI_ERROR.value());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.apache.mahout.classifier.naivebayes.AbstractNaiveBayesClassifier;
import org.apache.mahout.classifier.naivebayes.BayesUtils;
import org.apache.mahout.classifier.naivebayes.ComplementaryNaiveBayesClassifier;
import org.apache.mahout.classifier.naivebayes.NaiveBayesModel;
import org.apache.mahout.classifier.naivebayes.StandardNaiveBayesClassifier;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileIterable;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.Vector.Element;
import org.apache.mahout.vectorizer.TFIDF;

import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.Multiset;
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
public class BayesOperUtils {

	/**
	 * 
	 * @param conf
	 * @param dictionnaryPath
	 * @return
	 * @Description: 加载字典文件
	 */
	public static Map<String, Integer> readDictionnary(Configuration conf,
			Path dictionnaryPath) {
		Map<String, Integer> dictionnary = new HashMap<String, Integer>();
		for (Pair<Text, IntWritable> pair : new SequenceFileIterable<Text, IntWritable>(
				dictionnaryPath, true, conf)) {
			dictionnary.put(pair.getFirst().toString(), pair.getSecond().get());
		}
		return dictionnary;
	}

	/**
	 * 
	 * @param conf
	 * @param documentFrequencyPath
	 * @return
	 * @Description:文档特征 频率
	 */
	public static Map<Integer, Long> readDocumentFrequency(Configuration conf,
			String documentFrequencyPath) {
		Map<Integer, Long> documentFrequency = new HashMap<Integer, Long>();
		// 循环读取文档词频，方便计算TF-IDF
		File dfCountFile = new File(documentFrequencyPath);
		if (dfCountFile.isFile())
			return null;
		File[] dfCountFiles = dfCountFile.listFiles();
		for (int i = 0; i < dfCountFiles.length; i++) {
			if (dfCountFiles[i].getName().contains("part")) {
				Path documentFrequencyPathSeq = new Path(documentFrequencyPath
						+ File.separator + dfCountFiles[i].getName());
				for (Pair<IntWritable, LongWritable> pair : new SequenceFileIterable<IntWritable, LongWritable>(
						documentFrequencyPathSeq, true, conf)) {
					documentFrequency.put(pair.getFirst().get(), pair
							.getSecond().get());
				}
			}
		}
		return documentFrequency;
	}

	/**
	 * @param complementary
	 * @param model
	 * @return
	 * @Description: 分类算法封装
	 */
	public static AbstractNaiveBayesClassifier chooseClassifiet(
			Boolean complementary, NaiveBayesModel model) {
		AbstractNaiveBayesClassifier classifier;
		if (complementary) {
			classifier = new ComplementaryNaiveBayesClassifier(model);
		} else {
			classifier = new StandardNaiveBayesClassifier(model);
		}
		return classifier;
	}

	/**
	 * @param enAnlyzer
	 * @return
	 * @Description: 分类器设置
	 */
	public static Analyzer loadAnalyzer(Boolean enAnlyzer) {
		Analyzer analyzer;
		if (enAnlyzer)
			analyzer = new StandardAnalyzer(Version.LUCENE_43);
		else
			analyzer = new SmartChineseAnalyzer(Version.LUCENE_43, true);
		return analyzer;
	}

	/**
	 * 
	 * @param inputString
	 * @param wordCount
	 * @param words
	 * @param analyzer
	 * @throws IOException
	 * @Description: 分词
	 */
	public static void cutWordsByInputVector(String inputString, int wordCount,
			Multiset<String> words, Analyzer analyzer,
			Map<String, Integer> dictionary) throws IOException {

		// extract words from tweet
		TokenStream ts = analyzer.tokenStream("text", new StringReader(
				inputString));
		CharTermAttribute termAtt = ts.addAttribute(CharTermAttribute.class);
		ts.reset();
		while (ts.incrementToken()) {
			if (termAtt.length() > 0) {
				String word = ts.getAttribute(CharTermAttribute.class)
						.toString();
				Integer wordId = dictionary.get(word);
				// if the word is not in the dictionary, skip it
				if (wordId != null) {
					words.add(word);
					wordCount++;
				}
			}
		}
		analyzer.close();
	}

	/**
	 * @param dictionary
	 * @param documentFrequency
	 * @param documentCount
	 * @param wordCount
	 * @param words
	 * @return
	 * @Description: 求输入向量的TF-IDF
	 */
	public static Vector caclulateVectorInputTFIDF(
			Map<String, Integer> dictionary,
			Map<Integer, Long> documentFrequency, int documentCount,
			int wordCount, Multiset<String> words) {
		Vector vector = new RandomAccessSparseVector(10000);
		TFIDF tfidf = new TFIDF();
		for (Multiset.Entry<String> entry : words.entrySet()) {
			String word = entry.getElement();
			int count = entry.getCount();
			Integer wordId = dictionary.get(word);
			Long freq = documentFrequency.get(wordId);
			if (freq == null)
				freq = 1L;
			double tfIdfValue = tfidf.calculate(count, freq.intValue(),
					wordCount, documentCount);
			vector.setQuick(wordId, tfIdfValue);
		}
		return vector;
	}

	/**
	 * @param classifier
	 * @param vector
	 * @return
	 * @Description: 根据分类器分类并降序排序
	 */
	public static List<NodeClassfyScore> classifyAndScoreDesc(
			AbstractNaiveBayesClassifier classifier, Vector vector) {
		List<NodeClassfyScore> sortScoreList = new ArrayList<NodeClassfyScore>();
		Vector resultVector = classifier.classifyFull(vector);
		int bestCategoryId = -1;
		for (Element element : resultVector.all()) {
			int categoryId = element.index();
			double score = element.get();
			sortScoreList.add(new NodeClassfyScore(categoryId, score));
			// System.out.print("  " + labels.get(categoryId) + ": " + score);
		}

		Collections.sort(sortScoreList, new Comparator<NodeClassfyScore>() {
			@Override
			public int compare(NodeClassfyScore o1, NodeClassfyScore o2) {
				return o2.getScore().compareTo(o1.getScore());
			}

		});
		return sortScoreList;
	}

}

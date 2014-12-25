/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.framework;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.soledede.classfy.bayes.util.BayesConfigUtil;

/**
 * @Title:
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年6月30日
 * @Version:1.1.0
 */
public class FileInitialListener implements ServletContextListener {
	private static final Logger log = Logger
			.getLogger(ServletContextListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		InputStream  in = null;
		Pattern pt = Pattern.compile("^Window|window|WINDOW+\\*");
		Matcher m =pt.matcher(System.getProperties().getProperty("os.name"));
		if(m.find()){//windows
			in= this.getClass().getClassLoader()
					.getResourceAsStream("classficonfig.properties");
		}else{//unix
			in= this.getClass().getClassLoader()
					.getResourceAsStream("classficonfig_unix.properties");
			log.debug("进入Linux世界,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
		}
		
		
		Properties p = new Properties();
		try {
			p.load(in);
			if (BayesConfigUtil.classfiConfigMap == null)
				BayesConfigUtil.classfiConfigMap = new HashMap<String, String>();
			
			//问题节点分类
			String modelPath = p.getProperty("modelPath");
			String labelIndexPath = p.getProperty("labelIndexPath");
			String dictionaryPath = p.getProperty("dictionaryPath");
			String documentFrequencyPath = p
					.getProperty("documentFrequencyPath");

			String complementary = p.getProperty("complementary");
			String enAnlyzer = p.getProperty("enAnlyzer");

			String questionNodePath = p.getProperty("questionNodePath");
			
			//***************************************************************************//
			//餐厅节点分类
			String modelPath_res = p.getProperty("modelPath_res");
			String labelIndexPath_res = p.getProperty("labelIndexPath_res");
			String dictionaryPath_res = p.getProperty("dictionaryPath_res");
			String documentFrequencyPath_res = p
					.getProperty("documentFrequencyPath_res");


			String cadidaterespath = p.getProperty("cadidaterespath");
			//***************************************************************************//
			
			
			//***************************************************************************//
			//餐厅推荐理由节点分类
			String modelPath_son = p.getProperty("modelPath_son");
			String labelIndexPath_son = p.getProperty("labelIndexPath_son");
			String dictionaryPath_son = p.getProperty("dictionaryPath_son");
			String documentFrequencyPath_son = p
					.getProperty("documentFrequencyPath_son");


			String resonpath = p.getProperty("resonpath");
			//***************************************************************************//
			

			//***************************************************************************//
			//输入--》标签节点分类
			String modelPath_label_i = p.getProperty("modelPath_label_i");
			String labelIndexPath_label_i = p.getProperty("labelIndexPath_label_i");
			String dictionaryPath_label_i = p.getProperty("dictionaryPath_label_i");
			String documentFrequencyPath_label_i = p
					.getProperty("documentFrequencyPath_label_i");


			String inputlabelpath = p.getProperty("inputlabelpath");
			//***************************************************************************//
			
			//***************************************************************************//
			//标签--》餐厅节点分类
			String modelPath_label_r = p.getProperty("modelPath_label_r");
			String labelIndexPath_label_r = p.getProperty("labelIndexPath_label_r");
			String dictionaryPath_label_r = p.getProperty("dictionaryPath_label_r");
			String documentFrequencyPath_label_r = p
					.getProperty("documentFrequencyPath_label_r");


			String labelrespath = p.getProperty("labelrespath");
			//***************************************************************************//			
			
			
			
			
			//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^//
			//问题节点分类
			BayesConfigUtil.classfiConfigMap.put("modelPath", modelPath);
			BayesConfigUtil.classfiConfigMap.put("labelIndexPath",
					labelIndexPath);
			BayesConfigUtil.classfiConfigMap.put("dictionaryPath",
					dictionaryPath);
			BayesConfigUtil.classfiConfigMap.put("documentFrequencyPath",
					documentFrequencyPath);

			BayesConfigUtil.classfiConfigMap
					.put("complementary", complementary);
			BayesConfigUtil.classfiConfigMap.put("enAnlyzer", enAnlyzer);

			BayesConfigUtil.classfiConfigMap.put("questionNodePath",
					questionNodePath);
			
			//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^//
			
			
			//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^//
			//推荐理由--》餐厅节点分类
			BayesConfigUtil.classfiConfigMap.put("modelPath_son", modelPath_son);
			BayesConfigUtil.classfiConfigMap.put("labelIndexPath_son",
					labelIndexPath_son);
			BayesConfigUtil.classfiConfigMap.put("dictionaryPath_son",
					dictionaryPath_son);
			BayesConfigUtil.classfiConfigMap.put("documentFrequencyPath_son",
					documentFrequencyPath_son);

			BayesConfigUtil.classfiConfigMap.put("resonpath",
					resonpath);
			
			//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^//
			
			//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^//
			//餐厅节点分类
			BayesConfigUtil.classfiConfigMap.put("modelPath_res", modelPath_res);
			BayesConfigUtil.classfiConfigMap.put("labelIndexPath_res",
					labelIndexPath_res);
			BayesConfigUtil.classfiConfigMap.put("dictionaryPath_res",
					dictionaryPath_res);
			BayesConfigUtil.classfiConfigMap.put("documentFrequencyPath_res",
					documentFrequencyPath_res);

			BayesConfigUtil.classfiConfigMap.put("cadidaterespath",
					cadidaterespath);
			
			//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^//
			
			//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^//
			//输入--》标签节点分类
			BayesConfigUtil.classfiConfigMap.put("modelPath_label_i", modelPath_label_i);
			BayesConfigUtil.classfiConfigMap.put("labelIndexPath_label_i",
					labelIndexPath_label_i);
			BayesConfigUtil.classfiConfigMap.put("dictionaryPath_label_i",
					dictionaryPath_label_i);
			BayesConfigUtil.classfiConfigMap.put("documentFrequencyPath_label_i",
					documentFrequencyPath_label_i);

			BayesConfigUtil.classfiConfigMap.put("inputlabelpath",
					inputlabelpath);
			
			//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^//
			
			//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^//
			//标签--》餐厅节点分类
			BayesConfigUtil.classfiConfigMap.put("modelPath_label_r", modelPath_label_r);
			BayesConfigUtil.classfiConfigMap.put("labelIndexPath_label_r",
					labelIndexPath_label_r);
			BayesConfigUtil.classfiConfigMap.put("dictionaryPath_label_r",
					dictionaryPath_label_r);
			BayesConfigUtil.classfiConfigMap.put("documentFrequencyPath_label_r",
					documentFrequencyPath_label_r);

			BayesConfigUtil.classfiConfigMap.put("labelrespath",
					labelrespath);
			
			//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^//
			
		} catch (IOException e) {
			log.error("初始化读取分类字典、模型以及索引失败!"+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
				p = null;// 销毁properties
			} catch (IOException e) {
				log.error("初始化读取分类字典、模型以及索引失败!"+e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

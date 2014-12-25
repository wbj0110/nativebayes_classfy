/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.util;

/**
 * @Title:
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年7月14日
 * @Version:1.1.0
 */
public class PathName {

	// 训练生成目录 这里是map的key
	public static final String QUESTIONNODEPATH = "questionNodePath";  //输入--》问题节点
	public static final String CADIDATERESPATH = "cadidaterespath"; //输入--》餐厅节点
	public static final String RESONPATH = "resonpath"; //餐厅--》推荐理由节点
	public static final String LABELRESPATH = "labelrespath"; //标签--》餐厅节点
	public static final String INPUTLABELPATH = "inputlabelpath"; //输入--标签节点
	

	// 问题节点分类所需路径 map-key
	public static final String MODELPATH = "modelPath";
	public static final String LABELINDEXPATH = "labelIndexPath";
	public static final String DICTIONARYPATH = "dictionaryPath";
	public static final String DOCUMENTFREQUENCYPATH = "documentFrequencyPath";

	// 餐厅节点分类所需路径 map-key
	public static final String MODELPATH_RES = "modelPath_res";
	public static final String LABELINDEXPATH_RES = "labelIndexPath_res";
	public static final String DICTIONARYPATH_RES = "dictionaryPath_res";
	public static final String DOCUMENTFREQUENCYPATH_RES = "documentFrequencyPath_res";

	// 推荐理由节点分类所需路径 map-key
	public static final String MODELPATH_SON = "modelPath_son";
	public static final String LABELINDEXPATH_SON = "labelIndexPath_son";
	public static final String DICTIONARYPATH_SON = "dictionaryPath_son";
	public static final String DOCUMENTFREQUENCYPATH_SON = "documentFrequencyPath_son";
	
	// 输入--》标签节点分类所需路径 map-key
	public static final String MODELPATH_LABEL_I = "modelPath_label_i";
	public static final String LABELINDEXPATH_LABEL_I = "labelIndexPath_label_i";
	public static final String DICTIONARYPATH_LABEL_I = "dictionaryPath_label_i";
	public static final String DOCUMENTFREQUENCYPATH_LABEL_I = "documentFrequencyPath_label_i";
	
	// 标签--》推荐餐厅节点分类所需路径 map-key
	public static final String MODELPATH_LABEL_R = "modelPath_label_r";
	public static final String LABELINDEXPATH_LABEL_R = "labelIndexPath_label_r";
	public static final String DICTIONARYPATH_LABEL_R = "dictionaryPath_label_r";
	public static final String DOCUMENTFREQUENCYPATH_LABEL_R = "documentFrequencyPath_label_r";
	

}

/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.soledede.classfy.bayes.entity.Dictiona;

/**
 * @Title: 数据字典维护接口
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年7月11日
 * @Version:1.1.0
 */
public interface DicService {

	/**
	 * 
	 * @param tagType
	 * @return
	 * @throws Exception
	 * @Description:根据标签id查找关键词 （维度--》具体维度所对应的关键词）
	 */
	@Transactional
	public List<Dictiona> findByTagTypeId(int tagType) throws Exception;

	/**
	 * 
	 * @param cityId
	 * @return
	 * @throws Exception
	 * @Description:根据标签城市id查找关键词 （维度--》具体维度所对应的关键词）
	 */
	@Transactional
	public List<Dictiona> findByCityId(int cityId) throws Exception;
	
	
	/**
	 * 
	 * @param cityId
	 * @param tagType
	 * @return
	 * @throws Exception  
	 * @Description:根据cityid或tagtype获取Dic
	 */
	@Transactional
	public List<Dictiona> findByCityIdOrTagTypeId(int cityId,int tagType) throws Exception;

	@Transactional
	public void saveDic(Dictiona dic) throws Exception;
	
	@Transactional
	public void dictionaryManage(String word,Integer tagType,Integer cityId,Boolean isDelete) throws Exception;
	
	
	
}

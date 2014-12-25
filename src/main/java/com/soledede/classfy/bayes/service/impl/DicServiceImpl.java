/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */
package com.soledede.classfy.bayes.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.soledede.classfy.bayes.base.AbstractBaseDao;
import com.soledede.classfy.bayes.entity.Dictiona;
import com.soledede.classfy.bayes.service.DicService;

/**
 * @Title:
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年7月11日
 * @Version:1.1.0
 */
@Service
public class DicServiceImpl extends AbstractBaseDao<Dictiona, Integer>
		implements DicService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private String findByTagTypeIdHql = "from Dictiona where tagTypeId = ?";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xiaomishu.spring4rest.bayes.service.DicService#findByTagTypeId(int)
	 */
	@Override
	public List<Dictiona> findByTagTypeId(int tagType) throws Exception {
		/*
		 * Object [] args = {tagType}; return
		 * findByHql(findByTagTypeIdHql,args);
		 */

		String sql = "SELECT *  FROM T_DIC WHERE tag_type_id = ?";
		Object[] params = new Object[] { tagType };
		int[] types = new int[] { Types.INTEGER };
		return jdbcTemplate.query(sql, params, types, new DictionaMapper());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xiaomishu.spring4rest.bayes.service.DicService#findByCityId(int)
	 */
	@Override
	public List<Dictiona> findByCityId(int cityId) throws Exception {
		String sql = "SELECT * FROM T_DIC WHERE city_id = ?";
		Object[] params = new Object[] { cityId };
		int[] types = new int[] { Types.INTEGER };
		return jdbcTemplate.query(sql, params, types, new DictionaMapper());
	}

	
	
	@Override
	public List<Dictiona> findByCityIdOrTagTypeId(int cityId, int tagType)
			throws Exception {
		String sql = "SELECT * FROM T_DIC WHERE city_id=? OR tag_type_id=? ";
		Object[] params = new Object[] { cityId,tagType };
		int[] types = new int[] { Types.INTEGER ,Types.INTEGER};
		return jdbcTemplate.query(sql, params, types, new DictionaMapper());
	}

	@Override
	public void dictionaryManage(String word, Integer tagType, Integer cityId,
			Boolean isDelete) throws Exception {
			
			if(isDelete){
				//删除
				String sql = "DELETE FROM T_DIC WHERE word = ? and ((tag_type_id=? and (city_id is NULL OR city_id=0)) or (city_id=? and (tag_type_id is NULL OR tag_type_id=0))) ";
				Object[] params = new Object[] { word,tagType,cityId};
				int[] types = new int[] {Types.VARCHAR,Types.INTEGER,Types.INTEGER};
				jdbcTemplate.update(sql, params, types);
				}else{
				//保存
				Dictiona d =  new Dictiona(word, tagType, cityId);
				saveDic(d);
			}
	}

	@Override
	public void saveDic(Dictiona dic) throws Exception {
		save(dic);
	}

	
	
	
	protected class DictionaMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Dictiona dic = new Dictiona();
			dic.setId(rs.getInt("id"));
			dic.setCityId(rs.getInt("city_id"));
			dic.setTagTypeId(rs.getInt("tag_type_id"));
			dic.setWord(rs.getString("word"));
			return dic;
		}

	}

}

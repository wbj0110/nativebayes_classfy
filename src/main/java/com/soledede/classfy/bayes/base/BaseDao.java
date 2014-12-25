package com.soledede.classfy.bayes.base;

import java.io.Serializable;
import java.util.List;

import com.soledede.classfy.bayes.common.Pager;

/**
 * 
 * @Title:
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年7月10日
 * @Version:1.1.0
 */
public interface BaseDao<T, PK extends Serializable> {
	/**
	 * 增加
	 * 
	 * @param entity
	 */
	public void save(T entity);

	/**
	 * 删除
	 * 
	 * @param pk
	 */
	public void delete(PK pk);

	/**
	 * 修改
	 * 
	 * @param entity
	 */
	public void update(T entity);

	/**
	 * 查询 按ID
	 * 
	 * @param pk
	 */
	public T findById(PK pk);

	/**
	 * 查询全部信息
	 * 
	 * @return
	 */
	public List<T> findAll();

	/**
	 * 分页
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pager<T> findByPage(int pageNo, int pageSize);

}

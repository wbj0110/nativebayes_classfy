package com.soledede.classfy.bayes.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.LockMode;

import com.soledede.classfy.bayes.common.Pager;

public interface IDao<T, PK extends Serializable> {
	/**
	 * 增加
	 * 
	 * @param entity
	 */
	public void save(T entity);

	/**
	 * 
	 * @param entity
	 * @Description:
	 */
	public void saveOrUpdate(T entity);

	/**
	 * 删除
	 * 
	 * @param pk
	 */
	public void delete(Class<T> entityClass, PK pk);

	/**
	 * 
	 * @param entity
	 * @Description:
	 */
	public void delete(T entity);

	/**
	 * 修改
	 * 
	 * @param entity
	 */
	public void update(T entity);

	/**
	 * 
	 * @param entity
	 * @param lock
	 * @Description:
	 */
	public void update(T entity, LockMode lock);

	/**
	 * 查询 按ID
	 * 
	 * @param pk
	 */
	public T findById(Class<T> entityClass, PK pk);

	/**
	 * 
	 * @param hql
	 * @param values
	 * @return
	 * @Description:通过hql查询
	 */
	public List<T> findByHql(String hql, Object... values);

	/**
	 * 查询全部信息
	 * 
	 * @return
	 */
	public List<T> findAll(Class<T> entityClass);

	/**
	 * 分页信息
	 * 
	 * @param entityClass
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pager<T> findByPage(Class<T> entityClass, int pageNo, int pageSize);
}

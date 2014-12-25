package com.soledede.classfy.bayes.base;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soledede.classfy.bayes.common.GetEntityClassUtil;
import com.soledede.classfy.bayes.common.Pager;
import com.soledede.classfy.bayes.dao.IDao;

@Service
public abstract class AbstractBaseDao<T, PK extends Serializable> {
	Class entityClass = GetEntityClassUtil.getEntityClass(getClass());
	@Resource
	private IDao<T, Serializable> iDao;

	@Transactional
	public void save(T entity) {
		iDao.save(entity);
	}

	@Transactional
	public void saveOrUpdate(T entity) {
		iDao.saveOrUpdate(entity);
	}

	@Transactional
	public void delete(PK pk) {
		iDao.delete(entityClass, pk);
	}

	@Transactional
	public void update(T entity) {
		iDao.update(entity);
	}

	public T findById(PK pk) {
		return iDao.findById(entityClass, pk);
	}

	public List<T> findAll() {
		return iDao.findAll(entityClass);
	}

	public Pager<T> findByPage(int pageNo, int pageSize) {
		return iDao.findByPage(entityClass, pageNo, pageSize);
	}

	/**
	 * 
	 * @param hql
	 * @return
	 * @Description:通过hql查询
	 */
	@Transactional
	public List findByHql(String hql, Object... values) {
		return iDao.findByHql(hql, values);
	}

}

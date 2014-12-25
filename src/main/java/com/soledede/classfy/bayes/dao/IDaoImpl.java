package com.soledede.classfy.bayes.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.soledede.classfy.bayes.common.Pager;

@Repository
public class IDaoImpl<T, PK extends Serializable> implements
		IDao<T, Serializable> {
	/*
	 * @Resource public void setSessionFacotry(SessionFactory sessionFacotry) {
	 * super.setSessionFactory(sessionFacotry); }
	 */
	public IDaoImpl() {
		super();
	}

	/**
	 * Autowired 自动装配 相当于get() set()
	 */
	@Autowired
	protected SessionFactory sessionFactory;

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * gerCurrentSession 会自动关闭session，使用的是当前的session事务! :
	 * 
	 * @return$
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * " openSession 需要手动关闭session 意思是打开一个新的session
	 * 
	 * @return
	 */
	public Session getNewSession() {
		return sessionFactory.openSession();
	}

	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}

	@Override
	public List<T> findByHql(String hql, Object... values) {
		/*
		 * Session session = getNewSession(); Transaction tx =
		 * session.beginTransaction(); Query query = session.createQuery(hql);
		 * if(values!=null && values.length>0){ for(int i=0 ;i<
		 * values.length;i++){ query.setParameter(i+"", values[i]); } }
		 * tx.commit(); session.flush(); session.clear(); session.close();
		 * return query.list();
		 */
		return (List<T>) this.hibernateTemplate.find(hql, values);
	}

	@Override
	public void saveOrUpdate(T entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Class<T> entityClass, Serializable pk) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(T entity, LockMode lock) {
		// TODO Auto-generated method stub

	}

	@Override
	public T findById(Class<T> entityClass, Serializable pk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findAll(Class<T> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pager<T> findByPage(Class<T> entityClass, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据 id 查询信息
	 * 
	 * @param id
	 *            , E; v. ]( u' H8 Y! ?
	 * @return
	 */
	public Object load(Class c, String id) {
		Session session = getSession();
		return session.get(c, id);
	}

	/**
	 * 获取所有信息* {
	 * 
	 * @param c
	 *            #
	 * 
	 * @return
	 */
	public List getAllList(Class c) {
		String hql = "from " + c.getName();
		Session session = getSession();
		return session.createQuery(hql).list();
	}

	/**
	 * 获取总数量,
	 * 
	 * @param
	 * @return
	 */
	public Long getTotalCount(Class c) {
		Session session = getNewSession();
		String hql = "select count(*) from " + c.getName();
		Long count = (Long) session.createQuery(hql).uniqueResult();
		session.close();
		return count != null ? count.longValue() : 0;
	}

	/**
	 * 保存
	 * 
	 * @param bean
	 * 
	 */
	@Override
	public void save(Object bean) {
		try {
			Session session = getNewSession();
			session.save(bean);
			session.flush();
			session.clear();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 1 更新 &
	 * 
	 * @param bean
	 * 
	 */
	@Override
	public void update(Object bean) {
		Session session = getNewSession();
		session.update(bean);
		session.flush();
		session.clear();
		session.close();
	}

	/**
	 * 删除
	 * 
	 * @param bean
	 * 
	 */
	@Override
	public void delete(Object bean) {
		Session session = getNewSession();
		session.delete(bean);
		session.flush();
		session.clear();
		session.close();
	}

	/**
	 * 根据ID删除
	 * 
	 * @param c
	 *            类
	 * 
	 * @param id
	 * 
	 */
	public void delete(Class c, String id) {
		Session session = getNewSession();
		Object obj = session.get(c, id);
		session.delete(obj);
		flush();
		clear();
	}

	/**
	 * 批量删除, l6 J6 E V4 U/ T
	 * 
	 * @param c
	 *            类
	 * 
	 * @param ids
	 *            ID 集合
	 * 
	 */
	public void delete(Class c, String[] ids) {
		for (String id : ids) {
			Object obj = getSession().get(c, id);
			if (obj != null) {
				getSession().delete(obj);
			}
		}
	}

	/*
	 * public T get(PK id, LockMode lock,Class<T> entityClass) { T entity = (T)
	 * this.getHibernateTemplate().get(entityClass, id, lock); if (entity !=
	 * null) { this.flush();// 如果实体不为null,立即刷新,否则锁不会生效 } return entity; }
	 * 
	 * 
	 * public Integer getRowCount(DetachedCriteria criteria) {
	 * criteria.setProjection(Projections.rowCount()); return (Integer)
	 * this.findByCriteria(criteria, 0, 1).get(0); }
	 * 
	 * @SuppressWarnings("unchecked") public T load(Class<T> entityClass,PK id)
	 * { return (T) this.getHibernateTemplate().load(entityClass, id); }
	 * 
	 * @SuppressWarnings("unchecked") public T load(Class<T> entityClass,PK id,
	 * LockMode lock) { T entity = (T)
	 * this.getHibernateTemplate().load(entityClass, id, lock); if (entity !=
	 * null) { this.flush();// 如果实体不为null,立即刷新,否则锁不会生效 } return entity; }
	 * 
	 * @SuppressWarnings("unchecked") public List<T> loadAll(Class<T>
	 * entityClass) { return this.getHibernateTemplate().loadAll(entityClass); }
	 * 
	 * @SuppressWarnings("unchecked") public List<T> find(String hql) { return
	 * (List<T>)this.getHibernateTemplate().find(hql); }
	 * 
	 * @SuppressWarnings("unchecked") public List<T> find(String hql, Object...
	 * values) { return (List<T>) this.getHibernateTemplate().find(hql, values);
	 * }
	 * 
	 * @SuppressWarnings("unchecked") public List<T> findByNamedQuery(String
	 * queryName, Object... values) { return (List<T>)
	 * this.getHibernateTemplate().findByNamedQuery(queryName, values); }
	 * 
	 * @SuppressWarnings("unchecked") public List<T> findByNamedQuery(String
	 * queryName) { return (List<T>)
	 * this.getHibernateTemplate().findByNamedQuery(queryName); }
	 * 
	 * @SuppressWarnings("unchecked") public List<T>
	 * findByNamedQueryAndNamedParam(String queryName, Map<String, Object>
	 * params) { return (List<T>)
	 * this.getHibernateTemplate().findByNamedQueryAndNamedParam( queryName,
	 * (String[]) params.keySet().toArray(), params.values().toArray()); }
	 * 
	 * @SuppressWarnings("unchecked") public List<T> findByNamedParam(String
	 * queryName, Map<String, Object> params) { return (List<T>)
	 * this.getHibernateTemplate() .findByNamedParam(queryName, (String[])
	 * params.keySet().toArray(), params.values().toArray()); }
	 * 
	 * @SuppressWarnings("unchecked") public List<T>
	 * findByCriteria(DetachedCriteria criteria) { return (List<T>)
	 * this.getHibernateTemplate().findByCriteria(criteria); }
	 * 
	 * @SuppressWarnings("unchecked") public List<T>
	 * findByCriteria(DetachedCriteria criteria, Integer firstResult, Integer
	 * maxResults) { return
	 * (List<T>)this.getHibernateTemplate().findByCriteria(criteria,
	 * firstResult, maxResults); }
	 * 
	 * public void saveOrUpdate(T entity) {
	 * this.getHibernateTemplate().saveOrUpdate(entity); }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * public Integer bulkUpdate(String hql) { return
	 * this.getHibernateTemplate().bulkUpdate(hql); }
	 * 
	 * public Integer bulkUpdate(String hql, Object... values) { return
	 * this.getHibernateTemplate().bulkUpdate(hql, values); }
	 * 
	 * public void flush() throws HibernateException {
	 * this.getHibernateTemplate().flush(); }
	 * 
	 * public void lock(T entity, LockMode lock) throws HibernateException {
	 * this.getHibernateTemplate().lock(entity, lock); }
	 * 
	 * public DetachedCriteria createDetachedCriteria(Class<T> entityClass) {
	 * return DetachedCriteria.forClass(entityClass); }
	 * 
	 * public DetachedCriteria createDetachedCriteria(Class<T> entityClass,
	 * Class<? extends Serializable> c) { return DetachedCriteria.forClass(c); }
	 * 
	 * public Criteria createCriteria(Class<T> entityClass) { return
	 * this.createDetachedCriteria
	 * (entityClass).getExecutableCriteria(getHibernateTemplate
	 * ().getSessionFactory().openSession()); }
	 * 
	 * 
	 * public void delete(Class<T> entityClass, Serializable pk) { }
	 * 
	 * 
	 * @Override public void delete(T entity) {
	 * getHibernateTemplate().delete(entity); } public void delete(Collection<T>
	 * entitys){ this.getHibernateTemplate().deleteAll(entitys); } public void
	 * update(T entity) { getHibernateTemplate().update(entity); }
	 * 
	 * public void update(T entity, LockMode lock){
	 * this.getHibernateTemplate().update(entity, lock); this.flush();//
	 * 如果实体不为null,立即刷新,否则锁不会生效 }
	 * 
	 * public T findById(Class<T> entityClass, Serializable pk) { return
	 * getHibernateTemplate().get(entityClass, pk); }
	 * 
	 * public List<T> findAll(Class<T> entityClass) { return null; }
	 * 
	 * public void save(T entity) { getHibernateTemplate().save(entity); }
	 * 
	 * public Pager<T> findByPage(Class<T> entityClass,int pageNo,int pageSize)
	 * { Pager<T> pager = new Pager<T>();
	 * 
	 * return pager; }
	 */

}

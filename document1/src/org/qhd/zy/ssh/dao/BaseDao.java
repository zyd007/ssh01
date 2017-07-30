package org.qhd.zy.ssh.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;


import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.qhd.zy.ssh.model.Pager;
import org.qhd.zy.ssh.model.SystemContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
@SuppressWarnings("unchecked")

public class BaseDao<T> extends HibernateDaoSupport implements IBaseDao<T> {
	
	@SuppressWarnings("unused")
	@Resource(name="sessionFactory")
	private void setSuperSessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	private Class<T> clz;
	//把泛型转为class
	private Class<T> getClz() {
		if(clz==null) {
			//获取泛型的Class对象
			clz = ((Class<T>)
					(((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return clz;
	}
	@Override
	public void add(T t) {
		this.getHibernateTemplate().save(t);
	}

	@Override
	public void addObj(Object object) {
		this.getHibernateTemplate().save(object);
	}

	@Override
	public void delete(int id) {
		T t=this.load(id);
		this.getHibernateTemplate().delete(t);
	}

	@Override
	public void deleteObj(Object object) {
		this.getHibernateTemplate().delete(object);
	}

	@Override
	public void update(T t) {
		this.getHibernateTemplate().update(t);
	}

	@Override
	public void updateObj(Object object) {
		this.getHibernateTemplate().update(object);
	}

	@Override
	public T load(int id) {
		return this.getHibernateTemplate().load(getClz(), id);
	}
	private Query getQuery(String hql,Object[]args){
		Query q=this.getSession().createQuery(hql);
		if(args!=null){
			for(int i=0;i<args.length;i++){
				q.setParameter(i, args[i]);
			}
		}
		return q;
	}
	@Override
	public List<T> lists(String hql, Object[] args) {
		Query q=this.getQuery(hql, args);
		return q.list();
	}

	@Override
	public List<T> lists(String hql, Object arg) {
		return this.lists(hql, new Object[]{arg});
	}

	@Override
	public List<T> lists(String hql) {
		return this.lists(hql, null);
	}

	@Override
	public List<Object> listByObj(String hql, Object[] args) {
		return this.getQuery(hql, args).list();
	}

	@Override
	public List<Object> listByObj(String hql, Object arg) {
		return this.listByObj(hql, new Object[]{arg});
	}

	@Override
	public List<Object> listByObj(String hql) {
		return this.listByObj(hql, null);
	}
	private String getCountQuery(String hql){
		int index=hql.indexOf("from");
		hql=hql.substring(index);
		hql="select count(*) "+hql;
		hql=hql.replace("fetch"," ");
	
		return hql;
	}
	@Override
	public Pager<T> find(String hql, Object[] args) {
		int pagerOffest=SystemContext.getOffest();
		int pagerSize=SystemContext.getPagerSize();
		/*测试的时候可以用
		if(pagerOffest<0)pagerOffest=0;
		if(pagerSize<=0)pagerSize=10;*/
		Query q=this.getQuery(hql, args);
		Query cq=this.getQuery(this.getCountQuery(hql), args);
		long cs=(long)cq.uniqueResult();
		
		q=q.setFirstResult(pagerOffest).setMaxResults(pagerSize);
		Pager<T>pagers=new Pager<T>();
		pagers.setDatas(q.list());
		pagers.setPagerSize(pagerSize);
		pagers.setPagerOffest(pagerOffest);
		pagers.setTotalRecord(cs);
		return pagers;
	}

	@Override
	public Pager<T> find(String hql, Object arg) {
		return this.find(hql, new Object[]{arg});
	}

	@Override
	public Pager<T> find(String hql) {
		return this.find(hql, null);
	}

	@Override
	public Pager<Object> findByObj(String hql, Object[] args) {
		int pagerOffest=SystemContext.getOffest();
		int pagerSize=SystemContext.getPagerSize();
//		if(pagerOffest<0)pagerOffest=0;
//		if(pagerSize<=0)pagerSize=10;
		Query q=this.getQuery(hql, args);
		Query cq=this.getQuery(this.getCountQuery(hql), args);
		long cs=(long)cq.uniqueResult();
		q=q.setFirstResult(pagerOffest).setMaxResults(pagerSize);
		Pager<Object>pagers=new Pager<Object>();
		pagers.setDatas(q.list());
		pagers.setPagerSize(pagerSize);
		pagers.setPagerOffest(pagerOffest);
		pagers.setTotalRecord(cs);
		return pagers;
	}

	@Override
	public Pager<Object> findByObj(String hql, Object arg) {
		return this.findByObj(hql, new Object[]{arg});
	}

	@Override
	public Pager<Object> findByObj(String hql) {
		return this.findByObj(hql, null);
	}
	
	@Override
	public Object queryByHql(String hql, Object[] args) {
		Query q=this.getQuery(hql, args);
		return q.uniqueResult();
	}

	@Override
	public Object queryByHql(String hql, Object arg) {
		return this.queryByHql(hql, new Object[]{arg});
	}

	@Override
	public Object queryByHql(String hql) {
		return this.queryByHql(hql, null);
	}

	@Override
	public void executeByHql(String hql, Object[] args) {
		Query q=this.getQuery(hql, args);
		q.executeUpdate();
	}

	@Override
	public void executeByHql(String hql, Object arg) {
		this.executeByHql(hql, new Object[]{arg});
	}

	@Override
	public void executeByHql(String hql) {
		this.executeByHql(hql, null);
	}

}

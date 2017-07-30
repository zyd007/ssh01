package org.qhd.zy.ssh.dao;

import java.util.List;

import org.qhd.zy.ssh.model.Pager;

public interface IBaseDao<T> {
	public void add(T t);
	public void addObj(Object object);
	public void delete(int id);
	public void deleteObj(Object object);
	public void update(T t);
	public void updateObj(Object object);
	public T load(int id);
	/*
	 * 一般都需要三个方法重载(不分页的)
	 */
	public List<T> lists(String hql,Object[]args);
	public List<T> lists(String hql,Object arg);
	public List<T> lists(String hql);
	public List<Object> listByObj(String hql,Object[]args);
	public List<Object> listByObj(String hql,Object arg);
	public List<Object> listByObj(String hql);
	/*
	 * 一般都需要三个方法重载(分页的)
	 */
	public Pager<T> find(String hql,Object[]args);
	public Pager<T> find(String hql,Object arg);
	public Pager<T> find(String hql);
	public Pager<Object> findByObj(String hql,Object[]args);
	public Pager<Object> findByObj(String hql,Object arg);
	public Pager<Object> findByObj(String hql);
	/*
	 * 查询一个对象
	 */
	public Object queryByHql(String hql,Object[]args);
	public Object queryByHql(String hql,Object arg);
	public Object queryByHql(String hql);
	/*
	 *Dml 原生态sql 更新或者删除一组对象
	 */
	public void executeByHql(String hql,Object[]args);
	public void executeByHql(String hql,Object arg);
	public void executeByHql(String hql);
}

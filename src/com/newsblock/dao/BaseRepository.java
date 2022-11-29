package com.newsblock.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.query.Query;

public class BaseRepository<K> implements Repository<K, Serializable> {
	

	@Override
	public void save(K entity) {

		//DaoSession ds = DaoSessionPool.createTransaction();
		DaoSession ds = DaoSessionPool.createSession();
		ds.t = ds.session.beginTransaction();
		ds.session.save(entity);
		ds.t.commit();
		// ds.factory.close();
		ds.close();
	}

	@Override
	public void update(K entity) {

		//DaoSession ds = DaoSessionPool.createTransaction();
		DaoSession ds = DaoSessionPool.createSession();
		ds.t = ds.session.beginTransaction();
		ds.session.update(entity);
		ds.t.commit();
		// ds.factory.close();
		ds.close();
	}

	@Override
	public void deleteAll() {

		List<K> entityList = findAll();
		for (K entity : entityList) {
			delete(entity);
		}

	}

	@Override
	public void delete(K entity) {

		DaoSession ds = DaoSessionPool.createSession();
		ds.t = ds.session.beginTransaction();
		ds.session.delete(entity);
		ds.t.commit();
		// ds.factory.close();
		ds.close();
	}

	public List<K> entityList(String hql) {

		DaoSession ds = DaoSessionPool.createSession();

		@SuppressWarnings({ "unchecked", "deprecation" })
		List<K> list = (List<K>) ds.session.getSession().createQuery(hql).list();
		ds.close();
		return list;
	}

	public List<K> entityList(String hql, LinkedHashMap<String, Object> params) {

		DaoSession ds = DaoSessionPool.createSession();

		@SuppressWarnings({ "unchecked", "deprecation" })
		Query query = ds.session.getSession().createQuery(hql);
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		// query.setFirstResult(0);
		// query.setMaxResults(3);
		List<K> list = (List<K>) query.list();
		ds.close();
		return list;
	}

	@Override
	public K findById(Serializable id) {
		return null;
	}

	@Override
	public List<K> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Object[]> queryList(String sql) {

		DaoSession ds = DaoSessionPool.createSession();

		@SuppressWarnings({ "unchecked", "deprecation" })
		SQLQuery query = ds.session.getSession().createSQLQuery(sql);
		
		List<Object[]> list = query.getResultList();
		
		ds.close();
		
		return list;
	}
	
	protected String getInConditionIDs(ArrayList<Integer> listIds) {
		boolean flag_first = true;
		String ids = "";
		for(int id : listIds) {
			if(flag_first) {
				ids = id+"";
				flag_first = false;
			}else {
				ids = ids+","+id;
			}
		}
		return ids;
	}

}

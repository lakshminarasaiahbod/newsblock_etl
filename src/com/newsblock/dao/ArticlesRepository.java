package com.newsblock.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.newsblock.model.Articles;

public class ArticlesRepository extends BaseRepository<Articles> {

	@Override
	public Articles findById(Serializable id) {

		DaoSession ds = DaoSessionPool.createSession();
		Articles entity = (Articles) ds.session.get(Articles.class, id);
		ds.close();
		return entity;
	}

	@Override
	public List<Articles> findAll() {
		// TODO Auto-generated method stub
		return entityList("from Articles");
	}

	public List<Articles> findByList(List<Integer> articleIds, Timestamp timestamp) {

		// TODO Auto-generated method stub

		return entityList("from Articles where isactive = 1 and DATE(createdon) = DATE(" + timestamp + ") and  ("
				+ articleIds + ")");
	}

}

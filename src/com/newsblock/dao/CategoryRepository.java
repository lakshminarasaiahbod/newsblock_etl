package com.newsblock.dao;

import java.io.Serializable;
import java.util.List;

import com.newsblock.model.Articles;
import com.newsblock.model.Categories;

public class CategoryRepository extends BaseRepository<Categories> {
	
	@Override
	public Categories findById(Serializable id) {
		
		DaoSession ds = DaoSessionPool.createSession();
		Categories entity = (Categories) ds.session.get(Categories.class, id);
		ds.close();
		return entity;
	}
	
	@Override
	public List<Categories> findAll() {
		// TODO Auto-generated method stub
		return entityList("from Categories");
	}
	
	public List<Categories> findAllActive() {
		// TODO Auto-generated method stub
		return entityList("from Categories where isactive = 1");
	}
	
	
}



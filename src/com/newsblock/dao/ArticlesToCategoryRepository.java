package com.newsblock.dao;

import java.io.Serializable;
import java.util.List;

import com.newsblock.model.Articles;
import com.newsblock.model.ArticletoCategory;

public class ArticlesToCategoryRepository extends BaseRepository<ArticletoCategory> {
	
	@Override
	public ArticletoCategory findById(Serializable id) {
		
		DaoSession ds = DaoSessionPool.createSession();
		ArticletoCategory entity = (ArticletoCategory) ds.session.get(ArticletoCategory.class, id);
		ds.close();
		return entity;
	}
	
	@Override
	public List<ArticletoCategory> findAll() {
		// TODO Auto-generated method stub
		return entityList("from ArticletoCategory");
	}

	public List<ArticletoCategory> findByCategory(int id) {
		// TODO Auto-generated method stub
		return entityList("from ArticletoCategory where isactive = 1 and categoryid = " + id);
	}
	
	
}



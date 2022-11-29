package com.newsblock.dao;

import java.io.Serializable;
import java.util.List;

import com.newsblock.model.Articles;
import com.newsblock.model.ArticletoCategory;
import com.newsblock.model.ConfigProperties;
import com.newsblock.model.UserLogins;

public class UserLoginsRepository extends BaseRepository<UserLogins> {
	
	@Override
	public UserLogins findById(Serializable id) {
		
		DaoSession ds = DaoSessionPool.createSession();
		UserLogins entity = (UserLogins) ds.session.get(UserLogins.class, id);
		ds.close();
		return entity;
	}
	
	@Override
	public List<UserLogins> findAll() {
		// TODO Auto-generated method stub
		return entityList("from UserLogins");
	}
	
	
}



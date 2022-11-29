package com.newsblock.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.newsblock.model.Articles;
import com.newsblock.model.ArticletoCategory;
import com.newsblock.model.ConfigProperties;

public class ConfigPropertiesRepository extends BaseRepository<ConfigProperties> {
	
	@Override
	public ConfigProperties findById(Serializable id) {
		
		DaoSession ds = DaoSessionPool.createSession();
		ConfigProperties entity = (ConfigProperties) ds.session.get(ConfigProperties.class, id);
		ds.close();
		return entity;
	}
	
	@Override
	public List<ConfigProperties> findAll() {
		// TODO Auto-generated method stub
		return entityList("from ConfigProperties");
	}
	
	public List<ConfigProperties> findByPropertyName(String propertyname) {
		// TODO Auto-generated method stub
		return entityList("from ConfigProperties where isactive = 1 and propertyname = " + propertyname);
	}
	
	public HashMap<String, ConfigProperties> getMapByName() {

		List<ConfigProperties> list = findAll();
		HashMap<String, ConfigProperties> map = new HashMap<>();

		for (ConfigProperties config : list) {
			map.put(config.getPropertyname(), config);
		}
		return map;
	}

}



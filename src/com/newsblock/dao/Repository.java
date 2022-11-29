/**
 * 
 */
package com.newsblock.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author sravanpasunoori
 *
 */
@SuppressWarnings("hiding")
public interface Repository<K, Serializable> {

	public void save(K entity);
	
	public void update(K entity);
	
	public void deleteAll();
	
	public void delete(K entity);
	
	public List<K> findAll();
	
	public K findById(Serializable id);
		
}

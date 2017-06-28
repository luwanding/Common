package com.chyjr.uams.client.cache;

import java.util.Map;

public class CacheOperator {
	
	private DefaultCache cache = null;
	
	public CacheOperator (DefaultCache cache){
		this.cache = cache ;
	}
	
	public Object putObjectToCache(Object key, Object value){
			return cache.put(key, value);
	}
	
	public void putAllObjectToCache(Map map){
		 cache.putAll(map);
	}
	
	public void clear(){
		 cache.clear();
	}
	
	public Object removeObjectOfCache(Object key){
		 return cache.remove(key);
	}
	
	public Object getObjectOfCache(Object key){
		 return cache.get(key);
	}
	
	public Object getCacheName(){
		 return cache.getName();
	}
	
	public int getCacheByteSize(){
		 return cache.getCacheSize();
	}
	
	public int getCacheSize(){
		 return cache.size();
	}
}

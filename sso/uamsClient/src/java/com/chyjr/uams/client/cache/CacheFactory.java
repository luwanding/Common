package com.chyjr.uams.client.cache;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.chyjr.uams.client.util.ClientConfigure;

/**
 * 通过客户端缓存工厂创建缓存实例
 * @author jx_xudelin
 *
 */
public class CacheFactory {
	
	
	private static final Map<String,CacheOperator> CACHE_HANDLE = new HashMap();
	private final static CacheFactory cacheFactory = new CacheFactory();
	
	private CacheFactory(){
	}
	
	public  static CacheOperator getInstance(final String configFile) throws IOException{
		CacheOperator cache = CACHE_HANDLE.get(configFile);
		if(ClientConfigure.getInstance(configFile).getIsCache() && cache == null){
			synchronized(cacheFactory){
			     DefaultCache defaultCache = new DefaultCache("UAMS_Client_"+configFile,ClientConfigure.getInstance(configFile).getMaxCacheSize(),ClientConfigure.getInstance(configFile).getMaxLifeTime());
			     cache = new CacheOperator(defaultCache);
		    	 CACHE_HANDLE.put(configFile, cache);
			}
		}
		return cache;
	}
	
	
}

package com.chyjr.platform.cache.local;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.chyjr.platform.cache.ICacheInit;
import com.chyjr.platform.cache.local.core.DefaultCache;


/**
 * @ProjectName:  [PlatformCacheImpl]
 * @Package:      [com.chyjr.platform.cache.local.LocalCacheClientImpl.java] 
 * @ClassName:    [LocalCacheClientImpl]  
 * @Description:  [local cache impl]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 15, 2014 1:15:13 PM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 15, 2014 1:15:13 PM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class LocalCacheClientImpl {

    private DefaultCache<Object, Object> cache = null;

    @Autowired
    private ICacheInit cacheInitialization;

    /**
     * @param cache
     */
    public LocalCacheClientImpl(String instanceId, int maxCacheSize, int maxLifeTime) {
        this.cache = cacheInitialization.getLocalInstance(instanceId, maxCacheSize, maxLifeTime);
    }

    /**
     * put a object to cache
     *
     * @param key
     * @param value
     * @return this object
     */
    public Object putObjectToCache(Object key, Object value) {
        return cache.put(key, value);
    }

    /**
     * put all objects to cache
     *
     * @param map all put in objects
     */
    public void putAllObjectToCache(Map<?, ?> map) {
        cache.putAll(map);
    }

    /**
     * remove all objects of cache.
     */
    public void clear() {
        cache.clear();
    }

    /**
     * find cache by key and remove object in cache.
     *
     * @param key
     * @return remove this object
     */
    public Object removeObjectOfCache(Object key) {
        return cache.remove(key);
    }

    /**
     * find object by cache key
     *
     * @param key
     * @return
     */
    public Object getObjectOfCache(Object key) {
        return cache.get(key);
    }

    /**
     * get cache name
     */
    public Object getCacheName() {
        return cache.getName();
    }

    /**
     * get cache size
     *
     * @return
     */
    public int getCacheByteSize() {
        return cache.getCacheSize();
    }

    public int getCacheSize() {
        return cache.size();
    }
}

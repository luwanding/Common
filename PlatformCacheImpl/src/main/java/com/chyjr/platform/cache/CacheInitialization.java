package com.chyjr.platform.cache;

import com.alisoft.xplatform.asf.cache.ICacheManager;
import com.alisoft.xplatform.asf.cache.IMemcachedCache;
import com.alisoft.xplatform.asf.cache.memcached.CacheUtil;
import com.alisoft.xplatform.asf.cache.memcached.MemcachedCacheManager;
import com.chyjr.platform.cache.local.CacheLoaderCfg;
import com.chyjr.platform.cache.local.ICacheLoad;
import com.chyjr.platform.cache.local.LocalCacheConfig;
import com.chyjr.platform.cache.local.core.DefaultCache;
import com.chyjr.platform.common.util.SpringBeansUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存接口工厂实现类，用于获取缓存操作实例。
 * 1、分布式缓存 通过  getRomoteInstance(String cacheName)方法获得操作实例。
 * 2、本地缓存通过  getLocalInstance(final String instanceId,final int maxCacheSize, final long maxLifeTime)
 * 方法获得操作实例。
 *
 * @ProjectName:  [PlatformCacheImpl]
 * @Package:      [com.chyjr.platform.cache.CacheInitialization.java] 
 * @ClassName:    [CacheInitialization]  
 * @Description:  [缓存接口工厂实现类，用于获取缓存操作实例]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 15, 2014 9:53:39 AM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 15, 2014 9:53:39 AM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 */
public class CacheInitialization{

    /**
     * remote cache manager instance.
     */
    private static ICacheManager<IMemcachedCache> manager;
    private static Object lockObjectRemote = new Object();
    private static Object lockObjectLocal = new Object();


    /**
     * local cache operator handler pool
     */
    private static final Map<String, DefaultCache<Object, Object>> LOCAL_CACHE_HANDLE = new ConcurrentHashMap<String, DefaultCache<Object, Object>>();

    private static final Map<String, IMemcachedCache> REMOTE_CACHE_HANDLE = new ConcurrentHashMap<String, IMemcachedCache>();

    private final static String LINE_SPCE = System.getProperty("line.separator");

    private static final Logger Log = Logger.getLogger(CacheInitialization.class);

    //本地缓存配置文件地址
    private String localConfig;
    //分布式缓存配置文件地址
    private String remoteConfig;


    public CacheInitialization(String localConfig, String remoteConfig) {
        this.localConfig = localConfig;
        this.remoteConfig = remoteConfig;
        initLocalCache();
        initRemoteCache();
    }

    private void initLocalCache() {
        if (LOCAL_CACHE_HANDLE.size() == 0) {
            synchronized (lockObjectLocal) {
                if (LOCAL_CACHE_HANDLE.size() == 0) {
                    LocalCacheConfig.initConfig(localConfig);
                    for (LocalCacheConfig localCacheConfig : LocalCacheConfig.localCacheConfigList) {
                        DefaultCache defaultCache = new DefaultCache<Object, Object>(localCacheConfig.getCacheName(), localCacheConfig.getMaxSize(), localCacheConfig.getMaxLife());
                        try {
                            for (CacheLoaderCfg cfg : localCacheConfig.getLoader()) {
                                ICacheLoad cacheLoad = (ICacheLoad) SpringBeansUtil.getBean(cfg.getRef());
                                defaultCache.put(cfg.getKey(), cacheLoad.load());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        LOCAL_CACHE_HANDLE.put(localCacheConfig.getCacheName(), defaultCache);
                    }
                }
            }
        }
    }


    /**
     * 初始化与集中式缓存的连接池,初始化时依赖于缓存配置文件
     * memcached.xml,该文件
     */
    private void initRemoteCache() {
        if (manager == null) {
            synchronized (lockObjectRemote) {
                if (manager == null) {
                    manager = CacheUtil.getCacheManager(IMemcachedCache.class, MemcachedCacheManager.class.getName());
                    manager.setConfigFile(remoteConfig);
                    manager.setIsloadClassPath(false);
                    manager.start();
                }
            }
        }
    }


    /**
     * Create a local cache by instanceId
     * if this instanceId exist so return.
     *
     * @return CacheOperator
     * @throws IOException
     */
    public DefaultCache<Object, Object> getLocalInstance(final String cacheName) {
        return LOCAL_CACHE_HANDLE.get(cacheName);
    }

    /**
     * 通过缓存名获得一个缓存的操作实例
     *
     * @param cacheName 缓存名字
     * @return
     */
    public IMemcachedCache getRomoteInstance(final String cacheName) {
        IMemcachedCache memcache = manager.getCache(cacheName);
        REMOTE_CACHE_HANDLE.put(cacheName, memcache);
        return memcache;
    }


    /**
     * 显示分布式缓存当前使用情况
     *
     * @return 返回一段status描述
     */
    public String showRemoteCacheStatusInfo() {
        StringBuilder info = new StringBuilder();
        info.append(LINE_SPCE + " %%%%%%%%%%%%%%%%%%%%%%%% Remote Cache Info begin%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        Iterator<Entry<String, IMemcachedCache>> it = REMOTE_CACHE_HANDLE.entrySet().iterator();
        int size = REMOTE_CACHE_HANDLE.size();
        int index = 0;
        while (it.hasNext()) {
            Entry<String, IMemcachedCache> e = it.next();
            info.append(LINE_SPCE);
            info.append(" cache id: ");
            info.append(e.getKey());
            info.append(LINE_SPCE);
            info.append(" cache name: ");
            info.append(e.getKey());
            info.append(LINE_SPCE);
            info.append(" cache count: ");
            info.append(size);
            info.append(LINE_SPCE);
            info.append(" host: ");
            info.append((e.getValue().stats()[0].getServerHost()));
            info.append(LINE_SPCE);
            info.append(" remarkInfo: ");
            info.append((e.getValue().stats()[0].getStatInfo()));
            index++;
            if (index < size)
                info.append(LINE_SPCE);
            info.append(" ***************************************");
        }
        info.append(LINE_SPCE);
        info.append(" %%%%%%%%%%%%%%%%%%%%%%%% Remote Cache Info end %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        info.append(LINE_SPCE);
        return info.toString();
    }

    /**
     * 显示本地缓存当前使用情况
     *
     * @return 返回一段status描述
     */
    public String showLocalCacheStatusInfo() {
        StringBuilder info = new StringBuilder();
        info.append(LINE_SPCE + " %%%%%%%%%%%%%%%%%%%%%%%% Local Cache Info begin%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        Iterator<Entry<String, DefaultCache<Object, Object>>> it = LOCAL_CACHE_HANDLE.entrySet().iterator();
        int size = LOCAL_CACHE_HANDLE.size();
        int index = 0;
        while (it.hasNext()) {
            Entry<String, DefaultCache<Object, Object>> e = it.next();
            info.append(LINE_SPCE);
            info.append(" cache id: ");
            info.append(e.getKey());
            info.append(LINE_SPCE);
            info.append(" cache name: ");
            info.append(e.getValue().getName());
            info.append(LINE_SPCE);
            info.append(" cache size: ");
            info.append(e.getValue().getCacheSize());
            info.append(LINE_SPCE);
            info.append(" cache max size: ");
            info.append((e.getValue().getMaxCacheSize()));
            index++;
            if (index < size)
                info.append(LINE_SPCE);
            info.append(" ***************************************");
        }
        info.append(LINE_SPCE);
        info.append(" %%%%%%%%%%%%%%%%%%%%%%%% Local Cache Info end %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        info.append(LINE_SPCE);
        return info.toString();
    }

    /***
     * 停止缓存,

     public void stopCache(){
     REMOTE_CACHE_HANDLE.clear();
     manager.stop();
     }*/

}

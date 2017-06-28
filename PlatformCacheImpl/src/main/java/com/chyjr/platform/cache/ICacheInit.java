package com.chyjr.platform.cache;

import com.alisoft.xplatform.asf.cache.IMemcachedCache;
import com.chyjr.platform.cache.local.core.DefaultCache;


/**
 * @ProjectName:  [PlatformCacheImpl]
 * @Package:      [com.chyjr.platform.cache.ICacheInit.java] 
 * @ClassName:    [ICacheInit]  
 * @Description:  [缓存接口]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 15, 2014 9:55:08 AM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 15, 2014 9:55:08 AM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public interface ICacheInit {
	
	/**
	 * 获得本地缓存实例
	 * @param instanceId 实例编号
	 * @param maxCacheSize 最大缓存大小
	 * @param maxLifeTime 最大存在时间
	 * @return
	 */
    public DefaultCache<Object, Object> getLocalInstance(final String instanceId, final int maxCacheSize, final long maxLifeTime);
    
    /**
     * 获取集中式缓存
     * @param cacheName 缓存名称
     * @param configFile 缓存配置参数文件
     * @return
     */
    public IMemcachedCache getRomoteInstance(final String cacheName, final String configFile);
}

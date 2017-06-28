package com.chyjr.platform.cache.local.core;

/**
 * @ProjectName:  [PlatformCacheImpl]
 * @Package:      [com.chyjr.platform.cache.local.core.Cacheable.java] 
 * @ClassName:    [Cacheable]  
 * @Description:  [获取缓存大小]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 15, 2014 1:16:08 PM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 15, 2014 1:16:08 PM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public interface Cacheable extends java.io.Serializable {

    /**
     * Returns the approximate size of the Object in bytes. The size should be
     * considered to be a best estimate of how much memory the Object occupies
     * and may be based on empirical trials or dynamic calculations.<p>
     *
     * @return the size of the Object in bytes.
     */
    public int getCachedSize();
}
package com.chyjr.platform.cache.client;

/**
 * @ProjectName:  [PlatformCacheImpl]
 * @Package:      [com.chyjr.platform.cache.client.ICacheClient.java] 
 * @ClassName:    [ICacheClient]  
 * @Description:  [集中式缓存操作接口]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 15, 2014 10:27:34 AM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 15, 2014 10:27:34 AM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public interface ICacheClient {

    /**
     * 添加缓存元素超时时间按小时计算
     *
     * @param key   缓存
     * @param value 值
     * @param hour  过多少小时超时
     * @return
     */
    public Object putOutTimeHour(String key, Object value, int hour);

    /**
     * 添加缓存元素超时时间按分钟计算
     *
     * @param key    缓存
     * @param value  值
     * @param minute 过多少分钟超时
     * @return
     */
    public Object putOutTimeMinute(String key, Object value, int minute);

    /**
     * 添加缓存元素超时时间按天计算
     *
     * @param key   缓存
     * @param value 值
     * @param day   过多少天超时
     * @return
     */
    public Object putOutTimeDay(String key, Object value, int day);
}

package com.chyjr.platform.cache.client;


/**
 * @ProjectName:  [PlatformCacheImpl]
 * @Package:      [com.chyjr.platform.cache.client.RemoteCacheClientImpl.java] 
 * @ClassName:    [RemoteCacheClientImpl]  
 * @Description:  [分布式缓存实现接口]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 15, 2014 10:27:56 AM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 15, 2014 10:27:56 AM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class RemoteCacheClientImpl extends AbstractCacheClientImpl {


    /**
     * 初始化分布式缓存实例
     *
     * @param cacheName  缓存名称
     * @param appName    应用名称
     * @param moduleName 模块名称
     */
    public RemoteCacheClientImpl(String cacheName, String appName, String moduleName, String configFile) {
        super(cacheName, appName, moduleName, configFile);
    }

    /**
     * 添加缓存元素超时时间按小时计算
     *
     * @param key   缓存
     * @param value 值
     * @param hour  过多少小时超时
     * @return
     */
    public Object putOutTimeHour(String key, Object value, int hour) {
        return this.putOutTimeMinute(key, value, (hour * 60));
    }

    /**
     * 添加缓存元素超时时间按分钟计算
     *
     * @param key    缓存
     * @param value  值
     * @param minute 过多少分钟超时
     * @return
     */
    public Object putOutTimeMinute(String key, Object value, int minute) {
        return this.put(key, value, (minute * 60));
    }

    /**
     * 添加缓存元素超时时间按天计算
     *
     * @param key   缓存
     * @param value 值
     * @param day   过多少天超时
     * @return
     */
    public Object putOutTimeDay(String key, Object value, int day) {
        return this.putOutTimeHour(key, value, (day * 24));
    }

}

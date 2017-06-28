package com.chyjr.platform.cache.client;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.alisoft.xplatform.asf.cache.IMemcachedCache;
import com.alisoft.xplatform.asf.cache.memcached.MemcacheStats;
import com.alisoft.xplatform.asf.cache.memcached.MemcacheStatsSlab;
import com.alisoft.xplatform.asf.cache.memcached.MemcachedResponse;
import com.chyjr.platform.cache.ICacheInit;

/**
 * @ProjectName:  [PlatformCacheImpl]
 * @Package:      [com.chyjr.platform.cache.client.AbstractCacheClientImpl.java] 
 * @ClassName:    [AbstractCacheClientImpl]  
 * @Description:  [分布式式缓存操作实现抽象类]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 15, 2014 10:17:51 AM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 15, 2014 10:17:51 AM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public abstract class AbstractCacheClientImpl implements ICacheClient, IMemcachedCache {

    private IMemcachedCache cache;
    @Autowired
    private ICacheInit cacheInitialization;

    private String appName; //应用名称
    private String moduleName;//模块名称
    private static String splitChar = "_";

    /**
     * 初始化分布式缓存实例
     *
     * @param cacheName  缓存名称
     * @param appName    应用名称
     * @param moduleName 模块名称
     */
    public AbstractCacheClientImpl(String cacheName, String appName, String moduleName, String configFile) {
        this.cache = cacheInitialization.getRomoteInstance(cacheName, configFile);
    }

    /**
     * 删除所有缓存内的数据
     *
     * @return
     */
    public boolean clear() {
        return cache.clear();
    }


    /**
     * 获得组合Key
     *
     * @param key
     * @return
     */
    private String getKey(String key) {
        StringBuilder sb = new StringBuilder();
        sb.append(appName);
        sb.append(splitChar);
        sb.append(moduleName);
        sb.append(splitChar);
        sb.append(key);
        return sb.toString();
    }

    /**
     * 是否包含了指定key的数据
     *
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        return cache.containsKey(getKey(key));
    }

    /**
     * 释放Cache占用的资源
     */
    public void destroy() {
        cache.destroy();
    }

    /**
     * 获取缓存数据
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return cache.get(getKey(key));
    }

    /**
     * 缓存所有的key的集合
     *
     * @return
     */
    public Set<String> keySet() {
        return cache.keySet();
    }

    /**
     * 保存数据
     *
     * @param key
     * @param value
     * @return
     */
    public Object put(String key, Object value) {
        return cache.put(getKey(key), value);
    }

    /**
     * 保存有有效期的数据
     *
     * @param key
     * @param value
     * @param 有效期
     * @return
     */
    public Object put(String key, Object value, Date date) {
        return cache.put(getKey(key), value, date);
    }

    /**
     * 保存有有效期的数据
     *
     * @param key
     * @param value
     * @param 数据超时的秒数
     * @return
     */
    public Object put(String key, Object value, int arg2) {
        return cache.put(getKey(key), value, arg2);
    }

    /**
     * 移出缓存数据
     *
     * @param key
     * @return
     */
    public Object remove(String key) {
        return cache.remove(getKey(key));
    }

    /**
     * 缓存数据数量
     *
     * @return
     */
    public int size() {
        return cache.size();
    }

    /**
     * 缓存的所有value的集合
     *
     * @return
     */
    public Collection<Object> values() {

        return cache.values();
    }

    /**
     * 保存数据,前提是key不存在于memcache中，否则保存不成功
     *
     * @param key
     * @param value
     * @return
     */
    public boolean add(String key, Object value) {
        return cache.add(getKey(key), value);
    }

    /**
     * 保存有有效期的数据，前提是key不存在于memcache中，否则保存不成功
     *
     * @param key
     * @param value
     * @param 有效期
     * @return
     */
    public boolean add(String key, Object value, Date expiry) {
        return cache.add(getKey(key), value, expiry);
    }

    /**
     * key所对应的是一个计数器，实现减少decr的数量
     *
     * @param key
     * @param decr
     * @return
     */
    public long addOrDecr(String key, long decr) {
        return cache.addOrDecr(getKey(key), decr);
    }

    /**
     * key所对应的是一个计数器，实现增加inc的数量
     *
     * @param key
     * @param inc
     * @return
     */
    public long addOrIncr(String key, long inc) {
        return cache.addOrIncr(getKey(key), inc);
    }

    /**
     * 异步累减计数器，不保证累减成功
     *
     * @param key
     * @param decr
     */
    public void asynAddOrDecr(String key, long decr) {
        cache.asynAddOrDecr(getKey(key), decr);

    }

    /**
     * 异步累加计数器，不保证累加成功
     *
     * @param key
     * @param incr
     */
    public void asynAddOrIncr(String key, long incr) {
        cache.asynAddOrIncr(getKey(key), incr);

    }

    /**
     * 异步累减计数器，不保证累减成功
     *
     * @param key
     * @param decr
     */
    public void asynDecr(String key, long decr) {
        cache.asynDecr(getKey(key), decr);

    }

    /**
     * 异步累加计数器，不保证累加成功
     *
     * @param key
     * @param incr
     */
    public void asynIncr(String key, long incr) {
        cache.asynIncr(getKey(key), incr);
    }

    /**
     * 异步存入数据，当前立即返回，稍后存入数据
     *
     * @param key
     * @param value
     */
    public void asynPut(String key, Object value) {
        cache.asynPut(getKey(key), value);
    }

    /**
     * 异步存储计数器,不保证保存成功
     *
     * @param key
     * @param count
     */
    public void asynStoreCounter(String key, long count) {
        cache.asynStoreCounter(getKey(key), count);
    }

    /**
     * key所对应的是一个计数器，实现减少decr的数量
     *
     * @param key
     * @param decr
     * @return
     */
    public long decr(String key, long decr) {
        return cache.decr(getKey(key), decr);
    }

    /**
     * 降低memcache的交互频繁造成的性能损失，因此采用本地cache结合memcache的方式
     *
     * @param key
     * @param 本地缓存失效时间单位秒
     * @return
     */
    public Object get(String key, int localTTL) {
        return cache.get(getKey(key), localTTL);
    }

    /**
     * 获取寄存器，-1表示不存在
     *
     * @param key
     */
    public long getCounter(String key) {
        return cache.getCounter(getKey(key));
    }

    /**
     * 获取多个keys对应的key&value Entrys
     *
     * @param keys
     * @return
     */
    public Map<String, Object> getMulti(String[] keys) {
        if (keys != null) {
            String[] lkeys = new String[keys.length];
            for (int i = 0; i < keys.length; i++) {
                System.arraycopy(keys, 0, lkeys, 0, keys.length);
                lkeys[i] = getKey(lkeys[i]);
            }
            return cache.getMulti(lkeys);
        }
        return cache.getMulti(keys);
    }

    /**
     * key所对应的是一个计数器，实现增加inc的数量
     *
     * @param key
     * @param inc
     * @return
     */
    public Object[] getMultiArray(String[] keys) {
        if (keys != null) {
            String[] lkeys = new String[keys.length];
            for (int i = 0; i < keys.length; i++) {
                System.arraycopy(keys, 0, lkeys, 0, keys.length);
                lkeys[i] = getKey(lkeys[i]);
            }
            return cache.getMultiArray(lkeys);
        }
        return cache.getMultiArray(keys);
    }

    /**
     * key所对应的是一个计数器，实现增加inc的数量
     *
     * @param key
     * @param inc
     * @return
     */
    public long incr(String key, long incr) {
        return cache.incr(getKey(key), incr);
    }

    /**
     * 这个接口返回的Key如果采用fast模式，
     * 那么返回的key可能已经被清除或者失效，但是在内存中还有痕迹，如果是非fast模式，那么就会精确返回，但是效率较低
     *
     * @param 是否需要去交验key是否存在
     * @return
     */
    public Set<String> keySet(boolean fast) {
        return cache.keySet(fast);
    }


    /**
     * 保存数据,前提是key必须存在于memcache中，否则保存不成功
     *
     * @param key
     * @param value
     * @return
     */
    public boolean replace(String key, Object value) {
        return cache.replace(getKey(key), value);
    }

    /**
     * 保存有有效期的数据，前提是key必须存在于memcache中，否则保存不成功
     *
     * @param key
     * @param value
     * @param 有效期
     * @return
     */
    public boolean replace(String key, Object value, Date date) {
        return cache.replace(getKey(key), value, date);
    }

    /**
     * 设置统计时间，单位为秒
     *
     * @param checkInterval
     */
    public void setStatisticsInterval(long checkInterval) {
        cache.setStatisticsInterval(checkInterval);
    }

    /**
     * 统计Cache的响应时间
     *
     * @return
     */
    public MemcachedResponse statCacheResponse() {
        return cache.statCacheResponse();
    }

    /**
     * 统计Memcache使用的情况
     *
     * @return
     */
    public MemcacheStats[] stats() {
        return cache.stats();
    }

    /**
     * 统计Items的存储情况
     *
     * @param servers
     * @return
     */
    public Map<?, ?> statsItems() {
        return cache.statsItems();
    }

    /**
     * 统计服务器的Slab的情况
     *
     * @return
     */
    public MemcacheStatsSlab[] statsSlabs() {
        return cache.statsSlabs();
    }

    /**
     * 存储计数器
     *
     * @param key
     * @param count
     */
    public void storeCounter(String key, long count) {
        cache.storeCounter(getKey(key), count);
    }
}

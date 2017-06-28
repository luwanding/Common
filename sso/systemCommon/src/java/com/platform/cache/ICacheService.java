package com.platform.cache;

import java.util.Date;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author pengchenq
 * @version $ ICacheService.java, v 0.1 2011-8-23 下午04:31:49 pengchenq Exp $
 * @since   JDK1.6
 */
public interface ICacheService<K, V> {
    /**
     * 
     * <pre>
     * 保存数据到CACHE中
     * </pre>
     *
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value);
    
    /**
     * 
     * <pre>
     * 保存数据到CACHE中锁的
     * </pre>
     *
     * @param key
     * @param value
     * @return
     */
    public V add(K key, V value);
    
    /**
     * 
     * <pre>
     * 保存数据到CACHE，数据有效期,不推荐使用，DATE为过期时间，如果客户端和服务器的时间
     * 差别大，会出问题。
     * </pre>
     *
     * @param key
     * @param value
     * @param date
     * @return
     */
    public V put(K key, V value, Date date);
    
    /**
     * 
     * <pre>
     * 保存数据到CACHE，数据有效期锁的,不推荐使用，DATE为过期时间，如果客户端和服务器的时间
     * 差别大，会出问题。
     * </pre>
     *
     * @param key
     * @param value
     * @param date
     * @return
     */
    public V add(K key, V value, Date date);
    
    /**
     * 
     * <pre>
     * 保存数据到CACHE中，有效期为秒
     * </pre>
     *
     * @param key
     * @param value
     * @param TTL
     * @return
     */
    public V putTimeOut(K key, V value, int timeOut);
    
    /**
     * 
     * <pre>
     * 保存数据到CACHE中，有效期为秒锁的
     * </pre>
     *
     * @param key
     * @param value
     * @param TTL
     * @return
     */
    public V add(K key, V value, Integer TTL);
    
    /**
     * 保存数据到CACHE中，有效期为秒锁的
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public Object addTimeOut(String key, Object value, int  timeout);
    
    /**
     * 
     * <pre>
     * 从CACHE中获取数据
     * </pre>
     *
     * @param key
     * @return
     */
    public V get(K key);
    
    /**
     *     
     * <pre>
     * 将数据从CACHE移走
     * </pre>
     *
     * @param key
     * @return
     */
    public V remove(K key);
    
    /**
     * 
     * <pre>
     * 将CACHE中的数据替换掉
     * </pre>
     *
     * @param key
     * @param value
     * @return
     */
    public V replace(K key,V value);
    
    /**
     * 
     * <pre>
     * 清空CACHE
     * </pre>
     *
     * @return
     */
    public boolean flushAll();
}

package com.platform.cache.memcache.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.danga.MemCached.MemCachedClient;
import com.platform.cache.memcache.IMemcachedService;
import com.platform.cache.memcache.common.MemcacheUtil;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author pengchenq
 * @version $ MemcachedController.java, v 0.1 2011-8-24 下午02:58:10 pengchenq Exp $
 * @since   JDK1.6
 */
public class MemcahcedClientProxy implements IMemcachedService{

    private static final Logger         logger               = Logger
                                                                 .getLogger(MemcachedController.class);
    
    private static MemCachedClient      mc                   = null;

    private static MemcahcedClientProxy instance             = null;

    private static Object               ob                   = new Object();

    /**
    * 
    * <pre>
    * MEMCACHE的单例
    * </pre>
    *
    * @return
    */
    protected static MemcahcedClientProxy getInstance() {

        if (instance == null) {

            synchronized (ob) {

                if (instance == null) {

                    try {

                        instance = new MemcahcedClientProxy();

                    } catch (Exception e) {

                        logger.error(e);
                    }

                }

            }

        }

        return instance;
    }

    private MemcahcedClientProxy() {

    }

    public Object get(String key) {
        if (StringUtils.isBlank(key)) {
            logger.warn("param key can not null");
            return null;
        }
        logger.info("memcache key = " + addPrefix(key) );
        return mc.get(addPrefix(key));
    }

    public Boolean put(String key, Object value) {
        if (StringUtils.isBlank(key) || value == null || "".equals(value)) {
            logger.warn("param key,value can not null");
            return false;
        }
        logger.info("memcache key = " + addPrefix(key) );
        return mc.set(addPrefix(key), value);
    }
    
    public Boolean add(String key, Object value) {
        if (StringUtils.isBlank(key) || value == null || "".equals(value)) {
            logger.warn("param key,value can not null");
            return false;
        }
        logger.info("memcache key = " + addPrefix(key) );
        return mc.add(addPrefix(key), value);
    }

    public Boolean putTimeOut(String key, Object value, int timeout) {
        if (StringUtils.isBlank(key) || value == null || "".equals(value) || timeout < 1) {
            logger.warn("param key,value can not null");
            return false;
        }
        logger.info("memcache key = " + addPrefix(key) );
        return mc.set(addPrefix(key), value, new Date(timeout*1000));
    }   

    public Boolean put(String key, Object value, Date expiry) {
        if (StringUtils.isBlank(key) || value == null || "".equals(value) || expiry == null) {
            logger.warn("param key,value,expiry can not null");
            return false;
        }
        logger.info("memcache key = " + addPrefix(key) );
        return mc.set(addPrefix(key), value, expiry);
    }
    
    public Boolean add(String key, Object value, Date expiry) {
        if (StringUtils.isBlank(key) || value == null || "".equals(value) || expiry == null) {
            logger.warn("param key,value,expiry can not null");
            return false;
        }
        logger.info("memcache key = " + addPrefix(key) );
        return mc.add(addPrefix(key), value, expiry);
    }

    public Boolean remove(String key) {
        if (StringUtils.isBlank(key)) {
            logger.warn("param key can not null");
            return false;
        }
        logger.info("memcache key = " + addPrefix(key) );
        return mc.delete(addPrefix(key));
    }

    public Boolean replace(String key, Object value) {
        if (StringUtils.isBlank(key) || value == null || "".equals(value)) {
            logger.warn("param key,value can not null");
            return false;
        }
        logger.info("memcache key = " + addPrefix(key) );
        return mc.replace(addPrefix(key), value);
    }

    public boolean flushAll() {
        return mc.flushAll();
    }

    public Object put(String key, Object value, Integer TTL) {
        if (StringUtils.isBlank(key) || value == null || "".equals(value) || TTL == null
            || TTL.intValue() < 1) {
            logger.warn("param key,value,expiry can not null");
            return false;
        }
        logger.info("memcache key = " + addPrefix(key) );
        return mc.set(addPrefix(key), value, TTL.intValue());
    }
    
    public Object add(String key, Object value, Integer TTL) {
        if (StringUtils.isBlank(key) || value == null || "".equals(value) || TTL == null
            || TTL.intValue() < 1) {
            logger.warn("param key,value,expiry can not null");
            return false;
        }
        logger.info("memcache key = " + addPrefix(key) );
        return mc.add(addPrefix(key), value, TTL.intValue());
    }
    
    public Object addTimeOut(String key, Object value, int  timeout) {
        if (StringUtils.isBlank(key) || value == null || "".equals(value) ) {
            logger.info("param key,value,expiry can not null");
            return false;
        }
        logger.info("memcache key = " + addPrefix(key) );
        return mc.add(addPrefix(key), value, new Date(timeout*1000));
    }

    private String addPrefix(String key) {
        StringBuilder sb = new StringBuilder();
        sb.append(MemcacheUtil.getPropbean().getDefaultkey()).append(key);
        return sb.toString().trim();
    }

    protected static MemCachedClient getMc() {
        return mc;
    }

    protected static void setMc(MemCachedClient mc) {
        MemcahcedClientProxy.mc = mc;
    }

}

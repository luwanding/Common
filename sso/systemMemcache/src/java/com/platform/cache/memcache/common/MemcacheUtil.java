package com.platform.cache.memcache.common;

/**
 * <pre>
 * MEMCACHE的静态信息类
 * </pre>
 *
 * @author pengchenq
 * @version $ MemcacheUtil.java, v 0.1 2011-8-24 下午02:58:10 pengchenq Exp $
 * @since   JDK1.6
 */
public class MemcacheUtil {

    private static PropertysBean propbean;

    private static String        configFilePath;

    public static String getConfigFilePath() {
        return configFilePath;
    }

    public static void setConfigFilePath(String configFilePath) {
        MemcacheUtil.configFilePath = configFilePath;
    }

    public static PropertysBean getPropbean() {
        return propbean;
    }

    public static void setPropbean(PropertysBean propbean) {
        MemcacheUtil.propbean = propbean;
    }

   

}

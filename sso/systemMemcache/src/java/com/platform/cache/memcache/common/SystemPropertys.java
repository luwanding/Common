package com.platform.cache.memcache.common;

import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


/**
 * <pre>
 * 读取配置文件的信息
 * </pre>
 *
 * @author pengchenq
 * @version $ MemcacheUtil.java, v 0.1 2011-8-24 下午02:58:10 pengchenq Exp $
 * @since   JDK1.6
 */
public class SystemPropertys extends PropertyPlaceholderConfigurer{
    
    private static PropertysBean propbean = new PropertysBean();

    public static void resolvePlaceholder(Properties props) {
        
        StringBuilder buff = new StringBuilder();

        if (props != null) {
            
              
            MemcacheUtil.setConfigFilePath(props.getProperty("memcache.config.file"));
            
            
            buff.append(props.getProperty("memcache.project.name"));
            
            buff.append(props.getProperty("memcache.project.code"));
            
            
            if(buff.length() > 1){
                
                propbean.setDefaultkey(buff.toString());
                
            }else{
                
                propbean.setDefaultkey("default0");
            }
            
            MemcacheUtil.setPropbean(propbean);
        }

    }
}

package com.platform.cache.memcache.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.danga.MemCached.MemCachedClient;
import com.platform.cache.ICacheControllerService;
import com.platform.cache.memcache.IMemcachedService;
import com.platform.cache.memcache.common.CacheXMLParser;
import com.platform.cache.memcache.common.MemcacheUtil;
import com.platform.cache.memcache.common.SystemPropertys;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author pengchenq
 * @version $ MemcachedController.java, v 0.1 2011-8-24 下午02:58:10 pengchenq Exp $
 * @since   JDK1.6
 */
@Service("cacheClient")
public class MemcachedController implements ICacheControllerService<IMemcachedService> {

    private static final Logger logger               = Logger.getLogger(MemcachedController.class);

    private CacheXMLParser      cacheXMLParser       = null;

    private MemCachedClient     mc                   = null;
    
    private String fileName             = "cache.properties";

    @Override
    public void InitConfig() {

        logger.info("memcache init start");

        setConfigFile();

        cacheXMLParser = new CacheXMLParser(MemcacheUtil.getConfigFilePath());

        mc = cacheXMLParser.getCommonMemcachedClient();

        MemcahcedClientProxy.setMc(mc);

        logger.info("memcache init success");
    }

    @Override
    public MemcahcedClientProxy getCacheClient() {
        return MemcahcedClientProxy.getInstance();
    }

    @Override
    public void reload(String configFile) {
    }

    @Override
    public void setConfigFile() {
        
        InputStream inputStream = null;
        
        String filePath = System.getProperty("resources.config.path") + File.separator + fileName;
        
        if(filePath != null){
            
            try {
                
                inputStream = new FileInputStream(filePath);
                
            } catch (FileNotFoundException e) {
                
                logger.error("", e);
            }
        
        }else{
            
            inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);    

        }            
        
        Properties props = new Properties();    
    
        try {
            
            props.load(inputStream);
            
            SystemPropertys.resolvePlaceholder(props);
            
        } catch (IOException e) {
            
            logger.error("", e);
        } 
        
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }



}

package com.chyjr.platform.cache.local;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 * @ProjectName:  [PlatformCacheImpl]
 * @Package:      [com.chyjr.platform.cache.local.LocalCacheConfig.java] 
 * @ClassName:    [LocalCacheConfig]  
 * @Description:  [本地缓存配置实体]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 15, 2014 1:15:45 PM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 15, 2014 1:15:45 PM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class LocalCacheConfig {

    //缓存名称
    private String cacheName;

    //最大大小
    private long maxSize;//KB

    //生命周期
    private long maxLife;//秒

    //启动加载
//	private CacheLoaderCfg cacheLoaderCfg;

    private List<CacheLoaderCfg> loader = new ArrayList<CacheLoaderCfg>();

    public static List<LocalCacheConfig> localCacheConfigList = new ArrayList<LocalCacheConfig>();


    /**
     * 初始化本地缓存配置信息
     *
     * @return 返回配置信息实例
     */
    public static LocalCacheConfig initConfig(String cfgPath) {
        File file = new File(cfgPath);
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(file);
            Element root = document.getRootElement();
            List<Element> caches = root.elements("cache");

            for (Element element : caches) {
                LocalCacheConfig config = new LocalCacheConfig();
                config.setCacheName(element.attributeValue("name"));
                String mm = element.element("maxSize").getStringValue();
                String ml = element.element("maxLife").getStringValue();
                config.setMaxSize((1024 * 1024 * Long.parseLong(mm)));
                config.setMaxLife(Long.parseLong(ml));
                Element loaders = element.element("loader");
                List<Element> ele = loaders.elements();
                List<CacheLoaderCfg> cacheLoaderCfgList = new ArrayList<CacheLoaderCfg>();
                for (Element node : ele) {
                    CacheLoaderCfg cacheLoaderCfg1 = new CacheLoaderCfg();
                    cacheLoaderCfg1.setKey(node.attributeValue("key"));
                    cacheLoaderCfg1.setRef(node.element("value").element("ref").attributeValue("id"));
                    cacheLoaderCfgList.add(cacheLoaderCfg1);
                }
                config.setLoader(cacheLoaderCfgList);
                localCacheConfigList.add(config);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }

    public long getMaxLife() {
        return maxLife;
    }

    public void setMaxLife(long maxLife) {
        this.maxLife = maxLife;
    }

//    public CacheLoaderCfg getCacheLoaderCfg() {
//        return cacheLoaderCfg;
//    }
//
//    public void setCacheLoaderCfg(CacheLoaderCfg cacheLoaderCfg) {
//        this.cacheLoaderCfg = cacheLoaderCfg;
//    }

    public List<LocalCacheConfig> getLocalCacheConfigList() {
        return localCacheConfigList;
    }

    public void setLocalCacheConfigList(List<LocalCacheConfig> localCacheConfigList) {
        this.localCacheConfigList = localCacheConfigList;
    }

    public List<CacheLoaderCfg> getLoader() {
        return loader;
    }

    public void setLoader(List<CacheLoaderCfg> loader) {
        this.loader = loader;
    }
}

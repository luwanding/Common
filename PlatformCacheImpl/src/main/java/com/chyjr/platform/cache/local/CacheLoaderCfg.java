package com.chyjr.platform.cache.local;
/**
 * @ProjectName:  [PlatformCacheImpl]
 * @Package:      [com.chyjr.platform.cache.local.CacheLoaderCfg.java] 
 * @ClassName:    [CacheLoaderCfg]  
 * @Description:  [缓存加载配置实体类]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 15, 2014 1:01:59 PM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 15, 2014 1:01:59 PM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class CacheLoaderCfg {
    private String key = "";//唯一键值
    private String ref = "";//引用
    private String value = "";//值

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

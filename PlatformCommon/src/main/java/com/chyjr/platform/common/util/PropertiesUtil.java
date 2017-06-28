package com.chyjr.platform.common.util;

import com.chyjr.platform.common.constants.PlatformConstants;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;


/**
 * 
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.util.PropertiesUtil.java] 
 * @ClassName:    [PropertiesUtil]  
 * @Description:  [系统properties配置文件读取工具类]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 14, 2014 5:08:44 PM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 14, 2014 5:08:44 PM]  
 * @UpdateRemark: [] 
 * @Version:      [v1.0]
 *
 */
public class PropertiesUtil {
	
	private static HashMap<String,Properties> properties = new HashMap<String,Properties>();
	
	/**
	 * 加载系统配置文件,该方法内置缓存
	 * @param fileName 文件名称
	 * @param mod 模块名称
	 * @return 返回 Properties
	 * @throws IOException
	 */
	public static Properties loadP(String fileName,String mod) throws IOException {
		String key = resolveSystemProperty(PlatformConstants.SYS_CFG_PRO_PATH)+ (mod == null || mod.length() == 0 ? "" : File.separator+mod) +File.separator+fileName;
		File file = new File(key);
		String storeKey = key + "_" + file.lastModified();//缓存Key
		Properties p = null;
		if(!properties.containsKey(storeKey)){
			synchronized(properties){
				InputStream inputStream = new FileInputStream(file);
				p = new Properties();
				p.load(inputStream);
				inputStream.close();
				properties.put(storeKey, p);
			}
		}else{
			p = properties.get(storeKey);
		}
       return p;
    }
    
	private static String resolveSystemProperty(String key) {
       if(key==null){
           return PlatformConstants.SYS_CFG_ENV_PATH;
       }
        return key;
    }
    /**
     * 加载Properties文件
     * @param path
     * @param cl
     * @return
     * @throws Exception
     */
	public static Map<String,String> load(String path, Class<?> cl) throws Exception{
		
		URL url = cl.getClassLoader().getResource(path);
    	File file = new File(url.getPath());
    	Properties perties = new Properties();
    	Map<String,String> val = new HashMap<String,String>(10);
        FileInputStream fis = null;
    	try {
            fis = new FileInputStream(file);
			perties.load(new InputStreamReader(fis,"gbk"));
            if(perties.isEmpty()){
                return val;
            }
            Iterator<Entry<Object, Object>> it = perties.entrySet().iterator();
            for(;it.hasNext();){
                Entry<Object, Object> entry = it.next();
                val.put((String)entry.getKey(), (String)entry.getValue());
            }
            return val;
		} catch (FileNotFoundException e) {
			throw new Exception(e.getMessage(),e);
		} catch (IOException e) {
			throw new Exception(e.getMessage(),e);
		}finally {
            if(null != fis){
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new Exception(e.getMessage(),e);
                }
            }
        }

	}
}

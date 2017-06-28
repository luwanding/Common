package com.chyjr.platform.cache.init;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chyjr.platform.cache.CacheConstants;

/**
 * @ProjectName:  [PlatformCacheImpl]
 * @Package:      [com.chyjr.platform.cache.init.DictionaryMap.java] 
 * @ClassName:    [DictionaryMap]  
 * @Description:  [数据字典映射]  
 * @Author:       [chenz]  
 * @CreateDate:   [Oct 15, 2014 10:35:09 AM]  
 * @UpdateUser:   [chenz]  
 * @UpdateDate:   [Oct 15, 2014 10:35:09 AM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class DictionaryMap implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//所有数据字典数据
	private Map<String,List<DictionaryBean>> allDict = new HashMap<String, List<DictionaryBean>>();
	
	/**
	 * 根据数据字典编号获得数据字典值
	 * @param code
	 * @return
	 */
	public List<DictionaryBean> getDictionary(String code) {

		String memkey = CacheConstants.MEM_DICTIONARY_KEY_PREFIX + code;
		if(allDict != null){
			return allDict.get(memkey);
		}
		
		return null;
		
	}

	/**
	 * 获得所有数据字典数据
	 * @return
	 */
	public Map<String, List<DictionaryBean>> getAllDict() {
		return allDict;
	}

	/**
	 * 设置数据字典
	 * @param allDict
	 */
	public void setAllDict(Map<String, List<DictionaryBean>> allDict) {
		this.allDict = allDict;
	}
	
	
}

package com.chyjr.platform.cache.init;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alisoft.xplatform.asf.cache.IMemcachedCache;
import com.chyjr.platform.cache.CacheConstants;
import com.chyjr.platform.cache.CacheInitialization;
import com.chyjr.platform.common.util.JSONUtils;
/**
 * @ProjectName:  [PlatformCacheImpl]
 * @Package:      [com.chyjr.platform.cache.init.DictionaryProxy.java] 
 * @ClassName:    [DictionaryProxy]  
 * @Description:  [数据字典代理类]  
 * @Author:       [chenz]  
 * @CreateDate:   [Oct 15, 2014 10:36:55 AM]  
 * @UpdateUser:   [chenz]  
 * @UpdateDate:   [Oct 15, 2014 10:36:55 AM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class DictionaryProxy {

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate; //数据库操作

	@Autowired
	private CacheInitialization memCache;//缓存操作
	
	@Autowired
	private InitDictionary init;//初始化数据字典类
	

	private IMemcachedCache getMemClient() {
		return memCache.getRomoteInstance(CacheConstants.MEM_CLIENT_COMM);
	}
	
	/**
	 * 从数据库中读取数据字典值
	 * @param code
	 * @return
	 */
	private List<DictionaryBean> getDataByDB(String code) {

		//String sql = "SELECT id, groupCode,label,value,sortIndex FROM data_dictionary where groupCode=?  ORDER BY sortIndex";
		String sql = "SELECT id, groupCode,label,value,sortIndex,color,ioc FROM data_dictionary where groupCode=?  ORDER BY groupcode , sortIndex";

		return jdbcTemplate.query(sql, new DictionaryRowMapper(),
				new Object[] { code });

	}

	public void refreshDictionary() {
		init.loadDictData();

//		List<String> keys = (List<String>) getMemClient().get(
//				CacheConstants.MEM_DICTIONARY_ALL_KEY);
//		if(keys == null)
//			return ;
//		for (String k : keys) {
//			String code = k.substring(CacheConstants.MEM_DICTIONARY_KEY_PREFIX
//					.length());
//			List<DictionaryBean> data = getDataByDB(code);
//			getMemClient().replace(k, data);
//		}

	}
	
	/**
	 * 刷新数据字典值
	 * @param code
	 */
	public void refreshDictionary(String code) {
		List<DictionaryBean> data = getDataByDB(code);
		getMemClient().replace(CacheConstants.MEM_DICTIONARY_KEY_PREFIX + code,
				data);
	}
	
	/**
	 * 数据字典转换成json
	 * @param code
	 * @return
	 */
	public String getDictionary2Josn(String code) {

		try {
			List<DictionaryBean> list = getDictionary(code);
			if (list != null) {
				return JSONUtils.writeJson(list);
			}
			return "";
		} catch (IOException e) {

			e.printStackTrace();
			return "";
		}
	}
	
	public List<DictionaryBean> getDictionary(String code) {

		String memkey = CacheConstants.MEM_DICTIONARY_KEY_PREFIX + code;
		Object obj = getMemClient().get(memkey);
		if (obj != null) {
			return (List<DictionaryBean>) obj;
		}

		List<DictionaryBean> list = getDataByDB(code);
		getMemClient().put(memkey, list);

		return list;
	}

	public DictionaryMap getDictionary() {

		DictionaryMap map = new DictionaryMap();
		
		String memkey = CacheConstants.MEM_DICTIONARY_ALL_DATA_MAP;
		Map<String, List<DictionaryBean>> obj = (Map<String, List<DictionaryBean>>)getMemClient().get(memkey);
		
		map.setAllDict(obj);
		
		
		return map;
	}

	public DictionaryBean getDictionary(String code, String value) {

		String memkey = CacheConstants.MEM_DICTIONARY_KEY_PREFIX + code;
		Object obj = getMemClient().get(memkey);
		if (obj != null) {
			for (DictionaryBean dict : (List<DictionaryBean>) obj) {
				if (value.equals(dict.getValue())) {
					return dict;
				}
			}
		}
		return null;

	}

	public String getDictionary2Json(String code, String value) {
		try {

			Object obj = getDictionary(code, value);
			if (obj != null) {
				return JSONUtils.writeJson(obj);
			}
		} catch (IOException e) {

			e.printStackTrace();
			return "";
		}

		return "";

	}

	public List<RegionBean> getProvince() {

		Object obj = getMemClient().get(
				CacheConstants.MEM_DICTIONARY_KEY_PROVINCE);
		if (obj != null) {
			return (List<RegionBean>) obj;
		}

		return null;
	}

	public String getProvince2Josn() {
		try {
			List<RegionBean> list = getProvince();
			if (list != null) {
				return JSONUtils.writeJson(list);
			}
			return "";
		} catch (IOException e) {

			e.printStackTrace();
			return "";
		}
	}

	public RegionBean getProvince(int value) {

		Object obj = getMemClient().get(
				CacheConstants.MEM_DICTIONARY_KEY_PROVINCE);
		if (obj != null) {
			for (RegionBean region : (List<RegionBean>) obj) {
				if (value == region.getRegion_id()) {
					return region;
				}
			}
		}

		return null;
	}

	public String getProvince2Josn(int value) {
		try {
			RegionBean region = getProvince(value);
			if (region != null) {
				return JSONUtils.writeJson(region);
			}
			return "";
		} catch (IOException e) {

			e.printStackTrace();
			return "";
		}
	}

	public List<RegionBean> getCity(String provinceId) {

		String memkey = CacheConstants.MEM_DICTIONARY_KEY_CITY + "_"
				+ provinceId;
		Object obj = getMemClient().get(memkey);
		if (obj != null) {
			return (List<RegionBean>) obj;
		}

		return null;
	}

	public String getCity2Josn(String provinceId) {
		try {
			List<RegionBean> list = getCity(provinceId);
			if (list != null) {
				return JSONUtils.writeJson(list);
			}
			return "";
		} catch (IOException e) {

			e.printStackTrace();
			return "";
		}
	}

	public RegionBean getCity(String provinceId, int value) {

		String memkey = CacheConstants.MEM_DICTIONARY_KEY_CITY + "_"
				+ provinceId;
		Object obj = getMemClient().get(memkey);
		if (obj != null) {
			for (RegionBean region : (List<RegionBean>) obj) {
				if (value == region.getRegion_id()) {
					return region;
				}
			}
		}

		return null;
	}

	public String getCity2Josn(String provinceId, int value) {
		try {
			RegionBean region = getCity(provinceId, value);
			if (region != null) {
				return JSONUtils.writeJson(region);
			}
			return "";
		} catch (IOException e) {

			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 过滤数据字典的值
	 * @param Key
	 * @param val
	 * @return
	 */
	public String filterDictValue(String key,String val){
		if( val == null ) return null;
		List<DictionaryBean> list = getDictionary(key); 
		if(list == null  ) return null;
		
		boolean has = false;
		for(DictionaryBean db : list){
			if(val.equals(db.getValue())){
				has = true;
			}
		}
		if(has){
			return val;
		}				
		return null;
	}
	
	
	private MoneyBean getMoneyByDB(String code) {

		String sql = "SELECT s.`code`,s.`remark`,s.`money`,s.`daymax` FROM money_setting s WHERE s.`activeflag` = 1 AND s.`code`=?";

		return jdbcTemplate.queryForObject(sql,new Object[]{code},new MoneyRowMapper());

	}
	
	public MoneyBean getMoneySetting(String code){
		String memkey = CacheConstants.MEM_MONEY_DATA_ + code;
		Object obj = getMemClient().get(memkey);
		if (obj != null) {
			return (MoneyBean) obj;
		}
		return null;
	}
	
	public void refreshMoneySetting() {
		init.loadMoneyConfig();
	}
	
	public void refreshMoneySetting(String code) {
		MoneyBean data = getMoneyByDB(code);
		getMemClient().put(CacheConstants.MEM_MONEY_DATA_ + code,data);
	}

	public static void main(String[] args) {
		String code = "mem_dict_CITY"
				.substring(CacheConstants.MEM_DICTIONARY_KEY_PREFIX.length());
		System.out.print(code);
	}

}

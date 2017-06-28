package com.chyjr.platform.cache.init;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.alisoft.xplatform.asf.cache.IMemcachedCache;
import com.chyjr.platform.cache.CacheConstants;
import com.chyjr.platform.cache.CacheInitialization;

/**
 * @ProjectName:  [PlatformCacheImpl]
 * @Package:      [com.chyjr.platform.cache.init.InitDictionary.java] 
 * @ClassName:    [InitDictionary]  
 * @Description:  [加载数据字典]  
 * @Author:       [chenz]  
 * @CreateDate:   [Oct 15, 2014 10:43:34 AM]  
 * @UpdateUser:   [chenz]  
 * @UpdateDate:   [Oct 15, 2014 10:43:34 AM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class InitDictionary {
	
	
	private  static  Map<String, List<DictionaryBean>> map = new HashMap<String, List<DictionaryBean>>();
	
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate; 
	
	
	@Autowired
	private CacheInitialization memCache;

	private IMemcachedCache getMemClient(){
		return memCache.getRomoteInstance(CacheConstants.MEM_CLIENT_COMM);
	}
	
	/**
	 * 加载数据字典数据
	 */
	public void loadDictData() {
		try{
			String sql = "SELECT id, groupCode,label,value,sortIndex,color,ioc FROM data_dictionary ORDER BY groupcode , sortIndex";
			jdbcTemplate.query(sql, new RowCallbackHandler(){
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					if (rs != null) {
							int id = rs.getInt("id");
							String code = rs.getString("groupCode");
							String label = rs.getString("label");
							String value = rs.getString("value");
							String color = rs.getString("color");
							String ioc = rs.getString("ioc");
							int index = rs.getInt("sortIndex");
							
							DictionaryBean bean = new DictionaryBean();
							bean.setId(id);
							bean.setIndex(index);
							bean.setGroupCode(code);
							bean.setLabel(label);
							bean.setValue(value);
							bean.setColor(color);
							bean.setIoc(ioc);
							
							String memkey = CacheConstants.MEM_DICTIONARY_KEY_PREFIX + code;
							if(map.containsKey(memkey)){
								List<DictionaryBean> dlist = map.get(memkey);
								if(!dlist.contains(bean)){
									dlist.add(bean);
								}
							}else{
								List<DictionaryBean> dlist = new ArrayList<DictionaryBean>();
								dlist.add(bean);
								map.put(memkey, dlist);
							}
							
						}
					
				}});
			if(map.size() > 0){
				List<String> allKey = new ArrayList<String>();
				for(String key:map.keySet()){
					allKey.add(key);
					Object o = getMemClient().get(key);
					if(o == null || !o.equals(map.get(key))){
						getMemClient().replace(key, map.get(key));
					}
				}
				getMemClient().replace(CacheConstants.MEM_DICTIONARY_ALL_KEY, allKey);
			}
			//所有数据
			if(null == getMemClient().get(CacheConstants.MEM_DICTIONARY_ALL_DATA_MAP)){
				getMemClient().put("casdfasd", "abc");
				getMemClient().put(CacheConstants.MEM_DICTIONARY_ALL_DATA_MAP, map);
			    map = new HashMap<String, List<DictionaryBean>>();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void loadRegion(){
		loadProvince();
		loadCity();
	}
	
	/**
	 * 装载省数据
	 */
	private void loadProvince(){
		
		String sql = "SELECT region_id,region_name,region_type,parent_id FROM s_global_region WHERE region_type =1";
		
		List<RegionBean> pList = jdbcTemplate.query(sql,new RegionRowMapper());
		
		Object obj = getMemClient().get(CacheConstants.MEM_DICTIONARY_KEY_PROVINCE);
		if(obj == null){
			getMemClient().put(CacheConstants.MEM_DICTIONARY_KEY_PROVINCE, pList);
		}
				
	}
	
	/**
	 * 装载市数据
	 */
	private void loadCity(){

		String sql = "SELECT region_id,region_name,region_type,parent_id FROM s_global_region WHERE region_type =2";
		
		List<RegionBean> pList = jdbcTemplate.query(sql, new RegionRowMapper());
		
		Object obj = getMemClient().get(CacheConstants.MEM_DICTIONARY_KEY_CITY);
		if(obj == null){
			getMemClient().put(CacheConstants.MEM_DICTIONARY_KEY_CITY, pList);
		}
		
		Map<String, List<RegionBean>> map = new HashMap<String, List<RegionBean>>();
		for(RegionBean r : pList){
			String memkey = CacheConstants.MEM_DICTIONARY_KEY_CITY + "_" + r.getParent_id();
			if(map.containsKey(memkey)){
				List<RegionBean> dlist = map.get(memkey);
				if(!dlist.contains(r)){
					dlist.add(r);
				}
			}else{
				List<RegionBean> dlist = new ArrayList<RegionBean>();
				dlist.add(r);
				map.put(memkey, dlist);
			}
		}
		
		
		
		if(map.size() > 0){
			for(String key:map.keySet()){
				Object o = getMemClient().get(key);
				if(o == null){
					getMemClient().put(key, map.get(key));
				}
			}
		}
		
		
	}
	
	
	public void loadMoneyConfig() {

		String sql = "SELECT s.`code`,s.`remark`,s.`money`,s.`daymax` FROM money_setting s WHERE s.`activeflag` = 1";
		//final Map<String , MoneyBean> mmap = new HashMap<>();
		jdbcTemplate.query(sql, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				if (rs != null) {
						
						String code = rs.getString("code");
						String remark = rs.getString("remark");
						int money = rs.getInt("money");
						int daymax = rs.getInt("daymax");
						
						MoneyBean bean = new MoneyBean();
						bean.setCode(code);
						bean.setRemark(remark);
						bean.setMoney(money);
						bean.setDaymax(daymax);
						
						
						String memkey = CacheConstants.MEM_MONEY_DATA_ + code;
						getMemClient().put(memkey, bean);					
						
						//mmap.put(code, bean);
				}
				
			}});
		
		//所有数据
		//getMemClient().put(CacheConstants.MEM_MONEY_ALL_DATA_MAP, mmap);
	}
	
	
	

	private void initMEM(){
		loadDictData();
		loadMoneyConfig();
		//loadRegion();
			
	}


	

}

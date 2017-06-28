package com.chyjr.platform.cache.init;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/** 
 * @ProjectName:  [PlatformCacheImpl]
 * @Package:      [com.chyjr.platform.cache.init.DictionaryRowMapper.java] 
 * @ClassName:    [DictionaryRowMapper]  
 * @Description:  [一句话描述该类的功能]  
 * @Author:       [chenz]  
 * @CreateDate:   [Oct 15, 2014 10:39:16 AM]  
 * @UpdateUser:   [chenz]  
 * @UpdateDate:   [Oct 15, 2014 10:39:16 AM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class DictionaryRowMapper implements RowMapper<DictionaryBean> {
	
	/**
	 * map 转 bean 
	 */
	@Override
	public DictionaryBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		int id = rs.getInt("id");
		String code = rs.getString("groupCode");
		String label = rs.getString("label");
		String value = rs.getString("value");
		int index = rs.getInt("sortIndex");
		String ioc = rs.getString("ioc");
		String color = rs.getString("color");
		DictionaryBean bean = new DictionaryBean();
		bean.setId(id);
		bean.setIndex(index);
		bean.setGroupCode(code);
		bean.setLabel(label);
		bean.setValue(value);
		bean.setIoc(ioc);
		bean.setColor(color);
		
		
		return bean;
	}

}

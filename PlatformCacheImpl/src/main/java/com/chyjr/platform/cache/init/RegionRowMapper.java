package com.chyjr.platform.cache.init;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @ProjectName:  [PlatformCacheImpl]
 * @Package:      [com.chyjr.platform.cache.init.RegionRowMapper.java] 
 * @ClassName:    [RegionRowMapper]  
 * @Description:  [一句话描述该类的功能]  
 * @Author:       [chenz]  
 * @CreateDate:   [Oct 15, 2014 11:53:13 AM]  
 * @UpdateUser:   [chenz]  
 * @UpdateDate:   [Oct 15, 2014 11:53:13 AM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class RegionRowMapper implements RowMapper<RegionBean> {

	/**
	 * map 转 bean
	 */
	@Override
	public RegionBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		RegionBean rb = new RegionBean();
		rb.setRegion_id(rs.getInt("region_id"));
		rb.setParent_id(rs.getInt("parent_id"));
		rb.setRegion_name(rs.getString("region_name"));
		rb.setRegion_type(rs.getInt("region_type"));
		
		return rb;
	}

}

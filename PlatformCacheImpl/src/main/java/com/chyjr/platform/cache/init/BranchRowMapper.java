package com.chyjr.platform.cache.init;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/** 
 * @ProjectName:  [PlatformCacheImpl]
 * @Package:      [com.chyjr.platform.cache.init.BranchRowMapper.java] 
 * @ClassName:    [BranchRowMapper]  
 * @Description:  [机构每行数据映射关系]  
 * @Author:       [xudl]  
 * @CreateDate:   [Oct 15, 2016 10:39:16 AM]  
 * @UpdateUser:   [xudl]  
 * @UpdateDate:   [Oct 15, 2016 10:39:16 AM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class BranchRowMapper implements RowMapper<BranchBean> {
	
	/**
	 * map 转 bean 
	 */
	@Override
	public BranchBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		String id = rs.getString("branchid");
		int companyid = rs.getInt("companyid");
		String branchname = rs.getString("branchname");
		String cityid = rs.getString("cityid");
		String address = rs.getString("address");
		BranchBean bean = new BranchBean();
		bean.setBranchid(id);
		bean.setCompanyid(companyid);
		bean.setBranchname(branchname);
		bean.setCityid(cityid);
		bean.setAddress(address);
		
		return bean;
	}

}


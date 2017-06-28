package com.chyjr.platform.cache.init;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @ProjectName:  [PlatformCacheImpl]
 * @Package:      [com.chyjr.platform.cache.init.MoneyRowMapper.java] 
 * @ClassName:    [MoneyRowMapper]  
 * @Description:  [金币映射类]  
 * @Author:       [chenz]  
 * @CreateDate:   [Oct 15, 2014 11:50:21 AM]  
 * @UpdateUser:   [chenz]  
 * @UpdateDate:   [Oct 15, 2014 11:50:21 AM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class MoneyRowMapper implements RowMapper<MoneyBean> {
	
	/**
	 * map 转 bean
	 */
	@Override
	public MoneyBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		
		String code = rs.getString("code");
		String remark = rs.getString("remark");
		int money = rs.getInt("money");
		int daymax = rs.getInt("daymax");
		
		MoneyBean bean = new MoneyBean();
		bean.setCode(code);
		bean.setRemark(remark);
		bean.setMoney(money);
		bean.setDaymax(daymax);
		
		
		return bean;
	}

}

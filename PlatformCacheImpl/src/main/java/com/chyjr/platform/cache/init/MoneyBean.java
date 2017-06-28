package com.chyjr.platform.cache.init;

import java.io.Serializable;

/**
 * @ProjectName:  [PlatformCacheImpl]
 * @Package:      [com.chyjr.platform.cache.init.MoneyBean.java] 
 * @ClassName:    [MoneyBean]  
 * @Description:  [金币实体类]  
 * @Author:       [chenz]  
 * @CreateDate:   [Oct 15, 2014 11:48:42 AM]  
 * @UpdateUser:   [chenz]  
 * @UpdateDate:   [Oct 15, 2014 11:48:42 AM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class MoneyBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;//代码
	private String remark;//描述
	private Integer money;//金币数量
	private Integer daymax;//单日累加最大数
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public Integer getDaymax() {
		return daymax;
	}
	public void setDaymax(Integer daymax) {
		this.daymax = daymax;
	}
	
	
	
	
	
}

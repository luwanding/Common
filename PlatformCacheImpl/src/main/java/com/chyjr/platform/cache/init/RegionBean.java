package com.chyjr.platform.cache.init;

import java.io.Serializable;

/**
 * @ProjectName:  [PlatformCacheImpl]
 * @Package:      [com.chyjr.platform.cache.init.RegionBean.java] 
 * @ClassName:    [RegionBean]  
 * @Description:  [区域实体类]  
 * @Author:       [chenz]  
 * @CreateDate:   [Oct 15, 2014 11:51:37 AM]  
 * @UpdateUser:   [chenz]  
 * @UpdateDate:   [Oct 15, 2014 11:51:37 AM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class RegionBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int region_id;//区域编号
	private int parent_id;//父编号
	private String region_name;//区域名称
	private int region_type;//区域类型
	public int getRegion_id() {
		return region_id;
	}
	public void setRegion_id(int region_id) {
		this.region_id = region_id;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	public String getRegion_name() {
		return region_name;
	}
	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}
	public int getRegion_type() {
		return region_type;
	}
	public void setRegion_type(int region_type) {
		this.region_type = region_type;
	}
	
	
}

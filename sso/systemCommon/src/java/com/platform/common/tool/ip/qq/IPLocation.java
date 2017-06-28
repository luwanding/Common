package com.platform.common.tool.ip.qq;

/**
 * 
 * @category 用来封装ip相关信息，目前只有两个字段，ip所在的国家和地区
 */
public class IPLocation implements java.io.Serializable {

	private static final long serialVersionUID = -676539345230800275L;
	
	private String country;
	
	private String area;

	public IPLocation() {
		
		country = area = "";
	}

	public IPLocation getCopy() {
		
		IPLocation ret = new IPLocation();
		
		ret.country = country;
		
		ret.area = area;
		
		return ret;
	}

	public String getCountry() {
		
		return country;
	}

	public void setCountry(String country) {
		
		this.country = country;
	}

	public String getArea() {
		
		return area;
	}

	public void setArea(String area) {
		// 如果为局域网，纯真IP地址库的地区会显示CZ88.NET,这里把它去掉
		if (area.trim().equals("CZ88.NET")) {
			
			this.area = "";
			
		} else {
			
			this.area = area;
		}
	}
}
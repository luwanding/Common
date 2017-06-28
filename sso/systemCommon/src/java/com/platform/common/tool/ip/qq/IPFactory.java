package com.platform.common.tool.ip.qq;

import org.apache.log4j.Logger;

public class IPFactory {

	private static IPSeeker ipSeeker;

	private static final Logger logger = Logger.getLogger(IPFactory.class);

	static {

		String path = IPFactory.class.getResource("/").getPath();

		logger.info("QQWry path = " + path);

		try {

			ipSeeker = new IPSeeker("QQWry.Dat", path);

		} catch (Exception e) {

			logger.error(e);
		}
	}

	public static String getProvinceByIP(String ip) {

		logger.info("getProvinceByIP ip = " + ip);

		String province = "";

		try {
			
			ValidIp(ip);

			province = ipSeeker.getCountry(ip.trim());
			
			logger.info("getProvinceByIP province = " + province);
			
			if(province.length() > 3){
				
				province = province.substring(0, 2);
			}

		} catch (Exception e) {

			logger.error(e);

			return null;
		}

		logger.info("getProvinceByIP province = " + province);

		return province;
	}
	
	public static String getAddressByIP(String ip) {

		logger.info("getAddressByIP ip = " + ip);

		String address = "";

		try {
			
			ValidIp(ip);

			address = ipSeeker.getCountry(ip.trim());			

		} catch (Exception e) {

			logger.error(e);

			return "";
		}

		logger.info("getAddressByIP address = " + address);

		return address;
	}

	private static void ValidIp(String ip) throws Exception {

		if (ip == null || ip.trim().equals("")) {

			throw new Exception("ip is null");

		}

		// 如果有多个IP,去最后一个
		if (ip.indexOf(",") > 0) {

			ip = ip.substring(ip.lastIndexOf(",") + 1);
		}

		String[] ipSplit = ip.split("\\.");

		if (ipSplit == null || ipSplit.length != 4) {

			logger.error("IPFactory.getCountryByIP-------ip rule error");

			throw new Exception("IPFactory.getCountryByIP-------ip rule error");
		}

		if (ip.trim().equals("127.0.0.1")) {

			logger.error("IPFactory.getCountryByIP-------ip is local");
			
			throw new Exception("ip is 127.0.0.1");
		}
	}

}

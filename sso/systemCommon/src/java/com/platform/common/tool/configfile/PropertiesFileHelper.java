package com.platform.common.tool.configfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesFileHelper {

	private static final Logger logger = Logger
			.getLogger(PropertiesFileHelper.class);

	public static Properties loadPropFileReturnProp(String fileName) {

		logger.info("loadPropFileReturnProp start:" + fileName);

		Properties prop = new Properties();

		InputStream is = null;

		try {

			File file = new File(fileName);

			if (!file.exists()) {

				is = PropertiesFileHelper.class.getResourceAsStream(fileName);

			} else {

				is = new FileInputStream(file);

			}
			if (is != null) {

				prop.load(is);

			}
		} catch (IOException ex) {

			logger.error(ex);

		} finally {
			
			if (is != null) {
				
				try {
					
					is.close();
					
				} catch (IOException ex) {
					
					logger.error(ex);
				}
			}
		}

		logger.info("loadPropFileReturnProp is end prop = " + prop);

		return prop;
		
	}

}

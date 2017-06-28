package com.chyjr.connection;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCInsertConnection {
	
	private static BoneCP connectionPool = null;
	private static Object lock = new Object();
	
	private static void init() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver"); 	// load the DB driver
	 	BoneCPConfig config = new BoneCPConfig();	// create a new configuration object
	 	//config.setJdbcUrl("jdbc:mysql://10.12.5.32:3306/resume");	// set the JDBC url
	 	config.setJdbcUrl("jdbc:mysql://localhost:3306/resume_web");
		config.setUsername("root");			// set the username
		config.setPassword("wanglulu");				// set the password
		config.setPoolAvailabilityThreshold(100);
		connectionPool = new BoneCP(config); 	// setup the connection pool
	}
	
	public static Connection getConnection() throws SQLException{
		if(connectionPool == null){
			synchronized(lock){
				if(connectionPool == null){
					try {
						init();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return connectionPool.getConnection();
	}
	
	


}

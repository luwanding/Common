package com.chyjr.connection;

import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class JDBCConnection {
	
	private static BoneCP connectionPool = null;
	private static Object lock = new Object();
	
	public static void init(String url,String username,String password) throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver"); 	// load the DB driver
	 	BoneCPConfig config = new BoneCPConfig();	// create a new configuration object
	 	config.setJdbcUrl(url);
		config.setUsername(username);			// set the username
		config.setPassword(password);				// set the password
		config.setPoolAvailabilityThreshold(100);
		connectionPool = new BoneCP(config); 	// setup the connection pool
	}
	
	public static Connection getConnection() throws Exception{
		if(connectionPool == null){
            throw new Exception("请初始化数据库连接！");
		}
		return connectionPool.getConnection();
	}
	
	


}

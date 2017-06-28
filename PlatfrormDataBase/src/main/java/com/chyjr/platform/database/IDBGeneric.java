package com.chyjr.platform.database;

import java.util.List;
import java.util.Map;

public interface IDBGeneric  {
	
	Object insert(String sqlname, IEntity entity) throws Exception;

	int update(String sqlname, IEntity entity) throws Exception;
    int update(String sqlname) throws Exception;
    int update(String sqlname, Map<String, ?> paramMap) throws Exception;
    int update(String sqlname, Object arg) throws Exception;
    
	int select(String sqlname, Map<String, ?> paramMap)throws Exception;
	public int select2Int(String sqlname, IEntity entity) throws Exception;
	IEntity find_one(String sqlname, IEntity entity) throws Exception;
	IEntity find_one(String sqlname, String args) throws Exception;
	IEntity find_one(String sqlname, Map<String, ?> paramMap) throws Exception;
	List<?> findAll(String sqlname, IEntity entity) throws Exception;
	Map<String, Object> find(String sqlname, Map<String, ?> paramMap) throws Exception;
	List<?> findAll(String sqlname, Map<String, ?> paramMap) throws Exception;
	List<?> findAll(String sqlname, String param) throws Exception;
	List<?> findAll(String sqlname) throws Exception;
	List<Map<?,?>> findMapList(String sqlname, Map<String, String> paramMap) throws Exception;
	
	Object insert(String sqlname, Map<String, ?> paramMap) throws Exception;
	
	int delete(String sqlname, IEntity entity) throws Exception;
	int delete(String sqlname, Map<String, ?> paramMap) throws Exception;
	int delete(String sqlname,String id) throws Exception;

	
	
	
	
	//public Long getLastInsertId();
}

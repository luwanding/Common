package com.chyjr.platform.database;

import com.google.code.routing4db.proxy.RountingProxyFactory;
import com.google.code.routing4db.strategy.RoutingStrategy;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;


/**
 * @ProjectName:  [PlatfrormDataBase]
 * @Package:      [com.chyjr.platform.database.DBMybatisGenericImpl.java] 
 * @ClassName:    [DBMybatisGenericImpl]  
 * @Description:  [数据库mybatis操作基类]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 14, 2014 5:45:33 PM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 14, 2014 5:45:33 PM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
@Repository
public class DBMybatisGenericImpl<T> extends SimpleJdbcDaoSupport implements IDBGeneric , FactoryBean<T>, InitializingBean{
	
	
	/**
	 * 代理的接口
	 * */
	private Class<T> targetInterface = (Class<T>) com.chyjr.platform.database.IDBGeneric.class;
	
	/**
	 * 代理对象
	 * */
	private Object targetObject = this;
	
	/**
	 * 路由规则
	 * */
	@Autowired
	private RoutingStrategy routingStrategy;
	
	
	/**
	 * 返回代理对象
	 * */
	public T getObject() throws Exception {
		return RountingProxyFactory.proxy(targetObject, targetInterface, routingStrategy);
	}

	public Class<?> getObjectType() {
		return  targetInterface;
	}

	public boolean isSingleton() {
		return true;
	}

	public void setTargetInterface(Class<T> targetInterface) {
		this.targetInterface = targetInterface;
	}

	public void setTargetObject(Object targetObject) {
		this.targetObject = targetObject;
	}

	public void setRoutingStrategy(RoutingStrategy routingStrategy) {
		this.routingStrategy = routingStrategy;
	}


    private final Logger log = Logger.getLogger(DBMybatisGenericImpl.class);

	@Resource(name = "routing4DBDataSource")
	public void setDataSource1(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
//	public Long getLastInsertId(){
//		String sql ="select last_insert_id()";
//		return getJdbcTemplate().queryForLong(sql);
//	}
	
	@Autowired
	private SqlSession sqlSessionTemplate;
	
	
	public void setSqlSessionTemplate(SqlSession sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public Object insert(String sqlname, IEntity entity) throws Exception {
		if(!sqlname.startsWith("t_mail_")) {
			EntityUtils.reflect(entity);
		}
		return sqlSessionTemplate.insert(sqlname, entity);
	}

	@Override
	public int update(String sqlname, Object arg) throws Exception {
		return sqlSessionTemplate.update(sqlname, arg);
	}
	
	public int update(String sqlname, IEntity entity) throws Exception {
		if(!sqlname.startsWith("t_mail_")) {
			EntityUtils.reflect(entity);
		}
		return sqlSessionTemplate.update(sqlname, entity);
	}

	public int update(String sqlname) throws Exception {
		return sqlSessionTemplate.update(sqlname);
	}

	public int delete(String sqlname, IEntity entity) throws Exception {
		return sqlSessionTemplate.delete(sqlname,entity);
	}

	public int select(String sqlname, Map<String, ?> paramMap) throws Exception {
		return sqlSessionTemplate.selectOne(sqlname,paramMap);
	}
	
	public int select2Int(String sqlname, IEntity entity) throws Exception {
		return sqlSessionTemplate.selectOne(sqlname,entity);
	}

	public IEntity find_one(String sqlname, IEntity entity) throws Exception {
		return sqlSessionTemplate.selectOne(sqlname, entity);
	}

	public IEntity find_one(String sqlname, String args) throws Exception {

		return sqlSessionTemplate.selectOne(sqlname, args);
	}

	public IEntity find_one(String sqlname, Map<String, ?> paramMap)
			throws Exception {
		return sqlSessionTemplate.selectOne(sqlname, paramMap);
	}

	public List<?> findAll(String sqlname, IEntity entity) throws Exception {
		return sqlSessionTemplate.selectList(sqlname, entity);
	}

	public Object insert(String sqlname, Map<String, ?> paramMap)
			throws Exception {
		return sqlSessionTemplate.insert(sqlname, paramMap);
	}

	public int update(String sqlname, Map<String, ?> paramMap) throws Exception {
		return sqlSessionTemplate.update(sqlname, paramMap);
	}

	public int delete(String sqlname, Map<String, ?> paramMap) throws Exception {
		return sqlSessionTemplate.delete(sqlname, paramMap);
	}

	public Map<String, Object> find(String sqlname, Map<String, ?> paramMap)
			throws Exception {
		return sqlSessionTemplate.selectOne(sqlname, paramMap);
	}
	
	
	public List<?> findAll(String sqlname, Map<String, ?> paramMap)
			throws Exception {
		return sqlSessionTemplate.selectList(sqlname, paramMap);
	}
	
	 public int insertList(String sqlname, List<?> list)
			    throws Exception{
			    return this.sqlSessionTemplate.insert(sqlname, list);
	}
			  

	public List<?> findAll(String sqlname, String param) throws Exception {
		return sqlSessionTemplate.selectList(sqlname, param);
	}

	public List<?> findAll(String sqlname) throws Exception {
		return sqlSessionTemplate.selectList(sqlname);
	}

	public List<Map<?, ?>> findMapList(String sqlname, Map<String, String> paramMap)
			throws Exception {
		return sqlSessionTemplate.selectList(sqlname,paramMap);
	}

	public int delete(String sqlname,String id) throws Exception {
		return sqlSessionTemplate.delete(sqlname,id);
	}

}



package com.chyjr.platform.search.query;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;


/**
 * @ProjectName:  [PlatformSearchClient]
 * @Package:      [com.chyjr.platform.search.query.ISolrQueryUtil.java] 
 * @ClassName:    [ISolrQueryUtil]  
 * @Description:  [solr搜索查询接口]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 14, 2014 5:43:12 PM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 14, 2014 5:43:12 PM]  
 * @UpdateRemark: [] 
 * @Version:      [v1.0]
 *
 */
public interface ISolrQueryUtil {
	
	/**
	 * 根据指定查询条件和 module等参数
	 * 然后提交到solr服务器进行信息搜索，该方法一并提供结果集的排序功能。
	 * @param q 查询参数
	 * @param fl 返回字段
	 * @param sort 排序字段集合
	 * @param start 分页记录起始位置
	 * @param pageSize 每页记录大小
	 * @param requestMethod Http请求发起方式GET、POST
	 * @param solrModule 指定查询模块，该模块其实就相当于数据库中的表名，也是solr。xml中配置的solrclientid
	 * @return
	 * @throws SolrServerException
	 */
	public SolrDocumentList query(String q, String fl,String sort, int start, int pageSize, String solrModule) throws SolrServerException;
	
	/**
	 * 根据指定查询条件和 module等参数
	 * 然后提交到solr服务器进行信息搜索，
	 * @注意：
	 * 不支持结果集排序。
	 * @param q 查询参数
	 * @param fl 返回字段
	 * @param start 分页记录起始位置
	 * @param pageSize 每页记录大小
	 * @param requestMethod Http请求发起方式GET、POST
	 * @param solrModule 指定查询模块，该模块其实就相当于数据库中的表名，也是solr。xml中配置的solrclientid
	 * @return
	 * @throws SolrServerException
	 */
	public SolrDocumentList query(String q, String fl, int start, int pageSize, String solrModule) throws SolrServerException;
	

	/**
	 * 根据指定查询条件和 module等参数
	 * 然后提交到solr服务器进行信息搜索，
	 * @注意：
	 * 不支持结果集排序，
	 * 不支持返回字段定制，查询返回所有字段。
	 * @param q 查询参数
	 * @param start 分页记录起始位置
	 * @param pageSize 每页记录大小
	 * @param requestMethod Http请求发起方式GET、POST
	 * @param solrModule 指定查询模块，该模块其实就相当于数据库中的表名，也是solr。xml中配置的solrclientid
	 * @return
	 * @throws SolrServerException
	 */
	public SolrDocumentList query(String q, int start, int pageSize,String solrModule) throws SolrServerException;

    /**
     * 根据指定查询条件和 module等参数
     * 然后提交到solr服务器进行信息搜索，
     * @注意：
     * 不支持结果集排序，
     * 不支持返回字段定制，查询返回所有字段。
     * @param q 查询参数
     * @param start 分页记录起始位置
     * @param pageSize 每页记录大小
     * @param requestMethod Http请求发起方式GET、POST
     * @param solrModule 指定查询模块，该模块其实就相当于数据库中的表名，也是solr。xml中配置的solrclientid
     * @return
     * @throws SolrServerException
     */
    public  SolrDocumentList query(ModifiableSolrParams q, String solrModule) throws SolrServerException;
}

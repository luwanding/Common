package com.chyjr.platform.search.query;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Autowired;

import com.chyjr.platform.search.SolrSearchServer;

/**
 * @ProjectName:  [PlatformSearchClient]
 * @Package:      [com.chyjr.platform.search.query.SolrQueryUtilImpl.java] 
 * @ClassName:    [SolrQueryUtilImpl]  
 * @Description:  [Solr query 实现类]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 14, 2014 5:44:03 PM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 14, 2014 5:44:03 PM]  
 * @UpdateRemark: [] 
 * @Version:      [v1.0]
 *
 */
public class SolrQueryUtilImpl implements ISolrQueryUtil,ISolrUpdateUtil {
	
	@Autowired
	private SolrSearchServer solrSearchServer;
	
	//public final static String  RESPONSE_TYPE_JSON = "json";
	//public final static String  RESPONSE_TYPE_XML = "xml";
	private String responseType = "";
	private METHOD defaultMethod = METHOD.GET;//默认采用get 发起http请求
	
	public SolrQueryUtilImpl(String responseType){
		this.responseType = responseType;
	}



    /**
	 * 组合相关的查询输入条件
	 * @param q 拼接好的查询条件
	 * @param fl 查询返回字段
	 * @param sort 查询排序
	 * @param start 分页起始位置
	 * @param pageSize 查询返回记录条数
	 * @return 
	 */
	private SolrQuery getSolrQuery(String q, String fl,String sort, int start, int pageSize){
		 SolrQuery sq = new SolrQuery();
	     sq.set(CommonParams.Q, q);
	     if( StringUtils.isNotEmpty(fl) )
	    	 sq.set(CommonParams.FL, fl);
	     if( StringUtils.isNotEmpty(sort) )
	    	 sq.set(CommonParams.SORT, sort);
	     if( StringUtils.isNotEmpty(responseType) )
	    	 sq.set(CommonParams.WT, responseType);
	     sq.set(CommonParams.START, start);
	     sq.set(CommonParams.ROWS, pageSize);
	     return sq;
	}
	
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
	public SolrDocumentList query(String q, String fl, String sort, int start,
			int pageSize, String solrModule) throws SolrServerException {
		SolrQuery sq = getSolrQuery( q,  fl, sort,  start,  pageSize);
		 
	    HttpSolrServer solrServer = solrSearchServer.getInstance(solrModule);
	    QueryResponse qr =  solrServer.query(sq, defaultMethod);
	     return qr.getResults();
	}
	
	
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
	public SolrDocumentList query(String q, String fl, int start, int pageSize,
			String solrModule) throws SolrServerException {
		 SolrQuery sq = getSolrQuery( q,  fl, null,  start,  pageSize);
		 HttpSolrServer solrServer = solrSearchServer.getInstance(solrModule);
		 QueryResponse qr =  solrServer.query(sq, defaultMethod);
	     return qr.getResults();
	}
	
	
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
	public SolrDocumentList query(String q, int start, int pageSize,
			String solrModule) throws SolrServerException {
		 SolrQuery sq = getSolrQuery( q,  null, null,  start,  pageSize);
		 HttpSolrServer solrServer = solrSearchServer.getInstance(solrModule);
		 QueryResponse qr =  solrServer.query(sq, defaultMethod);
	     return qr.getResults();
	}

    public SolrDocumentList query(ModifiableSolrParams q,
                                  String solrModule) throws SolrServerException {
        HttpSolrServer solrServer = solrSearchServer.getInstance(solrModule);
        QueryResponse qr = solrServer.query(q);
        return qr.getResults();
    }
    
    /**
	 * 根据指定id更新单条记录
	 * @param doc 更新单条记录
	 * @return
	 * @throws SolrServerException
	 */
	public void update(SolrInputDocument doc,String solrModule) throws SolrServerException, IOException {
		HttpSolrServer solrServer = solrSearchServer.getInstance(solrModule);
		solrServer.add(doc);
		solrServer.commit();
	}


	
	/**
	 * 根据指定id更新多条记录
	 * @param doc 更新多条记录
	 * @return
	 * @throws SolrServerException
	 */
	public void update(List<SolrInputDocument> doc,String solrModule) throws SolrServerException,IOException {
		HttpSolrServer solrServer = solrSearchServer.getInstance(solrModule);
		solrServer.add(doc);
		solrServer.commit();
	}
}

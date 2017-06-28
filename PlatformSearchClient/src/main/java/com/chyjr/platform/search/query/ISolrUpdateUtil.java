package com.chyjr.platform.search.query;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;

/**
 * @ProjectName:  [PlatformSearchClient]
 * @Package:      [com.chyjr.platform.search.query.ISolrUpdateUtil.java] 
 * @ClassName:    [ISolrUpdateUtil]  
 * @Description:  [solr搜索更新接口]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 14, 2014 5:43:12 PM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 14, 2014 5:43:12 PM]  
 * @UpdateRemark: [] 
 * @Version:      [v1.0]
 *
 */
public interface ISolrUpdateUtil {
	
	/**
	 * 根据指定id更新单条记录
	 * @param doc 更新单条记录
	 * @return
	 * @throws SolrServerException
	 */
	public void update(SolrInputDocument doc,String solrModule) throws SolrServerException,IOException;
	
	/**
	 * 根据指定id list 更新多条记录
	 * @param doc 更新多条记录
	 * @return
	 * @throws SolrServerException
	 */
	public void update(List<SolrInputDocument> doc,String solrModule) throws SolrServerException,IOException;
	
}

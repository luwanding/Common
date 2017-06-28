package com.chyjr.platform.cache.init;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alisoft.xplatform.asf.cache.IMemcachedCache;
import com.chyjr.platform.cache.CacheConstants;
import com.chyjr.platform.cache.CacheInitialization;
import com.chyjr.platform.common.util.JSONUtils;
/**
 * @ProjectName:  [PlatformCacheImpl]
 * @Package:      [com.chyjr.platform.cache.init.BranchProxy.java] 
 * @ClassName:    [DictionaryProxy]  
 * @Description:  [机构缓存代理类]  
 * @Author:       [chenz]  
 * @CreateDate:   [Oct 15, 2014 10:36:55 AM]  
 * @UpdateUser:   [chenz]  
 * @UpdateDate:   [Oct 15, 2014 10:36:55 AM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class BranchProxy {

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate; //数据库操作

	@Autowired
	private CacheInitialization memCache;//缓存操作

	private IMemcachedCache getMemClient() {
		return memCache.getRomoteInstance(CacheConstants.MEM_CLIENT_COMM);
	}
	
	/**
	 * 从数据库中读取数据字典值
	 * @param code
	 * @return
	 */
	private List<BranchBean> getDataByDB() {

		//String sql = "SELECT id, groupCode,label,value,sortIndex FROM data_dictionary where groupCode=?  ORDER BY sortIndex";
		String sql = "SELECT branchid,companyid,branchname,cityid,address FROM branch WHERE activeflag=1";

		return jdbcTemplate.query(sql, new BranchRowMapper() );

	}
	
	/**
	 * 刷新机构值
	 * @param code
	 */
	public void refreshBranch() {
		List<BranchBean> data = getDataByDB();
		getMemClient().replace(CacheConstants.MEM_BRANCH_ALL_KEY, data);
	}
	
	
	private void init(){
		List<BranchBean> data = getDataByDB();
		getMemClient().put(CacheConstants.MEM_BRANCH_ALL_KEY, data);
	}
	
	
	public List<BranchBean> getBranch() {

		String memkey = CacheConstants.MEM_BRANCH_ALL_KEY ;
		Object obj = getMemClient().get(memkey);
		if (obj != null) {
			return (List<BranchBean>) obj;
		}
		
		List<BranchBean> list = getDataByDB();
		getMemClient().put(memkey, list);

		return list;
	}
	
	
	public BranchBean getBranch(String branchId) {

		String memkey = CacheConstants.MEM_BRANCH_ALL_KEY ;
		Object obj = getMemClient().get(memkey);
		if (obj != null) {
			
			List<BranchBean> branchList = (List<BranchBean>) obj;
			return getBranch( branchId,  branchList);
		}
		
		List<BranchBean> list = getDataByDB();
		getMemClient().put(memkey, list);

		return getBranch( branchId,  list);
	}
	
	
	private BranchBean getBranch(String branchId, List<BranchBean> branchList){
		if(branchId == null || branchList == null || branchList.size() == 0)
			return null;
		
		for(int i = 0; i < branchList.size(); i ++){
			if(branchId.equals(branchList.get(i).getBranchid())){
				return branchList.get(i);
			}
		}
		return null;
	}

}

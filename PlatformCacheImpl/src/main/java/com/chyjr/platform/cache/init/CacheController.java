package com.chyjr.platform.cache.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ProjectName:  [PlatformCacheImpl]
 * @Package:      [com.chyjr.platform.cache.init.CacheController.java] 
 * @ClassName:    [CacheController]  
 * @Description:  [平台公共缓存操作类]  
 * @Author:       [chenz]  
 * @CreateDate:   [Oct 15, 2014 10:28:23 AM]  
 * @UpdateUser:   [chenz]  
 * @UpdateDate:   [Oct 15, 2014 10:28:23 AM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
@Controller
@RequestMapping(value ="/cache")
public class CacheController {
	
	@Autowired
	DictionaryProxy dictProxy;
	
	
	/**
	 * 刷新数据字典
	 * @param code 数据字典编号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "dict/refresh/{code}")
	public String refreshDict(@PathVariable String code){
		if("All".equalsIgnoreCase(code)){
			dictProxy.refreshDictionary();
		}else{
			dictProxy.refreshDictionary(code);
		}		
		return "{result:true,msg:\"ok\"}";		
	}
	
	/**
	 * 刷新金币
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "money/refresh/{code}")
	public String refreshMoney(@PathVariable String code){
		if("All".equalsIgnoreCase(code)){
			dictProxy.refreshMoneySetting();
		}else{
			dictProxy.refreshMoneySetting(code);
		}		
		return "{result:true,msg:\"ok\"}";		
	}
	
	
	/**
	 * 数据字段转换成json格式
	 * @param code
	 * @return
	 */
	@ResponseBody	
	@RequestMapping(value = "dict/{code}")
	public String dictionary2Josn(@PathVariable String code ){
		
		String json = dictProxy.getDictionary2Josn(code);
		return json;
		
	}
	
	/**
	 * 省份信息转换成json
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "province")
	public String province2Josn(){		
		String json = dictProxy.getProvince2Josn();

		return json;
		
		
	}
	
	/**
	 * 城市信息转换为json
	 * @param provinceId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "city/{provinceId}")
	public String city2Josn(@PathVariable String provinceId){
		
		String json = dictProxy.getCity2Josn(provinceId);
		return json;

		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "dict/{code}/{value}")
	public String dictionary(@PathVariable String code,@PathVariable String value){
		
		String json = dictProxy.getDictionary2Json(code, value);
		return json;

		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "province/{value}")
	public String province(@PathVariable int value){	
		
		String json = dictProxy.getProvince2Josn(value);
		return json;
		
	}
	
	@ResponseBody
	@RequestMapping(value = "city/{provinceId}/{value}")
	public String city2Josn(@PathVariable String provinceId,@PathVariable int value){
		
		String json = dictProxy.getCity2Josn(provinceId,value);
		return json;

		
	}
	
	
}

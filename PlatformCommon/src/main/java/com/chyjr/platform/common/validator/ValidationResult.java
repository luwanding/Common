package com.chyjr.platform.common.validator;

import java.util.ArrayList;
import java.util.List;


/**
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.validator.ValidationResult.java] 
 * @ClassName:    [ValidationResult]  
 * @Description:  [Bean验证消息结果]  
 * @Author:       [weslly]  
 * @CreateDate:   [Oct 14, 2014 5:22:13 PM]  
 * @UpdateUser:   [weslly]  
 * @UpdateDate:   [Oct 14, 2014 5:22:13 PM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class ValidationResult {
	private boolean erros;
	
	private List<ValidationItem> items;
	
	public boolean isErros() {
		return erros;
	}
	public void setErros(boolean erros) {
		this.erros = erros;
	}
	public List<ValidationItem> getItems() {
		return items;
	}
	public void setItems(List<ValidationItem> items) {
		this.items = items;
	}
	public void addItem(ValidationItem i){
		if(!this.erros){
			this.erros = true;
		}
		if(this.items == null){
			items = new ArrayList<ValidationResult.ValidationItem>();
		}
		items.add(i);
	}
	public void addItem(String f, String m){
		addItem(new ValidationItem(f, m));
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{erros:\"").append(erros).append("\"");
		if(items.size() > 0){
			sb.append(", items:[");
			for(int i = 0; i < items.size(); i++){
				ValidationItem v = items.get(i);
				sb.append(v.toString());
				if(items.size() > 1 && i < items.size()-1){
					sb.append(",");
				}
			}
			sb.append("]}");
		}else{
			sb.append("}");
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * 
	 * @author weslly
	 * @date 2014年8月20日下午3:25:46
	 * @fileName ValidationResult.java
	 */
	public class ValidationItem{
		private String filedName;
		private String filedMsg;
		public ValidationItem(){}
		public ValidationItem(String f, String m){
			this.filedName = f;
			this.filedMsg = m;
		}
		public String getFiledName() {
			return filedName;
		}
		public void setFiledName(String filedName) {
			this.filedName = filedName;
		}
		public String getFiledMsg() {
			return filedMsg;
		}
		public void setFiledMsg(String filedMsg) {
			this.filedMsg = filedMsg;
		}
		@Override
		public String toString() {
			return "{filedName:\""+filedName+"\", filedMsg:\""+filedMsg+"\"}";
		}
	}
}

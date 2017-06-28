package com.chyjr.platform.datamodule;

/**
 * 
 * @ProjectName:  [PlatformDataModule]
 * @Package:      [com.chyjr.platform.datamodule.MobilePackageBean.java] 
 * @ClassName:    [MobilePackageBean]  
 * @Description:  [移动终端报文格式类]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 14, 2014 5:40:08 PM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 14, 2014 5:40:08 PM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class MobilePackageBean {
	
	private String transCode; //交易码
	private String packageFormat;//打包格式
	private int length;//报文长度
	private String p;//客户端校验串 
	private String terminalType;//客户端类型
	private Object content;//报文内容
	private String returnCode = "AAAAAAA";
	
	
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getPackageFormat() {
		return packageFormat;
	}
	public void setPackageFormat(String packageFormat) {
		this.packageFormat = packageFormat;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	
	

}

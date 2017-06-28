package com.platform.common.tool.exceloperator;

/**
 * 写Excel时用到的属性
 * @author 许德麟
 * @version 1.0
 */
public class ExcelAttribute {
	
	
	private String fileName ; //文件名称
	private String sheetName; //sheet 名称
	private String title;  //文档标题名称
	private String [] fieldNames; //字段名称
	private String hander; //页眉
	private String footer; //页脚
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String[] getFieldNames() {
		return fieldNames;
	}
	public void setFieldNames(String[] fieldNames) {
		this.fieldNames = fieldNames;
	}
	public String getFooter() {
		return footer;
	}
	public void setFooter(String footer) {
		this.footer = footer;
	}
	
	public String getHander() {
		return hander;
	}
	public void setHander(String hander) {
		this.hander = hander;
	}
	
	
}

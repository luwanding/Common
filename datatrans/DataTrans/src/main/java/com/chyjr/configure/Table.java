package com.chyjr.configure;

public class Table {
	private String fromtabname; //来自表名
	private String totabname; //存入表名
	
	public Field[] fields;

	public String getFromtabname() {
		return fromtabname;
	}

	public void setFromtabname(String fromtabname) {
		this.fromtabname = fromtabname;
	}

	public String getTotabname() {
		return totabname;
	}

	public void setTotabname(String totabname) {
		this.totabname = totabname;
	}

	public Field[] getFields() {
		return fields;
	}

	public void setFields(Field[] fields) {
		this.fields = fields;
	}
	
}

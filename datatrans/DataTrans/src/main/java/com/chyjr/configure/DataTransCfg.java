package com.chyjr.configure;

import java.util.List;

public class DataTransCfg {
	
	private ConnConfig connform;
	private ConnConfig connto;
	private List<Table> tables;
	public ConnConfig getConnform() {
		return connform;
	}
	public void setConnform(ConnConfig connform) {
		this.connform = connform;
	}
	public ConnConfig getConnto() {
		return connto;
	}
	public void setConnto(ConnConfig connto) {
		this.connto = connto;
	}
	public List<Table> getTables() {
		return tables;
	}
	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
}

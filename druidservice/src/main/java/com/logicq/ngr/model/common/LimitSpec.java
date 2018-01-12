package com.logicq.ngr.model.common;

import java.util.List;

public class LimitSpec {
	
	private String type;
	private int limit;
	private List<String> columns;
	
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public List<String> getColumns() {
		return columns;
	}
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	@Override
	public String toString() {
		return "LimitSpec [type=" + type + ", limit=" + limit + ", columns=" + columns + "]";
	}
	
	

}

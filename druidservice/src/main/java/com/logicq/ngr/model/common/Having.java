package com.logicq.ngr.model.common;

import java.io.Serializable;

public class Having implements Serializable {
	
	private String type;
	private String aggregation;
	private String value;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAggregation() {
		return aggregation;
	}
	public void setAggregation(String aggregation) {
		this.aggregation = aggregation;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Having [type=" + type + ", aggregation=" + aggregation + ", value=" + value + "]";
	}
}

package com.logicq.ngr.model.druid.common;

import java.util.List;

public class Filter {

	private String type;
	private List<Fields> fields;

	public String getType() {
		return type;
	}

	public List<Fields> getFields() {
		return fields;
	}

	public void setFields(List<Fields> fields) {
		this.fields = fields;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Filter [type=" + type + ", fields=" + fields + "]";
	}
}
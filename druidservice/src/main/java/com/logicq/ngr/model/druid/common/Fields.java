package com.logicq.ngr.model.druid.common;

import java.util.ArrayList;
import java.util.List;

public class Fields {

	private String type;
	private String dimension;
	private String value;
	private List<Fields> fields;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<Fields> getFields() {
		if (null == fields) {
			fields = new ArrayList<Fields>();
		}
		return fields;
	}

	public void setFields(List<Fields> fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		return "Fields [type=" + type + ", dimension=" + dimension + ", value=" + value + ", fields=" + fields + "]";
	}
}

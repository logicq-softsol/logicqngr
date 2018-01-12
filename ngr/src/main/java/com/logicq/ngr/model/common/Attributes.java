package com.logicq.ngr.model.common;

import java.util.Arrays;

public class Attributes {

	private String name;
	private String nodeproperty;
	private String defaultvalue;
	private String sort;
	private String order;
	private String[] values;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNodeproperty() {
		return nodeproperty;
	}
	public void setNodeproperty(String nodeproperty) {
		this.nodeproperty = nodeproperty;
	}
	public String getDefaultvalue() {
		return defaultvalue;
	}
	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String[] getValues() {
		return values;
	}
	public void setValues(String[] values) {
		this.values = values;
	}
	@Override
	public String toString() {
		return "Attributes [name=" + name + ", nodeproperty=" + nodeproperty + ", defaultvalue=" + defaultvalue
				+ ", sort=" + sort + ", order=" + order + ", values=" + Arrays.toString(values) + "]";
	}

	

}
package com.logicq.ngr.model.druid.common;

public class BaseFilter {
	private String type;
	private String value;
	private String applicableCondition;
	private String previousCondition;
	private String nextCondition;

	public BaseFilter() {
	}

	public BaseFilter(String type, String value, String applicableCondition, String previousCondition,
			String nextCondition) {
		this.type = type;
		this.value = value;
		this.applicableCondition = applicableCondition;
		this.previousCondition = previousCondition;
		this.nextCondition = nextCondition;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getApplicableCondition() {
		return applicableCondition;
	}

	public void setApplicableCondition(String applicableCondition) {
		this.applicableCondition = applicableCondition;
	}

	public String getPreviousCondition() {
		return previousCondition;
	}

	public void setPreviousCondition(String previousCondition) {
		this.previousCondition = previousCondition;
	}

	public String getNextCondition() {
		return nextCondition;
	}

	public void setNextCondition(String nextCondition) {
		this.nextCondition = nextCondition;
	}

	@Override
	public String toString() {
		return "BaseFilter [type=" + type + ", value=" + value + ", applicableCondition=" + applicableCondition
				+ ", previousCondition=" + previousCondition + ", nextCondition=" + nextCondition + "]";
	}
}
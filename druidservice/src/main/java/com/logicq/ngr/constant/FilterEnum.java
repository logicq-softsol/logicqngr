package com.logicq.ngr.constant;

import com.logicq.ngr.model.druid.common.BaseFilter;
import com.logicq.ngr.util.FilterHelper;

public enum FilterEnum {
	AND("&&"),
	OR("||"),
	NOT("!"),
	NOT_EQUALS("!="), 
	GRATER_THAN(">"), 
	GRATER_THAN_EQUALS(">="), 
	LESS_THAN("<"), 
	LESS_THAN_EQUALS("<="),
	EQUALS("==");
	
	private final String value;

	private FilterEnum(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public static FilterEnum getEnum(String value) {
		for (FilterEnum v : values())
			if (v.getValue().equals(value))
				return v;
		throw new IllegalArgumentException();
	}

	public static void checkEnumType(String value, BaseFilter filterdetails) {
		for (FilterEnum v : values()) {
			if (value.contains(v.getValue())) {
				String[] splitsymbol = null;
				if (value.contains(FilterEnum.GRATER_THAN_EQUALS.getValue())) {
					splitsymbol = value.split(FilterEnum.GRATER_THAN_EQUALS.getValue());
					filterdetails.setApplicableCondition(FilterHelper.expressionsMap.get(">="));
				} else if (value.contains(FilterEnum.LESS_THAN_EQUALS.getValue())) {
					splitsymbol = value.split(FilterEnum.LESS_THAN_EQUALS.getValue());
					filterdetails.setApplicableCondition(FilterHelper.expressionsMap.get("<="));
				} else if (value.contains(FilterEnum.GRATER_THAN.getValue())) {
					splitsymbol = value.split(FilterEnum.GRATER_THAN.getValue());
					filterdetails.setApplicableCondition(FilterHelper.expressionsMap.get(">"));
				} else if (value.contains(FilterEnum.LESS_THAN.getValue())) {
					splitsymbol = value.split(FilterEnum.LESS_THAN.getValue());
					filterdetails.setApplicableCondition(FilterHelper.expressionsMap.get("<"));
				} else {
					splitsymbol = value.split(v.getValue());
					filterdetails.setApplicableCondition(FilterHelper.expressionsMap.get(v.getValue()));
				}
				if (null != splitsymbol && splitsymbol.length > 0) {
					filterdetails.setType(splitsymbol[0]);
					filterdetails.setValue(splitsymbol[1]);
				}
			}
		}
	
	}
}
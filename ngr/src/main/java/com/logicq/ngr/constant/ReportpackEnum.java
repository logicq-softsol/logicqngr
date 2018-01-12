package com.logicq.ngr.constant;

public enum ReportpackEnum {

	Type("reportpack"), VIEWTYPE("default");
	private final String value;
	
	private ReportpackEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public static ReportpackEnum getEnum(String value) {
		for (ReportpackEnum v : values())
			if (v.getValue().equals(value))
				return v;
		throw new IllegalArgumentException();
	}
}

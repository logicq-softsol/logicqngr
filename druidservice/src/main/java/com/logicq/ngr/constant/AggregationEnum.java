package com.logicq.ngr.constant;

public enum AggregationEnum {

	SUM("SUM"), MIN("MIN"), MAX("MAX"), AVERAGE("AVG"), COUNT("COUNT");
	private final String value;

	private AggregationEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static AggregationEnum getEnum(String value) {
		for (AggregationEnum v : values())
			if (v.getValue().equals(value))
				return v;
		throw new IllegalArgumentException();
	}
}

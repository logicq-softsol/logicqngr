package com.logicq.ngr.constant;

public enum FeatureEnum {
	
	ALL("all"), DATA_ACESS("dataacess"),DEFAULT("default"),NORMAL("normal");
	private final String value;

	private FeatureEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static FeatureEnum getEnum(String value) {
		for (FeatureEnum v : values())
			if (v.getValue().equals(value))
				return v;
		throw new IllegalArgumentException();
	}

}

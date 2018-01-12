package com.logicq.ngr.constant;

public enum ViewType {
	DEFAULT("default"), NORMAL("normal");
	private final String value;

	private ViewType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static ViewType getEnum(String value) {
		for (ViewType v : values())
			if (v.getValue().equals(value))
				return v;
		throw new IllegalArgumentException();
	}
}
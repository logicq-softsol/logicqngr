package com.logicq.ngr.constant;

public enum EntityTypeEnum {
	
	PERFORMANCE("Performance"), EVENT("Event"),YOKUN("Yukon");
	private final String value;

	private EntityTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static EntityTypeEnum getEnum(String value) {
		for (EntityTypeEnum v : values())
			if (v.getValue().equals(value))
				return v;
		throw new IllegalArgumentException();
	}


}

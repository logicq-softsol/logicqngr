package com.logicq.ngr.vo;

public class SiteOptionVO {
	private String dimension;
	private Integer value;

	public SiteOptionVO(String dimension, String Integer) {
		super();
		this.dimension = dimension;
		this.value = value;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	

	

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "SiteOptionVo [dimension=" + dimension + ", value=" + value + "]";
	}

	
}

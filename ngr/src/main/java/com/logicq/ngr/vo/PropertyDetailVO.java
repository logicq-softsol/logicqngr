package com.logicq.ngr.vo;

import java.io.Serializable;
import java.util.Set;

public class PropertyDetailVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 83849433400112094L;
	private String key;
	private int startRange;
	private int endRange;
	private String pattern;
	private Set<Object> properties;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public Set<Object> getProperties() {
		return properties;
	}
	public void setProperties(Set<Object> properties) {
		this.properties = properties;
	}
	public int getStartRange() {
		return startRange;
	}
	public void setStartRange(int startRange) {
		this.startRange = startRange;
	}
	public int getEndRange() {
		return endRange;
	}
	public void setEndRange(int endRange) {
		this.endRange = endRange;
	}
	@Override
	public String toString() {
		return "PropertySetVO [key=" + key + ", startRange=" + startRange + ", endRange=" + endRange + ", pattern="
				+ pattern + ", properties=" + properties + "]";
	}
}

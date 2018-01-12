package com.logicq.ngr.model.common;


public class Dimensions {

	private String displayName;
	private String name;
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Dimensions [displayName=" + displayName + ", name=" + name + "]";
	}
	
}

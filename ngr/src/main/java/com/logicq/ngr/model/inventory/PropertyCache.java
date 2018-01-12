package com.logicq.ngr.model.inventory;

import java.io.Serializable;

public class PropertyCache implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8749186152681733064L;

	private String id;
	
	private String name;
	
	private String displayName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "PropertyCache [id=" + id + ", name=" + name + ", displayName=" + displayName + "]";
	}
}

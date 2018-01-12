package com.logicq.ngr.model.common;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Attribute implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2738635113698431608L;

	private String name;
	private String displayName;
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	
	@Override
	public String toString() {
		return "Attribute [name=" + name + ", displayName=" + displayName + ", id=" + id + "]";
	}	
}

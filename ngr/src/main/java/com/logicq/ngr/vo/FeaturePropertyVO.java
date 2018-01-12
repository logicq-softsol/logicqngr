package com.logicq.ngr.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.logicq.ngr.model.common.Attribute;

public class FeaturePropertyVO implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2520580960065358405L;
	
	private String name;
	private String displayName;
	private String id;
	private Attribute defaultacess;
	private List<Attribute> features=new ArrayList<>();
	private List<Attribute> report=new ArrayList<>();
	private List<Attribute> dashboard=new ArrayList<>();
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
	public List<Attribute> getReport() {
		return report;
	}
	public void setReport(List<Attribute> report) {
		this.report = report;
	}
	public List<Attribute> getDashboard() {
		return dashboard;
	}
	public void setDashboard(List<Attribute> dashboard) {
		this.dashboard = dashboard;
	}
	public List<Attribute> getFeatures() {
		return features;
	}
	public void setFeatures(List<Attribute> features) {
		this.features = features;
	}
	public Attribute getDefaultacess() {
		return defaultacess;
	}
	public void setDefaultacess(Attribute defaultacess) {
		this.defaultacess = defaultacess;
	}
	
	@Override
	public String toString() {
		return "FeaturePropertyVO [name=" + name + ", displayName=" + displayName + ", id=" + id + ", defaultacess="
				+ defaultacess + ", features=" + features + ", report=" + report + ", dashboard=" + dashboard + "]";
	}
}

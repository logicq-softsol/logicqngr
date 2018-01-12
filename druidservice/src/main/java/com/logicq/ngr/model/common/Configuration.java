package com.logicq.ngr.model.common;

import java.io.Serializable;
import java.util.List;

public class Configuration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -696378756782020756L;

	private List<Attribute> attributes;
	private List<Attribute> properties;
	private List<Metric> metrics;
	private String name;
	private String type;
	private String subType;
	private Attribute entity;
	private List<Attribute> legends;
	
	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public List<Attribute> getProperties() {
		return properties;
	}

	public void setProperties(List<Attribute> properties) {
		this.properties = properties;
	}

	public List<Metric> getMetrics() {
		return metrics;
	}

	public void setMetrics(List<Metric> metrics) {
		this.metrics = metrics;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public Attribute getEntity() {
		return entity;
	}

	public void setEntity(Attribute entity) {
		this.entity = entity;
	}

	public List<Attribute> getLegends() {
		return legends;
	}

	public void setLegends(List<Attribute> legends) {
		this.legends = legends;
	}

	@Override
	public String toString() {
		return "Configuration [attributes=" + attributes + ", properties=" + properties + ", metrics=" + metrics
				+ ", name=" + name + ", type=" + type + ", subType=" + subType + ", entity=" + entity + ", legends="
				+ legends + "]";
	}

}

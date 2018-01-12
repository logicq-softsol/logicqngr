package com.logicq.ngr.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.logicq.ngr.model.common.Attribute;

public class ReportPropertyVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3425660240059616430L;
	
	private List<Attribute> samplingPeriodInterval;
	
	private List<Attribute> duration;
	
	private Map<String,List<Attribute>> metrics=new HashMap<String, List<Attribute>>();
	
	
	private Map<String,List<Attribute>> legends=new HashMap<String, List<Attribute>>();
	
	
	private List<Attribute> entities;
	
	private Attribute defaultEntityType;
	
	private List<Attribute> normalEntityType;
	
	private Map<String,List<Attribute>> property=new HashMap<String, List<Attribute>>();
	//private List<Attribute> property;
	
	private  Map<String,List<ReportTypeVO>>   reportType=new HashMap<String, List<ReportTypeVO>>();
	
	private  Map<String,List<Attribute>> aggregationtype=new HashMap<String, List<Attribute>>();;
	
	
	

	public List<Attribute> getSamplingPeriodInterval() {
		return samplingPeriodInterval;
	}

	public void setSamplingPeriodInterval(List<Attribute> samplingPeriodInterval) {
		this.samplingPeriodInterval = samplingPeriodInterval;
	}

	public List<Attribute> getDuration() {
		return duration;
	}

	public void setDuration(List<Attribute> duration) {
		this.duration = duration;
	}
	public Map<String, List<Attribute>> getLegends() {
		return legends;
	}

	public void setLegends(Map<String, List<Attribute>> legends) {
		this.legends = legends;
	}

	public List<Attribute> getEntities() {
		return entities;
	}

	public void setEntities(List<Attribute> entities) {
		this.entities = entities;
	}
	
	public Map<String, List<Attribute>> getMetrics() {
		return metrics;
	}

	public void setMetrics(Map<String, List<Attribute>> metrics) {
		this.metrics = metrics;
	}

	public Map<String, List<Attribute>> getProperty() {
		return property;
	}

	public void setProperty(Map<String, List<Attribute>> property) {
		this.property = property;
	}

	public Map<String, List<ReportTypeVO>> getReportType() {
		return reportType;
	}

	public void setReportType(Map<String, List<ReportTypeVO>> reportType) {
		this.reportType = reportType;
	}

	public Map<String, List<Attribute>> getAggregationtype() {
		return aggregationtype;
	}

	public void setAggregationtype(Map<String, List<Attribute>> aggregationtype) {
		this.aggregationtype = aggregationtype;
	}

	public Attribute getDefaultEntityType() {
		return defaultEntityType;
	}

	public void setDefaultEntityType(Attribute defaultEntityType) {
		this.defaultEntityType = defaultEntityType;
	}

	public List<Attribute> getNormalEntityType() {
		return normalEntityType;
	}

	public void setNormalEntityType(List<Attribute> normalEntityType) {
		this.normalEntityType = normalEntityType;
	}

	

}
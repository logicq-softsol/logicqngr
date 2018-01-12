package com.logicq.ngr.model.admin.feature;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ReportFeatures implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3538729972360801143L;
	private String type;
	private Map<String, Object> featureProperty;
	private List<ReportFeatures> reportFeatures;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, Object> getFeatureProperty() {
		return featureProperty;
	}
	public void setFeatureProperty(Map<String, Object> featureProperty) {
		this.featureProperty = featureProperty;
	}
	public List<ReportFeatures> getReportFeatures() {
		return reportFeatures;
	}
	public void setReportFeatures(List<ReportFeatures> reportFeatures) {
		this.reportFeatures = reportFeatures;
	}
	@Override
	public String toString() {
		return "ReportFeatures [type=" + type + ", featureProperty=" + featureProperty + ", reportFeatures="
				+ reportFeatures + "]";
	}
	
}

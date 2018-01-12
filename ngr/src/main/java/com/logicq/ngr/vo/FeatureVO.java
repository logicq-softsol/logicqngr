package com.logicq.ngr.vo;

import java.io.Serializable;
import java.util.Map;

public class FeatureVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5665063059581178511L;
	private Long featureId;
	private String applicableTo;
	private String applicableType;
	private Map<String, Object> featureProperty;

	public Long getFeatureId() {
		return featureId;
	}

	public void setFeatureId(Long featureId) {
		this.featureId = featureId;
	}

	public String getApplicableTo() {
		return applicableTo;
	}

	public void setApplicableTo(String applicableTo) {
		this.applicableTo = applicableTo;
	}

	public String getApplicableType() {
		return applicableType;
	}

	public void setApplicableType(String applicableType) {
		this.applicableType = applicableType;
	}

	public Map<String, Object> getFeatureProperty() {
		return featureProperty;
	}

	public void setFeatureProperty(Map<String, Object> featureProperty) {
		this.featureProperty = featureProperty;
	}

	@Override
	public String toString() {
		return "FeatureVO [featureId=" + featureId + ", applicableTo=" + applicableTo + ", applicableType="
				+ applicableType + ", featureProperty=" + featureProperty + "]";
	}

}
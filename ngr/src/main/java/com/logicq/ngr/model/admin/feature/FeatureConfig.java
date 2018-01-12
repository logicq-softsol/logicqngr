package com.logicq.ngr.model.admin.feature;

import java.io.Serializable;

public class FeatureConfig implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -246794287413099149L;
	private ReportFeatures reportFeatureConfig;
	private ReportFeatures userFeatureConfig;
	
	public ReportFeatures getReportFeatureConfig() {
		return reportFeatureConfig;
	}
	public void setReportFeatureConfig(ReportFeatures reportFeatureConfig) {
		this.reportFeatureConfig = reportFeatureConfig;
	}
	public ReportFeatures getUserFeatureConfig() {
		return userFeatureConfig;
	}
	public void setUserFeatureConfig(ReportFeatures userFeatureConfig) {
		this.userFeatureConfig = userFeatureConfig;
	}
	
	@Override
	public String toString() {
		return "FeatureConfig [reportFeatureConfig=" + reportFeatureConfig + ", userFeatureConfig=" + userFeatureConfig
				+ "]";
	}
	
}

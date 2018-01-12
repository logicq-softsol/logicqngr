package com.logicq.ngr.vo;

import java.io.Serializable;
import java.util.List;

public class FeatureDetailsVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6152431902224576108L;

	private List<FeaturePropertyVO> management;
	
	private  List<FeaturePropertyVO> reportFeatureList;

	public List<FeaturePropertyVO> getManagement() {
		return management;
	}

	public void setManagement(List<FeaturePropertyVO> management) {
		this.management = management;
	}

	public List<FeaturePropertyVO> getReportFeatureList() {
		return reportFeatureList;
	}

	public void setReportFeatureList(List<FeaturePropertyVO> reportFeatureList) {
		this.reportFeatureList = reportFeatureList;
	}

	@Override
	public String toString() {
		return "FeatureDetailsVO [management=" + management + ", reportFeatureList=" + reportFeatureList + "]";
	}

}

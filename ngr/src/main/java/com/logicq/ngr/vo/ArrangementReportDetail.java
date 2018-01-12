package com.logicq.ngr.vo;

import java.io.Serializable;

public class ArrangementReportDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1438176785986887438L;

	private String reportId;
	private int reportIndex;
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public int getReportIndex() {
		return reportIndex;
	}
	public void setReportIndex(int reportIndex) {
		this.reportIndex = reportIndex;
	}
	@Override
	public String toString() {
		return "ArrangementReportDetail [reportId=" + reportId + ", reportIndex=" + reportIndex + ", type=" + type
				+ "]";
	}
}

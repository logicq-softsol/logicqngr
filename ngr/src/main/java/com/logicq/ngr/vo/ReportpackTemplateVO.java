package com.logicq.ngr.vo;

import java.util.Map;

public class ReportpackTemplateVO {

	
	private String reportpackId;
	
	private String reportpackTemplateId;

	private String reportType;
	
	private String userName;
	
	private Map<String, Object> jsonString;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getReportpackId() {
		return reportpackId;
	}

	public void setReportpackId(String reportpackId) {
		this.reportpackId = reportpackId;
	}

	public String getReportpackTemplateId() {
		return reportpackTemplateId;
	}

	public void setReportpackTemplateId(String reportpackTemplateId) {
		this.reportpackTemplateId = reportpackTemplateId;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public Map<String, Object> getJsonString() {
		return jsonString;
	}

	public void setJsonString(Map<String, Object> jsonString) {
		this.jsonString = jsonString;
	}

	@Override
	public String toString() {
		return "ReportpackTemplateVO [reportpackId=" + reportpackId + ", reportpackTemplateId=" + reportpackTemplateId
				+ ", reportType=" + reportType + ", userName=" + userName + ", jsonString=" + jsonString + "]";
	}
}

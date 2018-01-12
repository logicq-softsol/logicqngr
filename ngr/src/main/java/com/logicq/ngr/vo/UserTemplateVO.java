package com.logicq.ngr.vo;

import java.io.Serializable;
import java.util.Map;

public class UserTemplateVO extends BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6920509619299421086L;

	private Long userId;

	private String dashboardId;
	
	private String sourceDashboardId;

	private String templateId;

	private String reportType;
	
	private String type;

	private String userTemplateId;

	//private ReportRequest reportRequest;
	private Map<String, Object> jsonString;
	
	private EmailVO email;
	
	public String getSourceDashboardId() {
		return sourceDashboardId;
	}

	public EmailVO getEmail() {
		return email;
	}

	public void setEmail(EmailVO email) {
		this.email = email;
	}

	public void setSourceDashboardId(String sourceDashboardId) {
		this.sourceDashboardId = sourceDashboardId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(String dashboardId) {
		this.dashboardId = dashboardId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getUserTemplateId() {
		return userTemplateId;
	}

	public void setUserTemplateId(String userTemplateId) {
		this.userTemplateId = userTemplateId;
	}

	public Map<String, Object> getJsonString() {
		return jsonString;
	}

	public void setJsonString(Map<String, Object> jsonString) {
		this.jsonString = jsonString;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "UserTemplateVO [userId=" + userId + ", dashboardId=" + dashboardId + ", sourceDashboardId="
				+ sourceDashboardId + ", templateId=" + templateId + ", reportType=" + reportType + ", type=" + type
				+ ", userTemplateId=" + userTemplateId + ", jsonString=" + jsonString + "]";
	}
	
}

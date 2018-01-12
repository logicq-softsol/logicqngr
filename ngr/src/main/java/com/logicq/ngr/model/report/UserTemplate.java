package com.logicq.ngr.model.report;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "USER_TEMPLATE")
public class UserTemplate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6489273453326139901L;
	@Column(name = "REOPRT_TYPE",nullable = true)
	private String reportType;
	
	@Lob
	@Column(name = "REPORT_DETAILS",nullable = false)
	private byte[] jsonString;
	
	@EmbeddedId
    private UserTemplateKey userTemplateKey;

	@Column(name = "DELETED",nullable = false,columnDefinition = "boolean default false")
	private boolean deleted;
	
	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public byte[] getJsonString() {
		return jsonString;
	}

	public void setJsonString(byte[] jsonString) {
		this.jsonString = jsonString;
	}

	public UserTemplateKey getUserTemplateKey() {
		return userTemplateKey;
	}

	public void setUserTemplateKey(UserTemplateKey userTemplateKey) {
		this.userTemplateKey = userTemplateKey;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "UserTemplate [reportType=" + reportType + ", jsonString=" + Arrays.toString(jsonString)
				+ ", userTemplateKey=" + userTemplateKey + ", deleted=" + deleted + "]";
	}

}

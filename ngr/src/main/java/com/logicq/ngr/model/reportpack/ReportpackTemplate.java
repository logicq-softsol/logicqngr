package com.logicq.ngr.model.reportpack;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "REPORTPACK_TEMPLATE")
public class ReportpackTemplate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7166395526479872241L;
	
	@Column(name = "REPORT_TYPE",nullable = true)
	private String reportType;
	
	@Lob
	@Column(name = "REPORT_DETAILS",nullable = false)
	private byte[] jsonString;
	
	@EmbeddedId
    private ReportpackTemplateKey reportpackTemplateKey;

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

	public ReportpackTemplateKey getReportpackTemplateKey() {
		return reportpackTemplateKey;
	}

	public void setReportpackTemplateKey(ReportpackTemplateKey reportpackTemplateKey) {
		this.reportpackTemplateKey = reportpackTemplateKey;
	}

	@Override
	public String toString() {
		return "ReportpackTemplate [reportType=" + reportType + ", jsonString=" + Arrays.toString(jsonString)
				+ ", reportpackTemplateKey=" + reportpackTemplateKey + "]";
	}
}

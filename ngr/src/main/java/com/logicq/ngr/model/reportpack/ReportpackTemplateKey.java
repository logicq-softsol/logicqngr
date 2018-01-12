package com.logicq.ngr.model.reportpack;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ReportpackTemplateKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1906312103552998798L;
	
	@Column(name = "REPORTPACK_TEMPLATE_ID", nullable = false)
	private String reportpackTemplateId;

	@Column(name = "REPORTPACK_ID", nullable = false)
	private String reportpackId;

	public String getReportpackTemplateId() {
		return reportpackTemplateId;
	}

	public void setReportpackTemplateId(String reportpackTemplateId) {
		this.reportpackTemplateId = reportpackTemplateId;
	}

	public String getReportpackId() {
		return reportpackId;
	}

	public void setReportpackId(String reportpackId) {
		this.reportpackId = reportpackId;
	}

	@Override
	public String toString() {
		return "ReportpackTemplateKey [reportpackTemplateId=" + reportpackTemplateId + ", reportpackId=" + reportpackId
				+ "]";
	}
}

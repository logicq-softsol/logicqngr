package com.logicq.ngr.model.report;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "TEMPLATE")
public class Template implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1924634781008728169L;

	@Id
	@Column(name = "ID", nullable = false)
	private String templateId;

	@Lob
	@Column(name = "JSON", nullable = false)
	private byte[] templateJson;

	@Column(name = "TYPE", nullable = false)
	private String templateType;

	@Column(name = "CHART_TYPE", nullable = false)
	private String chartType;

	@Column(name = "DIMENSIONS", nullable = false)
	private String dimensions;

	public byte[] getTemplateJson() {
		return templateJson;
	}

	public void setTemplateJson(byte[] templateJson) {
		this.templateJson = templateJson;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public String getDimensions() {
		return dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	@Override
	public String toString() {
		return "Template [templateId=" + templateId + ", templateJson=" + Arrays.toString(templateJson)
				+ ", templateType=" + templateType + ", chartType=" + chartType + ", dimensions=" + dimensions + "]";
	}

}

package com.logicq.ngr.model.report;

import java.io.Serializable;

import com.logicq.ngr.model.response.BaseResponse;

public class Report implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8765303232630585423L;
	
	private String  reportType;
	
	private BaseResponse<?> response;

	public BaseResponse<?> getResponse() {
		return response;
	}

	public void setResponse(BaseResponse<?> response) {
		this.response = response;
	}


	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	
	
	@Override
	public String toString() {
		return "Report [response=" + response + "]";
	}

}

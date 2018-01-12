package com.logicq.ngr.model.response;

import java.io.Serializable;
import java.util.List;

public class ReportResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2211968229043986077L;
	
	private List<Object> response;

	public List<Object> getResponse() {
		return response;
	}

	public void setResponse(List<Object> response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "ReportResponse [response=" + response + "]";
	}

}

package com.logicq.ngr.model.report;

import java.io.Serializable;

import com.logicq.ngr.model.response.BaseResponse;

public class GraphReport implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6301823308873476227L;
	
	private BaseResponse<?> graphResponse;

	public BaseResponse<?> getGraphResponse() {
		return graphResponse;
	}

	public void setGraphResponse(BaseResponse<?> graphResponse) {
		this.graphResponse = graphResponse;
	}

	@Override
	public String toString() {
		return "GraphReport [graphResponse=" + graphResponse + "]";
	}

}

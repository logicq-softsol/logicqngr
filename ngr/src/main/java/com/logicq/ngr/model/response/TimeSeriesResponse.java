package com.logicq.ngr.model.response;

import java.util.Arrays;

public class TimeSeriesResponse extends BaseResponse<TimeSeriesResponse> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4562949838520835656L;
	private DruidTimeSeriesResponse[] response;

	public DruidTimeSeriesResponse[] getResponse() {
		return response;
	}

	public void setResponse(DruidTimeSeriesResponse[] response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "TimeSeriesResponse [response=" + Arrays.toString(response) + "]";
	}

}

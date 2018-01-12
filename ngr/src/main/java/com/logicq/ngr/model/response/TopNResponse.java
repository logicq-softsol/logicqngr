package com.logicq.ngr.model.response;

import java.io.Serializable;
import java.util.Arrays;

public class TopNResponse extends BaseResponse<TopNResponse> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3551132631346083296L;
	
	private DruidTopNResponse[] response;

	public DruidTopNResponse[] getResponse() {
		return response;
	}

	public void setResponse(DruidTopNResponse[] response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "TopNResponse [response=" + Arrays.toString(response) + "]";
	}


}

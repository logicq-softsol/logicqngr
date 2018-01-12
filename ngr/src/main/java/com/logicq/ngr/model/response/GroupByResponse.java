package com.logicq.ngr.model.response;

import java.util.Arrays;

/**
 * @author 611161800
 *
 */
public class GroupByResponse extends BaseResponse<GroupByResponse> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3263032143452848820L;

	private DruidGroupByResponse[] response;

	public DruidGroupByResponse[] getResponse() {
		return response;
	}

	public void setResponse(DruidGroupByResponse[] response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "GroupByResponse [response=" + Arrays.toString(response) + "]";
	}

}

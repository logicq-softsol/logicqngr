package com.logicq.ngr.model.report;

import java.io.Serializable;

import com.logicq.ngr.model.response.BaseResponse;

public class TableReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7298349096906170113L;

	private BaseResponse<?> tableResponse;

	public BaseResponse<?> getTableResponse() {
		return tableResponse;
	}

	public void setTableResponse(BaseResponse<?> tableResponse) {
		this.tableResponse = tableResponse;
	}

	@Override
	public String toString() {
		return "TableReport [tableResponse=" + tableResponse + "]";
	}

}
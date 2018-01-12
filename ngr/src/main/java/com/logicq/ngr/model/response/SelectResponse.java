package com.logicq.ngr.model.response;

public class SelectResponse extends BaseResponse<SelectResponse> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6067433514644067691L;

	private DruidSelectResponse[] druidSelectResponce;

	public DruidSelectResponse[] getDruidSelectResponce() {
		return druidSelectResponce;
	}

	public void setDruidSelectResponce(DruidSelectResponse[] druidSelectResponce) {
		this.druidSelectResponce = druidSelectResponce;
	}

}
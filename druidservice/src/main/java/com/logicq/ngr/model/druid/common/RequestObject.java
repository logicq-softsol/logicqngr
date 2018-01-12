package com.logicq.ngr.model.druid.common;

import com.logicq.ngr.model.druid.request.DruidBaseRequest;

public class RequestObject {
	private String url;
	private DruidBaseRequest druidBaseRequest;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public DruidBaseRequest getDruidBaseRequest() {
		return druidBaseRequest;
	}

	public void setDruidBaseRequest(DruidBaseRequest druidBaseRequest) {
		this.druidBaseRequest = druidBaseRequest;
	}
}
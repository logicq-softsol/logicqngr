package com.logicq.ngr.model.response;

import java.util.List;
import java.util.Map;

import com.logicq.ngr.model.common.Configuration;

public class RestAPIResponse {

	private String timestamp;

	private List<Map<String, Object>> result;
	private Configuration configuration;

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public List<Map<String, Object>> getResult() {
		return result;
	}

	public void setResult(List<Map<String, Object>> result) {
		this.result = result;
	}
}

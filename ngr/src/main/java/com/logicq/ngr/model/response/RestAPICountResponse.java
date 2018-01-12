package com.logicq.ngr.model.response;

import java.util.Map;

public class RestAPICountResponse extends DruidBaseResponse{
	private Map<String,Object> event;

	public Map<String, Object> getEvent() {
		return event;
	}

	public void setEvent(Map<String, Object> event) {
		this.event = event;
	}

}

package com.logicq.ngr.common.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:common.properties")
public class CommonAttribute {
	
	@Value("${common.zoneId}")
	private String zoneId;

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	@Override
	public String toString() {
		return "CommonAttribute [zoneId=" + zoneId + "]";
	}
}

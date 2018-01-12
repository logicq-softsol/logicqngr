package com.logicq.ngr.model.druid.common;

import java.util.Map;

public class PagingSpec {

	private Map<String, Object> pagingIdentifiers;
	private int threshold;

	public Map<String, Object> getPagingIdentifiers() {
		return pagingIdentifiers;
	}

	public void setPagingIdentifiers(Map<String, Object> pagingIdentifiers) {
		this.pagingIdentifiers = pagingIdentifiers;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	@Override
	public String toString() {
		return "PagingSpec [pagingIdentifiers=" + pagingIdentifiers + ", threshold=" + threshold + "]";
	}

}

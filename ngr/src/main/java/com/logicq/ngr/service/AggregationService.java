package com.logicq.ngr.service;

import com.logicq.ngr.model.AggregationType;

public interface AggregationService {
	
	public AggregationType fetchAggregationAccordingToFilter(String type, String dataSourceName)throws Exception;
}

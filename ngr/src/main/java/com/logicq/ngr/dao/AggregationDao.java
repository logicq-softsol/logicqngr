package com.logicq.ngr.dao;

import com.logicq.ngr.model.AggregationType;

public interface AggregationDao {

	public AggregationType fetchAggregationAccordingToFilter(String type, String dataSourceName)throws Exception;
}

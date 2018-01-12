package com.logicq.ngr.dao;

import java.util.List;

import com.logicq.ngr.model.GranularityMetadata;

public interface GranularityMetadataDAO {

	List<GranularityMetadata> getAllGranularityValues();

	String getGranularity(String samplingPeriod);
}

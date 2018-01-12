package com.logicq.ngr.service;

import java.util.List;

import com.logicq.ngr.model.GranularityMetadata;

public interface GranularityMetadataService {

	List<GranularityMetadata> getAllGranularityValues();

	String getGranularity(String samplingPeriod);
	
}
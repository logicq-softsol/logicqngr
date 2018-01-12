package com.logicq.ngr.dao;

import java.util.List;

import com.logicq.ngr.model.admin.feature.FeatureMetadata;

public interface FeatureMetadataDao {
	List<FeatureMetadata> getFeatureMetadata() throws Exception;
}
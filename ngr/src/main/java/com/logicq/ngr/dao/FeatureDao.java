package com.logicq.ngr.dao;

import java.util.List;

import com.logicq.ngr.model.admin.Feature;

public interface FeatureDao {
	//List<Feature> getFeatures(FeatureVO featureVO) throws Exception;
	void addfeature(Feature feature);
	void editFeature(Feature feature);
	void deleteFeature(Feature feature);
	List<Feature> getAllFeatures();
}
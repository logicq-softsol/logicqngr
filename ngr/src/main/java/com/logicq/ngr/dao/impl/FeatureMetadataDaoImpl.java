package com.logicq.ngr.dao.impl;

import java.util.List;

import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.FeatureMetadataDao;
import com.logicq.ngr.model.admin.feature.FeatureMetadata;

public class FeatureMetadataDaoImpl extends AbstractDAO<FeatureMetadata> implements FeatureMetadataDao	{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4287217686337420451L;

	@Override
	public List<FeatureMetadata> getFeatureMetadata() throws Exception {
		String query = " from FeatureMetadata fm";
		return executeQuery(query);
	}

}

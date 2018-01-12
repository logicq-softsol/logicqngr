package com.logicq.ngr.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.GranularityMetadataDAO;
import com.logicq.ngr.model.GranularityMetadata;

@Repository
public class GranularityMetadtaDAOImpl extends AbstractDAO<GranularityMetadata> implements GranularityMetadataDAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6569437506898255390L;

	@Override
	public List<GranularityMetadata> getAllGranularityValues() {
		return executeQuery(" from GranularityMetadata gm" );
	}

	@Override
	public String getGranularity(String samplingPeriod) {
		StringBuilder queryString=new StringBuilder();
		queryString.append(" from GranularityMetadata gm where gm.samplingPeriod='" + samplingPeriod + "'");
		GranularityMetadata granularityMetadata = executeQueryForUniqueRecord(queryString.toString());
		return granularityMetadata.getGranularity();
	}

}
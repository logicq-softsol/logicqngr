package com.logicq.ngr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.logicq.ngr.dao.GranularityMetadataDAO;
import com.logicq.ngr.model.GranularityMetadata;
import com.logicq.ngr.service.GranularityMetadataService;

@Service
public class GranularityMetadataServiceImpl implements GranularityMetadataService {

	@Autowired
	GranularityMetadataDAO granularityMetadataDAO;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<GranularityMetadata> getAllGranularityValues() {
		return granularityMetadataDAO.getAllGranularityValues();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getGranularity(String samplingPeriod) {
		return granularityMetadataDAO.getGranularity(samplingPeriod);
	}

}

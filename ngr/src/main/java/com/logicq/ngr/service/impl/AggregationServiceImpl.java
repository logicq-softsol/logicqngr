package com.logicq.ngr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.logicq.ngr.dao.AggregationDao;
import com.logicq.ngr.model.AggregationType;
import com.logicq.ngr.service.AggregationService;

@Service
@Transactional
public class AggregationServiceImpl implements AggregationService {
	
	@Autowired
	AggregationDao aggregationDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly=true)
	public AggregationType fetchAggregationAccordingToFilter(String type, String dataSourceName)
			throws Exception{		
		return aggregationDao.fetchAggregationAccordingToFilter(type, dataSourceName);			
	}
}

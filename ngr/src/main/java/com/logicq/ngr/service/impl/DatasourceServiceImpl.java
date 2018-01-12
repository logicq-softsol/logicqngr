package com.logicq.ngr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.logicq.ngr.dao.DatasourceDao;
import com.logicq.ngr.model.DatasourceDetail;
import com.logicq.ngr.service.DatasourceService;

@Service
@Transactional
public class DatasourceServiceImpl implements DatasourceService{

	@Autowired
	DatasourceDao datasourceDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
	public List<DatasourceDetail> getAllDatasource() {
		return datasourceDao.getAllDatasource();
	}

	@Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
	public DatasourceDetail fetchDataSourceAccordingToFilter(DatasourceDetail datasourceDetail) throws Exception {
		return datasourceDao.fetchDataSourceAccordingToFilter(datasourceDetail);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
	public DatasourceDetail fetchDataSourceAccordingToFilter(String samplingPeriod, String parentDataSource)
			throws Exception {
		return datasourceDao.fetchDataSourceAccordingToFilter(samplingPeriod,parentDataSource);
	}
	
}

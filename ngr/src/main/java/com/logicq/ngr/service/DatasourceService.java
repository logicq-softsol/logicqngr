package com.logicq.ngr.service;

import java.util.List;

import com.logicq.ngr.model.DatasourceDetail;

public interface DatasourceService {
	List<DatasourceDetail> getAllDatasource();
	
	DatasourceDetail fetchDataSourceAccordingToFilter(DatasourceDetail datasourceDetail) throws Exception;
	
	DatasourceDetail fetchDataSourceAccordingToFilter(String samplingPeriod, String parentDataSource) throws Exception;
	
}

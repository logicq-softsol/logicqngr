package com.logicq.ngr.dao;

import java.util.List;

import com.logicq.ngr.model.DatasourceDetail;


public interface DatasourceDao {
	List<DatasourceDetail> getAllDatasource();
	
	DatasourceDetail fetchDataSourceAccordingToFilter(DatasourceDetail datasourceDetail) throws Exception;
	
	DatasourceDetail fetchDataSourceAccordingToFilter(String samplingPeriod, String parentDataSource) throws Exception;
}

package com.logicq.ngr.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.DatasourceDao;
import com.logicq.ngr.model.DatasourceDetail;

@Repository
public class DatasourceDaoImpl extends AbstractDAO<DatasourceDetail> implements DatasourceDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8567814323235026450L;

	@Override
	public List<DatasourceDetail> getAllDatasource() {
		return (List<DatasourceDetail>) loadClass(DatasourceDetail.class);
	}

	@Override
	public DatasourceDetail fetchDataSourceAccordingToFilter(DatasourceDetail datasourceDetail) throws Exception {

		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("from DatasourceDetail dt ");

		StringBuilder whereQuery = new StringBuilder();

		if (null != datasourceDetail) {
			whereQuery.append("where ");
			if (datasourceDetail.getSamplingPeriod() != null) {
				whereQuery.append("dt.samplingPeriod='").append(datasourceDetail.getSamplingPeriod() + "'");
			} else {
				throw new Exception("SamplingPeriod can not be Null");
			}
			if (datasourceDetail.getParentDatasource() != null) {
				whereQuery.append(" and dt.parentDatasource='").append(datasourceDetail.getParentDatasource() + "'");
			} else {
				throw new Exception("ParentDatasource can not be Null");
			}
			selectQuery.append(whereQuery);
		}

		List<DatasourceDetail> resultDatasourceDetail = executeQuery(selectQuery.toString());

		if (!resultDatasourceDetail.isEmpty()) {
			datasourceDetail = resultDatasourceDetail.get(0);
		}
		return datasourceDetail;
	}

	@Override
	public DatasourceDetail fetchDataSourceAccordingToFilter(String samplingPeriod, String parentDataSource)
			throws Exception {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("from DatasourceDetail dt ");

		StringBuilder whereQuery = new StringBuilder();

		if (!StringUtils.isEmpty(samplingPeriod) && !StringUtils.isEmpty(parentDataSource)) {
			whereQuery.append("where ");
				whereQuery.append("dt.samplingPeriod='").append(samplingPeriod+ "'");
				whereQuery.append(" and dt.parentDatasource='").append(parentDataSource + "'");
			} else {
				throw new Exception("ParentDatasource can not be Null");
			}
			selectQuery.append(whereQuery);
		return executeQueryForUniqueRecord(selectQuery.toString());
	}

}

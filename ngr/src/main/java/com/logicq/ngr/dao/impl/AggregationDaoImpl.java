package com.logicq.ngr.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.AggregationDao;
import com.logicq.ngr.model.AggregationType;


@Repository
public class AggregationDaoImpl extends AbstractDAO<AggregationType> implements AggregationDao {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6413816344520503728L;

	@Override
	public AggregationType fetchAggregationAccordingToFilter(String type,String dataSourceName)
			throws Exception {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("from AggregationType at ");

		StringBuilder whereQuery = new StringBuilder();

		if (!StringUtils.isEmpty(type)) {
			whereQuery.append(" where ");
			whereQuery.append(" at.type='").append(type+ "'");				
		} else {
			throw new Exception("Type can not be Null");
		}
		if (!StringUtils.isEmpty(type)) {			
			whereQuery.append(" AND at.dataSourceName='").append(dataSourceName+ "'");				
		} else {
			throw new Exception("DataSourceName can not be Null");
		}
			selectQuery.append(whereQuery);
		return executeQueryForUniqueRecord(selectQuery.toString());
	}
	
}

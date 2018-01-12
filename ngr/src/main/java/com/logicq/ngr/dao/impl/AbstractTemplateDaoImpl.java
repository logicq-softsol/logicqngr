package com.logicq.ngr.dao.impl;

import org.springframework.stereotype.Repository;

import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.AbstractTemplateDao;
import com.logicq.ngr.model.AbstractTemplate;

@Repository
public class AbstractTemplateDaoImpl extends AbstractDAO<AbstractTemplate> implements AbstractTemplateDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2959705254353738749L;

	@Override
	public AbstractTemplate getAlarmTemplate(String type) throws Exception {
		StringBuilder queryString=new StringBuilder();
		queryString.append(" from AbstractTemplate at where at.type='"+type+"'");
		return executeQueryForUniqueRecord(queryString.toString()); 
	}

}

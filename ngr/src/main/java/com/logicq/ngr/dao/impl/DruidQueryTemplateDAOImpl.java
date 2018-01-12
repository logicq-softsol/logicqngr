package com.logicq.ngr.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.DruidQueryTemplateDAO;
import com.logicq.ngr.model.DruidQueryTemplate;

@Repository
public class DruidQueryTemplateDAOImpl extends AbstractDAO<DruidQueryTemplate> implements DruidQueryTemplateDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1882027680351030020L;

	@Override
	public List<DruidQueryTemplate> getAllTemplates() {
		return (List<DruidQueryTemplate>) loadClass(DruidQueryTemplate.class);
	}

	@Override
	public DruidQueryTemplate getTemplate(Long druidQueryId) {
		StringBuilder queryString=new StringBuilder();
		queryString.append(" from DruidQueryTemplate tp where tp.druidQueryId=" +druidQueryId);
		return executeQueryForUniqueRecord(queryString.toString());
	}

}
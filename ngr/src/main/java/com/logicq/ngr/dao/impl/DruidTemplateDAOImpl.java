package com.logicq.ngr.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.DruidTemplateDAO;
import com.logicq.ngr.model.DruidTemplate;

@Repository
public class DruidTemplateDAOImpl extends AbstractDAO<DruidTemplate> implements DruidTemplateDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3437677455334386891L;

	@Override
	public List<DruidTemplate> getAllTemplates() {
		return (List<DruidTemplate>) loadClass(DruidTemplate.class);
	}

	@Override
	public DruidTemplate getTemplate(String type) {
		StringBuilder queryString = new StringBuilder(" from DruidTemplate tp where tp.type='"+type+"' ");
		
		return executeQueryForUniqueRecord(queryString.toString());
	}

	

}
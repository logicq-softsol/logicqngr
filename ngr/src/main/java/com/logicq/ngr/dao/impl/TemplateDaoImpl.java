package com.logicq.ngr.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.TemplateDao;
import com.logicq.ngr.model.report.Template;

@Repository
public class TemplateDaoImpl extends AbstractDAO<Template> implements TemplateDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7707712905404765666L;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Template> getAllTemplates() {
		return (List<Template>) loadClass(Template.class);
	}

	@Override
	public List<Template> getTemplateAccordingtoTemplateType(String type) {
		StringBuilder queryString=new StringBuilder();
		queryString.append(" from Template tp where tp.templateType='"+type+"'");
		return executeQuery(queryString.toString());
	}

}
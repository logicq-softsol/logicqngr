package com.logicq.ngr.dao;

import java.util.List;

import com.logicq.ngr.model.report.Template;

public interface TemplateDao {

	public List<Template> getAllTemplates();
	
	List<Template> getTemplateAccordingtoTemplateType(String type);
	
}

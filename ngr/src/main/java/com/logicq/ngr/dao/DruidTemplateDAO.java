package com.logicq.ngr.dao;

import java.util.List;

import com.logicq.ngr.model.DruidTemplate;

public interface DruidTemplateDAO {

	List<DruidTemplate> getAllTemplates();

	DruidTemplate getTemplate(String type);
}

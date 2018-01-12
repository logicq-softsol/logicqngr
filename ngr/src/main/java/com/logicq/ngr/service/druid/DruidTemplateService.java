package com.logicq.ngr.service.druid;

import java.util.List;

import com.logicq.ngr.model.DruidTemplate;

public interface DruidTemplateService {

	List<DruidTemplate> getAllTemplates();

	DruidTemplate getTemplate(String type);
	
}
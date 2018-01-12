package com.logicq.ngr.service.druid;

import java.util.List;

import com.logicq.ngr.model.DruidQueryTemplate;

public interface DruidQueryTemplateService {

	List<DruidQueryTemplate> getAllTemplates();

	DruidQueryTemplate getTemplate(Long druidQueryId);
	
}
package com.logicq.ngr.dao;

import java.util.List;

import com.logicq.ngr.model.DruidQueryTemplate;

public interface DruidQueryTemplateDAO {

	List<DruidQueryTemplate> getAllTemplates();

	DruidQueryTemplate getTemplate(Long druidQueryId);
}

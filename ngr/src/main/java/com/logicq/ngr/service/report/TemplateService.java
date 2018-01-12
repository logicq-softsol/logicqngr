package com.logicq.ngr.service.report;

import java.util.List;

import com.logicq.ngr.model.report.Template;

public interface TemplateService {

	List<Template> getAllTemplate();
	List<Template> getTemplateAccordingtoTemplateType(String type);
	
}
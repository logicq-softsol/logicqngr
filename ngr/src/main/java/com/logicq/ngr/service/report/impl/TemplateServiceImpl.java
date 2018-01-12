package com.logicq.ngr.service.report.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.logicq.ngr.dao.TemplateDao;
import com.logicq.ngr.model.report.Template;
import com.logicq.ngr.service.report.TemplateService;

@Service
@Transactional
public class TemplateServiceImpl implements TemplateService {

	@Autowired
	TemplateDao templateDao;

	@Override
	public List<Template> getAllTemplate() {
		// templateDao.getAllTemplates();
		return (List<Template>) templateDao.getAllTemplates();
	}

	@Override
	public List<Template> getTemplateAccordingtoTemplateType(String type) {
		return templateDao.getTemplateAccordingtoTemplateType(type);
	}

}

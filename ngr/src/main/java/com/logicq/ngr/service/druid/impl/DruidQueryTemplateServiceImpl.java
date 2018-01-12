package com.logicq.ngr.service.druid.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.logicq.ngr.dao.DruidQueryTemplateDAO;
import com.logicq.ngr.model.DruidQueryTemplate;
import com.logicq.ngr.service.druid.DruidQueryTemplateService;

@Service
public class DruidQueryTemplateServiceImpl implements DruidQueryTemplateService {

	@Autowired
	DruidQueryTemplateDAO druidQueryTemplateDAO;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DruidQueryTemplate> getAllTemplates() {
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DruidQueryTemplate getTemplate(Long druidQueryId) {
		return druidQueryTemplateDAO.getTemplate(druidQueryId);
	}

}

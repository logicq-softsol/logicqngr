package com.logicq.ngr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.logicq.ngr.dao.AbstractTemplateDao;
import com.logicq.ngr.model.AbstractTemplate;
import com.logicq.ngr.service.report.AbstractTemplateService;

@Service
public class AbstractTemplateServiceImpl implements AbstractTemplateService{
	
	@Autowired
	AbstractTemplateDao abstractTemplateDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AbstractTemplate getAlarmTemplate(String type) throws Exception {
		return abstractTemplateDao.getAlarmTemplate(type);
	}

}

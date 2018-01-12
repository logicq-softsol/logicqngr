package com.logicq.ngr.service.report;

import com.logicq.ngr.model.AbstractTemplate;

public interface AbstractTemplateService {
	
	AbstractTemplate getAlarmTemplate(String type) throws Exception;

}

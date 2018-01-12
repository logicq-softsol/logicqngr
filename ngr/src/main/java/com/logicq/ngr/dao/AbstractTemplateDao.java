package com.logicq.ngr.dao;

import com.logicq.ngr.model.AbstractTemplate;

public interface AbstractTemplateDao {

	AbstractTemplate getAlarmTemplate(String type) throws Exception;
}

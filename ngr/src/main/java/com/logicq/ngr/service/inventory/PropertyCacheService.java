package com.logicq.ngr.service.inventory;

import java.util.List;
import java.util.Map;

import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.common.Attribute;
import com.logicq.ngr.model.inventory.EntityType;
import com.logicq.ngr.vo.FeatureDetailsVO;
import com.logicq.ngr.vo.ReportPropertyVO;

public interface PropertyCacheService {

	 Map<String, Object>  getPropertyValues( Map<String,Object> filterColumsValues,List<Attribute> properties, Attribute entity);

	 Map<String, String>  loadAttributesFromProperty();
	 
	 ReportPropertyVO loadDefaultPropertiesAccordingToUser(UserDetails userDetails,String entityType) throws Exception;
	 
	 FeatureDetailsVO getAllFeatureProperty();
	  
	 Map<String,Attribute> getFeatureSetup() throws Exception;
	 List<EntityType> getAllEntityTypeList();
	 List<Attribute> loadSpecificProperties(Attribute attribute,Attribute queryEntity,String fillterPatteren);
}

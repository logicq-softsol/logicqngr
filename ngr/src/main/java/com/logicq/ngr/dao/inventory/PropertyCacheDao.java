package com.logicq.ngr.dao.inventory;

import java.util.List;
import java.util.Map;

import com.logicq.ngr.model.common.Attribute;
import com.logicq.ngr.model.inventory.EntityType;

public interface PropertyCacheDao {

	List<?> loadProperty(Class claz);

	List<?> getPropertyValues(Map<String, Object> filterColumsValues, List<Attribute> properties, Attribute entity);

	List<?> loadAttributesForEntity();

	List<?> loadPropertyAccordingToType(Class claz, String type);

	Map<String, EntityType> loadEntityAsMap();

	List<?> loadDashboardSetups(String type);

	List<?> getAttributeForSpecificColum(String entityType, String columName, String fillterPattern);

	List<?> loadProperty();
}

package com.logicq.ngr.dao.inventory.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.logicq.ngr.controllers.LoadApplicationData;
import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.inventory.PropertyCacheDao;
import com.logicq.ngr.model.common.Attribute;
import com.logicq.ngr.model.inventory.EntityType;

@Repository
@Transactional
public class PropertyCacheDaoImpl extends AbstractDAO implements PropertyCacheDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7746292358868435998L;

	@Override
	public List<?> loadProperty(Class claz) {
		return loadClass(claz);
	}

	@Override
	public List<?> loadPropertyAccordingToType(Class claz, String type) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("from " + claz.getName());
		if (!StringUtils.isEmpty(type)) {
			selectQuery.append(" where  type= '" + type + "'");
		}
		return executeQuery(selectQuery.toString());
	}
	
	@Override
	public List<?> getPropertyValues(Map<String, Object> filterColumsValues, List<Attribute> properties,
			Attribute entity) {
		
		String selectColmns = null;
		List<?> list = null;
		
		Map<String, String> entityPropertyMap = LoadApplicationData.attributesMap;
		StringBuilder selectQuery = new StringBuilder();
		
		if (properties != null && !properties.isEmpty()) {
			for (Attribute attribute : properties) {
				if (selectColmns != null) {
					selectColmns = selectColmns + "," + entityPropertyMap.get(attribute.getId()) + " As "
							+ attribute.getId();
				} else {
					selectColmns = entityPropertyMap.get(attribute.getId()) + " As " + attribute.getId();
				}
			}

			selectQuery.append("Select " + selectColmns);
			selectQuery.append(" From " + entity.getName());

			if (filterColumsValues != null && !filterColumsValues.isEmpty()) {
				Set<String> keySet = filterColumsValues.keySet();
				Iterator<String> keySetIterator = keySet.iterator();

				while (keySetIterator.hasNext()) {
					String key = keySetIterator.next();

					if (!selectQuery.toString().contains(" Where")) {
						selectQuery.append(
								" Where " + entityPropertyMap.get(key) + "='" + filterColumsValues.get(key) + "'");
					} else {
						selectQuery.append(
								" And " + entityPropertyMap.get(key) + "='" + filterColumsValues.get(key) + "'");
					}

				}

			}

			list = executeQuery(selectQuery.toString());
		}

		return list;
	}

	@Override
	public List<?> loadAttributesForEntity() {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append(" From Property ");

		return executeQuery(selectQuery.toString());
	}

	@Override
	public Map<String, EntityType> loadEntityAsMap() {
		List<EntityType> entityList=(List<EntityType>) loadProperty(EntityType.class);
		Map<String, EntityType> entityMap=new HashMap<>();
		
		for(EntityType entity: entityList){
			entityMap.put(entity.getId().toLowerCase(), entity);
		}
		return entityMap;
	}
	
	@Override
	public List<?> loadDashboardSetups(String type) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("from  DashBoardSetup " );
		if (!StringUtils.isEmpty(type)) {
			selectQuery.append(" where  type in ('" + type + "','all')");
		}
		return executeQuery(selectQuery.toString());
	}
	
	
	@Override
	public List<?> getAttributeForSpecificColum(String entityType, String columName, String fillterPattern) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select distinct " + columName + " as value from " + entityType);
		if (!StringUtils.isEmpty(fillterPattern)) {
			selectQuery.append(" where " + columName + " like '" + fillterPattern + "%'");
		}
		return executeQuery(selectQuery.toString());
	}

	@Override
	public List<?> loadProperty() {
		String query="from Property";
		return executeQuery(query);
	}

}
package com.logicq.ngr.vo;

import java.util.List;

import com.logicq.ngr.model.common.Attribute;

public class EntityVO extends BaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3121783334075215707L;

	private String entityId;
	
	private String entityType;
	
	private String entityName;
	
	private String type;
	
	private List<Attribute>	 result;

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Attribute> getResult() {
		return result;
	}

	public void setResult(List<Attribute> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "EntityVO [entityId=" + entityId + ", entityType=" + entityType + ", entityName=" + entityName
				+ ", type=" + type + ", result=" + result + "]";
	}
	
}

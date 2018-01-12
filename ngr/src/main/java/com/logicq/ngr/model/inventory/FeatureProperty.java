package com.logicq.ngr.model.inventory;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "FEATURE_PROPERTY")
public class FeatureProperty implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3560491837461510375L;

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DISPLAYNAME")
	private String displayName;

	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "APPLICABLE_TO")
	private String applicableTo;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getApplicableTo() {
		return applicableTo;
	}

	public void setApplicableTo(String applicableTo) {
		this.applicableTo = applicableTo;
	}

	@Override
	public String toString() {
		return "FeatureProperty [id=" + id + ", name=" + name + ", displayName=" + displayName + ", type=" + type
				+ ", applicableTo=" + applicableTo + "]";
	}
}

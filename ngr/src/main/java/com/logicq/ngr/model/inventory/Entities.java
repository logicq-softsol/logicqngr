package com.logicq.ngr.model.inventory;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "ENTITY")
public class Entities implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4827038810935811456L;
	
	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DISPLAY_NAME")
	private String displayName;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "APPLICABLE_DATASOURCE")
	private String applicableDataSource;

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

	public String getApplicableDataSource() {
		return applicableDataSource;
	}

	public void setApplicableDataSource(String applicableDataSource) {
		this.applicableDataSource = applicableDataSource;
	}

	@Override
	public String toString() {
		return "Entities [id=" + id + ", name=" + name + ", displayName=" + displayName
				+ ", type=" + type + "]";
	}
}

package com.logicq.ngr.model.inventory;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "FEATURE_SETUP")
public class FeatureSetup implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9184588111241370255L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DISPLAY_NAME")
	private String displayName;

	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "APPLICABLE_TO")
	private String applicableto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getApplicableto() {
		return applicableto;
	}

	public void setApplicableto(String applicableto) {
		this.applicableto = applicableto;
	}

	
	@Override
	public String toString() {
		return "DashBoardSetup [id=" + id + ", name=" + name + ", displayName=" + displayName + ", type=" + type
				+ ", applicableto=" + applicableto + "]";
	}
	
}

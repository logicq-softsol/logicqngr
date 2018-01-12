package com.logicq.ngr.model;

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
@Table(name = "DATA_SOURCE_TYPE")
public class DataSourceType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2903746181299331968L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "KEYWORDS")
	private String keywords;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Override
	public String toString() {
		return "DataSourceType [id=" + id + ", type=" + type + ", name=" + name + ", keywords=" + keywords + "]";
	}
}

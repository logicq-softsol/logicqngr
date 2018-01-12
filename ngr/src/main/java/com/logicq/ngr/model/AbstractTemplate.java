package com.logicq.ngr.model;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "ABSTRACT_TEMPLATE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AbstractTemplate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6916936849576386865L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "TYPE")
	private String type;
	
	@Lob
	@Column(name = "JSON",nullable = false)
	private byte[] json;

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

	public byte[] getJson() {
		return json;
	}

	public void setJson(byte[] json) {
		this.json = json;
	}
	
	@Override
	public String toString() {
		return "AbstractTemplate [id=" + id + ", type=" + type + ", json=" + Arrays.toString(json) + "]";
	}
	
	
}

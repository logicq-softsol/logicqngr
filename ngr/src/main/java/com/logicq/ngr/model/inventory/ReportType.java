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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "REPORT_TYPE")
public class ReportType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -690433634123935521L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "TYPE")
	private String applicabletype;
	
	@Column(name = "SUBTYPE")
	private String subType;
	
	@Column(name = "IMAGE_PATH")
	private String imagePath;

	@Column(name = "ENTITY_TYPE")
	private String type;

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

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getApplicabletype() {
		return applicabletype;
	}

	public void setApplicabletype(String applicabletype) {
		this.applicabletype = applicabletype;
	}

	@Override
	public String toString() {
		return "ReportType [id=" + id + ", applicabletype=" + applicabletype + ", subType=" + subType + ", imagePath="
				+ imagePath + ", type=" + type + "]";
	}


}

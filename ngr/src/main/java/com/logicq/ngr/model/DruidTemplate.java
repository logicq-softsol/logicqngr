package com.logicq.ngr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 * @author 611022088
 *
 */
@Entity
@Table(name = "DRUID_TEMPLATE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DruidTemplate implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1452871079026410053L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "SUBTYPE")
	private String subtype;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DRUID_QUERY_ID", nullable = false)
	private DruidQueryTemplate druidQueryTemplate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public DruidQueryTemplate getDruidQueryTemplate() {
		return druidQueryTemplate;
	}

	public void setDruidQueryTemplate(DruidQueryTemplate druidQueryTemplate) {
		this.druidQueryTemplate = druidQueryTemplate;
	}

	@Override
	public String toString() {
		return "DruidTemplate [id=" + id + ", type=" + type + ", subtype=" + subtype + ", druidQueryTemplate="
				+ druidQueryTemplate + "]";
	}

}

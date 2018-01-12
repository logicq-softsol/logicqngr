package com.logicq.ngr.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Druid query json templates such as TopN , groupBy, timeseries, select
 * @author 611022088
 *
 */

@Entity
@Table(name = "DRUID_QUERY_TEMPLATE", uniqueConstraints = @UniqueConstraint(columnNames = {"QUERY_TYPE"}))
public class DruidQueryTemplate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5609246595030411719L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "DRUID_QUERY_ID")
	private Long druidQueryId;

	@Column(name = "QUERY_TYPE", unique = true, nullable = false)
	private String queryType;
	
	@Lob
	@Column(name = "QUERY_JSON",nullable = false)
	private byte[] queryJson;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "druidQueryTemplate")
	private List<DruidTemplate> druidTemplates;

	public Long getDruidQueryId() {
		return druidQueryId;
	}

	public void setDruidQueryId(Long druidQueryId) {
		this.druidQueryId = druidQueryId;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public byte[] getQueryJson() {
		return queryJson;
	}

	public void setQueryJson(byte[] queryJson) {
		this.queryJson = queryJson;
	}

	public List<DruidTemplate> getDruidTemplates() {
		return druidTemplates;
	}

	public void setDruidTemplates(List<DruidTemplate> druidTemplates) {
		this.druidTemplates = druidTemplates;
	}

	@Override
	public String toString() {
		return "DruidQueryTemplate [druidQueryId=" + druidQueryId + ", queryType=" + queryType + ", queryJson="
				+ Arrays.toString(queryJson) + "]";
	}
	
}

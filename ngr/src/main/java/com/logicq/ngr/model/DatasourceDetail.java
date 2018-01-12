package com.logicq.ngr.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "DATASOURCE_DETAILS")
public class DatasourceDetail  implements Serializable{
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="SAMPLING_PERIOD")
	private String samplingPeriod;
	
	@Column(name="NAME")
	private String datasourceName;
	
	@Column(name="PARENT")
	private String parentDatasource;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSamplingPeriod() {
		return samplingPeriod;
	}
	public void setSamplingPeriod(String samplingPeriod) {
		this.samplingPeriod = samplingPeriod;
	}
	public String getDatasourceName() {
		return datasourceName;
	}
	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}
	public String getParentDatasource() {
		return parentDatasource;
	}
	public void setParentDatasource(String parentDatasource) {
		this.parentDatasource = parentDatasource;
	}
	
	@Override
	public String toString() {
		return "DatasourceDetail [id=" + id + ", samplingPeriod=" + samplingPeriod + ", datasourceName="
				+ datasourceName + ", parentDatasource=" + parentDatasource 
				+ "]";
	}
	
	
		
}

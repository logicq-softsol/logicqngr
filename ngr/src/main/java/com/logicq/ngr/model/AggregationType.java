package com.logicq.ngr.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
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

@Entity
@Table(name = "AGGREGATION_TYPE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AggregationType  implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "DRUID_AGGREGATION_TYPE")
	private String druidAggregationType;
	
	@Column(name = "MYSQL_AGGREGATION_TYPE")
	private String msqlAggregationType;	
	
	@Column(name = "DATASOURCE_NAME")
	private String dataSourceName;	
	
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


	public String getDruidAggregationType() {
		return druidAggregationType;
	}


	public void setDruidAggregationType(String druidAggregationType) {
		this.druidAggregationType = druidAggregationType;
	}


	public String getMsqlAggregationType() {
		return msqlAggregationType;
	}


	public void setMsqlAggregationType(String msqlAggregationType) {
		this.msqlAggregationType = msqlAggregationType;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	@Override
	public String toString() {
		return "AggregationType [id=" + id + ", type=" + type + ", druidAggregationType=" + druidAggregationType
				+ ", msqlAggregationType=" + msqlAggregationType + ", dataSourceName=" + dataSourceName + "]";
	}	

	
}

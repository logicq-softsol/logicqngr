package com.logicq.ngr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 * @author 611022088
 *
 */

@Entity
@Table(name = "GRANULARIY_METADATA", uniqueConstraints = @UniqueConstraint(columnNames = {"SAMPLING_PERIOD"}))
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GranularityMetadata implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6316639347324141462L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "SAMPLING_PERIOD", unique = true, nullable = false)
	private String samplingPeriod;
	
	@Column(name = "GRANULARITY",nullable = false)
	private String granularity;

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

	public String getGranularity() {
		return granularity;
	}

	public void setGranularity(String granularity) {
		this.granularity = granularity;
	}

	@Override
	public String toString() {
		return "GranularityMetadata [id=" + id + ", samplingPeriod=" + samplingPeriod + ", granularity=" + granularity
				+ "]";
	}

}

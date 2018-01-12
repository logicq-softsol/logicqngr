package com.logicq.ngr.model.druid.request;

import java.io.Serializable;

import com.logicq.ngr.model.common.Configuration;
import com.logicq.ngr.model.common.Pagination;
import com.logicq.ngr.model.common.Rules;

public class ReportRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4855407669924208756L;

	private Configuration configuration;
	private Rules filter;
	private String samplingPeriod;
	private String intervals;
	private Pagination pagination;

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public Rules getFilter() {
		return filter;
	}

	public void setFilter(Rules filter) {
		this.filter = filter;
	}

	public String getSamplingPeriod() {
		return samplingPeriod;
	}

	public void setSamplingPeriod(String samplingPeriod) {
		this.samplingPeriod = samplingPeriod;
	}

	public String getIntervals() {
		return intervals;
	}

	public void setIntervals(String intervals) {
		this.intervals = intervals;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	@Override
	public String toString() {
		return "ReportRequest [configuration=" + configuration + ", filter=" + filter + ", samplingPeriod="
				+ samplingPeriod + ", intervals=" + intervals + ", pagination=" + pagination + "]";
	}

}

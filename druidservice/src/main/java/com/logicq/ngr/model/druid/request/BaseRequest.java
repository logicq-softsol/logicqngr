package com.logicq.ngr.model.druid.request;

import java.io.Serializable;
import java.util.Arrays;

import com.logicq.ngr.model.common.Rules;
import com.logicq.ngr.model.druid.common.Filter;

public class BaseRequest implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 7927897288192552829L;

	private int site;
	private String[] device;
	private String granularity;
	private String intervals;
	private Filter filter;
	private String dataSourceName;
	private Rules filterExpression;

	public int getSite() {
		return site;
	}

	public void setSite(int site) {
		this.site = site;
	}

	public String[] getDevice() {
		return device;
	}

	public void setDevice(String[] device) {
		this.device = device;
	}

	public String getGranularity() {
		return granularity;
	}

	public void setGranularity(String granularity) {
		this.granularity = granularity;
	}

	public String getIntervals() {
		return intervals;
	}

	public void setIntervals(String intervals) {
		this.intervals = intervals;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public Rules getFilterExpression() {
		return filterExpression;
	}

	public void setFilterExpression(Rules filterExpression) {
		this.filterExpression = filterExpression;
	}

	@Override
	public String toString() {
		return "BaseRequest [site=" + site + ", device=" + Arrays.toString(device) + ", granularity=" + granularity
				+ ", intervals=" + intervals + ", filter=" + filter + ", dataSourceName=" + dataSourceName
				+ ", filterExpression=" + filterExpression + "]";
	}



}
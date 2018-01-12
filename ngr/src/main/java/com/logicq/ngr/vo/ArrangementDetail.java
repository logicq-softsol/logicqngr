package com.logicq.ngr.vo;

import java.io.Serializable;
import java.util.List;

public class ArrangementDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6944492388360191797L;
	
	private int bag;
	
	private List<ArrangementReportDetail> reports;

	public int getBag() {
		return bag;
	}

	public void setBag(int bag) {
		this.bag = bag;
	}

	public List<ArrangementReportDetail> getReports() {
		return reports;
	}

	public void setReports(List<ArrangementReportDetail> reports) {
		this.reports = reports;
	}

	@Override
	public String toString() {
		return "ArrangementDetail [bag=" + bag + ", reports=" + reports + "]";
	}
}

package com.logicq.ngr.vo;

import java.io.Serializable;
import java.util.List;

public class ReportTypeVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8932410207171861260L;
	
	private int id;
	private String name;
	private String imagePath;
	private List<ReportSubTypeVO> reportSubType;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public List<ReportSubTypeVO> getReportSubType() {
		return reportSubType;
	}
	public void setReportSubType(List<ReportSubTypeVO> reportSubType) {
		this.reportSubType = reportSubType;
	}
	@Override
	public String toString() {
		return "ReportTypeVO [id=" + id + ", name=" + name + ", imagePath=" + imagePath + ", reportSubType="
				+ reportSubType + "]";
	}
}

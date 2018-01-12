package com.logicq.ngr.vo;

public class FeatureSetupVO {
	
	private Long id;
	private String name;
	private String displayName;
	private String type;
	private String applicableto;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getApplicableto() {
		return applicableto;
	}
	public void setApplicableto(String applicableto) {
		this.applicableto = applicableto;
	}
	@Override
	public String toString() {
		return "DashBoardSetupVO [id=" + id + ", name=" + name + ", displayName=" + displayName + ", type=" + type
				+ ", applicableto=" + applicableto + "]";
	}
	
	

}

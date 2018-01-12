package com.logicq.ngr.vo;

public class ReportPackDetailsVO  extends MessageVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7310259794941880910L;

	private String name;

	private String reportpackId;

	private Long profileId;
	
	private String destinationReportpackId;
	
	private String sourceReportpackId;
	
	private String reportpackTemplateId;

	private String userName;

	private String type;

	private String viewType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReportpackId() {
		return reportpackId;
	}

	public void setReportpackId(String reportpackId) {
		this.reportpackId = reportpackId;
	}

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	public String getDestinationReportpackId() {
		return destinationReportpackId;
	}

	public void setDestinationReportpackId(String destinationReportpackId) {
		this.destinationReportpackId = destinationReportpackId;
	}

	public String getSourceReportpackId() {
		return sourceReportpackId;
	}

	public void setSourceReportpackId(String sourceReportpackId) {
		this.sourceReportpackId = sourceReportpackId;
	}

	public String getReportpackTemplateId() {
		return reportpackTemplateId;
	}

	public void setReportpackTemplateId(String reportpackTemplateId) {
		this.reportpackTemplateId = reportpackTemplateId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	@Override
	public String toString() {
		return "ReportPackDetailsVO [name=" + name + ", reportpackId=" + reportpackId + ", profileId=" + profileId
				+ ", destinationReportpackId=" + destinationReportpackId + ", sourceReportpackId=" + sourceReportpackId
				+ ", reportpackTemplateId=" + reportpackTemplateId + ", userName=" + userName + ", type=" + type
				+ ", viewType=" + viewType + "]";
	}
}

package com.logicq.ngr.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.logicq.ngr.model.common.Attribute;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6533306060228257101L;

	private Long id;
	private String name;
	private String userName;
	private String role;
	private List<UserVO> usersList;
	
	//This fiedl is added to take the input from UI. This will contain the list of ReportPackIds
	private Set<String> reportPackList;
	
	//This field is added to set the response in seperate List instead of in Features
	private List<Attribute> reportPackListResponse;

	private FeatureDetailsVO features;
	
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<UserVO> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<UserVO> usersList) {
		this.usersList = usersList;
	}

	public FeatureDetailsVO getFeatures() {
		return features;
	}

	public void setFeatures(FeatureDetailsVO features) {
		this.features = features;
	}
	
	public List<Attribute> getReportPackListResponse() {
		return reportPackListResponse;
	}

	public void setReportPackListResponse(List<Attribute> reportPackListResponse) {
		this.reportPackListResponse = reportPackListResponse;
	}

	
	public Set<String> getReportPackList() {
		return reportPackList;
	}

	public void setReportPackList(Set<String> reportPackList) {
		this.reportPackList = reportPackList;
	}

	@Override
	public String toString() {
		return "ProfileVO [id=" + id + ", name=" + name + ", userName=" + userName + ", role=" + role + ", usersList="
				+ usersList + ", reportPackList=" + reportPackList + ", reportPackListResponse="
				+ reportPackListResponse + ", features=" + features + "]";
	}

	
}
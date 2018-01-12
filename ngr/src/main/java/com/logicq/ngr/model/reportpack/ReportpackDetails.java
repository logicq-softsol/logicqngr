package com.logicq.ngr.model.reportpack;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logicq.ngr.model.admin.Profile;

@Entity
@Table(name = "REPORTPACK_DETAILS")
public class ReportpackDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2503178898522759071L;

	@Column(name = "NAME", nullable = true)
	private String name;

	@Column(name = "TYPE", nullable = true)
	private String type;

	@Column(name = "VIEW_TYPE", nullable = true)
	private String viewType;
	
	@Id
	@Column(name = "REPORTPACK_ID", nullable = false)
	private String reportpackId;

	@JsonIgnore
	@ManyToMany(mappedBy = "reportpacks")
	private Set<Profile> profiles=new HashSet<>();
	
	@Column(name = "DELETED", nullable = false, columnDefinition = "boolean default false")
	private boolean deleted;
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getReportpackId() {
		return reportpackId;
	}

	public void setReportpackId(String reportpackId) {
		this.reportpackId = reportpackId;
	}

	public Set<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(Set<Profile> profiles) {
		this.profiles = profiles;
	}

	@Override
	public String toString() {
		return "ReportpackDetails [name=" + name + ", type=" + type + ", viewType=" + viewType + ", reportpackId="
				+ reportpackId + ", profiles=" + profiles + ", deleted=" + deleted + "]";
	}
}

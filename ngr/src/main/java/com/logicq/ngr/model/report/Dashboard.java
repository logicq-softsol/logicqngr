package com.logicq.ngr.model.report;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.logicq.ngr.model.UserDetails;

@Entity
@Table(name = "DASHBOARD_DETAILS")
public class Dashboard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6272470889206863748L;

	@Id
	@Column(name = "ID", nullable = false)
	private String dashboardId;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "TYPE", nullable = false)
	private String type;

	@Column(name = "VIEW_TYPE")
	private String viewType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private UserDetails userDetails;

	@Column(name = "DELETED", nullable = false, columnDefinition = "boolean default false")
	private boolean deleted;

	@Lob
	@Column(name = "ARRANGEMENT")
	private byte[] arrangement;

	public byte[] getArrangement() {
		return arrangement;
	}

	public void setArrangement(byte[] arrangement) {
		this.arrangement = arrangement;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(String dashboardId) {
		this.dashboardId = dashboardId;
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

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	@Override
	public String toString() {
		return "Dashboard [dashboardId=" + dashboardId + ", name=" + name + ", type=" + type + ", viewType=" + viewType
				+ ", arrangement=" + Arrays.toString(arrangement) + "]";
	}
}

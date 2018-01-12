package com.logicq.ngr.model.report;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.logicq.ngr.model.UserDetails;



@Embeddable
public class UserTemplateKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1092377783748206877L;


	@Column(name = "USER_TEMPLATE_ID", nullable = false)
	private String userTemplateId;

	@Column(name = "DASHBOARD_ID", nullable = false)
	private String dashboardId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private UserDetails userDetails;

	

	public String getUserTemplateId() {
		return userTemplateId;
	}

	public void setUserTemplateId(String userTemplateId) {
		this.userTemplateId = userTemplateId;
	}

	public String getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(String dashboardId) {
		this.dashboardId = dashboardId;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	@Override
	public String toString() {
		return "UserTemplateKey [ userTemplateId=" + userTemplateId + ", dashboardId="
				+ dashboardId + ", userDetails=" + userDetails + "]";
	}

}

package com.logicq.ngr.model.report;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class UserReport extends BaseReport implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6105208486613507558L;
	
	private String userName;
	
	private List<DashboardReport> dashboardReports;
	
	private String[] roles;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<DashboardReport> getDashboardReports() {
		return dashboardReports;
	}

	public void setDashboardReports(List<DashboardReport> dashboardReports) {
		this.dashboardReports = dashboardReports;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserReport [userName=" + userName + ", dashboardReports=" + dashboardReports + ", roles="
				+ Arrays.toString(roles) + "]";
	}
}

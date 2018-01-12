package com.logicq.ngr.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DashBoardVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3490264828495348401L;

	private String dashboardId;
	private String userName;
	private String dashBoardName;
	private String dashBoardType;
	private String dashBoardViewType;
	private List<Map<String,Object>> arrangement;

	public List<Map<String, Object>> getArrangement() {
		return arrangement;
	}

	public void setArrangement(List<Map<String, Object>> arrangement) {
		this.arrangement = arrangement;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDashBoardName() {
		return dashBoardName;
	}

	public void setDashBoardName(String dashBoardName) {
		this.dashBoardName = dashBoardName;
	}

	public String getDashBoardType() {
		return dashBoardType;
	}

	public void setDashBoardType(String dashBoardType) {
		this.dashBoardType = dashBoardType;
	}

	public String getDashBoardViewType() {
		return dashBoardViewType;
	}

	public void setDashBoardViewType(String dashBoardViewType) {
		this.dashBoardViewType = dashBoardViewType;
	}

	public String getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(String dashboardId) {
		this.dashboardId = dashboardId;
	}

	@Override
	public String toString() {
		return "DashBoardVO [dashboardId=" + dashboardId + ", userName=" + userName + ", dashBoardName=" + dashBoardName
				+ ", dashBoardType=" + dashBoardType + ", dashBoardViewType=" + dashBoardViewType + ", arrangement="
				+ arrangement + "]";
	}
}
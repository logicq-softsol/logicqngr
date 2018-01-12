package com.logicq.ngr.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleUserVo {

	private String roleType;
	private int totalUsers;
	private int enabledUsers;
	private int disabledUsers;

	public Integer getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(Integer totalUsers) {
		this.totalUsers = totalUsers;
	}

	public Integer getEnabledUsers() {
		return enabledUsers;
	}

	public void setEnabledUsers(Integer enabledUsers) {
		this.enabledUsers = enabledUsers;
	}

	public Integer getDisabledUsers() {
		return disabledUsers;
	}

	public void setDisabledUsers(Integer disabledUsers) {
		this.disabledUsers = disabledUsers;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	@Override
	public String toString() {
		return "RoleUserVo [totalUsers=" + totalUsers + ", enabledUsers=" + enabledUsers + ", disabledUsers="
				+ disabledUsers + ", roleType=" + roleType + "]";
	}

}

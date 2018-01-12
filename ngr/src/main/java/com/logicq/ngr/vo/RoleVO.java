package com.logicq.ngr.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleVO {

	private long roleId;
	private String name;
	private List<UserVO> userlist;
	
	public List<UserVO> getUserlist() {
		return userlist;
	}
	public void setUserlist(List<UserVO> userlist) {
		this.userlist = userlist;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "RoleVO [roleId=" + roleId + ", name=" + name + ", userlist=" + userlist + "]";
	}
	
}

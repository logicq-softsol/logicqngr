package com.logicq.ngr.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileUserVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2929055932156352526L;
	
	private ProfileVO profileVO;
	private RoleUserVo roleUserVO;
	
	
	public RoleUserVo getRoleUserVO() {
		return roleUserVO;
	}
	public void setRoleUserVO(RoleUserVo roleUserVO) {
		this.roleUserVO = roleUserVO;
	}
	public ProfileVO getProfileVO() {
		return profileVO;
	}
	public void setProfileVO(ProfileVO profileVO) {
		this.profileVO = profileVO;
	}
	@Override
	public String toString() {
		return "ProfileUserVO [profileVO=" + profileVO + ", roleUserVO=" + roleUserVO + "]";
	}
	
}

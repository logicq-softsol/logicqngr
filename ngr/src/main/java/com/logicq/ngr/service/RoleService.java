package com.logicq.ngr.service;

import java.util.List;

import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.admin.Role;
import com.logicq.ngr.vo.RoleVO;

public interface RoleService {
	List<Role> getAllRoles();

	void addRole(Role role);

	void editRole(Role role);

	void deleteRole(Role role);

	Role getRole(String role);
	
	Role getRole(Role role);
	
	String[] getAllowedRoles(UserDetails userDetails);
	
	Role getRoleByName(RoleVO roleVO) throws Exception;
	
	List<Role> getRoles(String allowedRoles);
}
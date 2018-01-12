package com.logicq.ngr.dao;

import java.util.List;

import com.logicq.ngr.model.admin.Role;
import com.logicq.ngr.vo.RoleVO;

public interface RoleDao {
	List<Role> getAllRoles();

	void addRole(Role role);

	void editRole(Role role);

	void deleteRole(Role role);
	
	Role getRole(String role);
	
	Role getRole(Role role);
	
	Role getRoleByName(RoleVO roleVO) throws Exception;
	
	Role getRoleId(String roleName);
	
	List<Role> getRoles(String allowedRoles);

}
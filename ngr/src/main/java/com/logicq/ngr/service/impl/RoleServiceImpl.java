package com.logicq.ngr.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logicq.ngr.dao.RoleDao;
import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.admin.Role;
import com.logicq.ngr.service.RoleService;
import com.logicq.ngr.vo.RoleVO;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleDao roleDao;

	@Override
	public List<Role> getAllRoles() {
		return roleDao.getAllRoles();
	}

	@Override
	public void addRole(Role role) {
		roleDao.addRole(role);
	}

	@Override
	public void editRole(Role role) {
		roleDao.editRole(role);
	}

	@Override
	public void deleteRole(Role role) {
		roleDao.deleteRole(role);
	}

	@Override
	public Role getRole(String role) {

		return roleDao.getRole(role);
	}

	@Override
	public Role getRoleByName(RoleVO roleVO) throws Exception {
		return roleDao.getRoleByName(roleVO);
	}

	@Override
	public Role getRole(Role role) {
		
		return roleDao.getRole(role);
	}

	@Override
	public String[] getAllowedRoles(UserDetails userDetails) {
		Role role=new Role();
		role=getRole(userDetails.getRole());
		if(role.getAllowedRoles()!=null && !role.getAllowedRoles().isEmpty()){
			return role.getAllowedRoles().split(",");
		}
		else{
			return null;
		}
	}

	@Override
	public List<Role> getRoles(String allowedRoles) {
		return roleDao.getRoles(allowedRoles);
	}
}
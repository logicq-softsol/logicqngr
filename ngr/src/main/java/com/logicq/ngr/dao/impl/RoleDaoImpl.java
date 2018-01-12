package com.logicq.ngr.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.RoleDao;
import com.logicq.ngr.model.admin.Role;
import com.logicq.ngr.vo.RoleVO;

@Repository
public class RoleDaoImpl extends AbstractDAO<Role> implements RoleDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4778222245754266001L;

	// Plase change this method name to get Role
	@Override
	public List<Role> getAllRoles() {
		return (List<Role>) loadClass(Role.class);
	}

	@Override
	public void addRole(Role role) {
		save(role);
	}

	@Override
	public void editRole(Role role) {
		update(role);
	}

	@Override
	public void deleteRole(Role role) {
		delete(role);
	}

/*	@Override
	public Role getUserRole(int roleId) {
		return getRecordById(Role.class, roleId);
	public Role getRole(String role) {
		return executeQueryForUniqueRecord(" from Role RL where RL.name='"+role+"'");
	}*/

	@Override
	public Role getRoleByName(RoleVO roleVO) throws Exception {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("from Role r ");

		StringBuilder whereQuery = new StringBuilder();

		if (null != roleVO) {
			whereQuery.append("where ");
			if (!StringUtils.isEmpty(roleVO.getName())) {
				whereQuery.append("r.name='").append(roleVO.getName() + "'");
			}else {
				throw new Exception("Role Name can not be Null");
			} 
		}
		selectQuery.append(whereQuery);
		Role role=executeQueryForUniqueRecord(selectQuery.toString());
		return role;
	}

	@Override
	public Role getRole(String role) {
		StringBuilder selectQuery = new StringBuilder();
		StringBuilder whereQuery = new StringBuilder();
		selectQuery.append("from Role r ");
		whereQuery.append("where ");
		if (!StringUtils.isEmpty(role)) {
			whereQuery.append("r.name='").append(role + "'");
	}
		selectQuery.append(whereQuery);
		return executeQueryForUniqueRecord(selectQuery.toString());
}

	@Override
	public Role getRole(Role role) {
		return getRecordById(Role.class,role.getRoleId());
	}

	@Override
	public Role getRoleId(String roleName) {
		String query = " select r.roleId from Role r where r.name='"+roleName+"'";
		return executeQueryForUniqueRecord(query);
	}
	
	@Override
	public List<Role> getRoles(String allowedRoles) {
		StringBuilder selectQuery = new StringBuilder();
		StringBuilder whereQuery = new StringBuilder();
		selectQuery.append("from Role r ");
		whereQuery.append(" where");
		String[] roles = { "" };
		if (allowedRoles.contains(",")) {
			roles = allowedRoles.split(",");
		} else {
			roles[0] = allowedRoles;
		}
		if (roles.length > 0) {
			whereQuery.append(" r.name in( ");
			for (int i = 0; i < roles.length; i++) {
				whereQuery.append("'" + roles[i] + "'");
				if (!(i == roles.length - 1)) {
					whereQuery.append(",");
				}
			}
			whereQuery.append(")");
			selectQuery.append(whereQuery);
		}
		return executeQuery(selectQuery.toString());
	}
}

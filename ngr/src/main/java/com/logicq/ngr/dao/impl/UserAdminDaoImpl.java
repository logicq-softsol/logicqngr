package com.logicq.ngr.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.UserAdminDao;
import com.logicq.ngr.model.UserDetails;

/**
 * 
 * @author 611164825
 *
 */
@Transactional
@Repository
public class UserAdminDaoImpl extends AbstractDAO<UserDetails> implements UserAdminDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4077087466629470837L;

	/**
	 * This method is used to add new user into USER_DETAILS Table of MySql
	 * Database
	 */
	public void addUser(UserDetails user) {
		save((UserDetails) user);
	}

	/**
	 * This method is used to update the user details in the database
	 */
	public UserDetails updateUser(UserDetails user) {
		update(user);
		return user;
	}
	
	

	/**
	 * This method is used to update the user details in the database
	 */
	public void updateUserDetails(UserDetails user) {
		update(user);
	}

	/**
	 * This method is used to delete user from database
	 */
	public void deleteUser(UserDetails user) {
		delete((UserDetails) getRecordById(UserDetails.class, user.getId()));
	}

	/**
	 * This method is used to fetch all the users from the database
	 */

	@SuppressWarnings("unchecked")
	public List<UserDetails> getAllUser(String allowedRole) {

		if(allowedRole.contains(",")){
			return getListOfAdminAndUser();
		}
		else{
			return getListOfUser();
		}
	}

	/**
	 * This method is used to fetch the user from the database using userId
	 */
	@Override
	public UserDetails getUser(String userName) {
		String query = "from UserDetails ud where ud.userName='" + userName + "'";
		return executeQueryForUniqueRecord(query);
	}

	/**
	 * This method is used to get active users based on role
	 */
	@Override
	public List<UserDetails> getActiveUsers(String role) {
		StringBuilder selectQuery = new StringBuilder();
		StringBuilder andQuery = new StringBuilder();
		selectQuery.append("from UserDetails ud ");
		selectQuery.append("where ");
		selectQuery.append("ud.isActive=1 ");
		String[] roles = { "" };
		if (role.contains(",")) {
			roles = role.split(",");
		} else {
			roles[0] = role;
		}
		if (roles.length > 0) {
			andQuery.append("and ud.role.name in(");
			for (int i = 0; i < roles.length; i++) {
				andQuery.append("'" + roles[i] + "'");
				if (!(i == roles.length - 1)) {
					andQuery.append(",");
				}
			}
			andQuery.append(")");
			selectQuery.append(andQuery);
		}
		return executeQuery(selectQuery.toString());
	}

	/**
	 * This method is used to get all users when admin gets login.
	 */
	@Override
	public List<UserDetails> getListOfUser() {
		String query = "FROM UserDetails ud where ud.role.name='user'";
		return executeQuery(query);
	}

	/**
	 * This method is used to get all users when admin gets login.
	 */
	@Override
	public List<UserDetails> getListOfAdminAndUser() {
		String query = "FROM UserDetails ud where ud.role.name='admin' or ud.role.name='user'";
		return executeQuery(query);
	}

	@Override
	public List<UserDetails> getAllUsers() {
		String query = " from UserDetails ud";
		return executeQuery(query);
	}

	@Override
	public List<UserDetails> getUsers(long profileId) {
		String query = " from UserDetails ud where ud.profile.profileId=" + profileId + "";
		return executeQuery(query);
	}

	@Override
	public void enableDisableUser(UserDetails userDetails) {
		String query = "from UserDetails ud where ud.id=" + userDetails.getId();
		UserDetails dbUser = executeQueryForUniqueRecord(query);
		dbUser.setEnabled(userDetails.getEnabled());
		update(dbUser);
	}

	@Transactional
	@Override
	public List<UserDetails> getMarkAsDeletedUser() {
		StringBuilder query = new StringBuilder();
		query.append("from UserDetails ud where ud.deleted = true OR modified = true");
		return executeQuery(query.toString());
	}

	@Override
	public void deleteUsers(List<UserDetails> deletedUserList) {
		deleteList(deletedUserList);
	}

	@Override
	public void markUsersAsModified(List<UserDetails> userdetails) {
		 updateList(userdetails);
	}

	@Override
	public void updateUserdetails(UserDetails userDetails) {
		update(userDetails);
	}
}
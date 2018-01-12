package com.logicq.ngr.dao;

import java.util.List;

import com.logicq.ngr.model.UserDetails;

public interface UserAdminDao {

	void addUser(UserDetails user);

	UserDetails updateUser(UserDetails user);

	void update(UserDetails user);

	void deleteUser(UserDetails user);

	List<UserDetails> getAllUser(String userRole);

	List<UserDetails> getUsers(long profileId);

	List<UserDetails> getAllUsers();

	UserDetails getUser(String userName);

	List<UserDetails> getListOfUser();

	List<UserDetails> getActiveUsers(String role);

	void enableDisableUser(UserDetails userVo);

	List<UserDetails> getListOfAdminAndUser();

	List<UserDetails> getMarkAsDeletedUser();

	void deleteUsers(List<UserDetails> deletedUserList);

	void markUsersAsModified(List<UserDetails> modifiedUserList);

	void updateUserdetails(UserDetails userDetails);

}

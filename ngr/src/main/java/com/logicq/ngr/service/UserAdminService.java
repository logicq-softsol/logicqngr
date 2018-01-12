package com.logicq.ngr.service;

import java.util.List;

import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.admin.Role;
import com.logicq.ngr.vo.ProfileUserVO;
import com.logicq.ngr.vo.RoleUserVo;
import com.logicq.ngr.vo.UserDetailVO;
import com.logicq.ngr.vo.UserVO;

public interface UserAdminService {

	UserDetails addUser(UserDetails user) throws Exception;

	List<UserDetails> getAllUser(String userRole);

	List<UserDetails> getUsers(long profileId);

	List<UserDetails> getAllUsers();

	UserDetails getUser(String userName);

	List<UserVO> getActiveUsers(String role);

	List<ProfileUserVO> getProfilesAccordingToRole(Role role);

	List<RoleUserVo> getUsersAccordingToAllowedRole(String allowedRole);

	void enableDisableUser(UserDetails userVo);

	UserVO getUserInformation(String userName);

	UserVO updateUser(UserVO userVo, String userName) throws Exception;

	UserDetailVO updateUser(UserDetailVO user) throws Exception;

	void updateUserDetails(UserDetails user) throws Exception;
	
	void markAsUserDelete(String username) throws Exception;
	void handleMarkDelete();

	void markUsersAsModified(List<UserDetails> userList);

	
}

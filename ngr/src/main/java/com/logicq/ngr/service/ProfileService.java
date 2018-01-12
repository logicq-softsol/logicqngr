package com.logicq.ngr.service;

import java.util.List;

import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.model.admin.Profile;
import com.logicq.ngr.model.admin.Role;
import com.logicq.ngr.vo.ProfileVO;
import com.logicq.ngr.vo.RoleVO;

public interface ProfileService {
	List<ProfileVO> getUserProfiles() throws Exception;

	List<ProfileVO> createProfile(ProfileVO profileVO) throws Exception;

	List<ProfileVO> editProfile(ProfileVO profileVO) throws Exception;

	List<ProfileVO> deleteProfile(ProfileVO profileVO) throws Exception;

	Profile getProfile(Long profileId);

	List<Profile> getProfileByRoleName(RoleVO roleVO) throws Exception;
	
	List<Role> getProfileAccordingToAllowedRole(String allowedRole);

	List<Profile> getModifiedUsers();
	
	void onProfileChange(Profile oldProfile) throws Exception;
	
	void assignProfile(UserDetails user, Profile newProfile) throws Exception;
}
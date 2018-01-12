package com.logicq.ngr.dao;

import java.util.List;

import com.logicq.ngr.model.admin.Profile;

public interface ProfileDao {
	List<Profile> getUserProfiles();

	Profile createProfile(Profile profile);

	void updateProfile(Profile profile);

	void deleteProfile(Profile profile);

	Profile getProfile(Profile profile);

	Profile getProfile(Long profileid);

	List<Profile> getModifiedProfiles();

}
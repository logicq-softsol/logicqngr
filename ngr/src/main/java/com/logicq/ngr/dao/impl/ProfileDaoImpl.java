package com.logicq.ngr.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.ProfileDao;
import com.logicq.ngr.model.admin.Profile;

@Repository
public class ProfileDaoImpl extends AbstractDAO<Profile> implements ProfileDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1440088095395924324L;

	@Override
	public List<Profile> getUserProfiles() {
		return (List<Profile>) loadClass(Profile.class);
	}

	/*@Override
	public Long createProfile(Profile profile) {
		return saveEntity(profile);
	}*/
	@Override
	public Profile createProfile(Profile profile) {
		 save(profile);
		 return profile;
	}

	@Override
	public Profile getProfile(Profile profile) {
		return getRecordById(Profile.class, profile.getProfileId());
	}

	@Override
	public Profile getProfile(Long profileid) {
		return getRecordById(Profile.class,profileid);
	}

	@Override
	public void updateProfile(Profile profile) {
		update(profile);
	}

	@Override
	public void deleteProfile(Profile profile) {
		delete(profile);
		
	}

	@Override
	public List<Profile> getModifiedProfiles() {
		StringBuilder query = new StringBuilder();
		query.append("from Profile where modified = true");
		return executeQuery(query.toString());
	}

}

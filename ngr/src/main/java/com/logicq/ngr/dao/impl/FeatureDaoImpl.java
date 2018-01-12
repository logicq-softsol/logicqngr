package com.logicq.ngr.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.FeatureDao;
import com.logicq.ngr.model.admin.Feature;
import com.logicq.ngr.service.UserAdminService;

@Repository
public class FeatureDaoImpl extends AbstractDAO<Feature> implements FeatureDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -462585179997202575L;

	@Autowired
	UserAdminService userAdminService;
	
	
	
	
	@Override
	public void addfeature(Feature feature) {
		save(feature);
	}

	@Override
	public void editFeature(Feature feature) {
		update(feature);
	}

	@Override
	public void deleteFeature(Feature feature) {
		delete(feature);
	}




	@Override
	public List<Feature> getAllFeatures() {
		return (List<Feature>) loadClass(Feature.class);
	}

	
}
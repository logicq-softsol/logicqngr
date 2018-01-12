package com.logicq.ngr.dao.impl;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.SiteOptionDao;
import com.logicq.ngr.model.common.SiteOption;

@Repository
public class SiteOptionDaoImpl extends AbstractDAO<SiteOption> implements SiteOptionDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3710925223590239936L;

	public List<SiteOption> loadSiteOption() {
		return (List<SiteOption>) loadClass(SiteOption.class);
	}

}

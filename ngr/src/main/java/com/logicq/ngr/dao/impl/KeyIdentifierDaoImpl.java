package com.logicq.ngr.dao.impl;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.KeyIdentifierDao;
import com.logicq.ngr.model.KeyIdentifier;

@Repository
public class KeyIdentifierDaoImpl extends AbstractDAO<KeyIdentifier> implements KeyIdentifierDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2029943487684545642L;

	@Override
	@Cacheable(value="sqlcache")
	public List<KeyIdentifier> getKeyIdentifiers() {
		return (List<KeyIdentifier>) loadClass(KeyIdentifier.class);
	}
}
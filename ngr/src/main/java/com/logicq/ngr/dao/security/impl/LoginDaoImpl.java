package com.logicq.ngr.dao.security.impl;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.logicq.ngr.dao.AbstractDAO;
import com.logicq.ngr.dao.security.LoginDao;
import com.logicq.ngr.model.UserDetails;

@Repository
public class LoginDaoImpl extends AbstractDAO<UserDetails> implements LoginDao {

	private static final long serialVersionUID = 6421454786987072597L;

	@Cacheable(value="sqlcache")
	public List<UserDetails> loadUsers() {
		return (List<UserDetails>) loadClass(UserDetails.class);
	}

}

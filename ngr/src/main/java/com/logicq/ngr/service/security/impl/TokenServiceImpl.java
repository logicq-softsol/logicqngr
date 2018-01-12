package com.logicq.ngr.service.security.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.logicq.ngr.dao.security.TokenDao;
import com.logicq.ngr.service.security.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	TokenDao tokenDao;

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List loadTokenExpiry() {
		return tokenDao.loadTokenExpiry();
	}

}

package com.logicq.ngr.service.security.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.logicq.ngr.dao.security.LoginDao;
import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.service.security.LoginService;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginDao loginDao;
	
	@Transactional(propagation = Propagation.REQUIRED,readOnly=true)
	public List<UserDetails> loadUsers() {
		return loginDao.loadUsers();
	
	}
}

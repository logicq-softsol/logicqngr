package com.logicq.ngr.service.security;

import java.util.List;

import com.logicq.ngr.model.UserDetails;


public interface LoginService {
	
	public List<UserDetails> loadUsers();  
}

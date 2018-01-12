package com.logicq.ngr.dao.security;

import java.util.List;

import com.logicq.ngr.model.UserDetails;

public interface LoginDao {
	
	public List<UserDetails> loadUsers();
}

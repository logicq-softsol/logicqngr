package com.logicq.ngr.dao.security;

import java.util.List;

public interface TokenDao {
	public List<Integer> loadTokenExpiry();
}

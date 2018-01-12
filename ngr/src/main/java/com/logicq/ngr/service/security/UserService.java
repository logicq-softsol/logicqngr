package com.logicq.ngr.service.security;

import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

import com.logicq.ngr.controllers.LoadApplicationData;
import com.logicq.ngr.vo.UserVO;

public class UserService implements UserDetailsService {

	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

	public final UserVO loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVO user = LoadApplicationData.userMap.get(username);
		if (user == null) {
			throw new NullPointerException("user not found.");
		}
		detailsChecker.check(user);
		return user;
	}

	public final void removeUser(String username) {

		if (username != null && !StringUtils.isEmpty(username)) {
			LoadApplicationData.userMap.remove(username);

		}
	}

}

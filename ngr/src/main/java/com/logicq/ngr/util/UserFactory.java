package com.logicq.ngr.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.logicq.ngr.common.helper.DateHelper;
import com.logicq.ngr.model.UserDetails;
import com.logicq.ngr.vo.UserVO;



public final class UserFactory {

    private UserFactory() {
    }

    public static UserVO create(UserDetails user) {
        return new UserVO(
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getEin(),
                mapToGrantedAuthorities(user.getRole().getName()),
                DateHelper.convertDateIntoString(user.getLastLoggedIn()),
                user.getEnabled()
        );
    }
    
   


    private static List<GrantedAuthority> mapToGrantedAuthorities(String role) {
    	 List<GrantedAuthority> grantedauthlist=new ArrayList<>();
    		GrantedAuthority grandauth=new SimpleGrantedAuthority(role);
    		grantedauthlist.add(grandauth);
        return   grantedauthlist;
        }
}

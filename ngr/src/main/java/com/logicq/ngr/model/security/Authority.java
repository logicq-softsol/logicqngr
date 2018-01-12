package com.logicq.ngr.model.security;

import java.util.List;

import org.springframework.security.core.userdetails.User;

import com.logicq.ngr.constant.AuthorityName;


public class Authority {

   
    private Long id;

  
    private AuthorityName name;

 
    private List<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthorityName getName() {
        return name;
    }

    public void setName(AuthorityName name) {
        this.name = name;
    }

   
}
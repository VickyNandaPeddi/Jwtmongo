package com.example.MongoJwt.service;

import com.example.MongoJwt.dao.Userdao;
import com.example.MongoJwt.model.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomUserdetailsservice implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(CustomUserdetailsservice.class);
    @Autowired
    private Userdao userdao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byUsername = userdao.findByUsername(username);
        logger.error("username in userdetailsservic is" + username);
        if (byUsername == null) {
            System.out.println("username not found");
        }
        return new org.springframework.security.core.userdetails.User(byUsername.getUsername(), byUsername.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_" + byUsername.getRoles().toUpperCase())));
    }
}

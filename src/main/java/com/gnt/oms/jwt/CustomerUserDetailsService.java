package com.gnt.oms.jwt;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gnt.oms.dao.YFSUserDAO;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.User;

@Slf4j
@Service
public class CustomerUserDetailsService implements UserDetailsService{

    @Autowired
    YFSUserDAO yfsUserDAO;

    private com.gnt.oms.entities.YFSUser yfsUser;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        yfsUser = yfsUserDAO.findByEmailId(email);
        log.info("Inside loadUserByUsername {}", email);
        if(!Objects.isNull(yfsUser)){
            return new User(yfsUser.getEmailId(), yfsUser.getPassword(), new ArrayList<>());
        }else{
            throw new UsernameNotFoundException("User not found");
        }
    }

    public com.gnt.oms.entities.YFSUser getUserDetail() {
       return yfsUser;
    }
    

}

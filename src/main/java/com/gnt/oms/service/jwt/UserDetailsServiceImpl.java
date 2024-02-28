package com.gnt.oms.service.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gnt.oms.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

     @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       com.gnt.oms.entities.User user = userRepository.findFirstByEmail(username);
       if(user == null) throw new UsernameNotFoundException("Username not found", null);
       return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());

    }
    
}

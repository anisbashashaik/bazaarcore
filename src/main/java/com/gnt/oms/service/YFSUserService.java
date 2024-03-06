package com.gnt.oms.service;

import java.util.Map;
import org.springframework.http.ResponseEntity;

import com.gnt.oms.wrapper.UserWrapper;

import java.util.List;

public interface YFSUserService {
    
    ResponseEntity<String> signUp(Map<String, String> requestMap);
    ResponseEntity<String> login(Map<String, String> requestMap);
    ResponseEntity<List<UserWrapper>> getAllUsers();
    ResponseEntity<String> updateUser(Map<String, String> requestMap);
}

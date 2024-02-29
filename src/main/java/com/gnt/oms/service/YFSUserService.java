package com.gnt.oms.service;

import java.util.Map;
import org.springframework.http.ResponseEntity;

public interface YFSUserService {
    
    ResponseEntity<String> signUp(Map<String, String> requestMap);
}

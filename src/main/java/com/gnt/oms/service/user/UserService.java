package com.gnt.oms.service.user;

import org.springframework.http.ResponseEntity;

import com.gnt.oms.dto.SignupDTO;
import com.gnt.oms.dto.UserDTO;

public interface UserService {

    UserDTO createUser(SignupDTO signupDTO);

    boolean hasUserWithEmail(String email);
    
}

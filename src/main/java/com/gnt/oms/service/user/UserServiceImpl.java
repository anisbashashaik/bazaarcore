package com.gnt.oms.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gnt.oms.dao.UserRepository;
import com.gnt.oms.dto.SignupDTO;
import com.gnt.oms.dto.UserDTO;
import com.gnt.oms.entities.YFSUser;

import jakarta.annotation.PostConstruct;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO createUser(SignupDTO signupDTO) {
        YFSUser user = new YFSUser();
        user.setUserName(signupDTO.getUserName());
        user.setEmailId(signupDTO.getEmailId());
        user.setRole("User");
        user.setStatus("true");
        user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
        YFSUser createdUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(createdUser.getUserId());
        userDTO.setUserName(createdUser.getUserName());
        userDTO.setEmailId(createdUser.getEmailId());
        userDTO.setRole(createdUser.getRole());
        userDTO.setStatus(createdUser.getStatus());
        return userDTO;
    }

    @Override
    public boolean hasUserWithEmail(String emailId) {        
        return userRepository.findFirstByEmailId(emailId) != null;
    }

    @PostConstruct
    public void createAdminUser() {
        YFSUser adminUser = userRepository.findByRole("Admin");
        if(adminUser == null) {
            YFSUser user = new YFSUser();
            user.setRole("Admin");
            user.setUserName("admin");
            user.setEmailId("sebastiangeorge@mailinator.com");
            user.setStatus("true");
            user.setPassword(new BCryptPasswordEncoder().encode("password"));
            userRepository.save(user);
        }

    }

}

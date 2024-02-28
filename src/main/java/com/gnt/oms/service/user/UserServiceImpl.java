package com.gnt.oms.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gnt.oms.dto.SignupDTO;
import com.gnt.oms.dto.UserDTO;
import com.gnt.oms.entities.User;
import com.gnt.oms.enums.UserRole;
import com.gnt.oms.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO createUser(SignupDTO signupDTO) {
        User user = new User();
        user.setName(signupDTO.getName());
        user.setEmail(signupDTO.getEmail());
        user.setUserRole(UserRole.USER);
        user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
        User createdUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());
        userDTO.setName(createdUser.getName());
        userDTO.setEmail(createdUser.getEmail());
        userDTO.setUserRole(createdUser.getUserRole());
        return userDTO;
    }

    @Override
    public boolean hasUserWithEmail(String email) {        
        return userRepository.findFirstByEmail(email) != null;
    }

    @PostConstruct
    public void createAdminUser() {
        User adminUser = userRepository.findByUserRole(UserRole.ADMIN);
        if(adminUser == null) {
            User user = new User();
            user.setUserRole(UserRole.ADMIN);
            user.setName("Admin");
            user.setEmail("admin@test.com");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        }

    }

}

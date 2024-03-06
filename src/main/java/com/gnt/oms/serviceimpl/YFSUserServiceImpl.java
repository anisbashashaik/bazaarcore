package com.gnt.oms.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gnt.oms.constants.OMSConstants;
import com.gnt.oms.dao.YFSUserDAO;
import com.gnt.oms.entities.User;
import com.gnt.oms.entities.YFSUser;
import com.gnt.oms.jwt.CustomerUserDetailsService;
import com.gnt.oms.jwt.JWTUtil;
import com.gnt.oms.jwt.JwtFilter;
import com.gnt.oms.service.YFSUserService;
import com.gnt.oms.utils.EmailUtils;
import com.gnt.oms.utils.OMSUtil;
import com.gnt.oms.wrapper.UserWrapper;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class YFSUserServiceImpl implements YFSUserService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    YFSUserDAO userDAO;

    @Autowired
    EmailUtils emailUtils;

    @SuppressWarnings("null")
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {

        log.info("inside singUp {}", requestMap);
        try {
            if (validateSignup(requestMap)) {
                YFSUser user = userDAO.findByEmailId(requestMap.get("emailId"));
                if (Objects.isNull(user)) {
                    userDAO.save(getUserFromMap(requestMap));
                    return OMSUtil.getResponseEntity("Successfully Registered", HttpStatus.OK);
                } else {
                    return OMSUtil.getResponseEntity("Email already exists", HttpStatus.BAD_REQUEST);
                }
            } else {
                return OMSUtil.getResponseEntity(OMSConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return OMSUtil.getResponseEntity(OMSConstants.SOMETHING_WENT_STRING, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    public boolean validateSignup(Map<String, String> requestMap) {

        if (requestMap.containsKey("userName") && requestMap.containsKey("password")
                && requestMap.containsKey("emailId") && requestMap.containsKey("contactNo")) {
            return true;
        } else {
            return false;
        }
    }

    public YFSUser getUserFromMap(Map<String, String> requestMap) {
        YFSUser user = new YFSUser();
        user.setUserName(requestMap.get("userName"));
        user.setPassword(new BCryptPasswordEncoder().encode(requestMap.get("password")));
        user.setContactNo(requestMap.get("contactNo"));
        user.setEmailId(requestMap.get("emailId"));
        user.setStatus("false");
        user.setRole("User");
        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try {

            log.info("Email ID: " + requestMap.get("emailId"));
            log.info("password: " + requestMap.get("password"));

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("emailId"), requestMap.get("password")));

            if (auth.isAuthenticated()) {
                log.info("status: " + customerUserDetailsService.getUserDetail().getStatus());
                if (customerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>("{\"token\" :  \""
                            + jwtUtil.generateToken(customerUserDetailsService.getUserDetail().getEmailId(),
                                    customerUserDetailsService.getUserDetail().getRole())
                            + "\" }", HttpStatus.OK);

                } else {
                    return new ResponseEntity<String>("{\"message\" : \"" + "Wait for admin approval" + "\"}",
                            HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{}", e);
        }
        return new ResponseEntity<String>("{\"message\" : \"" + "Bad Credentials" + "\"}",
                HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {

        try {

            if (jwtFilter.isAdmin()) {
                return new ResponseEntity<>(userDAO.getAllUsers(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateUser(Map<String, String> requestMap) {

        try {
            if (jwtFilter.isAdmin()) {
                Optional<YFSUser> optional =  userDAO.findById(Integer.parseInt(requestMap.get("userId")));
                if(!optional.isEmpty()){
                    userDAO.updateStatus(requestMap.get("status"), requestMap.get("userId"));
                    sendMailToAllAdmin(requestMap.get("status"), optional.get().getEmailId(), userDAO.getAllAdmin());
                    return OMSUtil.getResponseEntity("User Status Updated successfully", HttpStatus.OK);
                }else{
                    return OMSUtil.getResponseEntity("User id does not exists", HttpStatus.OK);
                }
            } else {
                return OMSUtil.getResponseEntity(OMSConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (

        Exception e) {
            e.printStackTrace();
        }
        return OMSUtil.getResponseEntity(OMSConstants.SOMETHING_WENT_STRING, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void sendMailToAllAdmin(String status, String emailId, List<String> allAdmin) {
        allAdmin.remove(jwtFilter.getCurrentUser());
        if(status!= null && status.equalsIgnoreCase("true")){
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Approved by Admin", "Dear User,  \n Account has been activated. Thanks", allAdmin);
        }else{
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Disabled by Admin", "Dear User, \n Account has been deactivated. Thanks", allAdmin);
        }
    }

}

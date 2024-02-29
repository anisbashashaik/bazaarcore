package com.gnt.oms.serviceimpl;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gnt.oms.constants.OMSConstants;
import com.gnt.oms.dao.YFSUserDAO;
import com.gnt.oms.entities.YFSUser;
import com.gnt.oms.service.YFSUserService;
import com.gnt.oms.utils.OMSUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class YFSUserServiceImpl implements YFSUserService{

    @Autowired
    YFSUserDAO userDAO;
    @SuppressWarnings("null")
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        
        log.info("inside singUp {}" , requestMap);
        try{
            if(validateSignup(requestMap)) {
                YFSUser user = userDAO.findByEmailId(requestMap.get("emailId"));
                if(Objects.isNull(user)){               
                    userDAO.save(getUserFromMap(requestMap));
                    return OMSUtil.getResponseEntity("Successfully Registered", HttpStatus.OK);
                }else{
                    return OMSUtil.getResponseEntity("Email already exists", HttpStatus.BAD_REQUEST);
                }
            }else{
                 return OMSUtil.getResponseEntity(OMSConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
       
        return OMSUtil.getResponseEntity(OMSConstants.SOMETHING_WENT_STRING, HttpStatus.INTERNAL_SERVER_ERROR);
        
    }

    public boolean validateSignup(Map<String, String> requestMap){

        if(requestMap.containsKey("userName") && requestMap.containsKey("password")
        && requestMap.containsKey("emailId") && requestMap.containsKey("contactNo")){
            return true;
        }else{
            return false;
        }
    }

    public YFSUser getUserFromMap(Map<String, String> requestMap){
        YFSUser user = new YFSUser();
        user.setUserName(requestMap.get("userName"));
        user.setPassword(requestMap.get("password"));
        user.setContactNo(requestMap.get("contactNo"));
        user.setEmailId(requestMap.get("emailId"));
        user.setStatus("false");
        user.setRole("User");        
        return user;
    }
    

}

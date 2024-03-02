package com.gnt.oms.restimpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.gnt.oms.constants.OMSConstants;
import com.gnt.oms.rest.YFSUserRest;
import com.gnt.oms.service.YFSUserService;
import com.gnt.oms.utils.OMSUtil;

@RestController
public class YFSUserRestImpl implements YFSUserRest{

    @Autowired
    YFSUserService userService;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        
        try {
            return userService.signUp(requestMap);
        }catch (Exception e) {
            e.printStackTrace();
        }
        OMSUtil.getResponseEntity(OMSConstants.SOMETHING_WENT_STRING, HttpStatus.INTERNAL_SERVER_ERROR);

        return null;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMapping) {
  
        try {
            return userService.login(requestMapping);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return OMSUtil.getResponseEntity(OMSConstants.SOMETHING_WENT_STRING, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}

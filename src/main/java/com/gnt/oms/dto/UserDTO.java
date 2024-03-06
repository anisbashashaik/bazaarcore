package com.gnt.oms.dto;


import com.gnt.oms.enums.UserRole;

import lombok.Data;

@Data
public class UserDTO {
    
    private Integer userId;     
    private String userName;
    private String emailId;
    private String password;
    //private byte[] img;
    //private UserRole role;
    private String role;   
    private String  status; 

}

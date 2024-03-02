package com.gnt.oms.dto;

import lombok.Data;

@Data
public class SignupDTO {
    
    private String userName;
    private String emailId;
    private String password;
    private String role;
}

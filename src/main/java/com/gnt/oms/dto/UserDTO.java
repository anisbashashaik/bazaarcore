package com.gnt.oms.dto;


import com.gnt.oms.enums.UserRole;

import lombok.Data;

@Data
public class UserDTO {
    
    private Long id;     
    private String name;
    private String email;
    private String password;
    private byte[] img;
    private UserRole userRole;

}

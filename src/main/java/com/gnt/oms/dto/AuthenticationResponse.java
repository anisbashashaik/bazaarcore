package com.gnt.oms.dto;

import lombok.Data;


@Data
public class AuthenticationResponse {

    public AuthenticationResponse(String jwt) {
        jwtToken = jwt;
    }

    private String jwtToken;

    
}

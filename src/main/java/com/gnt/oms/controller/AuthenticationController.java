package com.gnt.oms.controller;

import java.io.IOException;

import org.apache.tomcat.util.http.parser.Authorization;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gnt.oms.dto.AuthenticationRequest;
import com.gnt.oms.dto.AuthenticationResponse;
import com.gnt.oms.entities.User;
import com.gnt.oms.repository.UserRepository;
import com.gnt.oms.service.user.UserService;
import com.gnt.oms.utils.JwtUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;



@RestController
public class AuthenticationController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public void creteAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response)  
    throws IOException, BadCredentialsException, DisabledException, UsernameNotFoundException, JSONException, 
    ServletException{

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        }catch(BadCredentialsException e){
            throw new BadCredentialsException("Incorrect Username or password");
        }catch (DisabledException e){
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "User is not activated");
            return;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        User user = userRepository.findFirstByEmail(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(authenticationRequest.getUsername());
       // return new AuthenticationResponse(jwt);
       response.getWriter().write(new JSONObject().put("userId", user.getId())
       .put("role", user.getUserRole()).toString()
       );

       //response.setHeader("Access-Control-Allow-Origin", "*");
       //response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
       //response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With,observe");
      // response.setHeader("Access-Control-Max-Age", "3600");
       //response.setHeader("Access-Control-Allow-Credentials", "true");
       //response.setHeader("Access-Control-Expose-Headers", "Authorization");
      // response.addHeader("Access-Control-Expose-Headers", "responseType");
       //response.addHeader("Access-Control-Expose-Headers", "observe");

        response.addHeader("Access-Control-Expose-Headers", "Authorization");
       response.addHeader("Access-Control-Allow-Headers", "Authorization, Origin, X-Requested-With, Content-Type, Accept, X-Custom-Header");
       response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
    }

}

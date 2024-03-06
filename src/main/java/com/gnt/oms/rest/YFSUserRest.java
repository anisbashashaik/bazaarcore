package com.gnt.oms.rest;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gnt.oms.wrapper.UserWrapper;

import java.util.List;

@RequestMapping(path = "/yfsuser")
public interface YFSUserRest {
    
    @PostMapping(path ="/signup")
    public ResponseEntity<String> signUp(@RequestBody(required = true) Map<String, String> requestMapping);

    @PostMapping(path ="/login")
    public ResponseEntity<String> login(@RequestBody(required = true) Map<String, String> requestMapping);

    @GetMapping (path ="/get")
    public ResponseEntity<List<UserWrapper>> getAllUsers();

    @PostMapping(path = "/update")
    public ResponseEntity<String> updateUser(@RequestBody (required = true) Map<String, String> requestMapping);
}

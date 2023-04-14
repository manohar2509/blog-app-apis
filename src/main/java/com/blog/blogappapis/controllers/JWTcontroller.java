package com.blog.blogappapis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.blogappapis.exceptions.ResourceNotFoundException;
import com.blog.blogappapis.payloads.AuthDto;
import com.blog.blogappapis.services.JWTService;

@RestController
@RequestMapping("/api")
public class JWTcontroller {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthDto authDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword()));
        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(authDto.getUsername());
        }
        else{
            throw new ResourceNotFoundException("User","Email and Password",0);
        } 
    }
}

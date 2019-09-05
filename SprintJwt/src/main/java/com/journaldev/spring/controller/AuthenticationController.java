package com.journaldev.spring.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private String tokenHeader = "Authorization";
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@GetMapping(value = "/rest/auth")
    public ResponseEntity<?> createAuthenticationToken(HttpServletRequest request) throws AuthenticationException {

		String username = "Ravikumar";//request.getHeader("Authorization");

        final String token = jwtTokenUtil.generateToken(username);

        System.out.println("Token :: "+token);
        
        
        
        
        String decryptusername = jwtTokenUtil.getUsernameFromToken(token);
        
        System.out.println("Decrypted Username :: "+decryptusername);
        
        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }
}

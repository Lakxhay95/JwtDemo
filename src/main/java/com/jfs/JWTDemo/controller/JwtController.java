package com.jfs.JWTDemo.controller;

import com.jfs.JWTDemo.util.JwtUtil;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
public class JwtController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/generate-token")
    public String generateToken(@RequestParam String username, @RequestParam String password) {
        if(username.equals("admin") && "admin".equals(password)) {
            // authenticated -> generate token
            return jwtUtil.generateToken(username);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @GetMapping("/fund")
    public String fundTransfer(@RequestHeader(value="Authorization", required=false) String authorizationHeader) {
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            if(jwtUtil.validateToken(token)) {
                return "API is valid";
            } else {
                return "API is not valid";
            }
        } else {
            return "Authorization header is missing";
        }
    }
}

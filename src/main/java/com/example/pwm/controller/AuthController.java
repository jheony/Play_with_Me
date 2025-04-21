package com.example.pwm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;

public class AuthController {
    @GetMapping("/auth/signin/kakao")
    public ResponseEntity<?> kakaoLogin (@RequestParam("code") 
    String accessCode, HttpServletResponse httpServletResponse){
        return null;
        
    }
}

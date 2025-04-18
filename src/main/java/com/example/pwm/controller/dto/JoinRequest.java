package com.example.pwm.controller.dto;

import lombok.Data;

@Data
public class JoinRequest {
    private String email;
    
    private String passwd;

    private String name;
}

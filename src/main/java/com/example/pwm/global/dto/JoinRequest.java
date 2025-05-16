package com.example.pwm.global.dto;

import lombok.Data;

@Data
public class JoinRequest {
    private String email;
    
    private String passwd;

    private String name;
}

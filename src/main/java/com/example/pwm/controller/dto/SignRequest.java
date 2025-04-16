package com.example.pwm.controller.dto;

import lombok.Data;

@Data
public class SignRequest {
    private Long id;
    
    private String email;
    
    private String passwd;

    private String name;
}

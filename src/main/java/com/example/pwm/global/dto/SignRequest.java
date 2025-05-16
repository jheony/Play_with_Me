package com.example.pwm.global.dto;

import lombok.Data;

@Data
public class SignRequest {
    private Long id;

    private String email;

    private String passwd;

    private String name;
}

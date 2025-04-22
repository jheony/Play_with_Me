package com.example.pwm.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HostDTO {
    private Long id;
    private String email;
    private String passwd;
    private String name;
}

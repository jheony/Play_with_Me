package com.example.pwm.controller.dto;

import com.example.pwm.repository.entity.Host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignResponse {
    private Long id;

    private String email;

    private String passwd;

    private String name;

    public SignResponse(Host host) {
        this.email = host.getEmail();
        this.passwd = host.getPasswd();
        this.name = host.getName();
    }
}

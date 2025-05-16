package com.example.pwm.global.dto;

import com.example.pwm.domain.host.Host;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 회원 가입 응답 정보를 담는 클래스
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignResponse {

    private Long id;

    private String email;

    private String name;

    // Host 엔티티를 받아서 응답 객체 생성
    public SignResponse(Host host) {
        this.id = host.getId();
        this.email = host.getEmail();
        this.name = host.getName();
    }
}

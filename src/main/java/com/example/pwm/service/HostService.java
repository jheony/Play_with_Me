package com.example.pwm.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.pwm.controller.dto.JoinRequest;
import com.example.pwm.repository.HostRepository;
import com.example.pwm.repository.entity.Host;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HostService{

    private final HostRepository hostRepository;
    private final PasswordEncoder passwordEncoder;

    public String join(JoinRequest joinRequest) {
        
        // 이메일 중복 체크
        hostRepository.findByEmail(joinRequest.getEmail()).ifPresent(host -> { 
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        });

        // 비밀번호 암호화
        String hashPw = passwordEncoder.encode(joinRequest.getPasswd());

        Host host = Host.builder()
                .email(joinRequest.getEmail())
                .passwd(hashPw)
                .name(joinRequest.getName())
                .build();

        hostRepository.save(host);
        return "success";
    }
}
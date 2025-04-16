package com.example.pwm.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.pwm.controller.dto.SignRequest;
import com.example.pwm.repository.HostRepository;
import com.example.pwm.repository.entity.Host;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HostService{

    private final HostRepository hostRepository;
    private final PasswordEncoder passwordEncoder;

    public String join(SignRequest signRequest) {
        
        // 이메일 중복 체크
        hostRepository.findByEmail(signRequest.getEmail()).ifPresent(host -> { 
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        });

        // 비밀번호 암호화
        String hashPw = passwordEncoder.encode(signRequest.getPasswd());

        Host host = Host.builder()
                .email(signRequest.getEmail())
                .passwd(hashPw)
                .name(signRequest.getName())
                .build();

        hostRepository.save(host);
        return "success";
    }

    public String login(SignRequest signRequest){
        Host host = hostRepository.findByEmail(signRequest.getEmail()).orElseThrow(() -> new BadCredentialsException("잘못된 이메일입니다."));      

        if (!passwordEncoder.matches(signRequest.getPasswd(), host.getPasswd())) {
            throw new BadCredentialsException("잘못된 비밀번호입니다.");
        }

        return "Success";
    } 
}
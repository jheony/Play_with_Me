package com.example.pwm.service;

import org.springframework.stereotype.Service;

import com.example.pwm.controller.dto.JoinRequest;
import com.example.pwm.repository.HostRepository;
import com.example.pwm.repository.entity.Host;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HostServiceImpl implements HostService {

    private final HostRepository hostRepository;

    @Override
    public String join(JoinRequest joinRequest) {
        Host host = Host.builder()
                .name(joinRequest.getName())
                .email(joinRequest.getEmail())
                .passwd(joinRequest.getPasswd())
                .build();

        hostRepository.save(host);
        return "success";
    }

}

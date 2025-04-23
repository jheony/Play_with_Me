package com.example.pwm.service;

import com.example.pwm.controller.dto.HostDTO;
import com.example.pwm.controller.dto.PageRequestDTO;
import com.example.pwm.controller.dto.PageResponseDTO;
import com.example.pwm.controller.dto.SignRequest;

public interface HostService {
    Long register(HostDTO HostDto);

    HostDTO get(Long id);

    void modify(HostDTO hostDTO);

    void remove(Long id);

    PageResponseDTO<HostDTO> list(PageRequestDTO pageRequestDTO);

    String join(SignRequest signRequest); // 회원가입

    String login(SignRequest signRequest); // 로그인

}

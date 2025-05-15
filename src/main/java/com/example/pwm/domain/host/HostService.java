package com.example.pwm.domain.host;

import com.example.pwm.domain.host.HostDTO;
import com.example.pwm.global.dto.PageRequestDTO;
import com.example.pwm.global.dto.PageResponseDTO;
import com.example.pwm.global.dto.SignRequest;

public interface HostService {
    Long register(HostDTO HostDto);

    String get(Long id);

    void remove(Long id);

    PageResponseDTO<HostDTO> list(PageRequestDTO pageRequestDTO);

    String join(SignRequest signRequest); // 회원가입
}

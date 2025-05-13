package com.example.pwm.service;

import com.example.pwm.controller.dto.ReservDTO;

public interface ReservService {
    Long register(ReservDTO reservDTO);

    ReservDTO get(Long id);

    
}

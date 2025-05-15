package com.example.pwm.domain.reservation;

public interface ReservService {
    Long register(ReservDTO reservDTO, Long hostId);

    ReservDTO get(Long id);

    
}

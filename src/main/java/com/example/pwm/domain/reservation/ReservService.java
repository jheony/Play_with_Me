package com.example.pwm.domain.reservation;

public interface ReservService {
    Long register(ReservDTO reservDTO, Long hostId);
    String acceptReserv(Long resId);
    String cancelReserv(Long resId);

    ReservDTO get(Long id);

    
}

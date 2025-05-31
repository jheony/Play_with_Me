package com.example.pwm.domain.reservation;

public interface ReservService {
    Long addReserv(ReservDTO reservDTO, Long hostId);
    String acceptReserv(Long resId);
    String cancelReserv(Long resId);

    ReservDTO get(Long id);
    String getHostEmail(Long resId);
}

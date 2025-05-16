package com.example.pwm.domain.reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservRepository extends JpaRepository<Reservation, Long> {
    
}

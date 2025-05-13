package com.example.pwm.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pwm.repository.entity.Reservation;

public interface ReservRepository extends JpaRepository<Reservation, Long> {
    
}

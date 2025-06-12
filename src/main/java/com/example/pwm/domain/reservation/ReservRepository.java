package com.example.pwm.domain.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservRepository extends JpaRepository<Reservation, Long> {
    Page<Reservation> findByStartTimeBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // 상태 + 날짜 + 호스트 ID
    Page<Reservation> findByResHostIdAndReservStateAndStartTimeBetween(
            Long hostId, ReservState reservState, LocalDateTime start, LocalDateTime end, Pageable pageable);

    // 상태 + 호스트 ID
    Page<Reservation> findByResHostIdAndReservState(
            Long hostId, ReservState reservState, Pageable pageable);

    // 호스트 ID만
    Page<Reservation> findByResHostId(Long hostId, Pageable pageable);

}
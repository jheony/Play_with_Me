package com.example.pwm.domain.schedule;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Page<Schedule> findByScheHostIdAndStartTimeBetween(Long hostId, LocalDateTime startDate, LocalDateTime endDate,
            Pageable pageable);

    Page<Schedule> findByScheHostId(Long hostId, Pageable pageable);
}

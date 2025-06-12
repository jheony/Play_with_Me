package com.example.pwm.domain.schedule;

import org.springframework.stereotype.Service;

@Service
public interface ScheduleService {
    Long addSchedule(ScheduleDTO scheduleDTO, Long hostId);

    String removeSchedule(Long scheduleId);

    ScheduleDTO getSchedule(Long scheduleId);
}

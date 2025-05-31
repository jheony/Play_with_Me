package com.example.pwm.domain.schedule;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/api/schedule/{hostId}")
    public ResponseEntity<String> addSchedule(@PathVariable Long hostId, @RequestBody ScheduleDTO scheduleDTO) {
        try {
            scheduleService.addSchedule(scheduleDTO, hostId);
            return ResponseEntity.ok("일정 등록 성공");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body("일정 등록 실패");
        }
    }

    @DeleteMapping("/api/schedule/{scheId}")
    public ResponseEntity<String> addSchedule(@PathVariable Long hostId, @PathVariable Long scheId) {
        try {
            return ResponseEntity.ok("일정 삭제 성공");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body("일정 삭제 실패");
        }
    }
}

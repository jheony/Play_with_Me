package com.example.pwm.domain.host;

import com.example.pwm.domain.reservation.ReservDTO;
import com.example.pwm.domain.schedule.ScheduleDTO;
import com.example.pwm.global.dto.PageRequestDTO;
import com.example.pwm.global.dto.PageResponseDTO;
import com.example.pwm.global.dto.SignRequest;

public interface HostService {
    String join(SignRequest signRequest); // 회원가입

    String get(Long id); // 호스트 이메일

    void remove(Long id); // 탈퇴

    PageResponseDTO<ScheduleDTO> getScheList(PageRequestDTO pageRequestDTO); // 일정 목록 페이징

    PageResponseDTO<ReservDTO> getReservList(PageRequestDTO pageRequestDTO); // 예약 신청 목록 페이징
}
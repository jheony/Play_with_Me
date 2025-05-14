package com.example.pwm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pwm.controller.dto.ReservDTO;
import com.example.pwm.service.MailServiceImpl;
import com.example.pwm.service.ReservService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReservController {

  private final ReservService reservService;
  private final MailServiceImpl mailService;

  @PostMapping("/api/reserv")
  public ResponseEntity<String> reservation(@RequestBody ReservDTO reservDTO) {
    try {
      reservService.register(reservDTO);
      mailService.reservRequestEmailToHost(reservDTO);
      return ResponseEntity.ok("예약성공");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(400).body("예약 정보 저장 실패");
    }
  }
}

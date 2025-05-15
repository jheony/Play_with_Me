package com.example.pwm.domain.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pwm.domain.mail.MailServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReservController {

  private final ReservService reservService;
  private final MailServiceImpl mailService;

  @PostMapping("/api/{hostId}/reserv")
  public ResponseEntity<String> reservation(@PathVariable Long hostId, @RequestBody ReservDTO reservDTO) {
    try {
      reservService.register(reservDTO, hostId);
      mailService.reservRequestEmailToHost(reservDTO, hostId);
      
      return ResponseEntity.ok("예약성공");

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(400).body("예약 정보 저장 실패");
    }
  }
}

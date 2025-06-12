package com.example.pwm.domain.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

  @PostMapping("/api/reserv/{hostId}")
  public ResponseEntity<String> reservation(@PathVariable Long hostId, @RequestBody ReservDTO reservDTO) {
    try {
      reservDTO.setHostId(hostId);
      Long resId = reservService.addReserv(reservDTO, hostId);

      mailService.sendMailAddReserv(resId);

      return ResponseEntity.ok("예약성공, resID: "+resId);

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(400).body("예약 정보 저장 실패");
    }
  }

  @PatchMapping("/api/reserv/{resId}")
  public ResponseEntity<String> acceptReserv(@PathVariable Long resId) {
    try {
      reservService.acceptReserv(resId);
      mailService.reservAcceptEmailToReserv(resId);

      return ResponseEntity.ok("예약수락");

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(400).body("예약 상태 변경(수락) 실패");
    }
  }

  @DeleteMapping("/api/reserv/{resId}")
  public ResponseEntity<String> cancelReserv(@PathVariable Long resId) {
    try {
      mailService.reservCancelEmailToReserv(resId); // 메일 전송 후 삭제
      reservService.cancelReserv(resId);

      return ResponseEntity.ok("예약거절");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(400).body("예약 상태 변경(거절) 실패");
    }
  }
}

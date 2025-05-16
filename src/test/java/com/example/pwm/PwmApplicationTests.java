package com.example.pwm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.pwm.domain.mail.MailServiceImpl;
import com.example.pwm.domain.reservation.ReservService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class PwmApplicationTests {
    @Autowired
    private ReservService reservService;

    @Autowired
    private MailServiceImpl mailService;
    public void signupTest() {
        String name = "test";
        String passwd = "test1234";
        String email = "test@test.com";
    }

    @Test
    @DisplayName("예약 수락 시 상태변경과 메일 전송")
    public void acceptResTest() {

        reservService.acceptReserv(1L);
        mailService.reservAcceptEmailToHost(1L); 

    }

    @Test
    @DisplayName("예약 거절 시 DB 삭제와 메일 전송")
    public void canselResTest() {

        mailService.reservCancelEmailToHost(3L); 
        reservService.cancelReserv(3L);

    }
}
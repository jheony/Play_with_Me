package com.example.pwm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PwmApplication {
	public static void main(String[] args){		
		SpringApplication.run(PwmApplication.class, args);
	}
}
// 예약 신청 내역 메일 보내기
// 마이페이지 수락 -> DB 저장 acceptReserve
// 마이페이지 수락 -> 메일 발송
// 
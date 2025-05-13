package com.example.pwm.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl {
    private final JavaMailSender javaMailSender;

    public void sendSimpleMailMessage() {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        try {
			simpleMailMessage.setFrom("dwlgusbs@gmail.com");
            // 메일을 받을 수신자 설정
            simpleMailMessage.setTo("heon__y@naver.com");
            // 메일의 제목 설정
            simpleMailMessage.setSubject("테스트 메일 제목");
            // 메일의 내용 설정
            simpleMailMessage.setText("테스트 메일 내용");

			Properties props = new Properties();
			props.put("mail.smtp.starttls.enable", "true"); // STARTTLS 활성화
			props.put("mail.smtp.auth", "true"); // SMTP 인증 활성화
			
            javaMailSender.send(simpleMailMessage);
            log.info("메일 발송 성공!");
        } catch (Exception e) {
            log.info("메일 발송 실패!");
            throw new RuntimeException(e);
        }

    }
}

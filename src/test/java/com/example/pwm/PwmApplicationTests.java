package com.example.pwm;

import java.util.Properties;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.pwm.repository.HostRepository;
import com.example.pwm.repository.entity.Host;
import com.example.pwm.service.MailServiceImpl;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Nested
@Slf4j
class PwmApplicationTests {
	
	/*
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Test
	@DisplayName("메일 전송 테스트")
	void sendMail() throws Exception{
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
	*/
	// @Autowired
	// private HostRepository hostRepository;
	// @Autowired
	// private PasswordEncoder passwordEncoder;

	// @Test
	// void contextLoads() {
	// }

	// @Test
	// @DisplayName("회원가입 테스트")
	// void signup() throws Exception {
	// 	Host host = Host.builder()
	// 			.name("테스트")
	// 			.email("testg@test.com")
	// 			.passwd("test1234")
	// 			.build();

	// 	// 이메일 중복 체크
	// 	hostRepository.findByEmail(host.getEmail()).ifPresent(h -> {
	// 		throw new IllegalStateException("이미 존재하는 이메일입니다.");
	// 	});

	// 	// 비밀번호 암호화
	// 	String hashPw = passwordEncoder.encode(host.getPasswd());

	// 	Host testHost = Host.builder()
	// 			.email(host.getEmail())
	// 			.passwd(hashPw)
	// 			.name(host.getName())
	// 			.build();

	// 	hostRepository.save(testHost);
	// }

	// @Test
	// @DisplayName("로그인 테스트")
	// void login() throws Exception {
	// 	Host host = Host.builder()
	// 			.name("테스트")
	// 			.email("logintestg@test.com")
	// 			.passwd(passwordEncoder.encode("test1234"))
	// 			.build();
	// 	hostRepository.save(host);

	// 	Host h = hostRepository.findByEmail("logintestg@test.com")
	// 			.orElseThrow(() -> new BadCredentialsException("잘못된 이메일입니다."));

	// 	if (!passwordEncoder.matches("test1234", h.getPasswd())) {
	// 		throw new BadCredentialsException("잘못된 비밀번호입니다.");
	// 	}
	// 	System.out.println("로그인 테스트 성공: 이메일과 비밀번호가 일치합니다.");
	// }
}
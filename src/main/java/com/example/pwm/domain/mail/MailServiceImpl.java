package com.example.pwm.domain.mail;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.pwm.domain.host.HostDTO;
import com.example.pwm.domain.host.HostService;
import com.example.pwm.domain.reservation.ReservDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl {
    private final JavaMailSender javaMailSender;
    private final HostService hostService;

    public void sendSimpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        try {
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

    /*
     * 수신자가 여러명일 경우
     * // 문자열 배열 형태
     * String[] receivers = {"abcd@gmail.com", "efgh@gmail.com"};
     * simpleMailMessage.setTo(receivers);
     * 
     * // ArrayList의 경우 => 배열로 전환해줘야 함!
     * ArrayList<String> receiverList = new
     * ArrayList<>(Arrays.asList("abcd@gmail.com", "efgh@gmail.com"));
     * 
     * String[] changeReceivers = receiverList.toArray(new
     * String[receiverList.size()]);
     * simpleMailMessage.setTo(changeReceivers);
     */
    public void reservRequestEmailToHost(ReservDTO res, Long hostId) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            String hEmail = hostService.get(hostId); 
            // 메일을 받을 수신자 설정
            mimeMessageHelper.setTo(hEmail);
            // 메일의 제목 설정
            mimeMessageHelper.setSubject("[예약신청] " + res.getName() + "님이 만남을 신청했습니다.");

            // html 문법 적용한 메일의 내용
            String content = String.format("""
                    <!DOCTYPE html>
                    <html lang="ko">
                    <head>
                        <meta charset="UTF-8">
                    </head>
                    <body>
                        <div class="container">
                            <div class="title">✨ Play with Me ✨</div>
                            <div class="subtitle">님에게 보내는 안내 메일입니다</div>
                            <div class="content">
                                <p>안녕하세요 %s 님!</p>
                                <p>새로운 예약 신청이 있습니다!</p>
                                <p>예약자: %s</p>
                                <p>날짜: %s</p>
                                <p>장소: %s</p>
                                <p> Play with Me 페이지에서 수락 여부를 결정해주세요! </p>
                            </div>
                        </div>
                    </body>
                    </html>
                    """, res.getName(), res.getName(), res.getStartTime(), res.getLocation()); // 메일의 내용 설정
            mimeMessageHelper.setText(content, true);
            // 주소 연결
            // <a href="%s" class="button">확인하러 가기</a>

            javaMailSender.send(mimeMessage);

            log.info("메일 발송 성공!");
        } catch (Exception e) {
            log.info("메일 발송 실패!");
            throw new RuntimeException(e);
        }
    }
}

package com.example.pwm.domain.mail;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.pwm.domain.reservation.ReservDTO;
import com.example.pwm.domain.reservation.ReservService;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl {
    private final JavaMailSender javaMailSender;
    private final ReservService reservService;

    // 호스트에게 보내는 예약신청 안내
    public void reservRequestEmailToHost(Long resId) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            ReservDTO res = reservService.get(resId);
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            String hEmail = reservService.getHostEmail(res.getId());
            String hName = reservService.getHostName(res.getId());
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
                            <h2>✨ Play with Me</h2>
                            <p>안녕하세요 %s님,</p>
                            <p>새로운 예약 신청이 있습니다!</p>
                            <p>호스트: %s</p>
                            <p>예약자: %s</p>
                            <p>날짜: %s</p>
                            <p>내용: %s</p>
                            <p>장소: %s</p>
                            <p><strong>Play with Me의 마이페이지에서</strong></p>
                            <p><strong>수락 여부를 결정해주세요!</strong></p>
                    </body>
                    </html>
                    """, hName, hName, res.getName(), res.getStartTime(), res.getContent(), res.getLocation());
            // 메일의 내용 설정
            mimeMessageHelper.setText(content, true);
            // 주소 연결
            // <a href="%s" class="button">확인하러 가기</a>

            javaMailSender.send(mimeMessage);

            log.info("호스트 메일 발송 성공!");
        } catch (Exception e) {
            log.info("호스트 메일 발송 실패!");
            throw new RuntimeException(e);
        }
    }

    // 예약자에게 보내는 예약신청 안내
    public void reservRequestEmailToReserv(Long resId) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            ReservDTO res = reservService.get(resId);

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            String hName = reservService.getHostName(res.getId());
            // 메일을 받을 수신자 설정
            mimeMessageHelper.setTo(res.getEmail());
            // 메일의 제목 설정
            mimeMessageHelper.setSubject("[예약신청] " + res.getName() + "님이 신청하신 예약 내역입니다.");

            // html 문법 적용한 메일의 내용
            String content = String.format("""
                    <!DOCTYPE html>
                    <html lang="ko">
                    <head>
                        <meta charset="UTF-8">
                    </head>
                    <body>
                            <h2>✨ Play with Me</h2>
                            <p>안녕하세요 %s님, </p>
                            <p>예약 신청이 정상적으로 접수되었습니다!</p>
                            <p>호스트: %s</p>
                            <p>예약자: %s</p>
                            <p>날짜: %s</p>
                            <p>내용: %s</p>
                            <p>장소: %s</p>
                            <p><strong>예약 수락 여부는 메일로 안내드릴 예정입니다.</strong></p>
                    </body>
                    </html>
                    """, res.getName(), hName, res.getName(), res.getStartTime(), res.getContent(), res.getLocation());
            // 메일의
            // 내용 설정
            mimeMessageHelper.setText(content, true);
            // 주소 연결
            // <a href="%s" class="button">확인하러 가기</a>

            javaMailSender.send(mimeMessage);

            log.info("예약자 메일 발송 성공!");
        } catch (Exception e) {
            log.info("예약자 메일 발송 실패!");
            throw new RuntimeException(e);
        }
    }

    // 에약 신청시 호스트, 예약자에게 메일 발송
    public void sendMailAddReserv(Long resId) {
        reservRequestEmailToHost(resId);
        reservRequestEmailToReserv(resId);
    }

    // 수락 메일
    public void reservAcceptEmailToReserv(Long resId) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            ReservDTO res = reservService.get(resId);
            String hName = reservService.getHostName(resId);

            // 메일을 받을 수신자 설정
            mimeMessageHelper.setTo(res.getEmail());
            // 메일의 제목 설정
            mimeMessageHelper.setSubject("[예약수락] " + hName + "님이 만남을 수락했습니다.");

            // html 문법 적용한 메일의 내용
            String content = String.format("""
                    <!DOCTYPE html>
                    <html lang="ko">
                    <head>
                        <meta charset="UTF-8">
                    </head>
                    <body>
                            <h2>✨ Play with Me</h2>
                            <p>안녕하세요 %s님, 예약이 수락되었습니다!</p>
                            <p>호스트: %s</p>
                            <p>예약자: %s</p>
                            <p>날짜: %s</p>
                            <p>장소: %s</p>
                            <p>내용: %s</p>
                            <p><strong>%s님과 즐거운 시간 보내시길 바랍니다.</strong></p>
                            <p>Play with Me를 이용해주셔서 감사합니다!</p>
                    </body>
                    </html>
                    """, res.getName(), hName, res.getName(), res.getStartTime(), res.getLocation(), res.getContent(),
                    hName);
            // 메일의 내용 설정
            mimeMessageHelper.setText(content, true);

            javaMailSender.send(mimeMessage);

            log.info("예약 수락 메일 발송 성공!");
        } catch (Exception e) {
            log.info("예약 수락 메일 발송 실패!");
            throw new RuntimeException(e);
        }
    }

    // 거절 메일
    public void reservCancelEmailToReserv(Long resId) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            ReservDTO res = reservService.get(resId);
            String hName = reservService.getHostName(resId);

            // 메일을 받을 수신자 설정
            mimeMessageHelper.setTo(res.getEmail());
            // 메일의 제목 설정
            mimeMessageHelper.setSubject("[예약취소] " + hName + "님이 만남을 거절했습니다.");

            // html 문법 적용한 메일의 내용
            String content = String.format("""
                    <!DOCTYPE html>
                    <html lang="ko">
                    <head>
                        <meta charset="UTF-8">
                    </head>
                    <body>
                            <h2>✨ Play with Me</h2>
                            <p>안녕하세요 %s님, 예약이 취소되었습니다.</p>
                            <p>호스트: %s</p>
                            <p>예약자: %s</p>
                            <p>내용: %s</p>
                            <p><strong>다음 기회에 다시 만날 수 있기를 바랍니다.</strong></p>
                            <p>Play with Me 페이지에서 다른 예약을 신청해보세요!</p>
                    </body>
                    </html>
                    """, res.getName(), hName, res.getName(), res.getContent());
            // 메일의 내용 설정
            mimeMessageHelper.setText(content, true);
            // 주소 연결
            // <a href="%s" class="button">확인하러 가기</a>

            javaMailSender.send(mimeMessage);

            log.info("예약 거절 메일 발송 성공!");
        } catch (Exception e) {
            log.info("예약 거절 메일 발송 실패!");
            throw new RuntimeException(e);
        }
    }
}

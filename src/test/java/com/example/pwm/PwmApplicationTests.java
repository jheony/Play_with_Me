package com.example.pwm;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.example.pwm.domain.host.HostRepository;
import com.example.pwm.domain.host.Host;
import com.example.pwm.domain.host.HostService;
import com.example.pwm.domain.mail.MailServiceImpl;
import com.example.pwm.domain.reservation.ReservDTO;
import com.example.pwm.domain.reservation.ReservRepository;
import com.example.pwm.domain.reservation.ReservService;
import com.example.pwm.domain.reservation.ReservState;
import com.example.pwm.domain.reservation.Reservation;
import com.example.pwm.domain.schedule.Schedule;
import com.example.pwm.domain.schedule.ScheduleDTO;
import com.example.pwm.domain.schedule.ScheduleRepository;
import com.example.pwm.domain.schedule.ScheduleService;
import com.example.pwm.global.dto.PageRequestDTO;
import com.example.pwm.global.dto.PageResponseDTO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class PwmApplicationTests {
    @Autowired
    private ReservService reservService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private HostService hostService;

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private ReservRepository reservRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailServiceImpl mailService;

    // @Test
    // @DisplayName("회원가입")
    // public void signupTest() {

    // String name = "test";
    // String passwd = "test1234";
    // String email = "test@test.com";

    // // 이메일 중복 체크
    // hostRepository.findByEmail(email).ifPresent(host -> {
    // });

    // // 비밀번호 암호화
    // String hashPw = passwordEncoder.encode(passwd);

    // Host host = Host.builder()
    // .email(email)
    // .passwd(hashPw)
    // .name(name)
    // .build();

    // hostRepository.save(host);

    // }

    // @Test
    // @DisplayName("예약 신청")
    // public void reservTest() {
    // ReservDTO reservDTO = new ReservDTO();
    // for (int i = 0; i < 1; i++) {
    // int month = (i % 12) + 1;
    // int day = (i % 28) + 1;
    // int hour = (i % 10) + 9;
    // LocalDateTime startTime = LocalDateTime.of(2025, month, day, hour, 0);

    // reservDTO.setContent("Content" + i);
    // reservDTO.setEmail("heon__y@naver.com");
    // reservDTO.setLocation("Gwangju" + i);
    // reservDTO.setName("reservTest" + i);
    // reservDTO.setStartTime(startTime);

    // Reservation reservation = modelMapper.map(reservDTO, Reservation.class);

    // if (i > 50) {
    // reservation.setReservState(ReservState.CONFIRMED);

    // }
    // Host host = hostRepository.findById(3L)
    // .orElseThrow(() -> new IllegalArgumentException("해당 호스트가 존재하지 않습니다. ID: " +
    // 3L));
    // reservation.setResHost(host);
    // host.getRes().add(reservation);

    // reservRepository.save(reservation);

    // Long resId = reservation.getId();

    // mailService.sendMailAddReserv(resId);

    // reservService.acceptReserv(resId);
    // mailService.reservAcceptEmailToReserv(resId);

    // reservService.cancelReserv(resId);
    // mailService.reservCancelEmailToReserv(resId);

    // }
    // }

    // @Test
    // @Transactional
    // @DisplayName("예약 수락 시 DB 상태변경과 메일 전송")
    // public void confirmReserv() {
    // Reservation res = reservRepository.findById(1L).orElseThrow();
    // res.setReservState(ReservState.CONFIRMED);
    // reservRepository.save(res);
    // // mailService.reservAcceptEmailToHost(1L,"test@test.com");
    // }

    // @Test
    // @DisplayName("예약 거절 시 DB 삭제")
    // public void canselResTest() {
    //     reservService.cancelReserv(110L);
    //     // mailService.reservCancelEmailToHost(1L, "test@test.com");
    // }

    // @Test
    // @DisplayName("일정추가")
    // public void addSchedule() {
    // ScheduleDTO scheduleDTO = new ScheduleDTO();
    // for (int i = 1; i < 30; i++) {
    // int month = (i%12) +1;
    // int day = (i%28)+1;
    // int hour = (i%10)+9;
    // LocalDateTime startTime = LocalDateTime.of(2025, month, day, hour, 0);

    // scheduleDTO.setContent("testContent"+i);
    // scheduleDTO.setLocation("testSeoul"+i);
    // scheduleDTO.setStartTime(startTime);

    // Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);

    // Host host = hostRepository.findById(1L)
    // .orElseThrow(() -> new IllegalArgumentException("해당 호스트가 존재하지 않습니다. ID: " +
    // 1L));

    // schedule.setScheHost(host);
    // host.getSche().add(schedule);

    // scheduleRepository.save(schedule);
    // }
    // }
    // @Test
    // @DisplayName("일정삭제")
    // public void removeSche(){
    //     scheduleService.removeSchedule(102L);
    // }
}
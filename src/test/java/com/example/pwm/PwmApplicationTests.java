package com.example.pwm;

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
import com.example.pwm.global.dto.PageRequestDTO;
import com.example.pwm.global.dto.PageResponseDTO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class PwmApplicationTests {
    @Autowired
    private ReservService reservService;

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
    // @Transactional
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

    @Test
    @Transactional
    @DisplayName("예약 신청")
    public void reservTest() {
        ReservDTO reservDTO = new ReservDTO();
        reservDTO.setContent("Content");
        reservDTO.setEmail("reservTest@test.com");
        reservDTO.setLocation("gwangju");
        reservDTO.setName("reservTest");
        reservDTO.setStartTime("yyyy-mm-dd");
        Reservation reservation = modelMapper.map(reservDTO, Reservation.class);

        Host host = hostRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("해당 호스트가 존재하지 않습니다. ID: " + 1L));
        reservation.setResHost(host);
        host.getRes().add(reservation);

        reservRepository.save(reservation);

    }

    @Test
    @Transactional
    @DisplayName("예약 수락 시 DB 상태변경과 메일 전송")
    public void confirmReserv() {
        Reservation res = reservRepository.findById(1L).orElseThrow();
        res.setReservState(ReservState.CONFIRMED);
        reservRepository.save(res);
        // mailService.reservAcceptEmailToHost(1L,"test@test.com");
    }

    @Test
    @Transactional
    @DisplayName("예약 거절 시 DB 삭제와 메일 전송")
    public void canselResTest() {
        reservService.cancelReserv(1L);
        // mailService.reservCancelEmailToHost(1L, "test@test.com");
    }

    @Test
    @Transactional
    @DisplayName("일정추가")
    public void addSchedule() {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        scheduleDTO.setContent("Content");
        scheduleDTO.setLocation("seoul");
        scheduleDTO.setStartTime("yyyy-mm-dd");

        Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);

        Host host = hostRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("해당 호스트가 존재하지 않습니다. ID: " + 1L));

        schedule.setScheHost(host);
        host.getSche().add(schedule);

        scheduleRepository.save(schedule);
    }
    // @Test
    // @DisplayName("목록조회")
    // public void testList() {

    // PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
    // .page(1)
    // .size(10)
    // .build();
    // PageResponseDTO<ScheduleDTO> response = hostService.getList(pageRequestDTO);
    // log.info("list: {}",response);
    // }

    // @Test
    // @DisplayName("회원가입 실패 - 이메일 중복")
    // public void signupFailTest(){

    // }

}
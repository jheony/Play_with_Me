package com.example.pwm.domain.host;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.pwm.domain.reservation.ReservDTO;
import com.example.pwm.domain.reservation.ReservRepository;
import com.example.pwm.domain.reservation.Reservation;
import com.example.pwm.domain.schedule.Schedule;
import com.example.pwm.domain.schedule.ScheduleDTO;
import com.example.pwm.domain.schedule.ScheduleRepository;
import com.example.pwm.global.dto.PageRequestDTO;
import com.example.pwm.global.dto.PageResponseDTO;
import com.example.pwm.global.dto.SignRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HostServiceImpl implements HostService {

    private final HostRepository hostRepository;
    private final ScheduleRepository scheduleRepository;
    private final ReservRepository reservRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    // 회원가입 처리
    @Override
    public String join(SignRequest signRequest) {

        // 이메일 중복 체크
        hostRepository.findByEmail(signRequest.getEmail()).ifPresent(host -> {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        });

        // 비밀번호 암호화
        String hashPw = passwordEncoder.encode(signRequest.getPasswd());

        Host host = Host.builder()
                .email(signRequest.getEmail())
                .passwd(hashPw)
                .name(signRequest.getName())
                .build();

        hostRepository.save(host);
        return "success";
    }

    // host 조회
    @Override
    public String get(Long id) {
        Optional<Host> result = hostRepository.findById(id);
        Host host = result.orElseThrow();

        return host.getEmail();
    }

    // host 삭제
    @Override
    public void remove(Long id) {
        Host host = hostRepository.findById(id).orElseThrow();

        host.getRes().clear(); // 예약 리스트 초기화
        host.getSche().clear(); // 일정 리스트 초기화

        hostRepository.deleteById(id);
    }

    // 일정 목록 조회
    @Override
    public PageResponseDTO<ScheduleDTO> getScheList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("startTime").ascending());

        Page<Schedule> result;
        // 날짜 필터 존재 시 조건 검색
        if (pageRequestDTO.getHostId() != null && pageRequestDTO.getStartDate() != null
                && pageRequestDTO.getEndDate() != null) {
            result = scheduleRepository.findByScheHostIdAndStartTimeBetween(
                    pageRequestDTO.getHostId(),
                    pageRequestDTO.getStartDate(),
                    pageRequestDTO.getEndDate(),
                    pageable);
        } else if (pageRequestDTO.getHostId() != null) {
            result = scheduleRepository.findByScheHostId(
                    pageRequestDTO.getHostId(),
                    pageable);
        } else {
            // 전체 조회
            result = scheduleRepository.findAll(pageable);
        }

        List<ScheduleDTO> dtoList = result.getContent().stream()
                .map(sche -> modelMapper.map(sche, ScheduleDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<ScheduleDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCnt(result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<ReservDTO> getReservList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("startTime").ascending());

        Page<Reservation> result;

        // id+상태+시작+끝 날짜
        if (pageRequestDTO.getHostId() != null &&
                pageRequestDTO.getStartDate() != null &&
                pageRequestDTO.getEndDate() != null &&
                pageRequestDTO.getReservState() != null) {

            result = reservRepository.findByResHostIdAndReservStateAndStartTimeBetween(
                    pageRequestDTO.getHostId(),
                    pageRequestDTO.getReservState(),
                    pageRequestDTO.getStartDate(),
                    pageRequestDTO.getEndDate(),
                    pageable);

        } else if (pageRequestDTO.getHostId() != null &&
                pageRequestDTO.getReservState() != null) {

            result = reservRepository.findByResHostIdAndReservState(
                    pageRequestDTO.getHostId(),
                    pageRequestDTO.getReservState(),
                    pageable);

        } else if (pageRequestDTO.getHostId() != null) {

            result = reservRepository.findByResHostId(
                    pageRequestDTO.getHostId(),
                    pageable);

        } else {
            // 전체 조회
            result = reservRepository.findAll(pageable);
        }

        List<ReservDTO> dtoList = result.getContent().stream()
                .map(reserv -> modelMapper.map(reserv, ReservDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<ReservDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCnt(result.getTotalElements())
                .build();
    }
}
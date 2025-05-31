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
        hostRepository.deleteById(id);
    }

    // 일정 목록 조회
    @Override
    public PageResponseDTO<ScheduleDTO> getScheList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("id").ascending());

        Page<Schedule> result = scheduleRepository.findAll(pageable);

        List<ScheduleDTO> dtoList = result.getContent().stream().map(sche -> modelMapper.map(sche, ScheduleDTO.class)).collect(Collectors.toList());

        long totalCnt = result.getTotalElements();

        PageResponseDTO<ScheduleDTO> responseDTO = PageResponseDTO.<ScheduleDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCnt(totalCnt)
                .build();

        return responseDTO;

    }

    // 예약 목록 조회
    @Override
    public PageResponseDTO<ReservDTO> getReservList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("id").ascending());

        Page<Reservation> result = reservRepository.findAll(pageable);

        List<ReservDTO> dtoList = result.getContent().stream()
                .map(reserv -> modelMapper.map(reserv, ReservDTO.class))
                .collect(Collectors.toList());

        long totalCnt = result.getTotalElements();

        PageResponseDTO<ReservDTO> responseDTO = PageResponseDTO.<ReservDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCnt(totalCnt)
                .build();

        return responseDTO;
    }
}
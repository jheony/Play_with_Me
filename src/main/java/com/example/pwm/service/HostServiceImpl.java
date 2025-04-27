package com.example.pwm.service;

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

import com.example.pwm.controller.dto.HostDTO;
import com.example.pwm.controller.dto.PageRequestDTO;
import com.example.pwm.controller.dto.PageResponseDTO;
import com.example.pwm.controller.dto.SignRequest;
import com.example.pwm.repository.HostRepository;
import com.example.pwm.repository.entity.Host;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HostServiceImpl implements HostService {

    private final HostRepository hostRepository;
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
    // 등록
    @Override
    public Long register(HostDTO hostDTO) {
        Host host = modelMapper.map(hostDTO, Host.class);
        Host saveHost = hostRepository.save(host);

        return saveHost.getId();
    }

    // host 조회
    @Override
    public HostDTO get(Long id) {
        Optional<Host> result = hostRepository.findById(id);
        Host host = result.orElseThrow();
        HostDTO hostDTO = modelMapper.map(host, HostDTO.class);

        return hostDTO;
    }

    // host 수정
    @Override
    public void modify(HostDTO hostDTO) {
        Host host = hostRepository.findById(hostDTO.getId()).orElseThrow();
        host.changeName(hostDTO.getName());
        host.changePw(hostDTO.getPasswd());
        hostRepository.save(host);
    }

    // host 삭제
    @Override
    public void remove(Long id) {
        hostRepository.deleteById(id);
    }

    // 목록 조회
    @Override
    public PageResponseDTO<HostDTO> list(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("id").descending());

        Page<Host> result = hostRepository.findAll(pageable);

        List<HostDTO> dtoList = result.getContent().stream()
                .map(host -> modelMapper.map(host, HostDTO.class))
                .collect(Collectors.toList());

        long totalCnt = result.getTotalElements();

        PageResponseDTO<HostDTO> responseDTO = PageResponseDTO.<HostDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCnt(totalCnt)
                .build();

        return responseDTO;

    }
}
package com.example.pwm.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.pwm.controller.dto.HostDTO;
import com.example.pwm.controller.dto.SignRequest;
import com.example.pwm.repository.HostRepository;
import com.example.pwm.repository.entity.Host;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HostServiceImpl implements HostService{

    private final HostRepository hostRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public Long register(HostDTO hostDTO){
        Host host = modelMapper.map(hostDTO, Host.class);
        Host saveHost = hostRepository.save(host);

        return saveHost.getId();

    }
    @Override
    public HostDTO get(Long id){
        Optional<Host> result = hostRepository.findById(id);
        Host host = result.orElseThrow();
        HostDTO hostDTO = modelMapper.map(host, HostDTO.class);

        return hostDTO;
    }

    @Override
    public void modify(HostDTO hostDTO){
        Optional<Host> result = hostRepository.findById(hostDTO.getId());

        Host host = result.orElseThrow();

        host.changeName(hostDTO.getName());
        host.changePw(hostDTO.getPasswd());

        hostRepository.save(host);
    }
    @Override
    public void remove(Long id){
        hostRepository.deleteById(id);
    }
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

    @Override
    public String login(SignRequest signRequest){
        Host host = hostRepository.findByEmail(signRequest.getEmail()).orElseThrow(() -> new BadCredentialsException("잘못된 이메일입니다."));      

        if (!passwordEncoder.matches(signRequest.getPasswd(), host.getPasswd())) {
            throw new BadCredentialsException("잘못된 비밀번호입니다.");
        }

        return "Success";
    } 
}
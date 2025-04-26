package com.example.pwm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pwm.controller.dto.SignRequest;
import com.example.pwm.service.HostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HostController {

    private final HostService hostService;

    // 회원가입
    @PostMapping("api/signup")
    public ResponseEntity<String> signup(@RequestBody SignRequest signRequest) throws Exception {

        hostService.join(signRequest);

        return ResponseEntity.ok().body("회원가입 성공");
    }

    // 로그인 -> LoginForm으로
    // @PostMapping("api/host/signin")
    // public ResponseEntity<String> login(@RequestBody SignRequest signRequest) {

    //     hostService.login(signRequest);

    //     return ResponseEntity.ok().body("로그인 성공");
    // }

}

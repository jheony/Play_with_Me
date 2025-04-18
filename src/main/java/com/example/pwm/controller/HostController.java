package com.example.pwm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pwm.controller.dto.JoinRequest;
import com.example.pwm.service.HostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HostController {

    private final HostService hostService;

    // 회원가입 페이지
    @PostMapping("/signup")
    public ResponseEntity<String> join(@RequestBody JoinRequest joinRequest) throws Exception {
        hostService.join(joinRequest);
        return ResponseEntity.ok().body("회원가입 성공");
    }
}

package com.example.pwm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pwm.controller.dto.JoinRequest;
import com.example.pwm.service.HostService;

import lombok.RequiredArgsConstructor;

@Controller
@RestController
@RequiredArgsConstructor
public class HostController {

    private final HostService hostService;

    @GetMapping("/hello")
    public String getHello() {
        return "hello";
    }

    // 회원가입 페이지
    @PostMapping("/join")
    public String join(@RequestBody JoinRequest joinRequest) {
        String result = hostService.join(joinRequest);
        if ("success".equalsIgnoreCase(result)) {
            return "/hello";
        } else {
            return "fail";
        }
    }

}

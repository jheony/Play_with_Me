package com.example.pwm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pwm.service.MailServiceImpl;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailServiceImpl mailService;

    @GetMapping("/simple")
    public void sendSimpleMailMessage() {
    }
 
}
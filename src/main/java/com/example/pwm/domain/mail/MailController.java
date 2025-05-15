package com.example.pwm.domain.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailServiceImpl mailService;

    @GetMapping("/simple")
    public void sendSimpleMailMessage() {
    }
 
}
package com.example.pwm.domain.host;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pwm.global.dto.SignRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HostController {

    private final HostService hostService;

    // 회원가입 처리
    @PostMapping("/api/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody SignRequest signRequest) {
        hostService.join(signRequest);
        return ResponseEntity.ok(Map.of("message", "회원가입 성공"));
    }

}

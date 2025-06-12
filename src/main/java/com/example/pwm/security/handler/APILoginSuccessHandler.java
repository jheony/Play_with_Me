package com.example.pwm.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.pwm.domain.host.Host;
import com.example.pwm.domain.host.HostDTO;
import com.example.pwm.global.util.JWTUtil;
import com.google.gson.Gson;

import org.springframework.security.core.Authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        Host host = (Host) authentication.getPrincipal(); // Host 객체 사용

        Map<String, Object> claims = host.getClaims(); // Host에서 직접 claim 생성

        String accessToken = JWTUtil.generateToken(claims, 10);
        String refreshToken = JWTUtil.generateToken(claims, 60 * 24);

        claims.put("accessToken", accessToken);
        claims.put("refreshToken", refreshToken);

        response.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
    }
}

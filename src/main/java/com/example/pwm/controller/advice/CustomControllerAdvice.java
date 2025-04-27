package com.example.pwm.controller.advice;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.pwm.util.CustomJWTException;

 // 예외 처리 클래스
@RestControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<?> noExist(NoSuchElementException e) {
        String msg = e.getMessage();
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(Map.of("msg", msg));
    }

    //요청값이 잘못된 경우 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> handleIllegalArgumentException(MethodArgumentNotValidException e) {
        String msg = e.getMessage();
        return ResponseEntity
            .status(HttpStatus.NOT_ACCEPTABLE)
            .body(Map.of("msg", msg));
    }

    // JWT 관련 예외 처리
    @ExceptionHandler(CustomJWTException.class)
    protected ResponseEntity<?> handleJWTException(CustomJWTException e) {
        String msg = e.getMessage();
        return ResponseEntity
            .ok()
            .body(Map.of("error", msg));
    }
}

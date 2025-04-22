package com.example.pwm.service;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.pwm.controller.dto.HostDTO;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class) 
public class HostServiceTests {

    @Autowired
    private HostService hostService;

    @Test 
    @Order(1)
    public void testRegister(){
        HostDTO hostDTO = HostDTO.builder()
        .email("Test@test.com")
        .name("TEST")
        .passwd("test1234")
        .build();

        Long id = hostService.register(hostDTO);   
    }
    @Test
    public void testGet(){
        Long id = 1L;
        HostDTO hostDTO = hostService.get(id);
    }
}

// package com.example.pwm.service;

// import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
// import org.junit.jupiter.api.Order;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestMethodOrder;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// import com.example.pwm.controller.dto.HostDTO;
// import com.example.pwm.controller.dto.PageRequestDTO;
// import com.example.pwm.controller.dto.PageResponseDTO;

// import lombok.extern.slf4j.Slf4j;

// @Slf4j
// @SpringBootTest
// @TestMethodOrder(OrderAnnotation.class)
// public class HostServiceTests {

//     @Autowired
//     private HostService hostService;

//     @Test
//     @Order(1)
//     public void testRegister() {
//         HostDTO hostDTO = HostDTO.builder()
//                 .email("Test@test.com")
//                 .name("TEST")
//                 .passwd("test1234")
//                 .build();

//         Long id = hostService.register(hostDTO);
//         log.info("id {}", id);
//     }

//     @Test
//     public void testGet() {
//         Long id = 1L;
//         HostDTO hostDTO = hostService.get(id);
//         log.info("hostDTO {}", hostDTO);

//     }

//     @Test
//     public void testList() {

//         PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
//                 .page(2)
//                 .size(10)
//                 .build();

//         PageResponseDTO<HostDTO> response = hostService.list(pageRequestDTO);
//         log.info("response {}", response);
//     }
// }

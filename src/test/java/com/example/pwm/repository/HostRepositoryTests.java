package com.example.pwm.repository;

import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.pwm.repository.entity.Host;
import com.example.pwm.repository.entity.HostRole;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class HostRepositoryTests {
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Order(1)
    public void testInsert() {
        for (int i = 0; i <= 10; i++) {
            Host host = Host.builder()
                    .name("test"+i)
                    .email("test" + i + "@test.com")
                    .passwd(passwordEncoder.encode("test1234"))
                    .build();
            host.addRole(HostRole.USER);

            if(i>=3){
                host.addRole(HostRole.ADMIN);
            }
            hostRepository.save(host);
        }
        String email = "test4@test.com";
        Host host = hostRepository.getWithRoles(email);
        log.info("-------------------------------");
        log.info("{}",host);
    }

    @Test
    public void testRead() {
        Long id = 33L;
        Optional<Host> result = hostRepository.findById(id);
        Host host = result.orElseThrow();
        log.info("host{}", host);
    }

    @Test
    public void testModify() {
        Long id = 33L;
        Optional<Host> result = hostRepository.findById(id);
        Host host = result.orElseThrow();
        host.changeName("TEST 33");
        host.changePw("TEST!!!!");

        hostRepository.save(host);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        hostRepository.deleteById(id);
    }

    @Test
    public void testPaging() {
        for (int i = 0; i <= 100; i++) {
            Host host = Host.builder()
                    .name("test")
                    .email("test" + i + "@test.com")
                    .passwd("test1234")
                    .build();

            hostRepository.save(host);
        }
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());

        Page<Host> result = hostRepository.findAll(pageable);
        log.info("result{}", result.getTotalElements());
        result.getContent().stream().forEach(host -> log.info("host{}", host));
    }
}

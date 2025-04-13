package com.example.pwm.host.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.pwm.repository.HostRepository;
import com.example.pwm.repository.entity.Host;

@DataJpaTest
public class HostRepositoryTest {
    @Autowired
    private HostRepository hostRepository;

    @Test
    public void crudTest() {
        Host host = Host.builder()
                .name("플래처")
                .email("test1@gmail.com")
                .passwd("test1234")
                .build();
        // create test
        hostRepository.save(host);

        // get test
        Host foundHost = hostRepository.findById(4L).get();
        System.out.println("등록된 호스트 이름: " + (foundHost != null ? foundHost.getName() : "없음"));
    }

}

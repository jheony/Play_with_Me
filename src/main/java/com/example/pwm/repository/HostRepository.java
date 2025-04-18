package com.example.pwm.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pwm.repository.entity.Host;

public interface HostRepository extends JpaRepository<Host, Long> {

    Optional<Host> findByEmail(String email);
}
package com.example.pwm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.pwm.repository.entity.Host;

public interface HostRepository extends JpaRepository<Host, Long> {

    Optional<Host> findByEmail(String email);

    // 이메일로 회원 정보 조회
    @EntityGraph(attributePaths = {"hostRoleList"})
    @Query("select m from Host m where m.email = :email")
    Host getWithRoles(@Param("email") String email); 
}
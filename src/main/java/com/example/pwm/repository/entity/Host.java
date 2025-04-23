package com.example.pwm.repository.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "host")
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "h_idx")
    private Long id;

    @Column(nullable = false, length = 20, name = "h_name")
    private String name;

    @Column(nullable = false, unique = true, length = 30, name = "h_email")
    private String email;

    @Column(nullable = false, name = "h_passwd")
    private String passwd;

    @CreationTimestamp
    private LocalDate createdAt;

    public void changeName(String name) { // 이름 수정
        this.name = name;
    }

    public void changePw(String passwd) { // 비밀번호 수정
        this.passwd = passwd;
    }
}
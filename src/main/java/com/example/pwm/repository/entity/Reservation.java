package com.example.pwm.repository.entity;

import java.time.LocalDate;
import java.util.*;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reservation")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_idx")
    private Long id;

    @Column(nullable = false, length = 20, name = "r_name")
    private String name;

    @Column(nullable = false, name = "r_content")
    private String content;

    @Column(nullable = false, length = 30, name = "r_email")
    private String email;

    @Column(nullable = false, name = "r_start")
    private String startTime;

    @Column(name = "r_end")
    private String endTime;

    @Column(nullable = false, name = "r_location")
    private String location;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<ReservState> reservStateList = new ArrayList<>();

    // public void addRole(ReservState reservState) {
    //     reservStateList.add(reservState);
    // }

    // public void clearRole() {
    //     reservStateList.clear();
    // }

    @CreationTimestamp
    private LocalDate createdAt;

    // todo: 예약 수정 or 취소
    // public void changeName(String name) { // 이름 수정
    //     this.name = name;
    // }

    // public void changePw(String passwd) { // 비밀번호 수정
    //     this.passwd = passwd;
    // }
}
package com.example.pwm.domain.reservation;

import java.time.LocalDate;
import java.util.*;

import org.hibernate.annotations.CreationTimestamp;

import com.example.pwm.domain.host.Host;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reservation")
@Getter
@Setter
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
    
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ReservState reservState = ReservState.PENDING;

    @ManyToOne
    @JoinColumn(name = "h_id")
    private Host host;
    // public void addRole(ReservState reservState) {
    // reservStateList.add(reservState);
    // }

    // public void clearRole() {
    // reservStateList.clear();
    // }

    @CreationTimestamp
    private LocalDate createdAt;
}
package com.example.pwm.domain.schedule;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.example.pwm.domain.host.Host;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "schedule")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "s_idx")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "h_id")
    private Host scheHost;

    @Column(nullable = false, name = "s_content")
    private String content;

    @Column(nullable = false, name = "s_start")
    private String startTime;

    @Column(name = "s_end")
    private String endTime;

    @Column(nullable = false, name = "s_location")
    private String location;

    @CreationTimestamp
    private LocalDate createdAt;
}
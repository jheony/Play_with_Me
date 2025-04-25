package com.example.pwm.repository.entity;

import java.time.LocalDate;
import java.util.*;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "host")
@Getter
@Builder
@ToString(exclude = "hostRoleList")
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

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<HostRole> hostRoleList = new ArrayList<>();

    public void addRole(HostRole hostRole) {
        hostRoleList.add(hostRole);
    }

    public void clearRole() {
        hostRoleList.clear();
    }

    @CreationTimestamp
    private LocalDate createdAt;

    public void changeName(String name) { // 이름 수정
        this.name = name;
    }

    public void changePw(String passwd) { // 비밀번호 수정
        this.passwd = passwd;
    }
}
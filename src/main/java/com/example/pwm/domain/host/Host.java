package com.example.pwm.domain.host;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.pwm.domain.reservation.Reservation;
import com.example.pwm.domain.schedule.Schedule;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "host")
@Getter
@Builder
@ToString(exclude = "hostRoleList")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Host implements UserDetails {

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

    @ElementCollection(fetch = FetchType.EAGER)
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

    @Builder.Default
    @OneToMany(mappedBy = "resHost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> res = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "scheHost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> sche = new ArrayList<>();

    @Override // 권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    // 사용자의 id를 반환(고유한 값)
    @Override
    public String getUsername() {
        return email;
    }

    // 사용자의 패스워드 반환
    @Override
    public String getPassword() {
        return passwd;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았ㅇ므
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true->잠금되지 않았음
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true->사용 가능
    }

    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("email", email);
        dataMap.put("name", name);
        dataMap.put("roleNames", hostRoleList.stream()
                .map(role -> role.name())
                .collect(Collectors.toList()));
        return dataMap;
    }

    @Override
    public String toString() {
        return "Host{id=" + id + ", name=" + name + ", email=" + email + "}";
    }

}
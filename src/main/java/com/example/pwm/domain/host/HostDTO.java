package com.example.pwm.domain.host;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HostDTO extends User {

    private Long id;
    private String email;
    private String name;
    private List<String> roleNames = new ArrayList<>();
    
    public HostDTO(String email, String name, List<String> roleNames) {
        super(
            email,
            name,
            roleNames.stream()
                .map(str -> new SimpleGrantedAuthority("ROLE_" + str))
                .collect(Collectors.toList())
        );
        this.email = email;
        this.name = name;
        this.roleNames = roleNames;
    }

    //JWT 문자열 생성 시 사용
    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("email", email);
        dataMap.put("name", name);
        dataMap.put("roleNames", roleNames);
        return dataMap;
    }
    
}

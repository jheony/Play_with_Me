package com.example.pwm.domain.host;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HostDTO extends User {
    @Schema(hidden = true)
    private Long id;

    private String email;
    
    private String name;

    private String passwd;
    
    @Schema(hidden = true)
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
    
}

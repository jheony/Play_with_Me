package com.example.pwm.controller.dto;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class HostDTO extends User{
    private Long id;
    private String email;
    private String passwd;
    private String name;
    private List<String> roleNames = new ArrayList<>();
    
    public HostDTO(String email, String passwd, String name, List<String> roleNames){
        super(
            email,
            passwd,
            roleNames.stream().map(str -> new SimpleGrantedAuthority("ROLE_"+str)).collect(Collectors.toList())
        );
        this.email = email;
        this.passwd = passwd;
        this.name = name;
        this.roleNames = roleNames;
    }
    
    //JWT 문자열 생성 시 사용
    public Map<String, Object> getClaims(){
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("email", email);
        dataMap.put("passwd", passwd);
        dataMap.put("name", name);
        dataMap.put("roleNames", roleNames);

        return dataMap;
    }
}

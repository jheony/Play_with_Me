package com.example.pwm.domain.reservation;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReservDTO {

    private Long id;
    private String name;
    private String content;
    private String email;

    private String startTime;
    private String endTime;
    private String location;

    private List<String> reservState = new ArrayList<>();    
}

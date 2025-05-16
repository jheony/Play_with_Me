package com.example.pwm.domain.reservation;

import com.example.pwm.domain.host.Host;

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

    private ReservState reservState = ReservState.PENDING;

    private Host host;
 
}

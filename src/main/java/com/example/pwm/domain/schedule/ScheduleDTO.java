package com.example.pwm.domain.schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.pwm.domain.host.Host;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class ScheduleDTO {
    @Schema(hidden = true)
    private Long id;

    private String content;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
}

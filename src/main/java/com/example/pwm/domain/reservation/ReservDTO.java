package com.example.pwm.domain.reservation;

import java.time.LocalDateTime;

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
public class ReservDTO {
    @Schema(hidden = true)
    private Long id;
    @Schema(hidden = true)
    private Long hostId;

    private String name;
    private String content;
    private String email;
    private LocalDateTime startTime;
    private String location;

    @Schema(hidden = true)
    private ReservState reservState = ReservState.PENDING;
}

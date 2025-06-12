package com.example.pwm.global.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.pwm.domain.reservation.ReservState;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    @Nullable
    private ReservState reservState;

    @Nullable
    private String yearMonth;

    @Schema(hidden = true)
    private LocalDateTime startDate;

    @Schema(hidden = true)
    private LocalDateTime endDate;

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;

        YearMonth ym = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"));

        this.startDate = ym.atDay(1).atStartOfDay();
        this.endDate = ym.atEndOfMonth().atTime(23, 59, 59); 
    }

    
    @Schema(hidden = true)
    private Long hostId;
}

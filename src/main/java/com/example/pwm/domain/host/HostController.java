package com.example.pwm.domain.host;

import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pwm.domain.reservation.ReservDTO;
import com.example.pwm.domain.schedule.ScheduleDTO;
import com.example.pwm.global.dto.PageRequestDTO;
import com.example.pwm.global.dto.PageResponseDTO;
import com.example.pwm.global.dto.SignRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HostController {

    private final HostService hostService;
    private final AuthenticationManager authenticationManager;

    // 회원가입
    @PostMapping("/api/host/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody SignRequest signRequest) {
        hostService.join(signRequest);
        return ResponseEntity.ok(Map.of("message", "회원가입 성공"));
    }

    // 탈퇴
    @DeleteMapping("/api/host/{hostId}/delete")
    public ResponseEntity<Map<String, String>> signout(@PathVariable Long hostId) {
        hostService.remove(hostId);
        return ResponseEntity.ok(Map.of("message", "탈퇴 성공"));
    }

    @GetMapping("api/host/{hostId}/scheList")
    public PageResponseDTO<ScheduleDTO> scheduleList(@PathVariable(value = "hostId") Long hostId,
            PageRequestDTO pageRequestDTO,
            Model model) {

        log.info("list............." + pageRequestDTO);

        model.addAttribute("result", hostService.getScheList(pageRequestDTO));

        return hostService.getScheList(pageRequestDTO);
    }

    @GetMapping("api/host/{hostId}/reservList")
    public PageResponseDTO<ReservDTO> reservList(@PathVariable(value = "hostId") Long hostId,
            PageRequestDTO pageRequestDTO,
            Model model) {
        pageRequestDTO.setHostId(hostId);

        log.info("list............." + pageRequestDTO);

        model.addAttribute("result", hostService.getReservList(pageRequestDTO));

        return hostService.getReservList(pageRequestDTO);
    }
}

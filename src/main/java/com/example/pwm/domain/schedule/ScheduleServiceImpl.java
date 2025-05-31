package com.example.pwm.domain.schedule;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.pwm.domain.host.Host;
import com.example.pwm.domain.host.HostRepository;
import com.example.pwm.domain.reservation.Reservation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ModelMapper modelMapper;
    private final ScheduleRepository scheduleRepository;
    private final HostRepository hostRepository;

    @Override
    public Long addSchedule(ScheduleDTO scheduleDTO, Long hostId) {
        Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);

        Host host = hostRepository.findById(hostId)
                .orElseThrow(() -> new IllegalArgumentException("해당 호스트가 존재하지 않습니다. ID: " + hostId));

        schedule.setScheHost(host);
        host.getSche().add(schedule);

        Schedule saveSche = scheduleRepository.save(schedule);

        return saveSche.getId();
    }

    @Override
    public String removeSchedule(Long scheduleId) {
        Schedule sche = scheduleRepository.findById(scheduleId).orElseThrow();
        scheduleRepository.delete(sche);

        return "일정이 삭제되었습니다.";
    }

    @Override
    public ScheduleDTO getSchedule(Long scheduleId) {
        Schedule sche = scheduleRepository.findById(scheduleId).orElseThrow();
        ScheduleDTO scheduleDTO = modelMapper.map(sche, ScheduleDTO.class);

        return scheduleDTO;
    }
}

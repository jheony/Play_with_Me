package com.example.pwm.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.pwm.controller.dto.ReservDTO;
import com.example.pwm.repository.ReservRepository;
import com.example.pwm.repository.entity.Reservation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservServiceImpl implements ReservService{

    private final ReservRepository reservRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long register(ReservDTO reservDTO){
        Reservation reservation = modelMapper.map(reservDTO, Reservation.class);
        Reservation saveReserv = reservRepository.save(reservation);

        return saveReserv.getId();
    }

    @Override
    public ReservDTO get(Long id){
        Optional<Reservation> result = reservRepository.findById(id);
        Reservation reserv = result.orElseThrow();
        ReservDTO reservDTO = modelMapper.map(reserv, ReservDTO.class);

        return reservDTO;
    }

}

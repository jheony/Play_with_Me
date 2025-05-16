package com.example.pwm.domain.reservation;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.pwm.domain.host.Host;
import com.example.pwm.domain.host.HostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservServiceImpl implements ReservService {

    private final ReservRepository reservRepository;
    private final HostRepository hostRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long register(ReservDTO reservDTO, Long hostId) {
        Reservation reservation = modelMapper.map(reservDTO, Reservation.class);

        Host host = hostRepository.findById(hostId)
                .orElseThrow(() -> new IllegalArgumentException("해당 호스트가 존재하지 않습니다. ID: " + hostId));

        reservation.setHost(host);
        host.getRes().add(reservation);

        Reservation saveReserv = reservRepository.save(reservation);

        return saveReserv.getId();
    }

    @Override
    public String acceptReserv(Long resId) {
        Reservation res = reservRepository.findById(resId).orElseThrow();
        res.setReservState(ReservState.CONFIRMED);
        reservRepository.save(res);
        return "예약이 수락되었습니다.";
    }

    @Override
    public String cancelReserv(Long resId) {
        Reservation res = reservRepository.findById(resId).orElseThrow();
        reservRepository.delete(res);
        return "예약이 거절되었습니다.";
    }

    @Override
    public ReservDTO get(Long id) {
        Optional<Reservation> result = reservRepository.findById(id);
        Reservation reserv = result.orElseThrow();
        ReservDTO reservDTO = modelMapper.map(reserv, ReservDTO.class);

        return reservDTO;
    }

}

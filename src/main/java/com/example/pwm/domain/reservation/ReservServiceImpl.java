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

    // 새로운 예약 신청
    @Override
    public Long addReserv(ReservDTO reservDTO, Long hostId) {
        Reservation reservation = modelMapper.map(reservDTO, Reservation.class);

        Host host = hostRepository.findById(hostId)
                .orElseThrow(() -> new IllegalArgumentException("해당 호스트가 존재하지 않습니다. ID: " + hostId));

        reservation.setResHost(host);
        host.getRes().add(reservation);

        Reservation saveReserv = reservRepository.save(reservation);

        return saveReserv.getId();
    }

    // 예약 수락
    @Override
    public String acceptReserv(Long resId) {
        Reservation res = reservRepository.findById(resId).orElseThrow();
        res.setReservState(ReservState.CONFIRMED);
        reservRepository.save(res);
        return "예약이 수락되었습니다.";
    }

    // 예약 거절
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

    @Override
    public String getHostEmail(Long resId) {
        Reservation res = reservRepository.findById(resId).orElseThrow();
        Host host = res.getResHost();

        return host.getEmail();
    }
}

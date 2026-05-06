package com.github.valentinary.cinemabooking.service;

import com.github.valentinary.cinemabooking.dto.SeatDto;
import com.github.valentinary.cinemabooking.dto.SeatStatus;
import com.github.valentinary.cinemabooking.entity.Seat;
import com.github.valentinary.cinemabooking.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final ReservationSeatRepository reservationSeatRepository;

    public List<SeatDto> getSeatsForSession(Long sessionId) {
        if (sessionId == null || sessionId <= 0) {
            throw new IllegalArgumentException("Session id is incorrect: " + sessionId);
        }
        List<Seat> allSeats = seatRepository.findAllBySessionId(sessionId);
        if (CollectionUtils.isEmpty(allSeats)) {
            return Collections.emptyList();
        }
        List<SeatReservationProjection> reservations = reservationSeatRepository.findReservedBySessionId(sessionId);
        LocalDateTime now = LocalDateTime.now();
        Set<Long> reservedSeatIds = reservations.stream()
                .filter(r -> r.isReserved(now))
                .map(SeatReservationProjection::getSeatId)
                .collect(Collectors.toSet());
        return allSeats.stream()
                .map(s -> SeatDto.builder()
                        .id(s.getId())
                        .row(s.getSeatRow())
                        .num(s.getSeatNum())
                        .status(reservedSeatIds.contains(s.getId()) ? SeatStatus.BOOKED : SeatStatus.AVAILABLE)
                        .build())
                .toList();
    }
}

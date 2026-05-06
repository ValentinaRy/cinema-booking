package com.github.valentinary.cinemabooking.service;

import com.github.valentinary.cinemabooking.entity.Reservation;
import com.github.valentinary.cinemabooking.entity.ReservationSeat;
import com.github.valentinary.cinemabooking.entity.ReservationStatus;
import com.github.valentinary.cinemabooking.repository.ReservationRepository;
import com.github.valentinary.cinemabooking.repository.ReservationSeatRepository;
import com.github.valentinary.cinemabooking.repository.SeatReservationProjection;
import com.github.valentinary.cinemabooking.repository.SessionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {
    private static final int RESERVATION_TTL_MINUTES = 20;
    private final ReservationRepository reservationRepository;
    private final ReservationSeatRepository reservationSeatRepository;

    @Transactional
    public Long createReservation(Long sessionId, Long userId, List<Long> seatIds) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reservedUntil = now.plusMinutes(RESERVATION_TTL_MINUTES);
        List<SeatReservationProjection> reservedBySessionId = reservationSeatRepository.findReservedBySessionId(sessionId);
        if (reservedBySessionId.stream()
                .filter(seat -> seat.isReserved(now))
                .map(SeatReservationProjection::getSeatId)
                .anyMatch(seatIds::contains)) {
            throw new IllegalArgumentException("Seats are not available");
        }
        Reservation reservation = reservationRepository.save(Reservation.builder()
                .sessionId(sessionId)
                .reservedBy(userId)
                .reservedUntil(reservedUntil)
                .status(ReservationStatus.PENDING)
                .build());
        reservationSeatRepository.saveAll(
                seatIds.stream()
                        .map(seatId -> ReservationSeat.builder()
                                .reservationId(reservation.getId())
                                .seatId(seatId)
                                .build())
                        .toList());
        return reservation.getId();
    }

    public void confirmReservation(Long id) {

    }

    public void cancelReservation(Long id) {

    }
}

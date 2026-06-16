package com.github.valentinary.cinemabooking.service;

import com.github.valentinary.cinemabooking.entity.Reservation;
import com.github.valentinary.cinemabooking.entity.ReservationSeat;
import com.github.valentinary.cinemabooking.entity.ReservationStatus;
import com.github.valentinary.cinemabooking.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {
    private static final int RESERVATION_TTL_MINUTES = 20;
    private final ReservationRepository reservationRepository;
    private final ReservationSeatRepository reservationSeatRepository;
    private final SessionRepository sessionRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public Long createReservation(Long sessionId, Long userId, List<Long> seatIds) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reservedUntil = now.plusMinutes(RESERVATION_TTL_MINUTES);
        if (!sessionRepository.existsById(sessionId)) {
            throw new IllegalArgumentException("Session id not found: " + sessionId);
        }
        if (!seatIds.stream().allMatch(seatRepository::existsById)) {
            throw new IllegalArgumentException("Some of the seats not found: " + seatIds);
        }
        List<SeatReservationProjection> reservedBySessionId = reservationSeatRepository.findReservedBySessionId(sessionId);
        if (reservedBySessionId.stream()
                .filter(seat -> seat.isReserved(now))
                .map(SeatReservationProjection::getSeatId)
                .anyMatch(seatIds::contains)) {
            throw new IllegalArgumentException("Seats are not available");
        }
        Reservation reservation = reservationRepository.save(Reservation.builder()
                .reservedBy(userId)
                .reservedUntil(reservedUntil)
                .status(ReservationStatus.PENDING)
                .build());
        try {
            reservationSeatRepository.saveAll(
                    seatIds.stream()
                            .map(seatId -> ReservationSeat.builder()
                                    .reservationId(reservation.getId())
                                    .sessionId(sessionId)
                                    .seatId(seatId)
                                    .build())
                            .toList());
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Seats are already reserved");
        }
        return reservation.getId();
    }

    @Transactional
    public void confirmReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation id not found: " + id));
        if (reservation.getStatus().equals(ReservationStatus.CANCELED)) {
            throw new IllegalArgumentException("Reservation already cancelled");
        }
        if (reservation.getReservedUntil().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Reservation expired");
        }
        reservation.setStatus(ReservationStatus.DONE);
    }

    public void cancelReservation(Long id) {

    }
}

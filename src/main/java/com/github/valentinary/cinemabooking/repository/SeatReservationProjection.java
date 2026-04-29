package com.github.valentinary.cinemabooking.repository;

import com.github.valentinary.cinemabooking.entity.ReservationStatus;

import java.time.LocalDateTime;

public interface SeatReservationProjection {
    Long getSeatId();
    ReservationStatus getStatus();
    LocalDateTime getReservedUntil();
}

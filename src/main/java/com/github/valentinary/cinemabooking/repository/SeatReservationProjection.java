package com.github.valentinary.cinemabooking.repository;

import com.github.valentinary.cinemabooking.entity.ReservationStatus;

import java.time.LocalDateTime;

public interface SeatReservationProjection {
    Long getSeatId();
    ReservationStatus getStatus();
    LocalDateTime getReservedUntil();

    default boolean isReserved(LocalDateTime now) {
        return ReservationStatus.DONE.equals(this.getStatus())
                || ReservationStatus.PENDING.equals(this.getStatus()) && now.isBefore(this.getReservedUntil());
    }
}

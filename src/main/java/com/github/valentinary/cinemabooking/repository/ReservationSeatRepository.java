package com.github.valentinary.cinemabooking.repository;

import com.github.valentinary.cinemabooking.entity.ReservationSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationSeatRepository extends JpaRepository<ReservationSeat, Long> {
    @Query("""
        SELECT rs.seatId AS seatId,
                r.status AS status,
                r.reservedUntil AS reservedUntil
        FROM Reservation r
        JOIN ReservationSeat rs ON rs.reservationId = r.id
        WHERE r.sessionId = :sessionId
    """)
    List<SeatReservationProjection> findBySessionId(
            @Param("sessionId") Long sessionId);
}

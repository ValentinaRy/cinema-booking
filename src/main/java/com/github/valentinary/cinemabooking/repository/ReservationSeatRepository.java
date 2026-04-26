package com.github.valentinary.cinemabooking.repository;

import com.github.valentinary.cinemabooking.entity.ReservationSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationSeatRepository extends JpaRepository<ReservationSeat, Long> {
}
